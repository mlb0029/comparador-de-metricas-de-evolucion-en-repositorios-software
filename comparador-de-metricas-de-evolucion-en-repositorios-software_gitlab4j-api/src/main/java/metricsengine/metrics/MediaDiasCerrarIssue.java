package metricsengine.metrics;

import java.util.ArrayList;
import java.util.List;

import metricsengine.values.IValor;
import metricsengine.values.valores.ValorDecimal;

/**
 * Métrica: Media de días que se tarda en cerrar una issue.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class MediaDiasCerrarIssue implements IMetric{
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Constantes de tiempo",//categoria, 
			"MediaDiasCerrarIssue",//nombre, 
			"Media de dias que se tarda en cerrar una Issue",//descripcion, 
			"¿Cuánto se tarda de media en cerrar una Issue?",//proposito, 
			"MDCI = [Sumatorio de (TFi - TIi) i = 0 hasta i = NTIC] / NTIC. NTIC = Número de issues cerradas, TF = tiempo de cierre del issue, TI = tiempo de inicio del issue",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"MDCI >= 0. Cuánto más pequeño mejor",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NTIC = Contador; TF, TI = Tiempo"//tipoMedida
	);
	
	/**
	 * Parámetro diasEnCerrarIssues: Conjunto de días que han pasado abiertas las issues ya cerradas.
	 */
	private List<Integer> diasEnCerrarIssues = new ArrayList<Integer>();

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
	public MediaDiasCerrarIssue() {}
	
	/**
	 * Constructor que ya establece los parámetros de la métrica y calcula el resultado.
	 * 
	 * @param diasEnCerrarIssues Parámetro diasEnCerrarIssues: Conjunto de días que han pasado abiertas las issues ya cerradas.
	 * @param ntic Parámetro NTIC: Número de issues cerradas.
	 */
	public MediaDiasCerrarIssue(List<Integer> diasEnCerrarIssues, Integer ntic) {
		this.diasEnCerrarIssues = diasEnCerrarIssues;
		this.ntic = ntic;
		calculate();
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#calcularMetrica()
	 */
	public IValor calculate() {
		Integer sumaDias = diasEnCerrarIssues.stream().reduce(0, Integer::sum);
		this.resultado = (double) (sumaDias / ntic);
		return getResultado();
	}
	
	/**
	 * Establece los parámetros de la métrica.
	 * 
	 * @param diasEnCerrarIssues Parámetro diasEnCerrarIssues: Conjunto de días que han pasado abiertas las issues ya cerradas.
	 * @param ntic Parámetro NTIC: Número de issues cerradas.
	 */
	public void setParametros(List<Integer> diasEnCerrarIssues, Integer ntic) {
		this.diasEnCerrarIssues = diasEnCerrarIssues;
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
	public List<Integer> getDiasEnCerrarIssues() {
		return diasEnCerrarIssues;
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
	@Override
	public IValor getResultado() {
		return new ValorDecimal(resultado);
	}
}
	