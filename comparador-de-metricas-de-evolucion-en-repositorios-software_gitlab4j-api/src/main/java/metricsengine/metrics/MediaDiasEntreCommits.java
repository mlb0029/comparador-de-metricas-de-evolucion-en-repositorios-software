package metricsengine.metrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import metricsengine.values.IValor;
import metricsengine.values.valores.ValorDecimal;

/**
 * Media de días que pasan entre commits.
 * 
 * @author MALB
 * @since 17/11/2018
 *
 */
public class MediaDiasEntreCommits implements metricsengine.metrics.IMetric{
	/**
	 * Descripción de la métrica.
	 */
	public static final DescripcionMetrica descripcion = new DescripcionMetrica(
			"Constantes de tiempo",//categoria, 
			"MediaDiasEntreCommits",//nombre, 
			"Media de días que pasan entre dos commits consecutivos",//descripcion, 
			"¿Cúanto tiempo suele pasar desde un commit hasta el siguiente?",//proposito, 
			"MDEC = [Sumatorio de (TCi-TCj) desde i=1, j=0 hasta i=NTC] / NTC. NTC = Número total de commits, TC = Tiempo de Commit",//formula, 
			"Repositorio de un gestor de repositorios",//fuenteDeMedicion, 
			"MDEC > 0. Cuanto más pequeño mejor.",//interpretacion, 
			"Ratio",//tipoEscala, 
			"NTC = Contador; TC = Tiempo"//tipoMedida
	);

	/**
	 * Parámetro: Fechas de creación de todos los commits.
	 */
	private List<Date> fechasCommits = new ArrayList<Date>();
	
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
	public MediaDiasEntreCommits() {}
	
	/**
	 * Constructor que ya establece los parámetros de la métrica y calcula el resultado.
	 * 
	 * @param fechasCommits Parámetro: Fechas de creación de todos los commits.
	 * @param ntc Parámetro NTC: Número total de commits.
	 */
	public MediaDiasEntreCommits(List<Date> fechasCommits, Integer ntc) {
		setParametros(fechasCommits, ntc);
		calculate();
	}

	/* (non-Javadoc)
	 * @see motorMetricas.defMetricas.IMetrica#calcularMetrica()
	 */
	@Override
	public IValor calculate() {
		try {
			List<Integer> diasEntreCommits = new ArrayList<Integer>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			fechasCommits = fechasCommits.stream().sorted().collect(Collectors.toList());
			for (int i = 0, j = 1; j < fechasCommits.size(); i++, j++) {
				Date fechaInicial = dateFormat.parse(fechasCommits.get(i).toString());
				Date fechaFinal = dateFormat.parse(fechasCommits.get(j).toString());
				Integer dias = (int) (fechaFinal.getTime() - fechaInicial.getTime());
				diasEntreCommits.add(dias);
			}
			Integer sumDias = diasEntreCommits.stream().reduce(0, Integer::sum);
			this.resultado = (double) (sumDias / ntc);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getResultado();
	}

	/**
	 * Establece los parámetros de la métrica.
	 * 
	 * @param fechasCommits Parámetro: Fechas de creación de todos los commits.
	 * @param ntc Parámetro NTC: Número total de commits.
	 */
	public void setParametros(List<Date> fechasCommits, Integer ntc) {
		this.fechasCommits = fechasCommits;
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
	 * Obtiene Parámetro: Fechas de creación de todos los commits.
	 * 
	 * @return Parámetro: Fechas de creación de todos los commits.
	 */
	public List<Date> getFechasCommits() {
		return fechasCommits;
	}

	/**
	 * Obtiene Parámetro: Número total de commits.
	 * 
	 * @return Parámetro: Número total de commits.
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
