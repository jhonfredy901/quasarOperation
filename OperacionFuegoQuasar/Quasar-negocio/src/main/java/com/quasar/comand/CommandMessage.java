/**
 * 
 */
package com.quasar.comand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	private List<List<String>> messagesReceived;

	@Override
	protected void preValidate() throws QuasarException {
		messagesReceived = new ArrayList<>();
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
		result = getMessage(messagesReceived);
	}

	@Override
	public String getOut() {
		return result;
	}

	private String getMessage(List<List<String>> msgList) throws QuasarException {
		List<String> listWords = new ArrayList<>();
		for (List<String> msg : msgList) {
			listWords = Stream.concat(listWords.stream(), msg.stream()).distinct().collect(Collectors.toList());
		}
		listWords.remove("");
		if (!validateMessagesSize(msgList, listWords.size())) {
			throw new QuasarException(EnumError.ERR_103.getValue());
		}
		removeLag(msgList, listWords.size());
		String message = completeMessage(msgList);
		if (!validateMessageWords(listWords, message)) {
			throw new QuasarException(EnumError.ERR_104.getValue());
		}
		return message;
	}

	public boolean validateMessagesSize(List<List<String>> messages, int size) {
		for (List<String> m : messages) {
			if (m.size() < size) {
				return false;
			}
		}
		return true;
	}

	private void removeLag(List<List<String>> msgList, int lagSize) {
		int s = 0;
		for (int i = 0; i < msgList.size(); i++) {
			s = msgList.get(i).size();
			msgList.set(i, msgList.get(i).subList(s - lagSize, s));
		}
	}

	private String completeMessage(List<List<String>> msgList) {
		String phrase = "";
		for (List<String> m : msgList) {
			if (!m.isEmpty() && !m.get(0).equals("")) {
				phrase = (m.size() == 1) ? m.get(0) : m.get(0) + " ";
				msgList.stream().forEach(s -> s.remove(0));
				return phrase + completeMessage(msgList);
			}
		}
		return "";
	}

	public boolean validateMessageWords(List<String> listWords, String message) {
		List<String> msg = Arrays.stream(message.split(" ")).collect(Collectors.toList());
		Collections.sort(listWords);
		Collections.sort(msg);
		return Arrays.equals(listWords.toArray(), msg.toArray());
	}

}
