package metricsengine.metrics;

import metricsengine.values.IValor;

/**
 * Funciones esenciales de na métrica.
 * 
 * @author MALB
 * @since 22/11/2018
 */
public interface IMetric {
	
	/**
	 * Calcula y devuelve el resultado de la métrica.
	 * 
	 * @return Resultado de la métrica.
	 */
	IValor calculate();
}
