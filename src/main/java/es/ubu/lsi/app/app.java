/**
 * 
 */
package es.ubu.lsi.app;

import es.ubu.lsi.conexion.GitLab.ConexionAutenticadaGitLab;
import es.ubu.lsi.conexion.GitLab.FabricaConexionGitLab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * @author MALB
 *
 */
public class app {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String accessToken;
		try {
			System.out.print("Access Token: ");
			accessToken = stdIn.readLine();
			ConexionAutenticadaGitLab conexion = (ConexionAutenticadaGitLab) FabricaConexionGitLab.getInstancia().crearConexionAutenticada();
			conexion.establecerConexion_PersonalAccessToken("https://gitlab.com", accessToken);
			System.out.println("Conectado como: " + conexion.getNombreUsuario());
			System.out.println("Repositorios:");
			for (Integer idRepo : conexion.getNombresRepositorios().keySet()) {
				System.out.println(idRepo + "> " + conexion.getNombresRepositorios().get(idRepo) + ". NumIssues: " + conexion.getNumIssues(idRepo));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
