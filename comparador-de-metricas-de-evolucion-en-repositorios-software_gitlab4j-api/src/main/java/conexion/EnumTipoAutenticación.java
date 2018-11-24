package conexion;

/**
 * Tipos de autenticación.
 * 
 * @author MALB
 * @since 13/11/2018
 *
 */
public enum EnumTipoAutenticación {
	/**
	 * Conexión no autenticada.
	 */
	SIN_AUTENTICAR,
	/**
	 * Login mediante usuario y contraseña.
	 */
	USUARIO_CONTRASENIA,
	/**
	 * Login mediante token.
	 */
	TOKEN
}
