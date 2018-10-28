package de.imut.oop.talk1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A driver for a simple sender of network traffic.
 * 
 * @author Ralf Buschermoehle
 * @version 1.00
 */
public class Talk {

	private static final int DEFAULT_LISTEN = 2048;
	private static final int DEFAULT_TALK = 2048;
	private static final String DEFAULT_IP = "localhost";

	/**
	 * Parses the port (string) and returns the port number (int). If the string is
	 * not a number the defaultPort ist returned.
	 * 
	 * @param port        (string) to be parsed
	 * @param defaultPort if port string cannot be parsed
	 * @return parsed port
	 */
	private static int parsePart(String port, int defaultPort) {
		try {
			return Integer.parseInt(port);
		} catch (NumberFormatException e) {
			System.err.println("Port is not valid! " + port + ", using default value : " + defaultPort);
		}
		return defaultPort;
	}

	/**
	 * reads the (user) name from console (to print in front of messages)
	 * 
	 * @return
	 */
	private static String getUserName() {
		String userName = null;
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Please enter your name: ");
		try {
			userName = inputReader.readLine();
		} catch (IOException e) {
			System.out.println("IO-Error: " + e.getMessage());
		}
		return userName;
	}

	/**
	 * A simple talk/chat application.
	 * 
	 * @param args arguments transferred from the operating system args[0]: the port
	 *             to listen to (default: 2048) args[1]: the port to talk to
	 *             (default: 2049) args[2]: remote machine to talk to (default:
	 *             localhost)
	 * 
	 */
	public static void main(final String[] args) {
		int talk = DEFAULT_TALK;
		int listen = DEFAULT_LISTEN;
		String remote = DEFAULT_IP;

		switch (args.length) {
		case 3:
			remote = args[2];
		case 2:
			talk = parsePart(args[1], DEFAULT_TALK);
		case 1:
			listen = parsePart(args[0], DEFAULT_LISTEN);
		}

		String userName = getUserName();

		// Sender
		Sender sender = new Sender(remote, listen, userName);
		Thread senderThread = new Thread(sender, "Sender");
		senderThread.start();

		// Receiver
		Receiver receiver = new Receiver(talk);
		Thread receiverThread = new Thread(receiver, "Receiver");
		receiverThread.start();
	}
}
