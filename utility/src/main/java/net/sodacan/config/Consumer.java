package net.sodacan.config;

public class Consumer {
	private String url;
	private int sessionTimeoutSec;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSessionTimeoutSec() {
		return sessionTimeoutSec;
	}
	public void setSessionTimeoutSec(int sessionTimeoutSec) {
		this.sessionTimeoutSec = sessionTimeoutSec;
	}
	
}
