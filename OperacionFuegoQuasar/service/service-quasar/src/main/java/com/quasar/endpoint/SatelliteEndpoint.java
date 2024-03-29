package com.quasar.endpoint;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.quasar.command.GetSatellites;
import com.quasar.comun.GenericResponse;
import com.quasar.comun.QuasarException;
import com.quasar.dto.FilterGet;
import com.quasar.model.Satellite;

/**
 * Servicio que permite realizar operacion CRUD sobre un satellite
 * 
 * @author jhon hernandez
 */
@Path("/satellite")
public class SatelliteEndpoint {

	@PersistenceContext(unitName = "Quasar-persistence-unit")
	private EntityManager em;

	@Resource
	private UserTransaction ut;

	@Inject
	private GetSatellites getsatellites;

	@POST
	@Consumes("application/json")
	public Response create(Satellite entity) throws QuasarException {
		try {
			ut.begin();
			em.persist(entity);
			ut.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			throw new QuasarException("No fue posible realizar la creación del satelite");
		}
		return Response
				.created(UriBuilder.fromResource(SatelliteEndpoint.class).path(String.valueOf(entity.getId())).build())
				.build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) throws QuasarException {
		Satellite entity = em.find(Satellite.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			ut.begin();
			em.remove(em.contains(entity) ? entity : em.merge(entity));
			ut.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			throw new QuasarException("No fue posible realizar la eliminación del satelite");
		}
		return Response.status(Status.OK).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<Satellite> findByIdQuery = em.createQuery(
				"SELECT DISTINCT s FROM Satellite s WHERE s.id = :entityId ORDER BY s.id", Satellite.class);
		findByIdQuery.setParameter("entityId", id);
		Satellite entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public Response listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
		GenericResponse genResponse = new GenericResponse();
		FilterGet filter = new FilterGet();
		filter.setStart(startPosition);
		filter.setMax(maxResult);
		try {
			getsatellites.setInput(filter);
			getsatellites.execute();
			genResponse.setData(getsatellites.getOut());
		} catch (QuasarException e) {
			genResponse.setMessage(e.getMessage());
		}
		return Response.ok(genResponse).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(@PathParam("id") Long id, Satellite entity) throws QuasarException {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(Satellite.class, id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		try {
			ut.begin();
			entity = em.merge(entity);
			ut.commit();
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			throw new QuasarException("No fue posible realizar la actualización del satelite");
		}

		return Response.ok(entity).build();
	}
}