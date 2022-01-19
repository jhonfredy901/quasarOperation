package com.quasar.comun;

import org.slf4j.Logger;
/**
 * 
 * @author jhon hernandez
 *
 */
public class QuasarException extends Exception {
	/**
	 * id de serializacion
	 */
	private static final long serialVersionUID = 2851547010089981307L;
	/**
	 * Log
	 */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(QuasarException.class);
	/**
	 * Codigo de excepción
	 */
	private int code;

	/**
	 * Constructor por parámetro
	 * 
	 * @param message Message
	 * @param cause   Cause
	 */
	public QuasarException(String message, Throwable cause) {
		super(message, cause);
		LOG.error("Error en <<executeCommand>> ->> mensaje ->> {} / causa ->> {} ", cause.getMessage(),
				cause.getCause());
	}

	public QuasarException(int code, String message, Throwable cause) {
		super(message, cause);
		LOG.error("Error en <<executeCommand>> codigo->>{} ->> mensaje ->> {} / causa ->> {} ", code,
				cause.getMessage(), cause.getCause());
	}

	public QuasarException(int code, String message) {
		super(message);
		this.code = code;
		LOG.error("Error en <<executeCommand>> codigo->>{} ->> mensaje ->> {} ", code, message);
	}

	public QuasarException(String message) {
		super(message);
		LOG.error("Error en <<executeCommand>> ->> mensaje ->> {} ", message);
	}

	public QuasarException(Throwable cause) {
		super(cause);
		LOG.error("Error en <<executeCommand>> ->> mensaje ->> {} / causa ->> {} ", cause.getMessage(),
				cause.getCause());
	}

	/**
	 * @return the status
	 */
	public int getCode() {
		return code;
	}

}
