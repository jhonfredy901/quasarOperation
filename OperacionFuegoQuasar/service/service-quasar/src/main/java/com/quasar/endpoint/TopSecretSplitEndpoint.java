package com.quasar.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.quasar.command.CommandLocation;
import com.quasar.command.CommandMessage;
import com.quasar.comun.GenericResponse;
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

	/**
	 * Comando concreto que permite obtener la localizacion de una nave
	 */
	@Inject
	private CommandLocation getLocation;
	/***
	 * Comando concreto que permite obtener el mensaje enviado
	 */
	@Inject
	private CommandMessage getMessage;
	/**
	 * Respuesta particular cuando se obtiene el mensaje y localización
	 */
	private ResponseQuasar quasar;
	/**
	 * Respuesta generica del servicio
	 */
	private GenericResponse generic;

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{satellite_name}")
	public Response getSplit(@PathParam("satellite_name") String satelliteName, SatelliteDto satellite) {
		quasar = new ResponseQuasar();
		generic = new GenericResponse();
		try {
			satellite.setName(satelliteName);
			executeGetLocationMessage(satellite);
		} catch (QuasarException e) {
			generic.setMessage(e.getMessage());
			generic.setData(Status.NOT_FOUND);
			return Response.ok(generic).build();
		}
		return Response.ok(quasar).build();
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("{satellite_name}")
	public Response postSplit(@PathParam("satellite_name") String satelliteName, SatelliteDto satellite) {
		quasar = new ResponseQuasar();
		generic = new GenericResponse();
		try {
			satellite.setName(satelliteName);
			executeGetLocationMessage(satellite);
		} catch (QuasarException e) {
			generic.setMessage(e.getMessage());
			generic.setData(Status.NOT_FOUND);
			return Response.ok(generic).build();
		}
		return Response.ok(quasar).build();
	}

	/**
	 * Permite generalizar el consumo de los comandos para obtener ubicación y
	 * mensaje
	 * 
	 * @param satellite
	 * @return
	 * @throws QuasarException
	 */
	private ResponseQuasar executeGetLocationMessage(SatelliteDto satellite) throws QuasarException {
		quasar = new ResponseQuasar();
		SatelliteContainer satelliteContainer = new SatelliteContainer();
		List<SatelliteDto> satellites = new ArrayList<>();
		satellites.add(satellite);
		satelliteContainer.setSatellites(satellites);

		getLocation.setInput(satelliteContainer);
		getLocation.execute();
		quasar.setPosition(getLocation.getOut());

		getMessage.setInput(satelliteContainer);
		getMessage.execute();
		quasar.setMessage(getMessage.getOut());
		return quasar;
	}

}
