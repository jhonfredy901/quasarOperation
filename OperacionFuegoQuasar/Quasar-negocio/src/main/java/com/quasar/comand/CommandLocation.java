package com.quasar.comand;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.quasar.comun.Command;
import com.quasar.comun.QuasarException;
import com.quasar.comun.ResponseQuasar;
import com.quasar.dto.FilterGet;
import com.quasar.dto.Position;
import com.quasar.dto.SatelliteContainer;
import com.quasar.dto.SatelliteDto;

/**
 * Comando concreto que permite por medio de la obtencion de tres puntos, y la
 * distancia de tres satelites retornar la ubicación de un objeto
 * 
 * @author jhon hernandez
 *
 */
public class CommandLocation extends Command<SatelliteContainer, ResponseQuasar> {
	@Inject
	private GetSatellites getsatellites;

	@Override
	protected void preValidate() throws QuasarException {
//		if (input.length() < 3) {
//			throw new QuasarException(EnumError.ERR_101.getNum(), EnumError.ERR_101.getValue());
//		}
		isValid = true;
//		fillList(input);
	}

	@Override
	protected void executeCommand() throws QuasarException {
		FilterGet filter = new FilterGet();
		getsatellites.setInput(filter);
		getsatellites.execute();

		getLocation(getDistances(), null);

	}

	@Override
	public ResponseQuasar getOut() {
		return result;
	}

	private void getLocation(List<Double> distances, double[][] positions) {
//		List<Satellite> result = input.stream().map(temp -> {
//			Satellite obj = new Satellite();
//			obj.setDistance(temp.getDistance());
//			obj.setMessage(temp.getMessage());
//			if ("mkyong".equals(temp.getName())) {
//				obj.setExtra("this field is for mkyong only!");
//			}
//			return obj;
//		}).collect(Collectors.toList());
	}

	/**
	 * 
	 * @return
	 */
	private List<Double> getDistances() {
		List<Double> distances = new ArrayList<>();
		for (SatelliteDto satellite : input.getSatellites()) {
			distances.add(satellite.getDistance());
		}
		return distances;
	}

	private List<Position> getPositions() {
		List<Position> positions = new ArrayList<>();
		return positions;
	}

}
