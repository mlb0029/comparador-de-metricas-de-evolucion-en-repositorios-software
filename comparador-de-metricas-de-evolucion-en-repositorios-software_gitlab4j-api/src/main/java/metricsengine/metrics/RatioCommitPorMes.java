package metricsengine.metrics;

import metricsengine.values.IValor;
import metricsengine.values.valores.ValorDecimal;

/**
 * Métrica: Ratio de commits por mes.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class RatioCommitPorMes implements IMetric {
	
	/**
	 * Descripción de la métrica.
	 */
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

	/**
	 * Parámetro NTC: Número total de commits.
	 */
	private Integer ntc = null;
	
	/**
	 * Resultado obtenido al calcular la métrica.
	 */
	private Double resultado = null;
	
	
	
	public RatioCommitPorMes() {}
	
	
	
	public RatioCommitPorMes(Integer ntc) {
		setParametros(ntc);
	}

	/**
	 * Establece los parámetros de la métrica.
	 * 
	 * @param ntc Parámetro NTC: Número total de commits.
	 */
	public void setParametros(Integer ntc) {
		this.ntc = ntc;
	}

	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#calcularMetrica()
	 */
	@Override
	public IValor calculate() {
		resultado = (double) (ntc/12);
		return getResultado();
	}
	
	/**
	 * Devuelve la descripción de la métrica.
	 * 
	 * @return Descripción de la métrica.
	 */
	public static DescripcionMetrica getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Parámetro NTC: Número total de commits.
	 * 
	 * @return Parámetro NTC: Número total de commits.
	 */
	public Integer getNtc() {
		return ntc;
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#getResultado()
	 */
	@Override
	public IValor getResultado() {
		return new ValorDecimal(resultado);
	}
}
