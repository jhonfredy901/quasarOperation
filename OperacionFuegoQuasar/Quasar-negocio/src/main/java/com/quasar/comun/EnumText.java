/**
 * 
 */
package com.quasar.comun;

/**
 * @author jhernandez
 *
 */
public enum EnumText {

	VAL("val");

	/**
	 * 
	 */
	private final String value;

	/**
	 * Constructor
	 * 
	 * @param value
	 */
	private EnumText(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
