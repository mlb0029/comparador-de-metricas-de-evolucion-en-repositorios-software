/**
 * 
 */
package motorMetricas.metricas;

import motorMetricas.DescripcionMetrica;

/**
 * Métrica: Contador de commits en mes pico.
 * <p>
 * Número de commits en el mes que más commits se han realizado por número total de commits.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class ContadorCommitsPico {
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Constantes de tiempo",//categoria, 
			"ContadorCommitsPico",//nombre, 
			"Número de commits en el mes que más commits se han realizado en relación con el número total de commits",//descripcion, 
			"¿Cuál es la proporción de trabajo realizado en el mes con mayor número de cambios?",//proposito, 
			"CCP = NCMP / NTC. NCMP = Número de commits en el mes pico, NTC = Número total de commits",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"0 <= CCP <= 1. Mejor valores intermedios",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NCMP, NTC = Contador"//tipoMedida
	);
}
