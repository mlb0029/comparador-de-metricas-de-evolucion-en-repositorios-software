package ap;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import conexion.ConexionGitLab;
import conexion.IConexionAPI;

/**
 * Función principal a ejecutar mediante Java Application.
 * 
 * @author MALB
 * @since 01/11/2018
 *
 */
public class Ap {

	/**
	 * Función main.
	 * 
	 * @param args No hay argumentos.
	 */
	public static void main(String[] args) {
		try {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("LOGIN");
			System.out.println("________________");
			System.out.print("Introduzca un token de acceso o pulse Enter para entrar sin autenticación: ");
			String privateToken = stdIn.readLine();
			IConexionAPI conexionAPI = (privateToken != "")? ConexionGitLab.establecerConexion() : ConexionGitLab.establecerConexion(privateToken);
			System.out.println();
			System.out.println("PARTE 1. Obtener URLs de proyectos de un usuario");
			System.out.println("________________");
			System.out.print("Introducir nombre de usuario: ");
			String nombreUsuario = stdIn.readLine();
			for (String URLProyecto : conexionAPI.obtenerURLRepositoriosUsuario(nombreUsuario)) {
				System.out.println("* " + URLProyecto);
			}
			System.out.println();
			System.out.println("PARTE 2. Obtener URL de un proyecto");
			System.out.println("________________");
			System.out.print("Nombre usuario del proyecto: ");
			nombreUsuario = stdIn.readLine();
			System.out.print("Nombre del proyecto: ");
			String nombreProyecto = stdIn.readLine();
			System.out.println("* "+ conexionAPI.obtenerURLProyecto(nombreUsuario, nombreProyecto));
			System.out.println();
			System.out.println("PARTE 3. Obtener ID de un proyecto a partir de URL");
			System.out.println("________________");
			System.out.print("URL: ");
			String URLProyecto = stdIn.readLine();
			System.out.println("ID: "+ conexionAPI.obtenerIDProyecto(URLProyecto));
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
