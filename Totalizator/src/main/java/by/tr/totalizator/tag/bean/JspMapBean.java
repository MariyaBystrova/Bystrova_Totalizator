package by.tr.totalizator.tag.bean;

import java.io.Serializable;
import java.util.Map;

public class JspMapBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> map;
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getElement(String name){
		return map.get(name);
	}
	public int getSize(){
		return map.size();
	}
}