/**
 * 
 */
package es.ubu.lsi.conexion.GitLab;

import java.util.Map;

import es.ubu.lsi.conexion.comun.tipos.IConexionSinAutenticar;

/**
 * @author migue
 *
 */
public class ConexionSinAutenticarGitLab implements IConexionSinAutenticar {

	
	private static ConexionSinAutenticarGitLab instance = null;
	
	private ConexionSinAutenticarGitLab() {
		// TODO Auto-generated constructor stub
	}
	
	public static ConexionSinAutenticarGitLab getInstance() {
		if (instance == null) instance = new ConexionSinAutenticarGitLab();
		return instance;
	}

}
