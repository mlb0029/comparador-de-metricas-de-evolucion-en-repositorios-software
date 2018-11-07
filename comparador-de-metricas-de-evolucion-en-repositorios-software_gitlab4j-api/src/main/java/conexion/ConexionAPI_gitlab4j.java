package conexion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProjectFilter;

import util.Constantes;

/**
 * Fachada entre la aplicación y GitLab API.
 * 
 * @author MALB
 * @since 01/11/2018
 */
public class ConexionAPI_gitlab4j implements IConexionAPI {

	/**
	 * Instancia de la conexión.
	 */
	private static ConexionAPI_gitlab4j conexion;

	/**
	 * Tipo de conexión.
	 */
	private TipoConexion tipoConexion;

	/**
	 * Java GitLab API: gitlab4j
	 */
	private GitLabApi gitLabApi;

	/**
	 * Contructor que recibe como parámetro el tipo de conexión.
	 * 
	 * @param tipoConexion Autenticada o sin autenticar.
	 */
	private ConexionAPI_gitlab4j(TipoConexion tipoConexion) {
		this.tipoConexion = tipoConexion;
	}

	/**
	 * Establece conexión con GitLab API sin autenticación de usuario.
	 * 
	 * @return Una fachada de conexión entre la aplicación y GitLab API.
	 */
	public static IConexionAPI establecerConexion() {
		conexion = new ConexionAPI_gitlab4j(TipoConexion.SIN_AUTENTICAR);
		conexion.gitLabApi = new GitLabApi(Constantes.HOST_URL, "");
		return conexion;
	}

	/**
	 * Establece conexión con GitLab API mediante usuario y contraseña.
	 * 
	 * @param usuario
	 * @param contrasenia
	 * @return Una fachada de conexión entre la aplicación y GitLab API.
	 * @throws GitLabApiException
	 */
	public static IConexionAPI establecerConexion(String usuario, String contrasenia) throws GitLabApiException {
		conexion = new ConexionAPI_gitlab4j(TipoConexion.AUTENTICADA);
		conexion.gitLabApi = GitLabApi.oauth2Login(Constantes.HOST_URL, usuario, contrasenia.toCharArray());
		return conexion;
	}

	/**
	 * Establece conexión con GitLab API mediante private_token (personal access token).
	 * 
	 * @param privateToken
	 * @return Una fachada de conexión entre la aplicación y GitLab API.
	 */
	public static IConexionAPI establecerConexion(String privateToken) {
		conexion = new ConexionAPI_gitlab4j(TipoConexion.AUTENTICADA);
		conexion.gitLabApi = new GitLabApi(Constantes.HOST_URL, privateToken);
		return conexion;
	}

	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#obtenerNombresRepositoriosUsuario(java.lang.String)
	 */
	@Override
	public List<String> obtenerURLRepositoriosUsuario(String nombreUsuario) throws GitLabApiException {
		if (nombreUsuario.equals("")) {
			return obtenerURLRepositoriosUsuario();
		}
		List<String> nombresRepositorios = new ArrayList<String>();
		ProjectFilter filtro = new ProjectFilter();
		List<Project> proyectos = gitLabApi.getProjectApi().getUserProjects(nombreUsuario, filtro);
		nombresRepositorios = proyectos.stream().map(p -> p.getHttpUrlToRepo()).collect(Collectors.toList());
		return nombresRepositorios;
	}
	
	/**
	 * @return
	 * @throws GitLabApiException
	 */
	public List<String> obtenerURLRepositoriosUsuario() throws GitLabApiException {
		List<String> nombresRepositorios = new ArrayList<String>();
		List<Project> proyectos = gitLabApi.getProjectApi().getOwnedProjects();
		nombresRepositorios = proyectos.stream().map(p -> p.getHttpUrlToRepo()).collect(Collectors.toList());
		return nombresRepositorios;
	}
	
	//git@gitlab.com:mlb0029/comparador-de-metricas-de-evolucion-en-repositorios-software.git
	
	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#getProyecto(java.lang.String)
	 */
	@Override
	public String obtenerURLProyecto(String nombreUsuario, String nombreProyecto) throws GitLabApiException{
		List<String> nombresRepositorios = new ArrayList<String>();
		ProjectFilter filtro = new ProjectFilter();
		filtro.withSearch(nombreProyecto);
		List<Project> proyectos = gitLabApi.getProjectApi().getUserProjects(nombreUsuario, filtro);
		nombresRepositorios = proyectos.stream().map(p -> p.getHttpUrlToRepo()).collect(Collectors.toList());
		return (nombresRepositorios.size() >= 1)?nombresRepositorios.get(0):"No se ha encontrado un proyecto de nombre '" + nombreProyecto + "' del usuario '" + nombreUsuario + "'.";
	}

	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#comprobarConexion()
	 */
	@Override
	public Boolean comprobarConexion() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#obtenerIDProyecto(java.lang.String)
	 */
	@Override
	public String obtenerIDProyecto(String URLProyecto) throws GitLabApiException {
		String sProyecto = URLProyecto.replaceAll(Constantes.HOST_URL + "/", "");
		String nombreProyecto = sProyecto.split("/")[sProyecto.split("/").length - 1];
		String propietarioYGrupo = sProyecto.replaceAll("/" + nombreProyecto, "");
		Project pProyecto = gitLabApi.getProjectApi().getProject(propietarioYGrupo, nombreProyecto);
		return pProyecto.getId().toString();
	}
	
	
}
