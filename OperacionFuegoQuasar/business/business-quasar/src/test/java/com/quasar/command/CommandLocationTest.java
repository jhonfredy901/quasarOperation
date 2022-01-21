package com.quasar.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.quasar.dto.Position;

class CommandLocationTest {

	private CommandLocation getlocation;

	@Test
	void getLocationNormal() {
		getlocation = new CommandLocation();
		double[][] positions = new double[][] { { -100.0, -100.0 }, { 200.0, -100.0 }, { 100.0, 100.0 } };
		double[] distances = new double[] { 200.0, 150.0, 150.0 };
		Position expectedPosition = new Position();
		expectedPosition.setX(76.6654832005285);
		expectedPosition.setY(-38.38414879028662);
		Position position = getlocation.getLocation(distances, positions);
		assertEquals(expectedPosition.getX(), position.getX());
		assertEquals(expectedPosition.getY(), position.getY());
	}
	
	@Test
	void getLocationTwopositions() {
		getlocation = new CommandLocation();
		double[][] positions = new double[][] { { -100.0, -100.0 }, { 200.0, -100.0 }};
		double[] distances = new double[] { 200.0, 150.0};
		Position expectedPosition = new Position();
		expectedPosition.setX(76.1872745753129);
		expectedPosition.setY(-100.0);
		Position position = getlocation.getLocation(distances, positions);
		assertEquals(expectedPosition.getX(), position.getX());
		assertEquals(expectedPosition.getY(), position.getY());
	}
	
	@Test
	void getLocationfourPostions() {
		getlocation = new CommandLocation();
		double[][] positions = new double[][]{{5.0, -6.0}, {13.0, -15.0}, {21.0, -3.0}, {12.42, -21.2}};
        double[] distances = new double[]{8.06, 13.97, 23.32, 15.31};
		Position expectedPosition = new Position();
		expectedPosition.setX(-0.35656276826238276);
		expectedPosition.setY(-12.241368395561665);
		Position position = getlocation.getLocation(distances, positions);
		assertEquals(expectedPosition.getX(), position.getX());
		assertEquals(expectedPosition.getY(), position.getY());
	}

}
