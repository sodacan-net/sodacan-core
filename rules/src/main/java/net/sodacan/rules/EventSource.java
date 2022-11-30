package net.sodacan.rules;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.io.ResourceFactory;

import net.sodacan.rules.config.Config;

//import net.sodacan.strip.StripService;

public class EventSource implements Runnable {
	public static final int TICK_INTERVAL = 1000*60;
	Logger logger = LogManager.getLogger(EventSource.class);
	Config config;
	KieSession kSession;
	static EventSource instance = null;
	static ExecutorService service;
	private FactHandle monthFH;
	private FactHandle dayFH;
	private FactHandle yearFH;
	private FactHandle hourFH;
	private FactHandle minuteFH;
	private FactHandle daynightFH;
	private int month;
	private int day;
	private int year;
	private int hour;
	private int minute;
	private String daynight;
	private Sun sun;
	ZonedDateTime now;
	protected final ZoneId zoneId = ZoneId.of("America/Los_Angeles");
	
	private BlockingQueue<Element> queue;

	private EventSource() {
	}

	public void initQueue() {
		queue = new LinkedBlockingQueue<Element>();
	}

	public void initRules() {
		// load up the knowledge base, configuration in kmodule.xml
		KieServices ks = KieServices.Factory.get();
		KieModuleModel kieModuleModel = ks.newKieModuleModel();

		KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel( "rules ")
		        .setDefault( true )
		        .setEqualsBehavior( EqualityBehaviorOption.EQUALITY );

		KieSessionModel ksessionModel = kieBaseModel.newKieSessionModel( "ksession-rules" )
		        .setDefault( true )
		        .setType( KieSessionModel.KieSessionType.STATEFUL )
		        .setClockType( ClockTypeOption.get("realtime") );
		KieFileSystem kfs = ks.newKieFileSystem();
		Map<String,String> map = kieModuleModel.getConfigurationProperties();
		kfs.writeKModuleXML(kieModuleModel.toXML());
		// The actual rule files are loaded here
		for (String filename : config.getRules().getFiles()) {
			kfs.write(ResourceFactory.newFileResource(filename));
		}
//		kfs.write(ResourceFactory.newFileResource("rules/common.drl"));
//		kfs.write(ResourceFactory.newFileResource("rules/lamp.drl"));
//		kfs.write(ResourceFactory.newFileResource("rules/show.drl"));
		KieBuilder kieBuilder = ks.newKieBuilder( kfs ).buildAll();
		if (kieBuilder.getResults().getMessages( Message.Level.ERROR ).size() !=0) {
			throw new RulesException("Rule compilation error");
		};
		
		KieContainer kieContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
		kSession = kieContainer.newKieSession("ksession-rules");

		// Our callbacks
		Sender sender = Sender.getInstance();
		sender.setSession(kSession);
		kSession.setGlobal("sender", sender);
		// Sunrise and sunset
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of(config.getTimezone()));
		sun = FetchSunriseSunset.get(now);
		ZonedDateTime sunrise = sun.getSunriseDT(zoneId);
		ZonedDateTime sunset = sun.getSunsetDT(zoneId);
		if (now.isAfter(sunrise) && now.isBefore(sunset))
			daynight = "day";
		else
			daynight = "night";
		daynightFH = kSession.insert(new DayNight(daynight));
		
//		int sunsetHour = sunset.getHour();
		// Get date facts started
		month = now.get(ChronoField.MONTH_OF_YEAR);
		day = now.get(ChronoField.DAY_OF_MONTH);
		year = now.get(ChronoField.YEAR);
		hour = now.get(ChronoField.HOUR_OF_DAY);
		minute = now.get(ChronoField.MINUTE_OF_HOUR);
		monthFH = kSession.insert(new Month(month));
		dayFH = kSession.insert(new Day(day));
		yearFH = kSession.insert(new Year(year));
		hourFH = kSession.insert(new Hour(hour));
		minuteFH = kSession.insert(new Minute(minute));
		kSession.addEventListener(new DebugRuleRuntimeEventListener() {

			@Override
			public void objectUpdated(ObjectUpdatedEvent event) {
//        		super.objectUpdated(event);
				String eventName;
				if (event.getRule() == null) {
					eventName = "<code>";
				} else {
					eventName = event.getRule().getName();
				}
				logger.debug("Updated Object " + event.getObject() + " by " + eventName);
			}

			@Override
			public void objectDeleted(ObjectDeletedEvent event) {
//        		super.objectDeleted(event);
				String eventName;
				if (event.getRule() == null) {
					eventName = "<code>";
				} else {
					eventName = event.getRule().getName();
				}
				logger.debug("Deleted Object " + event.getOldObject() + " by " + eventName);
			}

			@Override
			public void objectInserted(ObjectInsertedEvent event) {
//        		super.objectInserted(event);
				String eventName;
				if (event.getRule() == null) {
					eventName = "<code>";
				} else {
					eventName = event.getRule().getName();
				}
				logger.debug("Inserted Object " + event.getObject() + " by " + eventName);
			}
		});
		// Cause the init (no when) rules to run
		kSession.fireAllRules();
	}
	public void initTickTimer() {
		new Timer().scheduleAtFixedRate( new TimerTask() {
			@Override
			public void run() {
				// In normal mode, we use the system time, otherwise, time is set explicitly from a REST call.
				EventSource.getInstance().queue.add(new Tick(ZonedDateTime.now(zoneId)));
			}
		}, 0, TICK_INTERVAL);
	}
	public static EventSource getInstance() {
		if (instance == null) {
			instance = new EventSource();
			instance.config = Config.getInstance();
			instance.initRules();
			instance.initQueue();
			// Ticks are automatic in normal mode
			if ("normal".equals(instance.config.getRules().getMode())) {
				instance.initTickTimer();
			}
			// Kick off rule execution (in a separate thread)
			service = Executors.newSingleThreadExecutor();
			service.submit(instance);
		}
		return instance;
	}

	/**
	 * Add an event to rule engine. The event is queued and processed one-at-a-time
	 * in sequence.
	 * 
	 * @param name
	 */
	public void addEvent(String name) {
		Event event = new Event(name, null);
		queue.add(event);
	}

	public void addEvent(Event event) {
		queue.add(event);
	}

	public void addState(String name, String value) {
		State state= new State(name, value);
		queue.add(state);
	}

	public void changeTimeFacts(Tick tick) {
		now = tick.getNow();
		// If we're on a new day, query for today's sunrise and sunset
		if (now.getDayOfMonth()!=sun.getQueryTime().getDayOfMonth()) {
			sun = FetchSunriseSunset.get(now);
		}
		String tmp;
		if (now.isAfter(sun.getSunriseDT(zoneId)) && now.isBefore(sun.getSunsetDT(zoneId)))
			tmp = "day";
		else
			tmp = "night";
		if (!tmp.equals(daynight)) {
			daynight = tmp;
			kSession.update(daynightFH, new DayNight(daynight));
		}
		if (month!=now.get(ChronoField.MONTH_OF_YEAR)) {
			month = now.get(ChronoField.MONTH_OF_YEAR);
			kSession.update(monthFH, new Month(month));
		}
		if (day!=now.get(ChronoField.DAY_OF_MONTH)) {
			day = now.get(ChronoField.DAY_OF_MONTH);
			kSession.update(dayFH, new Day(day));
		}
		if (year!=now.get(ChronoField.YEAR)) {
			year = now.get(ChronoField.YEAR);
			kSession.update(yearFH,new Year(year));
		}
		if (hour!=now.get(ChronoField.HOUR_OF_DAY)) {
			hour = now.get(ChronoField.HOUR_OF_DAY);
			kSession.update(hourFH, new Hour(hour));
		}
		if (minute!=now.get(ChronoField.MINUTE_OF_HOUR)) {
			minute = now.get(ChronoField.MINUTE_OF_HOUR);
			kSession.update(minuteFH, new Minute(minute));
		}
	}
	/**
	 * Process events as they are removed from the queue
	 */
	@Override
	public void run() {
		Thread.currentThread().setName("Events");
		try {
			while (true) {
				Element element = queue.take();
				if (element instanceof Tick) {
					changeTimeFacts((Tick)element);
				}
				FactHandle fh = kSession.insert(element);
				kSession.fireAllRules();
				kSession.delete(fh);
			}
		} catch (InterruptedException e) {
		}
	}

	public void log(String line) {
		logger.info(line + ", factCount=" + kSession.getFactCount());
	}
}
