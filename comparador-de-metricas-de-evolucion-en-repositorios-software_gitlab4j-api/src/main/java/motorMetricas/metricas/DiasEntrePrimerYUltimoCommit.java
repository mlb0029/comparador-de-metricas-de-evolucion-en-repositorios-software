package motorMetricas.metricas;

import motorMetricas.DescripcionMetrica;

/**
 * Métrica: Días entre primer y último commit.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class DiasEntrePrimerYUltimoCommit {
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Constantes de tiempo",//categoria, 
			"DiasEntrePrimerYUltimoCommit",//nombre, 
			"Días transcurridos entre el primer y el ultimo commit",//descripcion, 
			"¿Cuantos días han pasado entre el primer y el último commit?",//proposito, 
			"DEPUC = TC2- TC1. TC2 = Tiempo de último commit, TC1 = Tiempo de primer commit.",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"DEPUC >= 0",//interpretacion, 
			"Absoluta",//tipoEscala, 
			"TC = Tiempo"//tipoMedida
	);
}
