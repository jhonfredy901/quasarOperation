/**
 * 
 */
package com.quasar.dto;

import java.util.List;

/**
 * @author jhernandez
 *
 */
public class SatelliteContainer {
	/**
	 * Listado de satelites enviados como apoyo para calcular localización
	 */
	private List<SatelliteDto> satellites;

	/**
	 * @return the satellites
	 */
	public List<SatelliteDto> getSatellites() {
		return satellites;
	}

	/**
	 * @param satellites the satellites to set
	 */
	public void setSatellites(List<SatelliteDto> satellites) {
		this.satellites = satellites;
	}

}
