/**
 * 
 */
package es.ubu.lsi.conexion.comun.fabricas;

import es.ubu.lsi.conexion.comun.tipos.*;
/**
 * @author migue
 *
 */
public interface IFabricaConexion {
	public IConexionSinAutenticar crearConexionSinAutenticar();
	public IConexionAutenticada crearConexionAutenticada();
}
