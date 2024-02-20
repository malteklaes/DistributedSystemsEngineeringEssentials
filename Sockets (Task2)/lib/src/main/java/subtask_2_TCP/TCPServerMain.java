package subtask_2_TCP;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServerMain {
	private static final int PORTNUMBER = 7020;

	public static void main(String[] args) {
		Thread serverThread = null;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(PORTNUMBER);
			System.out.println("[Server] Server is up and runnning on port: " + PORTNUMBER);
			TCPServerThreadImplementation server = new TCPServerThreadImplementation(serverSocket.accept());
			serverThread = new Thread(server);
			serverThread.run();
			serverSocket.close();
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			serverThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
