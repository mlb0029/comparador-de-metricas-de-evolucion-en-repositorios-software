/**
 * 
 */
package es.ubu.lsi.conexion.comun.tipos;

import java.util.Map;

/**
 * @author migue
 *
 */
public interface IConexionAutenticada extends IConexion{
	public String getNombreUsuario();

	public int getNumIssues(Integer projectId);
	public Map<Integer, String> getNombresRepositorios();
}
