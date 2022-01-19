package com.quasar.comun;

import java.util.Map;

/**
 * @author jhon hernandez
 *
 */
public class ResponseQuasar {

	private Map<String, Double> position;
	private String message;

	public Map<String, Double> getPosition() {
		return position;
	}

	public void setPosition(Map<String, Double> position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
