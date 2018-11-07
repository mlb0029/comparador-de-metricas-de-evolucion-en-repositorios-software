package conexion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;

import util.Constantes;

/**
 * Fachada entre la aplicación y GitLab API.
 * 
 * @author MALB
 * @since 01/11/2018
 */
public class ConexionAPI_javaGitlabAPI implements IConexionAPI {

	/**
	 * Instancia de la conexión.
	 */
	private static ConexionAPI_javaGitlabAPI conexion;

	/**
	 * Tipo de conexión.
	 */
	private TipoConexion tipoConexion;

	/**
	 * Java GitLab API: java-gitlab-api

	 */
	private GitlabAPI gitLabApi;

	/**
	 * Contructor que recibe como parámetro el tipo de conexión.
	 * 
	 * @param tipoConexion
	 */
	private ConexionAPI_javaGitlabAPI(TipoConexion tipoConexion) {
		this.tipoConexion = tipoConexion;
	}

	/**
	 * Establece conexión con GitLab API sin autenticación de usuario.
	 * 
	 * @return Una fachada de conexión entre la aplicación y GitLab API.
	 */
	public static IConexionAPI establecerConexion() {
		conexion = new ConexionAPI_javaGitlabAPI(TipoConexion.SIN_AUTENTICAR);
		conexion.gitLabApi = GitlabAPI.connect(Constantes.HOST_URL, "");
		return conexion;
	}

	/**
	 * Establece conexión con GitLab API mediante usuario y contraseña.
	 * 
	 * @param usuario
	 * @param contrasenia
	 * @return Una fachada de conexión entre la aplicación y GitLab API.
	 * @throws IOException 
	 * @throws GitLabApiException
	 */
	public static IConexionAPI establecerConexion(String usuario, String contrasenia) throws IOException {
		conexion = new ConexionAPI_javaGitlabAPI(TipoConexion.AUTENTICADA);
		GitlabSession session = GitlabAPI.connect(Constantes.HOST_URL, usuario, contrasenia); 
		conexion.gitLabApi = GitlabAPI.connect(Constantes.HOST_URL, session.getPrivateToken());
		return conexion;
	}

	/**
	 * Establece conexión con GitLab API mediante private_token (personal access token).
	 * 
	 * @param privateToken
	 * @return Una fachada de conexión entre la aplicación y GitLab API.
	 */
	public static IConexionAPI establecerConexion(String privateToken) {
		conexion = new ConexionAPI_javaGitlabAPI(TipoConexion.AUTENTICADA);
		conexion.gitLabApi = GitlabAPI.connect(Constantes.HOST_URL, privateToken);
		return conexion;
	}

	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#obtenerNombresRepositoriosUsuario(java.lang.String)
	 */
	@Override
	public List<String> obtenerURLRepositoriosUsuario(String nombreUsuario) throws IOException { // TODO obtenerURLRepositoriosUsuario
		List<String> nombresRepositorios = new ArrayList<String>();
		if (nombreUsuario.equals("")) {
			return obtenerURLRepositoriosUsuario();
		}
		return nombresRepositorios;
	}
	
	/**
	 * @return
	 * @throws IOException 
	 * @throws GitLabApiException
	 */
	public List<String> obtenerURLRepositoriosUsuario() throws IOException {
		List<String> nombresRepositorios = new ArrayList<String>();
		List<GitlabProject> proyectos = gitLabApi.getOwnedProjects();
		nombresRepositorios = proyectos.stream().map(p -> p.getHttpUrl()).collect(Collectors.toList());
		return nombresRepositorios;
	}
	
	//git@gitlab.com:mlb0029/comparador-de-metricas-de-evolucion-en-repositorios-software.git
	
	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#getProyecto(java.lang.String)
	 */
	@Override
	public String obtenerURLProyecto(String nombreUsuario, String nombreProyecto) {// TODO obtenerURLProyecto
		List<String> nombresRepositorios = new ArrayList<String>();
//		ProjectFilter filtro = new ProjectFilter();
//		filtro.withSearch(nombreProyecto);
//		List<Project> proyectos = gitLabApi.getProjectApi().getUserProjects(nombreUsuario, filtro);
//		nombresRepositorios = proyectos.stream().map(p -> p.getHttpUrlToRepo()).collect(Collectors.toList());
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
	public String obtenerIDProyecto(String URLProyecto) throws IOException {
		String sProyecto = URLProyecto.replaceAll(Constantes.HOST_URL + "/", "");
		String nombreProyecto = sProyecto.split("/")[sProyecto.split("/").length - 1];
		String propietarioYGrupo = sProyecto.replaceAll("/" + nombreProyecto, "");
		GitlabProject pProyecto = gitLabApi.getProject(propietarioYGrupo, nombreProyecto);
		return pProyecto.getId().toString();
	}
	
	
}