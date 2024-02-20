package subtask_3_4_UDP;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPClientMain {
	public static final String inetAddressName = "localhost";
	public static final int portNumber = 7030;
	public static final int bufferLength = 1024;
	public static final int numberToSend = 42;
	public static final int blobSize = 512;
	public static final String sender = "user-";
	public static Message msgToSend;
	public static final int transactionRounds = 1000;
	public static final int numberOfThreads = UDPServerMain.NUMBEROFTHREADS;

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

		for (int i = 0; i < transactionRounds; i++) {
			System.out.println("Iteration number: #" + i);
			msgToSend = new Message(numberToSend, sender + i, blobSize);
			executor.execute(() -> {
				try {
					DatagramSocket datagramSocket = new DatagramSocket();
					InetAddress inetAddress = InetAddress.getByName(inetAddressName);
					UDPClientThreadImplementation client = new UDPClientThreadImplementation(datagramSocket,
							inetAddress, bufferLength, portNumber, msgToSend);
					client.run();
				} catch (SocketException | UnknownHostException e) {
					e.printStackTrace();
				}
			});
		}
		executor.shutdown();
	}
}
