package org.t3.farm.control;

public abstract class DeviceBase {
	private String facility;
	private String device;
	private String name;
	
	/**
	 * Construct a generic object from a key string
	 * @param key
	 */
	protected DeviceBase( String key ) {
		String k[] = key.split("-");
		if (k.length!=3) {
			throw new RuntimeException("DevParam key \"" + key + "\" must be in the form facility-device-name");
		}
		this.facility = k[0];
		this.device = k[1];
		this.name = k[2];
	}
	
	/**
	 * Construct a generic object from facility, device, and name fields
	 * @param key
	 */
	protected DeviceBase( String facility, String device, String name ) {
		this.facility = facility;
		this.device = device;
		this.name = name;
	}
	
	/**
	 * The device name is the concatenation of facility and device
	 * @return
	 */
	public String getDeviceName() {
		return facility+"-"+device;
	}
	/**
	 * The key is the concatenation of facility, device, and name
	 * @return
	 */
	public String getKey() {
		return facility+"-"+device+"-"+name;
	}
	
	public String getFacility() {
		return facility;
	}

	public String getDevice() {
		return device;
	}

	public String getName() {
		return name;
	}
	

}
