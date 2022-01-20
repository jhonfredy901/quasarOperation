package com.quasar.comun;

public enum EnumError {

	ERR_101(101, "Verifíque los parametros de entrada del servicio, e intente de nuevo, se esperan al menos 3 satelites para obtener la ubicación"),
	ERR_102(102, "El contenido del mensaje enviado no es correcto"),
	ERR_103(103, "El tamaño del mensaje es incorrecto, valide por favor"),
	ERR_104(104, "No fue posible entender el mensaje"),
	;
	
	private final int num;
	private final String value;

	private EnumError(int num, String value) {
		this.num = num;
		this.value = value;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Se concatena el numero del errro y sumensaje
	 * 
	 * @return
	 */
	public String getMessage() {
		StringBuilder message = new StringBuilder();
		message.append(num).append(": ").append(value);
		return message.toString();
	}

}
