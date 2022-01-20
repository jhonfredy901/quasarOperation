package com.quasar.endpoint;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.quasar.comand.CommandLocation;
import com.quasar.comand.CommandMessage;
import com.quasar.comun.QuasarException;
import com.quasar.comun.ResponseQuasar;
import com.quasar.dto.SatelliteContainer;

/**
 * Servicio que permite obtener la ubicacion y el mensaje emitido por una nave a
 * traves de trilateración
 * 
 * @author jhon hernandez
 *
 */
@Path("/topsecret")
public class TopSecretEndpoint {

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
			getLocation.setInput(satellites);
			getLocation.execute();
			quasar.setPosition(getLocation.getOut());

			getMessage.setInput(satellites);
			getMessage.execute();
			quasar.setMessage(getMessage.getOut());

		} catch (QuasarException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(quasar).build();
	}

}
