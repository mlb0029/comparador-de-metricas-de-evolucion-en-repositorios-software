package conexion;

/**
 * Fábrica de conexión a los APIs.
 * 
 * @author MALB
 * @since 13/11/2018
 */
public interface IFabricaConexion {
	
	/**
	 * Establece una conexión con un API.
	 * 
	 * @param tipoDeConexion Tipo de conexión.
	 * @param args Argumentos para establecer una conexión: token, usuario y contraseña, etc.
	 * @return Conexión con el API.
	 */
	IConexionAPI establecerConexion(EnumTipoAutenticación tipoAutenticacion, String[] args);
}
