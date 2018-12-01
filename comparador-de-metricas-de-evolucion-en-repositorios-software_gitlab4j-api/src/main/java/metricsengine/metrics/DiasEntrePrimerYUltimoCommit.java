package metricsengine.metrics;

import java.util.Date;

import metricsengine.values.IValor;
import metricsengine.values.valores.ValorEntero;

/**
 * Métrica: Días entre primer y último commit.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class DiasEntrePrimerYUltimoCommit implements metricsengine.metrics.IMetric {
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Constantes de tiempo",//categoria, 
			"DiasEntrePrimerYUltimoCommit",//nombre, 
			"Días transcurridos entre el primer y el ultimo commit",//descripcion, 
			"¿Cuantos días han pasado entre el primer y el último commit?",//proposito, 
			"DEPUC = TC2- TC1. TC2 = Tiempo de último commit, TC1 = Tiempo de primer commit.",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"DEPUC >= 0",//interpretacion, 
			"Absoluta",//tipoEscala, 
			"TC = Tiempo"//tipoMedida
	);

	/**
	 * Parámetro TC2: fecha ultimo commit.
	 */
	private Date fechaUltimoCommit = null;
	
	/**
	 * Parámetro TC1: fecha primer commit.
	 */
	private Date fechaPrimerCommit = null;
	
	/**
	 * Resultado obtenido al calcular la métrica.
	 */
	private Integer resultado = null;
	
	/**
	 * Constructor vacio.
	 */
	public DiasEntrePrimerYUltimoCommit() {}
	
	/**
	 * Constructor que ya establece los parámetros de la métrica y calcula el resultado.
	 * 
	 * @param fechaUltimoCommit Parámetro TC2: fecha ultimo commit.
	 * @param fechaPrimerCommit Parámetro TC1: fecha primer commit.
	 */
	public DiasEntrePrimerYUltimoCommit(Date fechaUltimoCommit, Date fechaPrimerCommit) {
		setParametros(fechaUltimoCommit, fechaPrimerCommit);
		calculate();
	}
	
	/**
	 * Establece los parámetros de la métrica.
	 * 
	 * @param fechaUltimoCommit Parámetro TC2: fecha ultimo commit.
	 * @param fechaPrimerCommit Parámetro TC1: fecha primer commit.
	 */
	public void setParametros(Date fechaUltimoCommit, Date fechaPrimerCommit) {
		this.fechaUltimoCommit = fechaUltimoCommit;
		this.fechaPrimerCommit = fechaPrimerCommit;
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#calcularMetrica()
	 */
	@Override
	public IValor calculate() {
		this.resultado = (int) (fechaUltimoCommit.getTime() - fechaUltimoCommit.getTime());
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
	 * Devuelve Parámetro TC2: fecha ultimo commit.
	 * 
	 * @return Parámetro TC2: fecha ultimo commit.
	 */
	public Date getFechaUltimoCommit() {
		return fechaUltimoCommit;
	}
	
	/**
	 * Devuelve Parámetro TC1: fecha primer commit.
	 * 
	 * @return Parámetro TC1: fecha primer commit.
	 */
	public Date getFechaPrimerCommit() {
		return fechaPrimerCommit;
	}
	
	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#getResultado()
	 */
	@Override
	public IValor getResultado() {
		return new ValorEntero(resultado);
	}


	
	
}
