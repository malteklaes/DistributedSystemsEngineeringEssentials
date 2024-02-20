package subtask_1_TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerMain {
	private static final int PORTNUMBER = 7000;

	public static void main(String[] args) {
		Thread serverThread = null;
		try {
			ServerSocket serverSocket = new ServerSocket(PORTNUMBER); // only for initial contact to a client
			System.out.println("[Server] Server running on port: " + PORTNUMBER + "...");

			// while (true) { // Server acts only once
			Socket entrySocket = serverSocket.accept();
			TCPServerThreadImplementation serverSocketImplementation = new TCPServerThreadImplementation(entrySocket);
			serverThread = new Thread(serverSocketImplementation);
			serverThread.start();
			// }

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
