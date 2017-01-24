package by.tr.totalizator.tag.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents a UseBean component for map.
 * 
 * @author Mariya Bystrova
 *
 */
public class JspMapBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * Returns the value to which the specified String key is mapped, or null if
	 * this map contains no mapping for the key.
	 * 
	 * @param name
	 *            a String value key.
	 * @return the value to which the specified String key is mapped, or null if
	 *         this map contains no mapping for the key.
	 */
	public String getElement(String name) {
		return map.get(name);
	}

	/**
	 * Returns the number of key-value mappings.
	 * 
	 * @return the number of key-value mappings.
	 */
	public int getSize() {
		return map.size();
	}
}