package com.quasar.comun;

import org.slf4j.Logger;

/**
 * Abstración de patrón comando para permitir realizar uso en comandos concretos
 * para ejecución
 * 
 * @author jhon hernandez
 *
 */
public abstract class Command<I, O> {
	/**
	 * Instancia del Log
	 */
	protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(Command.class);
	/**
	 * Propiedad para manejar datos de entrada para ejecución
	 */
	protected I input;
	/**
	 * Propiedad para retornan el resultado de la ejecucion del comando
	 */
	protected O result;
	/**
	 * Propiedad para manejar si es valido o no el comando a ejecutar
	 */
	protected boolean isValid;

	/**
	 * Metodo que permite prevalidar datos antes de realizar operación
	 * 
	 * @throws QuasarException
	 */
	protected abstract void preValidate() throws QuasarException;

	/**
	 * Método que permite realizar la ejecución del correspondiente comando enviado
	 * 
	 * @throws QuasarException
	 */
	protected abstract void executeCommand() throws QuasarException;

	/**
	 * Retorna resultado esperado de salida
	 * 
	 * @return Objeto de tipo P
	 */
	public abstract O getOut();

	public void execute() throws QuasarException {
		LOG.info("Inicia ejecución de comando");
		try {
			preValidate();
			if (isValid) {
				executeCommand();
			}
			LOG.info("Finalizó ejecución de comando");
		} catch (QuasarException e) {
			throw e;
		} catch (Exception e1) {
			throw new QuasarException(e1);
		}
	}

	public void setInput(I input) {
		this.input = input;
	}

}
