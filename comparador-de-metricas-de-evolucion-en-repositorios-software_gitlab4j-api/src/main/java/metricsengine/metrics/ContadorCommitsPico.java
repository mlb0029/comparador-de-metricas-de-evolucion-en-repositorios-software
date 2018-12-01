/**
 * 
 */
package metricsengine.metrics;

import metricsengine.values.IValor;
import metricsengine.values.valores.ValorDecimal;

/**
 * Métrica: Contador de commits en mes pico.
 * <p>
 * Número de commits en el mes que más commits se han realizado por número total de commits.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class ContadorCommitsPico implements IMetric {
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Constantes de tiempo",//categoria, 
			"ContadorCommitsPico",//nombre, 
			"Número de commits en el mes que más commits se han realizado en relación con el número total de commits",//descripcion, 
			"¿Cuál es la proporción de trabajo realizado en el mes con mayor número de cambios?",//proposito, 
			"CCP = NCMP / NTC. NCMP = Número de commits en el mes pico, NTC = Número total de commits",//formula,
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"0 <= CCP <= 1. Mejor valores intermedios",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NCMP, NTC = Contador"//tipoMedida
	);

	/**
	 * Parámetro NCMP: Número de commits en mes pico.
	 */
	private Integer ncmp = null;
	
	/**
	 * Parámetro NTC: Número total de commits.
	 */
	private Integer ntc = null;
	
	/**
	 * Resultado obtenido al calcular la métrica.
	 */
	private Double resultado = null;
	
	/**
	 * Constructor vacio.
	 */
	public ContadorCommitsPico() {}
	
	/**
	 * Constructor que ya establece los parámetros de la métrica y calcula el resultado.
	 * 
	 * @param ncmp Parámetro NCMP: Número de commits en mes pico.
	 * @param ntc Parámetro NTC: Número total de commits.
	 */
	public ContadorCommitsPico(Integer ncmp, Integer ntc) {
		setParametros(ncmp, ntc);
		calculate();
	}

	/**
	 * Establece los parámetros de la métrica.
	 * 
	 * @param ncmp Parámetro NCMP: Número de commits en mes pico.
	 * @param ntc Parámetro NTC: Número total de commits.
	 */
	public void setParametros(Integer ncmp, Integer ntc) {
		this.ncmp = ncmp;
		this.ntc = ntc;
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#calcularMetrica()
	 */
	@Override
	public IValor calculate() {
		resultado = (double) (ncmp/ntc);
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
	 * Devuelve Parámetro NCMP: Número de commits en mes pico.
	 * 
	 * @return Parámetro NCMP: Número de commits en mes pico.
	 */
	public Integer getNcmp() {
		return ncmp;
	}
	
	/**
	 * Devuelve Parámetro NTC: Número total de commits.
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
