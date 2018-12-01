package metricsengine.values.valores;

import metricsengine.values.IValor;

/**
 * Define el valor Double.
 * 
 * @author MALB
 * @since 22/11/2018
 *
 */
public class ValorDecimal implements IValor {

	/**
	 * Valor Double.
	 */
	private Double valor;
	
	/**
	 * Constructor.
	 * 
	 * @param valor Valor.
	 */
	public ValorDecimal(Double valor) {
		this.valor = valor;
	}

	/**
	 * Obtener valor.
	 * 
	 * @return Valor.
	 */
	public Double getValor() {
		return valor;
	}
	
	/**
	 * Establecer valor.
	 * 
	 * @param valor Valor.
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.valores.IValor#obtenerValorTxt()
	 */
	@Override
	public String obtenerValorTxt() {
		return valor.toString();
	}
}
