package metricsengine.metrics;

/**
 * Descripción de una métrica.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class DescripcionMetrica {
	
	/**
	 * Categoria de la métrica.
	 */
	private String categoria;

	/**
	 * Nombre de la métrica.
	 */
	private String nombre;

	/**
	 * Descripción de la métrica.
	 */
	private String descripcion;

	/**
	 * Propósito de la métrica.
	 */
	private String proposito;

	/**
	 * Fórmula para calcular la métrica.
	 */
	private String formula;

	/**
	 * Fuente dónde se obtienen los parámetros.
	 */
	private String fuenteDeMedicion;

	/**
	 * Interpretación de la métrica.
	 */
	private String interpretacion;

	/**
	 * Tipo de escala de la métrica.
	 */
	private String tipoEscala;

	/**
	 * Tipo de medida de la métrica.
	 */
	private String tipoMedida;

	/**
	 * Costructor.
	 * 
	 * @param categoria Categoría de la métrica.
	 * @param nombre Nombre.
	 * @param descripcion Descripción.
	 * @param proposito Propósito.
	 * @param formula Fórmula para calcular la métrica.
	 * @param fuenteDeMedicion Fuente dónde se obtienen los parámetros.
	 * @param interpretacion Interpretación de la métrica.
	 * @param tipoEscala Tipo de escala.
	 * @param tipoMedida Tipo de medida.
	 */
	public DescripcionMetrica(String categoria, String nombre, String descripcion, String proposito, String formula,
			String fuenteDeMedicion, String interpretacion, String tipoEscala, String tipoMedida) {
		this.categoria = categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.proposito = proposito;
		this.formula = formula;
		this.fuenteDeMedicion = fuenteDeMedicion;
		this.interpretacion = interpretacion;
		this.tipoEscala = tipoEscala;
		this.tipoMedida = tipoMedida;
	}

	/** Obtiene la categoria de la métrica.
	 * 
	 * @return Categoria de la métrica.
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * Obtiene el nombre de la métrica.
	 * 
	 * @return Nombre de la métrica.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene la descripción de la métrica.
	 * 
	 * @return Descripción de la métrica.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Obtiene el propósito de la métrica.
	 * 
	 * @return Propósito de la métrica.
	 */
	public String getProposito() {
		return proposito;
	}

	/**
	 * Obtiene la fórmula de la métrica.
	 * 
	 * @return Fórmula de la métrica.
	 */
	public String getFormula() {
		return formula;
	}

	/**
	 * Obtiene la fuente de medición de la métrica.
	 * 
	 * @return Fuente de medición.
	 */
	public String getFuenteDeMedicion() {
		return fuenteDeMedicion;
	}

	/**
	 * Obtiene la interpretación de la métrica.
	 * 
	 * @return Interpretación de la métrica.
	 */
	public String getInterpretacion() {
		return interpretacion;
	}

	/**
	 * Obtiene el tipo de escala.
	 * 
	 * @return Tipo de escala.
	 */
	public String getTipoEscala() {
		return tipoEscala;
	}

	/**
	 * Obtiene el tipo de medida.
	 * 
	 * @return Tipo de medida.
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}
}
