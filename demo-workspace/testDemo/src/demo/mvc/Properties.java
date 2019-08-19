package demo.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Properties {
	private Map<String, String> properties;

	public Properties() {
		this.properties = new HashMap<String, String>();
	}

	public Properties(int initialCapacity) {
		this.properties = new HashMap<String, String>(initialCapacity);
	}

	public Properties(Map<String, String> properties) {
		this.properties = new HashMap<String, String>(properties);
	}

	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	public void setProperties(Map<String, String> properties) {
		this.properties.clear();
		this.properties.putAll(properties);;
	}

}
