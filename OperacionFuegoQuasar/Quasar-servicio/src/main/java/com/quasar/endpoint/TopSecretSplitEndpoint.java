package com.quasar.endpoint;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.quasar.comand.CommandLocation;
import com.quasar.comand.CommandMessage;
import com.quasar.comun.QuasarException;
import com.quasar.comun.ResponseQuasar;
import com.quasar.dto.SatelliteContainer;
import com.quasar.dto.SatelliteDto;

/**
 * Servicio que permite obtener la ubicación y el mensaje emitido por una nave a
 * trraves de trilateración teniendo en cuenta el envio por medio del nombre del
 * satellite
 * 
 * @author jhon hernandez
 *
 */
@Path("/topsecret-split")
public class TopSecretSplitEndpoint {

	@Inject
	private CommandLocation getLocation;

	@Inject
	private CommandMessage getMessage;

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response create(SatelliteContainer satellites) {
		ResponseQuasar quasar = new ResponseQuasar();
		try {
			executeGetLocationMessage(satellites);
		} catch (QuasarException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(quasar).build();
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{satellite_name}")
	public Response postSplit(@PathParam("satellite_name") String satelliteName, SatelliteDto satellite) {
		ResponseQuasar quasar = new ResponseQuasar();
		try {
			satellite.setName(satelliteName);
			SatelliteContainer satellites = new SatelliteContainer();
			satellites.getSatellites().add(satellite);
			executeGetLocationMessage(satellites);
		} catch (QuasarException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(quasar).build();
	}

	private ResponseQuasar executeGetLocationMessage(SatelliteContainer satellites) throws QuasarException {
		ResponseQuasar quasar = new ResponseQuasar();
		getLocation.setInput(satellites);
		getLocation.execute();
		quasar.setPosition(getLocation.getOut());

		getMessage.setInput(satellites);
		getMessage.execute();
		quasar.setMessage(getMessage.getOut());
		return quasar;
	}

}
