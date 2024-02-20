package subtask_2_UDP;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServerMain {

	public static final int portNumber = 7030;
	public static final int bufferLength = 1024;

	public static void main(String[] args) {
		DatagramSocket datagramSocket;
		Thread serverThread = null;
		try {
			datagramSocket = new DatagramSocket(portNumber);
			UDPServerThreadImplementation server = new UDPServerThreadImplementation(datagramSocket, bufferLength,
					portNumber);
			serverThread = new Thread(server);
			serverThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} finally {
			if (serverThread != null) {
				try {
					serverThread.join();
					System.out.println("[Server] Server has been terminated.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
