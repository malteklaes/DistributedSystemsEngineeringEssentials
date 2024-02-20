package subtask_1_TCP;

import java.io.IOException;

public class TCPClientMain {
	private static int NUMBERTOSEND = 42;
	private static final int PORTNUMBER = 7000;

	public static void main(String[] args) {
		Thread clientThread = null;
		try {
			TCPClientThreadImplementation client = new TCPClientThreadImplementation(NUMBERTOSEND, PORTNUMBER);
			clientThread = new Thread(client);
			clientThread.start();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
