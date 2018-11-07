package conexion;

import java.io.IOException;
import java.util.List;

/**
 * Fachada de conexión a los APIs.
 * 
 * @author MALB
 * @since 01/10/2018
 *
 */
public interface IConexionAPI {
	
	/**
	 * Comprueba que se ha establecido la conexión con el API correctamente.
	 * 
	 * @return True si hay conexión, False en caso contrario.
	 */
	public Boolean comprobarConexion();
	
	/**
	 * Obtiene los nombres de los proyectos de un usuario.
	 * 
	 * @param nombreUsuario
	 * @return Lista con los nombres de los proyectos de un usuario.
	 * @throws IOException 
	 */
	List<String> obtenerURLRepositoriosUsuario(String nombreUsuario) throws IOException;
	
	/**
	 * Obtiene URL de un proyecto especificando usuario y el nombre del proyecto.
	 * 
	 * @param nombreUsuario
	 * @param nombreProyecto
	 * @return URL de proyecto
	 * @throws IOException 
	 */
	String obtenerURLProyecto(String nombreUsuario, String nombreProyecto) throws IOException;
	
	/**
	 * Obtiene el ID de un proyecto especificando su URL.
	 * 
	 * @param URLProyecto
	 * @return ID del proyecto.
	 * @throws IOException 
	 */
	String obtenerIDProyecto(String URLProyecto) throws IOException ;
}