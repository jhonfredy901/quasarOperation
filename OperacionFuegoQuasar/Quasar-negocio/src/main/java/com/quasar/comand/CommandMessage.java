/**
 * 
 */
package com.quasar.comand;

import java.util.ArrayList;
import java.util.List;

import com.quasar.comun.Command;
import com.quasar.comun.EnumError;
import com.quasar.comun.QuasarException;
import com.quasar.dto.SatelliteContainer;
import com.quasar.dto.SatelliteDto;

/**
 * Comando que permite obtener un mensaje enviado a distintos satellites que por
 * razones de interferencia tiene un desface (lag)
 * 
 * @author jhernandez
 *
 */
public class CommandMessage extends Command<SatelliteContainer, String> {

	@Override
	protected void preValidate() throws QuasarException {
		List<List<String>> messagesReceived = new ArrayList<>();
		for (SatelliteDto ls : input.getSatellites()) {
			messagesReceived.add(ls.getMessage());
		}
		if (messagesReceived.isEmpty()) {
			throw new QuasarException(EnumError.ERR_102.getMessage());
		}
		isValid = true;
	}

	@Override
	protected void executeCommand() throws QuasarException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getOut() {
		return result;
	}

}
