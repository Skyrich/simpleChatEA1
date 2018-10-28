package de.imut.oop.talk1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A simple sender of network traffic.
 * 
 * @author Ralf Buschermoehle
 * @version 1.00
 */
public class Sender implements Runnable {
	private int remotePort = 0;
	private String remoteIP = null;
	private String userName = null;

	/**
	 * A sender of information over the network.
	 * 
	 * @param remote - remote machine to talk to.
	 * @param port   - remote port to talk to.
	 */
	public Sender(final String remote, final int port, String userName) {
		this.remoteIP = remote;
		this.remotePort = port;
		this.userName = userName;
	}

	/*
	 * Sender thread activation
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public final void run() {
		Socket socket = null;
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		DataOutputStream outputStream = null;
		String line = null;

		System.out.println("Waiting for connection to " + remoteIP + ":" + remotePort + " ...");

		do {
			try {
				socket = new Socket(remoteIP, remotePort);
			} catch (UnknownHostException e) {
				System.err.println(e);
				System.exit(-1);
			} catch (IOException e) {
				Thread.yield();
			}
		} while (socket == null);

		System.out.println("Connection established! End talk with \"exit.\".");

		try {
			outputStream = new DataOutputStream(socket.getOutputStream());
			outputStream.writeUTF(userName);

			do {
				line = inputReader.readLine();
				outputStream.writeUTF(line);
			} while (!"exit.".equals(line));

			socket.close();
		} catch (IOException e) {
			System.out.println("IO-Error: " + e.getMessage());
		}

		System.exit(0);
	}

	public static void main(final String[] args) {
		// Sender
		Sender sender = new Sender("localhost", 2049, "SenderClass");
		Thread senderThread = new Thread(sender, "Sender");
		senderThread.start();

	}
}
