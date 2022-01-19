package com.quasar.comun;

import com.quasar.dto.Position;

/**
 * @author jhon hernandez
 *
 */
public class ResponseQuasar {

	private Position position;
	private String message;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
