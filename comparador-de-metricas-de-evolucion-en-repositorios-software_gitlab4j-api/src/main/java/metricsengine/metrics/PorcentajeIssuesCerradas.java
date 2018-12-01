package metricsengine.metrics;

import metricsengine.values.IValor;
import metricsengine.values.valores.ValorDecimal;

/**
 * Métrica: Porcentaje de issues cerradas.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class PorcentajeIssuesCerradas implements IMetric {
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
	
	/**
	 * Parámetro NTI: Número total de issues.
	 */
	private Integer nti = null;
	
	/**
	 * Parámetro NTIC: Número de issues cerradas.
	 */
	private Integer ntic = null;
	
	/**
	 * Resultado obtenido al calcular la métrica.
	 */
	public Double resultado = null;

	/**
	 * Constructor vacio.
	 */
	public PorcentajeIssuesCerradas() {}
		
	/**
	 * Constructor que ya establece los parámetros de la métrica y calcula el resultado.
	 * 
	 * @param nti Número total de issues.
	 * @param ntic Número de issues cerradas.
	 */
	public PorcentajeIssuesCerradas(Integer nti, Integer ntic) {
		this.nti = nti;
		this.ntic = ntic;
		calculate();
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#calcularMetrica()
	 */
	public IValor calculate() {
		this.resultado = (double) ((ntic/nti)*100);
		return getResultado();
	}
	
	/**
	 * Establece los parámetros de la métrica.
	 * 
	 * @param nti Número total de issues.
	 * @param ntic Número de issues cerradas.
	 */
	public void setParametros(Integer nti, Integer ntic) {
		this.nti = nti;
		this.ntic = ntic;
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
	 * Obtiene Parámetro: Número total de issues.
	 * 
	 * @return Parámetro: Número total de issues.
	 */
	public Integer getNti() {
		return nti;
	}

	/**
	 * Obtiene Parámetro: Número de issues cerradas.
	 * 
	 * @return Parámetro: Número de issues cerradas.
	 */
	public Integer getNtic() {
		return ntic;
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#getResultado()
	 */
	public IValor getResultado() {
		return new ValorDecimal(resultado);
	}
}
