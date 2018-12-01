package metricsengine.values.valores;

import metricsengine.values.IValor;

/**
 * Define el valor entero.
 * 
 * @author MALB
 * @since 22/11/2018
 *
 */
public class ValorEntero implements IValor {

	/**
	 * Valor entero.
	 */
	private Integer valor;
	
	/**
	 * Constructor.
	 * 
	 * @param valor Valor.
	 */
	public ValorEntero(Integer valor) {
		this.valor = valor;
	}

	/**
	 * Obtener valor.
	 * 
	 * @return Valor.
	 */
	public Integer getValor() {
		return valor;
	}
	
	/**
	 * Establecer valor.
	 * 
	 * @param valor Valor.
	 */
	public void setValor(Integer valor) {
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
