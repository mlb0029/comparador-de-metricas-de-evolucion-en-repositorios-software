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
public class ConexionGitLab implements IConexionAPI {

	/**
	 * Instancia de la conexión.
	 */
	private static ConexionGitLab conexion;

	/**
	 * Tipo de conexión.
	 */
	private EnumTipoConexion tipoConexion;

	/**
	 * Java GitLab API: gitlab4j
	 */
	private GitLabApi gitLabApi;

	/**
	 * Contructor que recibe como parámetro el tipo de conexión.
	 * 
	 * @param tipoConexion Autenticada o sin autenticar.
	 */
	private ConexionGitLab(EnumTipoConexion tipoConexion) {
		this.tipoConexion = tipoConexion;
	}

	/**
	 * Establece conexión con GitLab API sin autenticación de usuario.
	 * 
	 * @return Una fachada de conexión entre la aplicación y GitLab API.
	 */
	public static ConexionGitLab establecerConexion() {
		conexion = new ConexionGitLab(EnumTipoConexion.SIN_AUTENTICAR);
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
	public static ConexionGitLab establecerConexion(String usuario, String contrasenia) {
		try {
			conexion = new ConexionGitLab(EnumTipoConexion.AUTENTICADA);
			conexion.gitLabApi = GitLabApi.oauth2Login(Constantes.HOST_URL, usuario, contrasenia.toCharArray());
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conexion;
	}

	/**
	 * Establece conexión con GitLab API mediante private_token (personal access token).
	 * 
	 * @param privateToken
	 * @return Una fachada de conexión entre la aplicación y GitLab API.
	 * @throws GitLabApiException 
	 */
	public static ConexionGitLab establecerConexion(String privateToken) {
		try {
			conexion = new ConexionGitLab(EnumTipoConexion.AUTENTICADA);
			conexion.gitLabApi = new GitLabApi(Constantes.HOST_URL, privateToken);
			conexion.gitLabApi.getUserApi().getCurrentUser();
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conexion;
	}

	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#obtenerNombresRepositoriosUsuario(java.lang.String)
	 */
	@Override
	public List<String> obtenerURLRepositoriosUsuario(String nombreUsuario) {
		List<String> nombresRepositorios = new ArrayList<String>();
		try {
			if (nombreUsuario.equals("")) {
				return obtenerURLRepositoriosUsuario();
			}
			ProjectFilter filtro = new ProjectFilter();
			List<Project> proyectos;
			proyectos = gitLabApi.getProjectApi().getUserProjects(nombreUsuario, filtro);
			nombresRepositorios = proyectos.stream().map(p -> p.getHttpUrlToRepo()).collect(Collectors.toList());
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nombresRepositorios;
	}
	
	/**
	 * @return
	 * @throws GitLabApiException
	 */
	public List<String> obtenerURLRepositoriosUsuario() {
		List<String> nombresRepositorios = new ArrayList<String>();
		try {
			List<Project> proyectos;
			proyectos = gitLabApi.getProjectApi().getOwnedProjects();
			nombresRepositorios = proyectos.stream().map(p -> p.getHttpUrlToRepo()).collect(Collectors.toList());
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nombresRepositorios;
	}
	
	//git@gitlab.com:mlb0029/comparador-de-metricas-de-evolucion-en-repositorios-software.git
	
	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#getProyecto(java.lang.String)
	 */
	@Override
	public String obtenerURLProyecto(String nombreUsuario, String nombreProyecto) {
		String retorno = "No se ha encontrado un proyecto de nombre '" + nombreProyecto + "' del usuario '" + nombreUsuario + "'.";
		try {
			List<String> nombresRepositorios = new ArrayList<String>();
			ProjectFilter filtro = new ProjectFilter();
			filtro.withSearch(nombreProyecto);
			List<Project> proyectos;
			proyectos = gitLabApi.getProjectApi().getUserProjects(nombreUsuario, filtro);
			nombresRepositorios = proyectos.stream().map(p -> p.getHttpUrlToRepo()).collect(Collectors.toList());
			retorno = (nombresRepositorios.size() >= 1)?nombresRepositorios.get(0):retorno;
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see conexion.IConexionAPI#obtenerIDProyecto(java.lang.String)
	 */
	@Override
	public String obtenerIDProyecto(String URLProyecto) {
		String retorno = "";
		try {
			String sProyecto = URLProyecto.replaceAll(Constantes.HOST_URL + "/", "");
			String nombreProyecto = sProyecto.split("/")[sProyecto.split("/").length - 1];
			String propietarioYGrupo = sProyecto.replaceAll("/" + nombreProyecto, "");
			Project pProyecto;
			pProyecto = gitLabApi.getProjectApi().getProject(propietarioYGrupo, nombreProyecto);
			retorno = pProyecto.getId().toString();
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	public EnumTipoConexion getTipoConexion() {
		return tipoConexion;
	}
	
	
}
