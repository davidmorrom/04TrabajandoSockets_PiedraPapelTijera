package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HiloServidor implements Runnable{
	private Thread hilo;
	private static int numCliente;
	private Socket socketAlCliente;
	
	public HiloServidor(Socket socketAlCliente) {
		numCliente++;
		hilo = new Thread(this, "Cliente_N"+numCliente);
		this.socketAlCliente = socketAlCliente;
		hilo.start();
	}
	@Override
	public void run() {
		System.out.println("Estableciendo comunicacion con " + hilo.getName());
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
	}

}
