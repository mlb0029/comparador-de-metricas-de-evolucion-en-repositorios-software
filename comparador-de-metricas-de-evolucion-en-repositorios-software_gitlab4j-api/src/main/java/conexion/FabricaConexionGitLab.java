package conexion;

/**
 * Clase que permite establecer una conexión con el API de GitLab.
 * 
 * @author MALB
 * @since 13/11/2018
 *
 */
public class FabricaConexionGitLab implements IFabricaConexion {

	/**
	 * Unica instancia de la clase.
	 */
	private static FabricaConexionGitLab instancia = null;

	/**
	 * Constructor privado.
	 */
	private FabricaConexionGitLab() {	}

	/**
	 * Obtiene la instancia de la clase.
	 * 
	 * @return La instancia de la clase.
	 */
	public static FabricaConexionGitLab getInstancia() {
		if (instancia.equals(null))
			instancia = new FabricaConexionGitLab();
		return instancia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * conexion.IFabricaConexion#establecerConexion(conexion.EnumTipoAutenticación,
	 * java.lang.String[])
	 */
	@Override
	public IConexionAPI establecerConexion(EnumTipoAutenticación tipoAutenticacion, String[] args) {
		IConexionAPI conexion = null;
		try {
			switch (tipoAutenticacion) {
			case SIN_AUTENTICAR:
				conexion = ConexionGitLab.establecerConexion();
				break;
			case USUARIO_CONTRASENIA:
				conexion = ConexionGitLab.establecerConexion(args[0], args[1]);
				break;
			case TOKEN:
				conexion = ConexionGitLab.establecerConexion(args[0]);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conexion;
	}
}
