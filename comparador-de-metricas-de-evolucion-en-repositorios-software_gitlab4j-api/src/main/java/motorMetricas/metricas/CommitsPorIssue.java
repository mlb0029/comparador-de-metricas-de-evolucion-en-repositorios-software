package motorMetricas.metricas;

import motorMetricas.DescripcionMetrica;

/**
 * Métrica: Commits por issue.
 * <p>
 * Número total de commits por número total de issues.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class CommitsPorIssue {
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Proceso de Orientación",//categoria, 
			"CommitsPorIssue",//nombre, 
			"Número de commits por issue",//descripcion, 
			"¿Cuántos commits realizados por cada issue?",//proposito, 
			"CI = NTC/NTI. NTI = Numero total de issues, NTC = Número total de commits",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"CI >= 0, Si se acerca a 1 se definen bien las issues, si alto: no se definen bien las issues, si bajo: desarrollo del proyecto lento",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NTI, NTC = Contador"//tipoMedida
	);
}
