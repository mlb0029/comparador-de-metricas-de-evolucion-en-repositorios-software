package metricsengine.metrics;

import metricsengine.values.IValor;
import metricsengine.values.valores.ValorDecimal;

/**
 * Métrica: Commits por issue.
 * <p>
 * Número total de commits por número total de issues.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class CommitsPorIssue implements IMetric{
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Proceso de Orientación",//categoria, 
			"CommitsPorIssue",//nombre, 
			"Número de commits por issue",//descripcion, 
			"¿Cuántos commits realizados por cada issue?",//proposito, 
			"CI = NTI/NTC. NTI = Numero total de issues, NTC = Número total de commits",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"CI >= 0, Mejor valores cercanos a 1",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NTI, NTC = Contador"//tipoMedida
	);
	
	/**
	 * Parámetro NTI: Número total de issues.
	 */
	public Integer nti = null;
	
	/**
	 * Parámetro NTC: Número total de commits.
	 */
	public Integer ntc = null;
	
	/**
	 * Resultado obtenido al calcular la métrica.
	 */
	public Double resultado = null;
	
	/**
	 * Constructor vacio.
	 */
	public CommitsPorIssue() {}
	
	/**
	 * Constructor que ya establece los parámetros de la métrica y calcula el resultado.
	 * 
	 * @param nti Parámetro NTI: Número total de issues.
	 * @param ntc Parámetro NTC: Número total de commits.
	 */
	public CommitsPorIssue(Integer nti, Integer ntc) {
		setParametros(nti, ntc);
		calculate();
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#calcularMetrica()
	 */
	public IValor calculate() {
		this.resultado = (double) (nti/ntc);
		return getResultado();
	}
	
	/**
	 * Establece los parámetros de la métrica.
	 * 
	 * @param nti Parámetro NTI: Número total de issues.
	 * @param ntc Parámetro NTC: Número total de commits.
	 */
	public void setParametros(Integer nti, Integer ntc) {
		this.nti = nti;
		this.ntc = ntc;
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
	 * Obtiene Parámetro NTI: Número total de issues.
	 * 
	 * @return Parámetro NTI: Número total de issues.
	 */
	public Integer getNti() {
		return nti;
	}

	/**
	 * Obtiene Parámetro NTC: Número total de commits.
	 * 
	 * @return Parámetro NTC: Número total de commits.
	 */
	public Integer getNtc() {
		return ntc;
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#getResultado()
	 */
	public IValor getResultado() {
		return new ValorDecimal(resultado);
	}
}
