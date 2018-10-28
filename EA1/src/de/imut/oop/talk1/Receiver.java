package de.imut.oop.talk1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple receiver of network traffic.
 * 
 * @author Ralf Buschermoehle
 * @version 1.00
 */
public class Receiver implements Runnable {
	private int port = 0;

	/**
	 * A Receiver of information from the network.
	 * 
	 * @param port to listen to
	 */
	public Receiver(final int port) {
		this.port = port;
	}

	/*
	 * Receiver thread activation
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("resource")
	public final void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			Socket client = server.accept(); // wait for connection
			DataInputStream in = new DataInputStream(client.getInputStream());
			String name = in.readUTF();
			String message = null;

			do {
				message = in.readUTF();
				System.out.println(name + ": " + message);
			} while (!"exit.".equals(message));

			client.close();
		} catch (IOException e) {
			System.out.println("IO-Error: " + e.getMessage());
		}

		System.out.println("Receiver: Connection closed!");
		System.exit(1);
	}

	public static void main(final String[] args) {
		// Receiver
		Receiver receiver = new Receiver(2048);
		Thread receiverThread = new Thread(receiver, "Receiver");
		receiverThread.start();
	}
}
