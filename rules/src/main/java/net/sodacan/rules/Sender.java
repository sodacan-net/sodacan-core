package net.sodacan.rules;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.kie.api.runtime.KieSession;
public class Sender {
	static Sender instance = null;
	static ExecutorService service;

	KieSession kSession;

	private Sender( ) {
	}
	
	public void setSession(KieSession kSession) {
		this.kSession = kSession;
	}
	
	public static Sender getInstance() {
		if (instance == null) {
			instance = new Sender();
			// Create a thread pool to handle the sends
			service = Executors.newCachedThreadPool();
		}
		return instance;
	}

	public void send(String address, String message) {
		service.submit(new SenderWorker( address, message));
	}
}
