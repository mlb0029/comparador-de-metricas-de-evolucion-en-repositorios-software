package conexion;

import java.util.List;

import org.gitlab4j.api.GitLabApiException;

/**
 * Fachada de conexión a los APIs.
 * 
 * @author MALB
 * @since 01/10/2018
 *
 */
public interface IConexionAPI {
	
	/**
	 * Obtiene los nombres de los proyectos de un usuario.
	 * 
	 * @param nombreUsuario
	 * @return Lista con los nombres de los proyectos de un usuario.
	 * @throws GitLabApiException
	 */
	List<String> obtenerURLRepositoriosUsuario(String nombreUsuario) throws GitLabApiException;
	
	/**
	 * Obtiene URL de un proyecto especificando usuario y el nombre del proyecto.
	 * 
	 * @param nombreUsuario Nombre del usuario
	 * @param nombreProyecto Nombre del proyecto
	 * @return URL de proyecto
	 * @throws GitLabApiException
	 */
	String obtenerURLProyecto(String nombreUsuario, String nombreProyecto) throws GitLabApiException;
	
	/**
	 * Obtiene el ID de un proyecto especificando su URL.
	 * 
	 * @param URLProyecto
	 * @return ID del proyecto.
	 * @throws GitLabApiException 
	 */
	String obtenerIDProyecto(String URLProyecto) throws GitLabApiException;
}
