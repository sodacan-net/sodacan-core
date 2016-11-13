package org.t3.farm.control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.Row;
import org.kie.api.runtime.rule.ViewChangedEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Engine  {
	static Logger logger = LoggerFactory.getLogger(Engine.class);
	private KieServices kieServices;
//	private KieBase kieBase;
	private KieSession kieSession;
	private KieContainer kieContainer;
	private EntryPoint eventStream;
	private LiveQuery devParamChangeQuery;
	private List<DevParamChange> dpcs = null;
	private List<FactHandle> eventFactHandles = null;
	
	public Engine() {
		init(null);
	}
	public void addEventListener(RuleRuntimeEventListener eventListener) {
		kieSession.addEventListener( eventListener );
	}
	
	public void init(String ruleFile) {
		kieServices = KieServices.Factory.get();
		kieContainer = kieServices.getKieClasspathContainer();
//		kieBase = kieContainer.getKieBase("kbase1");
		kieSession = kieContainer.newKieSession("ksession1");
		eventStream = kieSession.getEntryPoint( "EventStream" );
//		devParamChangeQuery = kieSession.openLiveQuery( "Device Parameter Changes", new Object[] {  }, this );
		new Thread() {
		        @Override
		        public void run() {
		    		kieSession.fireUntilHalt();
		        }
		    }.start();
	 }

	public void insertDevEvent( DevEvent event) {
//		if (eventFactHandles==null) {
//			eventFactHandles = new ArrayList<FactHandle>();
//		}
		FactHandle e1 = eventStream.insert( event );
//		eventStream.delete(e1);
//		eventFactHandles.add(e1);
	}
	
	public void insertTestResult( TestResult result ) {
		kieSession.insert( result );
	}
	
	
	public String getDeviceParamValue( String key ) {
		DevParam r = getDeviceParam( key );
		if (r==null) {
			return null;
        } else {
        	return r.getValue();
        }
	}
	public int getHearbeatCount() {
		QueryResults results = kieSession.getQueryResults( "find All Heartbeats", new Object[] { } );
		return results.size();
	}
	
	public DevParam getDeviceParam( String key ) {
		QueryResults results = kieSession.getQueryResults( "find Device Parameter", new Object[] { key } );
		if (results.size()==0) {
			return null;
		} else {
            return (DevParam) results.iterator().next().get("$r");
        }
	}
	
	public void insertDevParam(DevParam p) {
//		// We want to know when a device parameter changes
//		p.addPropertyChangeListener(this);
		// See if the object is already in working memory
		DevParam p1 = getDeviceParam( p.getKey() );
		if (p1==null) {
			// Add as new object
//			logger.info("Insert  " + p + " into wm");
			kieSession.insert( p );
			
		} else {
			// Just update the value field
			FactHandle fh = kieSession.getFactHandle(p1);
			kieSession.update(fh, p, "value");
//			logger.info("update  " + p + " in wm");
		}
	}
	
	public void sendDeviceParamChanges() {
//		dpcs.forEach((dpc) -> {
//			DevParam dp = new DevParam(dpc.getDevParam().getKey(), dpc.getNewValue());
//			insertDevParam( dp );
//		});
	}
	
	public void close() {
//		devParamChangeQuery.close();
		kieSession.halt();
//		kieSession.dispose();
	}
	
//	@Override
//	public void propertyChange(PropertyChangeEvent event) {
//		DevParam p = (DevParam) event.getSource();
//		System.out.println("PropertyChange " + p );
//		
//	}

//	@Override
//	public void rowInserted(Row row) {
//		Object raw = row.get("$r");
//		if (raw instanceof DevParamChange) {
//			DevParamChange dpc = (DevParamChange) raw;
//			logger.info("DPC Arrived: " + dpc);
//		} else {
//			logger.info("Other Arrived: " + raw);
//		}
////		DevParam dp = new DevParam(dpc.getDevParam().getKey(), dpc.getNewValue());
////		insertDevParam( dp );
////		dpcs.add(dpc);
////		FactHandle fh = kieSession.getFactHandle(dpc);
////		kieSession.delete(fh);
//	}
//
//	@Override
//	public void rowDeleted(Row row) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void rowUpdated(Row row) {
//		// TODO Auto-generated method stub
//		
//	}

}

