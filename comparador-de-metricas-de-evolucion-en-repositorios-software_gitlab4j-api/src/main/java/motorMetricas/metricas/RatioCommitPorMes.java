package motorMetricas.metricas;

import motorMetricas.DescripcionMetrica;

/**
 * Métrica: Ratio de commits por mes.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class RatioCommitPorMes {
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Constantes de tiempo",//categoria, 
			"RatioCommitPorMes",//nombre, 
			"Muestra el número de commits relativos al número de meses",//descripcion, 
			"¿Cuál es el número medio de cambios por mes?",//proposito, 
			"RCM = NTC / 12",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"RCM > 0. Cuanto más alto mejor",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NTC = Contador"//tipoMedida
	);
}
