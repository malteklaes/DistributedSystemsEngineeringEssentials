package subtask_1_UDP;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClientMain {
	public static final int portNumber = 7005;
	public static final int bufferLength = 1024;
	public static final String inetAddressName = "localhost";
	public static final int numberToSend = 42;

	public static void main(String[] args) {
		Thread clientThread = null;
		try {
			DatagramSocket datagramSocket = new DatagramSocket();
			InetAddress inetAddress = InetAddress.getByName(inetAddressName);
			UDPClientThreadImplementation client = new UDPClientThreadImplementation(datagramSocket, inetAddress,
					bufferLength, portNumber, numberToSend);
			clientThread = new Thread(client);
			clientThread.start();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (clientThread != null) {
				try {
					clientThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
