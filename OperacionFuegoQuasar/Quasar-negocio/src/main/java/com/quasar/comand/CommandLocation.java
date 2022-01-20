package com.quasar.comand;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.quasar.comun.Command;
import com.quasar.comun.EnumError;
import com.quasar.comun.QuasarException;
import com.quasar.dto.FilterGet;
import com.quasar.dto.Position;
import com.quasar.dto.SatelliteContainer;
import com.quasar.model.Satellite;

/**
 * Comando concreto que permite por medio de la obtencion de tres puntos, y la
 * distancia de tres satelites retornar la ubicación de un objeto
 * 
 * @author jhon hernandez
 *
 */
public class CommandLocation extends Command<SatelliteContainer, Position> {
	@Inject
	private GetSatellites getsatellites;

	@Override
	protected void preValidate() throws QuasarException {
		if (input.getSatellites().size() < 3) {
			throw new QuasarException(EnumError.ERR_101.getValue());
		}
		isValid = true;
	}

	@Override
	protected void executeCommand() throws QuasarException {
		result = getLocation(getDistances(), getPositions());
	}

	@Override
	public Position getOut() {
		return result;
	}

	/**
	 * Permite obtener la localización [x,y] de un objeto en un plano bidimensional
	 * por medio de trilateración
	 * 
	 * @param distances
	 * @param positions
	 * @return ResponseQuasar
	 */
	private Position getLocation(double[] distances, double[][] positions) {
		NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
				new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
		Optimum optimum = solver.solve();
		double position[] = optimum.getPoint().toArray();
		Position posit = new Position();
		posit.setX(position[0]);
		posit.setY(position[1]);
		return posit;
	}

	/**
	 * Obtener listado de distancias de los satelites enviados
	 * 
	 * @return double[]
	 */
	private double[] getDistances() {
		double[] distances = new double[input.getSatellites().size()];
		for (int i = 0; i < input.getSatellites().size(); i++) {
			distances[i] = input.getSatellites().get(i).getDistance();
		}
		return distances;
	}

	/**
	 * Se obtiene los datos de los satelites que llegan como parametro de la
	 * peticion y con ellos su correspondiente posición
	 * 
	 * @return List<Position>
	 * @throws QuasarException
	 */
	private double[][] getPositions() throws QuasarException {
		double[][] positions = new double[input.getSatellites().size()][];
		try {
			FilterGet filter = new FilterGet();
			getsatellites.setInput(filter);
			getsatellites.execute();
			List<Satellite> satellites = getsatellites.getOut();

			String[] points;
			for (int i = 0; i < input.getSatellites().size(); i++) {
				for (Satellite satelliteBd : satellites) {
					if (input.getSatellites().get(i).getName().equals(satelliteBd.getName())) {
						Position position = new Position();
						position.setX(satelliteBd.getX());
						position.setY(satelliteBd.getY());
						points = position.toString().split(",");
						positions[i] = Arrays.stream(points).map(Double::valueOf).mapToDouble(Double::doubleValue)
								.toArray();
					}
				}
			}
		} catch (QuasarException e) {
			throw new QuasarException("No fue posible obtener las posiciónes de los satelites");
		}
		return positions;
	}

}
