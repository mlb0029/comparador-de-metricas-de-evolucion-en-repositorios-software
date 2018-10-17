/**
 * 
 */
package es.ubu.lsi.conexion.GitLab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gitlab4j.api.Constants.TokenType;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Session;

import es.ubu.lsi.conexion.comun.tipos.IConexionAutenticada;

/**
 * @author migue
 *
 */
public class ConexionAutenticadaGitLab implements IConexionAutenticada {

	private static ConexionAutenticadaGitLab instance = null;

	private GitLabApi gitLabApi = null;

	private ConexionAutenticadaGitLab() {
	}

	public static ConexionAutenticadaGitLab getInstance() {
		if (instance == null) instance = new ConexionAutenticadaGitLab();
		return instance;
	}
	
	public Boolean isConnected() {
		return gitLabApi != null;
	}

	public void establecerConexion_login(String hostUrl, String username, String password) throws GitLabApiException {
		gitLabApi = GitLabApi.oauth2Login(hostUrl, username, password.toCharArray());
	}

	public void establecerConexion_OAuth2Token(String hostUrl, String accessToken) {
		gitLabApi = new GitLabApi(hostUrl, TokenType.ACCESS, accessToken);
	}

	public void establecerConexion_PersonalAccessToken(String hostUrl, String privateToken) {
		gitLabApi = new GitLabApi(hostUrl, privateToken);
	}

	public void establecerConexion_SessionCookie(String hostUrl, Session session) {
		gitLabApi = new GitLabApi(hostUrl, session);
	}

	@Override
	public int getNumIssues(Integer projectId) {
		int numIssues = 0;
		try {
			if (gitLabApi.getProjectApi().getProject(projectId).getIssuesEnabled()) {
				numIssues = gitLabApi.getIssuesApi().getIssues(projectId).size();
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numIssues;
	}

	@Override
	public Map<Integer, String> getNombresRepositorios() {
		Map<Integer, String> nombresRepositorios = new HashMap<Integer, String>();
		try {
			for (Project project : gitLabApi.getProjectApi().getOwnedProjects()) {
				nombresRepositorios.put(project.getId(), project.getName());
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nombresRepositorios;
	}

	@Override
	public String getNombreUsuario() {
		try {
			return this.gitLabApi.getUserApi().getCurrentUser().getName();
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
