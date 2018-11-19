package motorMetricas.metricas;

import motorMetricas.DescripcionMetrica;

/**
 * Métrica: Número total de issues.
 * <p>
 * Número total de issues creadas en el repositorio.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class NumeroTotalIssues {
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Proceso de Orientación",//categoria, 
			"NumeroTotalIssues",//nombre, 
			"Número total de issues creadas en el repositorio",//descripcion, 
			"¿Cuántas issues se han definido en el repositorio?",//propósito, 
			"NTI. NTI = número total de issues",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"NTI >= 0. Mejor valores bajos",//interpretación, 
			"Absoluta",//tipoEscala, 
			"NTI = Contador"//tipoMedida
	);
}
