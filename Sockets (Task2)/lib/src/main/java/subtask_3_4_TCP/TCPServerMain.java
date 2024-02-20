package subtask_3_4_TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServerMain {
	public static final int PORTNUMBER = 7020;
	public static final int NUMBEROFTHREADS = 500;
	public static final int TRANSACTIONROUNDS = NUMBEROFTHREADS;
	public static final int MULTITHREADINGTOGGLE = 1;

	public static void main(String[] args) {
		switch (MULTITHREADINGTOGGLE) {
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
		Thread serverThread = null;
		ServerSocket serverSocket = null;

		try {
			System.out.println("[Server] Server is up and runnning on port: " + PORTNUMBER);
			TCPServerThreadImplementation server = new TCPServerThreadImplementation(PORTNUMBER);
			serverThread = new Thread(server);
			serverThread.run();
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			serverThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void runWithMultithreading() throws UnknownHostException, ClassNotFoundException, IOException {
		ExecutorService executor = Executors.newFixedThreadPool(NUMBEROFTHREADS);

		try {
			for (int i = 0; i < TRANSACTIONROUNDS; i++) {
				System.out.println("[Server] Server is up and running on port: " + PORTNUMBER + i);
				TCPServerThreadImplementation server = new TCPServerThreadImplementation(PORTNUMBER + i);
				executor.execute(server);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		executor.shutdown();
	}

}
