package conexion;

/**
 * Tipos de conexión.
 * 
 * @author MALB
 * @since 01/11/2018
 */
public enum TipoConexion {
	/**
	 * Conexión con el API, autenticando un usuario mediante algún método de autenticación.
	 */
	AUTENTICADA,
	
	/**
	 * Conexión con el API sin autenticar.
	 * <p>
	 * Solo permite acceder a repositorios públicos.
	 */
	SIN_AUTENTICAR
}
