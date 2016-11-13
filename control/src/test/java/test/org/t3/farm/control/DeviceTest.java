package test.org.t3.farm.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t3.farm.control.DevParam;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for Devive-related classes
 */
public class DeviceTest 
{
    final static Logger logger = LoggerFactory.getLogger(DeviceTest.class);
    
    
    /**
     * Create a device parameter object
     */
	@Test
    public void test1()
    {
    	DevParam devParam = new DevParam("fac1-dev1-param1", "value1");
        assertNotNull( devParam );
        logger.info("test1: " + devParam.getName() + " created");
    }
    /**
     * Verify that two DevParam objects with different names are not equal
     */
	@Test
    public void test2()
    {
    	DevParam p1 = new DevParam("fac1-dev1-param1", "val1");
    	DevParam p2 = new DevParam("fac1-dev1-param2", "val1");
        assertNotNull( p1 );
        assertNotNull( p2 );
        assertFalse(p1.equals(p2));
        logger.info("test2: " + p1.getName() + "!=" + p2.getName());
    }
    /**
     * Verify that two DevParams objects with the same name compare equal
     * Notice that the values do not have to be equal
     */
	@Test
    public void test3()
    {
    	DevParam p1 = new DevParam("fac1-dev1-param1", "val1");
    	DevParam p2 = new DevParam("fac1-dev1-param1", "val2");
        assertNotNull( p1 );
        assertNotNull( p2 );
        assertTrue(p1.equals(p2));
        logger.info("test3: " + p1.getKey() + "==" + p2.getKey());
    }
	
    /**
     * Verify that a DevParam object's name matches a string 
     */
	@Test
    public void test4()
    {
    	DevParam p1 = new DevParam("fac1-dev1-param1", "val1");
        assertNotNull( p1 );
        assertTrue(p1.equals("fac1-dev1-param1"));
        logger.info("test4: " + p1.getKey() + "==\"fac1-dev1-param1\"");
    }

    /**
     * Verify that a DevParam key is well formed 
     */
	@Test
    public void test5()
    {
		try {
			new DevParam("fac1-dev1", "val1");
			new DevParam("fac1", "val1");
			new DevParam("fac1-dev1-name-more", "val1");
			assertFalse(true);
		} catch (Exception e) {
			 logger.info("test5: Correctly caught key exception: " + e);
		}
    }
//    /**
//     * Verify that the map is ordered by key and that a subsequent update for a key replaces that item.
//     */
//	@Test
//    public void test5() {
//    	Device dev1 = new Device("fac1", "dev1");
//    	dev1.putDevParam("param3", new DevParam("value3"));
//    	dev1.putDevParam("param1", new DevParam("value1"));
//    	dev1.putDevParam("param2", new DevParam("value2"));
//    	dev1.putDevParam("param1", new DevParam("value3"));
//    	dev1.equals("fac1-dev1");
//    	assertEquals(dev1.toString(), "fac1-dev1{param1=value3,param2=value2,param3=value3}");
//        logger.info(dev1.toString());
//    }
}
