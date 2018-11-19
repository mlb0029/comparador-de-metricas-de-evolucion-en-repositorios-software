package motorMetricas.metricas;

import motorMetricas.DescripcionMetrica;

/**
 * Métrica: Porcentaje de issues cerradas.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class PorcentajeIssuesCerradas {
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Proceso de Orientación",//categoria, 
			"PorcentajeIssuesCerradas",//nombre, 
			"Porcentaje de issues cerradas",//descripcion, 
			"¿Qué porcentaje de issues definidas en el repositorio se han cerrado?",//proposito, 
			"PIC = NTIC*100/NTI. NTIC = Número total de issues cerradas, NTI = Numero total de issues",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"0 <= PIC <= 100. Cuanto más alto mejor.",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NTI, NTIC = Contador"//tipoMedida
	);
}
