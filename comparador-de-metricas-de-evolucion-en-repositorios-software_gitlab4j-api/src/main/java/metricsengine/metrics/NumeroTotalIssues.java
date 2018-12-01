package metricsengine.metrics;

import metricsengine.values.IValor;
import metricsengine.values.valores.ValorEntero;

/**
 * Métrica: Número total de issues.
 * <p>
 * Número total de issues creadas en el repositorio.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class NumeroTotalIssues implements IMetric{
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
	
	/**
	 * Parámetro NTI: Número total de issues.
	 */
	private Integer nti = null;
	
	/**
	 * Resultado obtenido al calcular la métrica.
	 */
	public Integer resultado = null;
	
	/**
	 * Constructor vacio.
	 */
	public NumeroTotalIssues() {}
	
	/**
	 * Constructor que ya establece los parámetros de la métrica y calcula el resultado.
	 * 
	 * @param nti Número total de issues.
	 */
	public NumeroTotalIssues(Integer nti) {
		setParametros(nti);
		calculate();
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#calcularMetrica()
	 */
	public IValor calculate() {
		this.resultado = nti;
		return getResultado();
	}
	
	/**
	 * Establece los parámetros de la métrica.
	 * 
	 * @param nti Número total de issues.
	 */
	public void setParametros(Integer nti) {
		this.nti = nti;
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

	/* (non-Javadoc)
	 * @see motorMetricas.metricas.IMetrica#getResultado()
	 */
	public IValor getResultado() {
		return new ValorEntero(resultado);
	}
}