package at.fhhagenberg.sqe.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public abstract class Model {
	private HashMap<String, Object> mPropertyValues;
	protected PropertyChangeSupport mPropertyChangeSupport = null;
	
	public Model() {
		mPropertyChangeSupport = new PropertyChangeSupport(this);
		mPropertyValues = new HashMap<String, Object>();
	}
	
	public void addListener(PropertyChangeListener listener) {
		mPropertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	protected void setProperty(String name, Object newValue) {
		Object oldValue = null;
		if(mPropertyValues.containsKey(name)) {
			oldValue = mPropertyValues.get(name);
		}
		mPropertyValues.put(name, newValue);
		mPropertyChangeSupport.firePropertyChange(name, oldValue, newValue);
		
	}
	
	protected Object getProperty(String name) {
		return mPropertyValues.get(name);
	}
	
}
