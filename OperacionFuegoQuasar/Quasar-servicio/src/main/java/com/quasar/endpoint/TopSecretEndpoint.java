package com.quasar.endpoint;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.quasar.comand.CommandLocation;
import com.quasar.comun.QuasarException;
import com.quasar.dto.SatelliteContainer;

/**
 * Servicio que permite obtener la ubicacion y el mensaje emitido por una nave
 * 
 * @author jhon hernandez
 *
 */
@Path("/topsecret")
//@Stateless
public class TopSecretEndpoint {

	@Inject
	private CommandLocation getLocation;

	@POST
	@Produces("application/json")
	@Consumes({ "application/json", "application/text" })
	public Response create(SatelliteContainer satellites) {
		try {
//			JSONObject json = new JSONObject(jsonSatellites);
//			JSONArray satellites = (JSONArray) json.get("satellites");
			getLocation.setInput(satellites);
			getLocation.execute();
		} catch (QuasarException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(getLocation.getOut()).build();
	}

}
