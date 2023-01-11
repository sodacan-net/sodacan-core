package org.t3.farm.control;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A device consist of one or more device Parameters (DevParam). Each parameter has a datatype.
 * @author john
 */
public class DevParam extends DeviceBase {
//	public enum DataType {TRIGGER, TOGGLE, LEVEL, TIMESTAMP, COUNT, RGB, RGBW, W, SCENE, TEXT, NULL}; 
//	private DataType dataType;
	String parameter;
	String value;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	/**
	 * Construct a new DevParam
	 * @param value of the parameter encoded as a string
	 */
	public DevParam(String key, String value) {
		super( key );
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		String oldValue = this.value;
		this.value = value;
		pcs.firePropertyChange("value", oldValue, value);
	}
	
	/**
	 * Equality can be determined by a simple string match on the "key" of this object.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof String) {
			return getKey().equals(other);
		}
		if (other instanceof DevParam) {
			return getKey().equals(((DevParam) other).getKey());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return getKey().hashCode();
	}
	

	@Override
	public String toString() {
		return getKey() + "=" + value;
	}
	
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

}
