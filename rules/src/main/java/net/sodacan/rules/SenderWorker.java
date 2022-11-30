package net.sodacan.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SenderWorker implements Runnable {
	Logger logger = LogManager.getLogger(SenderWorker.class);
	String address;
	String message;
	public SenderWorker( String address, String message) {
		this.address = address;
		this.message = message;
	}
	@Override
	public void run() {
//		try {
			  	logger.info("Send '" + message + "' to " + address);
//		} catch (InterruptedException e) {
//		}
		
	}

}
