package test.org.t3.farm.control;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import org.t3.farm.control.DevParam;
import org.t3.farm.control.ButtonEvent;
import org.t3.farm.control.DevEvent;
import org.t3.farm.control.Engine;
import org.t3.farm.control.ParameterChangeSender;

public class EngineTest {
	Engine engine = null;
	static Logger logger = LoggerFactory.getLogger(EngineTest.class);
	
	public void fireEngine() {
//		   try {
//		        System.err.println("[[ Sleeping ...]]");
//		        Thread.sleep(2000);
//		    } catch (final InterruptedException e) {
//		        e.printStackTrace();
//		    }
//		long startTime = System.currentTimeMillis();
//		System.out.println("Fire Rules ...");
//		engine.fire();
//		long endTime = System.currentTimeMillis();
//		System.out.println("Running rules in " + (endTime - startTime) + "ms");
//		    System.err.println("[[ awake ...]]");
	}

	@Test
	public void test1() {
		engine = new Engine();
		engine.addEventListener(new ParameterChangeSender());
		long startTime = System.currentTimeMillis();
		logger.info("Fire Rules ...");
		new Thread() {
	        @Override
	        public void run() {
	    		engine.insertDevParam(new DevParam("fac1-dev1-p1", "value1"));
	    		engine.insertDevParam(new DevParam("fac1-dev1-p2", "value2-1"));
	    		engine.insertDevParam(new DevParam("fac1-dev1-p2", "value2-2"));
	    		engine.insertDevParam(new DevParam("fac1-dev1-state", "on"));
	    		engine.insertDevParam(new DevParam("fac1-dev1-desc", "Light Fixture 1"));
	    		engine.insertDevParam(new DevParam("fac1-dev2-state", "off"));
	    		engine.insertDevParam(new DevParam("fac1-dev3-desc", "Light Fixture 2"));
	    		engine.insertDevEvent(new ButtonEvent("fac1-dev4"));
	    		for (int x=0; x< 100;x++) {
	    			engine.insertDevEvent(new ButtonEvent("fac1-dev1"));
	    		}
	        }
	    }.start();
		try {
	        System.err.println("[[ Sleeping ...]]");
	        Thread.sleep(2000);
	    } catch (final InterruptedException e) {
	        e.printStackTrace();
	    }
		fireEngine();
//		assertEquals("on", engine.getDeviceParamValue("fac1-dev1-state"));
		long endTime = System.currentTimeMillis();
		logger.info("Running rules in " + (endTime - startTime) + "ms");

		engine.close();
	}

}
