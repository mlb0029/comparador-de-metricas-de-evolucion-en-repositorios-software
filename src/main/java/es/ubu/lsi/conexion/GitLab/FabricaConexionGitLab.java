/**
 * 
 */
package es.ubu.lsi.conexion.GitLab;

import es.ubu.lsi.conexion.comun.fabricas.*;
import es.ubu.lsi.conexion.comun.tipos.IConexion;
import es.ubu.lsi.conexion.comun.tipos.IConexionAutenticada;
import es.ubu.lsi.conexion.comun.tipos.IConexionSinAutenticar;

/**
 * @author migue
 *
 */
public class FabricaConexionGitLab implements IFabricaConexion {

	private static FabricaConexionGitLab instancia = null;
	
	private IConexion UltimaConexion = null;
	
	public FabricaConexionGitLab() {
	}

	
	public static FabricaConexionGitLab getInstancia() {
		if (instancia == null) instancia = new FabricaConexionGitLab();
		return instancia;
	}


	@Override
	public IConexionSinAutenticar crearConexionSinAutenticar() {
		return ConexionSinAutenticarGitLab.getInstance();
	}


	@Override
	public IConexionAutenticada crearConexionAutenticada() {
		return ConexionAutenticadaGitLab.getInstance();
	}

	public IConexion getUltimaConexion() {
		return UltimaConexion;
	}
	
}
