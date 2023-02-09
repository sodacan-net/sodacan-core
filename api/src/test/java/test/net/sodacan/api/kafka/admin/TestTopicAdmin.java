package test.net.sodacan.api.kafka.admin;



import java.util.ArrayList;
import java.util.List;

import net.sodacan.config.Config;
import net.sodacan.messagebus.MB;
import net.sodacan.mode.Mode;

public class TestTopicAdmin {
	List<String> brokers = new ArrayList<>();

//	@Test
//	public void testInitializeAll() {
//		Initialize initialize = new Initialize();
//		initialize.setupTopics();
//		initialize.setupDefaultMode();
//	}
	
//	@Test
	public void testListTopics() {
		Mode mode = Mode.getInstance();
		MB mb = mode.getMessageBusService().getMB(Config.getInstance());
		System.out.println(mb.listTopics());
	}
	
}
