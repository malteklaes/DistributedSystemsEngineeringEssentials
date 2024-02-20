package subtask_3_4_UDP;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPServerMain {

	public static final int portNumber = 7030;
	public static final int bufferLength = 1024;
	public static final int NUMBEROFTHREADS = 4;

	public static void main(String[] args) {
		DatagramSocket datagramSocket;
		ExecutorService executor = Executors.newFixedThreadPool(NUMBEROFTHREADS);

		try {
			datagramSocket = new DatagramSocket(portNumber);

			for (int i = 0; i < NUMBEROFTHREADS; i++) {
				UDPServerThreadImplementation server = new UDPServerThreadImplementation(datagramSocket, bufferLength,
						portNumber);
				executor.execute(server);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
}
