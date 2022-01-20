/**
 * 
 */
package com.quasar.command;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.quasar.comun.Command;
import com.quasar.comun.QuasarException;
import com.quasar.dto.FilterGet;
import com.quasar.model.Satellite;

/**
 * @author jhon hernandez
 *
 */
public class GetSatellites extends Command<FilterGet, List<Satellite>> {

	@PersistenceContext(unitName = "Quasar-persistence-unit")
	private EntityManager em;

	@Override
	protected void preValidate() throws QuasarException {
		isValid = true;
	}

	@Override
	protected void executeCommand() throws QuasarException {
		TypedQuery<Satellite> findAllQuery = em.createQuery("SELECT DISTINCT s FROM Satellite s ORDER BY s.id",
				Satellite.class);
		if (null != input.getStart()) {
			findAllQuery.setFirstResult(input.getStart());
		}
		if (null != input.getMax()) {
			findAllQuery.setMaxResults(input.getMax());
		}
		result = findAllQuery.getResultList();
	}

	@Override
	public List<Satellite> getOut() {
		return result;
	}

}
