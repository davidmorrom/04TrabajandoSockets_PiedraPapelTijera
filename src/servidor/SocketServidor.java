package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServidor {
	public static final int PUERTO = 2017;
	
	public static InputStreamReader entrada1 = null;
	public static PrintStream salida1 = null;
	public static Socket socketAlCliente1 = null;
	
	public static InputStreamReader entrada2 = null;
	public static PrintStream salida2 = null;
	public static Socket socketAlCliente2 = null;
	
	InetSocketAddress direccion = new InetSocketAddress(PUERTO);
	public static void main(String[] args) {
		System.out.println("      APLICACIÓN DE SERVIDOR      ");
		System.out.println("----------------------------------");

		int peticion = 1;

		try (ServerSocket servidor = new ServerSocket()) {
			int puntuacion1 = 0;
			int puntuacion2 = 0;
			String puntuacionFinal = "";
			boolean seguir = true;
			InetSocketAddress direccion = new InetSocketAddress(PUERTO);
			servidor.bind(direccion);
			
			System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
			while (seguir) {
				if (peticion == 1) {
					socketAlCliente1 = servidor.accept();
					System.out.println("SERVIDOR: peticion numero " + peticion + " recibida");
					peticion++;
					entrada1 = new InputStreamReader(socketAlCliente1.getInputStream());
					salida1 = new PrintStream(socketAlCliente1.getOutputStream());
					salida1.println("Eres el jugador 1");
				} else if (peticion == 2) {
					socketAlCliente2 = servidor.accept();
					System.out.println("SERVIDOR: peticion numero " + peticion + " recibida");
					entrada2 = new InputStreamReader(socketAlCliente2.getInputStream());
					salida2 = new PrintStream(socketAlCliente2.getOutputStream());
					salida2.println("Eres el jugador 2");
					seguir = false;
				}
			}
			while (puntuacion1 != 3 && puntuacion2 != 3) {
				salida1.println("Puntuación: Jugador1 = " + puntuacion1 + " rondas ganadas Jugador2 = " + puntuacion2 + " rondas ganadas");
				salida2.println("Puntuación: Jugador1 = " + puntuacion1 + " rondas ganadas Jugador2 = " + puntuacion2 + " rondas ganadas");
				String ganadorRonda = "";
				int gana = 0;
				BufferedReader bf1 = new BufferedReader(entrada1);
				int eleccion1 = Integer.parseInt(bf1.readLine());
				System.out.println("SERVIDOR: Me ha llegado del cliente1: " + eleccion1);
				BufferedReader bf2 = new BufferedReader(entrada2);
				int eleccion2 = Integer.parseInt(bf2.readLine());
				System.out.println("SERVIDOR: Me ha llegado del cliente2: " + eleccion2);
				gana = jugar(eleccion1, eleccion2);
				switch (gana) {
				case 1:
					ganadorRonda = "Gana la ronda el jugador 1";
					puntuacion1++;
					break;
				case 2:
					ganadorRonda = "Gana la ronda el jugador 2";
					puntuacion2++;
					break;
				case 3:
					ganadorRonda = "Los jugadores han empatado la ronda";
					break;
				default:
					break;
				}
				salida1.println(ganadorRonda);
				salida2.println(ganadorRonda);
				if (puntuacion1 == 3 || puntuacion2 == 3) {
					salida1.println("fin");
					salida2.println("fin");
				} else {
					salida1.println("nofin");
					salida2.println("nofin");
				}
			}
			if (puntuacion1 == 3) {
				puntuacionFinal = "Ha ganado la partida el jugador 1";
			} else if (puntuacion2 == 3){
				puntuacionFinal = "Ha ganado la partida el jugador 2";
			}
			salida1.println(puntuacionFinal);
			salida2.println(puntuacionFinal);
			Thread.sleep(5000);
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
	}
	
	public static int jugar(int jugador1, int jugador2) {
		// ganador: 0 = empate, 1 = gana jugador1, 2 = gana jugador2
		int ganador = 0;
		switch (jugador1) {
		// 1 = piedra, 2 = papel 3 = tijera
		case 1:
			if (jugador2 == 2) {
				ganador = 2;
			}else if (jugador2 == 3) {
				ganador = 1;
			} else {
				ganador = 0;
			}
			break;
		case 2:
			if (jugador2 == 1) {
				ganador = 1;
			}else if (jugador2 == 3) {
				ganador = 2;
			} else {
				ganador = 0;
			}
			break;
		case 3:
			if (jugador2 == 1) {
				ganador = 2;
			}else if (jugador2 == 2) {
				ganador = 1;
			} else {
				ganador = 0;
			}
			break;
		default:
			break;
		}
		return ganador;
	}
}
