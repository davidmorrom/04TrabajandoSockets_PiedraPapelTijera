package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketCliente {

	public static final int PUERTO = 2022;
	public static final String IP_SERVER = "192.168.22.136";

	public static void main(String[] args) {
		System.out.println("        APLICACIÓN CLIENTE         ");
		System.out.println("-----------------------------------");
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
		Socket socketAlServidor = new Socket();
		String numero1;
		String operandos;
		PrintStream salida;
		InputStreamReader entrada;
		BufferedReader bf;
		String resultado;
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.println("Menú juego: \n" + "1. Piedra \n" + "2. Papel \n" + "3. Tijera");
				System.out.print("Introduzca la opción deseada: ");
				System.out.println("CLIENTE: Introduzca los numeros a sumar");
				do {
					numero1 = sc.nextLine();
					if (Integer.parseInt(numero1) == 1 || Integer.parseInt(numero1) == 2
							|| Integer.parseInt(numero1) == 3) {
						System.out.println("PORFAVOR INTRODUZCA UNA OPCIÓN VÁLIDA");
					}
				} while (Integer.parseInt(numero1) == 1 || Integer.parseInt(numero1) == 2
						|| Integer.parseInt(numero1) == 3);
				System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
				socketAlServidor.connect(direccionServidor);
				System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + " por el puerto " + PUERTO);
				salida = new PrintStream(socketAlServidor.getOutputStream());
				salida.println(numero1);
				entrada = new InputStreamReader(socketAlServidor.getInputStream());
				bf = new BufferedReader(entrada);
				System.out.println("CLIENTE: Esperando al resultado del servidor...");
				resultado = bf.readLine();

			}
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la dirección" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
	}

}
