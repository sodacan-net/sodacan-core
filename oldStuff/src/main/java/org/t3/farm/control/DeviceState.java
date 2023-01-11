package org.t3.farm.control;

import java.util.Map;
import java.util.TreeMap;


/**
 * A device object contains the name of the device and one or more parameters associated with that device.
 * @author john
 */
public class DeviceState {
	private static final long serialVersionUID = 1L;
	
	private String facility;
	private String device;
	private Map<String, DevParam> param;

	/**
	 * Get the collection of parameters as a mutable map. If one doesn't exist, it is created.
	 * @return
	 */
	public Map<String, DevParam> getParam() {
		if (param==null) {
			param = new TreeMap<String,DevParam>();
		}
		return param;
	}
	
	public void putDevParam( String name, DevParam dp ) {
		getParam().put(name, dp);
	}
	
	/**
	 * Simple device constructor
	 * @param facility name
	 * @param device name
	 */
	public DeviceState( String facility, String device) {
		this.facility = facility;
		this.device = device;
	}
	
	/**
	 * Equality can be determined by a simple string match on the name of the device.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof String) {
			return getName().equals(other);
		}
		if (other instanceof DeviceState) {
			return getName().equals(((DeviceState) other).getName());
		}
		return false;
	}
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	/**
	 * The device name is the concatenation of facility and device
	 * @return
	 */
	public String getName() {
		return facility+"-"+device;
	}

	@Override
	public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(getName());
    	sb.append("{");
    	int first = sb.length();
    	getParam().forEach((k,v)-> {
    		if (first!=sb.length()) {
            	sb.append(",");
    		}
    		sb.append(k);
    		sb.append("=");
    		sb.append(v);
    	});
    	sb.append("}");
		return sb.toString();
	}
	
}
