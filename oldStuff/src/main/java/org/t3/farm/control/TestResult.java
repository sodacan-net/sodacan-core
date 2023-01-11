package org.t3.farm.control;

public class TestResult {
	private String id;
	private String result;
	
	public TestResult( String id, String result ) {
		this.id = id;
		this.result = result;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
