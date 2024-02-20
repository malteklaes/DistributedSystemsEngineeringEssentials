package subtask_2_TCP;

import java.io.IOException;

public class TCPClientMain {
	private static int NUMBERTOSEND = 42;
	private static String SENDER = "user1";
	private static final int PORTNUMBER = 7020;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Thread clientThread = null;
		Message msg = new Message(NUMBERTOSEND, SENDER);
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
