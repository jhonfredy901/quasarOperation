package com.quasar.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.quasar.comun.QuasarException;

class CommandMessageTest {

	private CommandMessage getMessage;

	@Test
	void testGetMessage() {
		getMessage = new CommandMessage();
		List<List<String>> messages = new ArrayList<List<String>>();
		String[] s1 = { "este", "", "", "mensaje", "" };
		String[] s2 = { "", "es", "", "", "secreto" };
		String[] s3 = { "este", "", "un", "", "" };
		messages.add(Arrays.stream(s1).collect(Collectors.toList()));
		messages.add(Arrays.stream(s2).collect(Collectors.toList()));
		messages.add(Arrays.stream(s3).collect(Collectors.toList()));
		String message = "";
		try {
			message = getMessage.getMessage(messages);
		} catch (QuasarException e) {
			assertEquals("No fue posible obtener el mensaje", e.getMessage());
		}
		String expectedMsg = "este es un mensaje secreto";
		assertEquals(message, expectedMsg);
	}

	@Test
	void testGetMessagefourSatellites() {
		getMessage = new CommandMessage();
		List<List<String>> messages = new ArrayList<List<String>>();
		String[] s1 = { "este", "", "", "mensaje", "" };
		String[] s2 = { "", "es", "", "", "secreto" };
		String[] s3 = { "este", "", "un", "", "" };
		String[] s4 = { "", "", "un", "mensaje", "" };
		messages.add(Arrays.stream(s1).collect(Collectors.toList()));
		messages.add(Arrays.stream(s2).collect(Collectors.toList()));
		messages.add(Arrays.stream(s3).collect(Collectors.toList()));
		messages.add(Arrays.stream(s4).collect(Collectors.toList()));
		String message = "";
		try {
			message = getMessage.getMessage(messages);
		} catch (QuasarException e) {
			assertEquals("No fue posible obtener el mensaje", e.getMessage());
		}
		String expectedMsg = "este es un mensaje secreto";
		assertEquals(message, expectedMsg);
	}

	@Test
	void testGetMessageWithError() {
		getMessage = new CommandMessage();
		List<List<String>> messages = new ArrayList<List<String>>();
		String[] s1 = { "este", "", "", "mensaje", "" };
		String[] s2 = { "", "es", "", "", "secreto" };
		String[] s3 = { "este", "", "un", "", "", "" };
		messages.add(Arrays.stream(s1).collect(Collectors.toList()));
		messages.add(Arrays.stream(s2).collect(Collectors.toList()));
		messages.add(Arrays.stream(s3).collect(Collectors.toList()));
		try {
			String message = getMessage.getMessage(messages);
		} catch (QuasarException e) {
			assertEquals("No fue posible entender el mensaje!", e.getMessage());
		}
	}

}
