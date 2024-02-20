package subtask_3_4_TCP;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPClientMain {
	private static int NUMBERTOSEND = 42;
	private static String SENDER = "user-";
	private static final int PORTNUMBER = TCPServerMain.PORTNUMBER;
	public static final int blobSize = 1024;
	public static final int numberOfThreads = TCPServerMain.NUMBEROFTHREADS;
	public static final int transactionRounds = TCPServerMain.TRANSACTIONROUNDS;
	public static final int multithreadingToggle = TCPServerMain.MULTITHREADINGTOGGLE;

	public static void main(String[] args) {
		switch (multithreadingToggle) {
		case 0:
			try {
				runWithoutMultithreading();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			try {
				runWithMultithreading();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			break;
		}

	}

	private static void runWithoutMultithreading() throws UnknownHostException, ClassNotFoundException, IOException {
		Thread clientThread = null;
		for (int i = 0; i < transactionRounds; i++) {
			System.out.println("TCP-Iteration number: #" + i);

			Message msg = new Message(NUMBERTOSEND, SENDER + i, blobSize);
			TCPClientThreadImplementation client = new TCPClientThreadImplementation(msg, PORTNUMBER);

			clientThread = new Thread(client);
			clientThread.run();

			try {
				clientThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void runWithMultithreading() throws UnknownHostException, ClassNotFoundException, IOException {
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

		for (int i = 0; i < transactionRounds; i++) {
			System.out.println("TCP-Iteration number: #" + i);

			Message msg = new Message(NUMBERTOSEND, SENDER + i, blobSize);
			TCPClientThreadImplementation client = new TCPClientThreadImplementation(msg, PORTNUMBER + i);

			executor.execute(client);
		}

		executor.shutdown();
	}

}
