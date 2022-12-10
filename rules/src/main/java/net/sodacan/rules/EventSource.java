package net.sodacan.rules;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
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
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.io.ResourceFactory;

import net.sodacan.api.resource.FactPublisher;
import net.sodacan.rules.config.Config;

//import net.sodacan.strip.StripService;

public class EventSource implements Runnable {
	public static final int TICK_INTERVAL = 1000*60;
	Logger logger = LogManager.getLogger(EventSource.class);
	Config config;
	KieSession kSession;
	static EventSource instance = null;
	static ExecutorService service;
	private FactHandle tickFH = null;
	protected ZonedDateTime now;
	protected ZoneId zoneId;
	
	private BlockingQueue<Element> queue;

	private EventSource() {
	}

	public void initQueue() {
		queue = new LinkedBlockingQueue<Element>();
	}

	public ZoneId getZoneId() {
		return zoneId;
	}

	public void setZoneId(ZoneId zoneId) {
		this.zoneId = zoneId;
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
		kSession.setGlobal("logger", logger);
		kSession.addEventListener(new DebugRuleRuntimeEventListener() {

			@Override
			public void objectUpdated(ObjectUpdatedEvent event) {
//        		super.objectUpdated(event);
				String eventName;
				if (event.getRule() == null) {
					eventName = "code";
				} else {
					eventName = event.getRule().getName();
				}
				if (event.getObject() instanceof State) {
					FactPublisher.broadcastState((State)event.getObject());
				}
				if (event.getObject() instanceof Countdown) {
					FactPublisher.broadcastCountdown((Countdown)event.getObject(), "update");
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
				if (event.getOldObject() instanceof State) {
					FactPublisher.broadcastState((State)event.getOldObject());
				}
				if (event.getOldObject() instanceof Countdown) {
					FactPublisher.broadcastCountdown((Countdown)event.getOldObject(), "delete");
				}
				logger.debug("Deleted Object " + event.getOldObject() + " by " + eventName);
			}

			@Override
			public void objectInserted(ObjectInsertedEvent event) {
//        		super.objectInserted(event);
				String eventName;
				if (event.getRule() == null) {
					eventName = "code";
				} else {
					eventName = event.getRule().getName();
				}
				if (event.getObject() instanceof State) {
					FactPublisher.broadcastState((State)event.getObject());
				}
				if (event.getObject() instanceof Countdown) {
					FactPublisher.broadcastCountdown((Countdown)event.getObject(), "insert");
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
			instance.zoneId = ZoneId.of(instance.config.getTimezone());
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

	public List<State> getAllStates() {
		QueryResults results = kSession.getQueryResults( "All states" );
		logger.debug( "Query found " + results.size() + " states" );
		List<State> states = new ArrayList<State>();
		for ( QueryResultsRow row : results ) {
		    State state = ( State ) row.get( "$state" );
		    states.add(state);
		}
		return states;
	}
	public List<Countdown> getAllCountdowns() {
		QueryResults results = kSession.getQueryResults( "All Countdowns" );
		logger.debug( "Query found " + results.size() + " Countdowns" );
		List<Countdown> countdowns = new ArrayList<Countdown>();
		for ( QueryResultsRow row : results ) {
		    Countdown tw = ( Countdown ) row.get( "$c" );
		    countdowns.add(tw);
		}
		return countdowns;
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
					FetchSunriseSunset.get((Tick)element,zoneId);
					if (tickFH==null) {
						tickFH = kSession.insert(element);
					} else {
						kSession.update(tickFH, element);
					}
					kSession.fireAllRules();
					// We leave the tick event as is until another one comes along
					// Thus, the tick event is persistent. normal events are removed after firing.
				} else {
					FactHandle fh = kSession.insert(element);
					kSession.fireAllRules();
					if (element instanceof Event) {
						kSession.delete(fh);
					}
				}
			}
		} catch (InterruptedException e) {
		}
	}

	public void log(String line) {
		logger.info(line + ", factCount=" + kSession.getFactCount());
	}
}
