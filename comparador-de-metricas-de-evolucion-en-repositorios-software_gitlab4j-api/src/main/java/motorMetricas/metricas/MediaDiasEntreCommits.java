package motorMetricas.metricas;

import motorMetricas.DescripcionMetrica;

/**
 * Media de días que pasan entre commits.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class MediaDiasEntreCommits {
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Constantes de tiempo",//categoria, 
			"MediaDiasEntreCommits",//nombre, 
			"Media de días que pasan entre dos commits consecutivos",//descripcion, 
			"¿Cúanto tiempo suele pasar desde un commit hasta el siguiente?",//proposito, 
			"MDEC = [Sumatorio de (TCi-TCj) desde i=1, j=0 hasta i=NTC] / NTC. NTC = Número total de commits, TC = Tiempo de Commit",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"MDEC > 0. Cuanto más pequeño mejor.",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NTC = Contador; TC = Tiempo"//tipoMedida
	);
}
