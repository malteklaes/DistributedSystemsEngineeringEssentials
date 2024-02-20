package subtask_1_TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPServerThreadImplementation implements Runnable {

	private Socket communicationSocket;
	private DataInputStream dataInputStream = null; // for integer, instead of InputStreamReader
	private DataOutputStream dataOutputSteam = null;
	private OutputStreamWriter outputStreamWriter = null;

	private boolean readOnce = false;

	public TCPServerThreadImplementation(Socket communicationSocket) {
		this.communicationSocket = communicationSocket;
		System.out.println("[Server] Server-Thread was initialized: [" + this.communicationSocket.toString() + "]");
	}

	@Override
	public void run() {
		System.out.println("[Server] Server-Thread is now running...");

		try {
			dataInputStream = new DataInputStream(communicationSocket.getInputStream());
			outputStreamWriter = new OutputStreamWriter(communicationSocket.getOutputStream());
			dataOutputSteam = new DataOutputStream(communicationSocket.getOutputStream());

			while (!readOnce) {
				int processedNumber = this.processInput(dataInputStream);
				this.sendMessage(processedNumber);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeGates();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private int processInput(DataInputStream dataIn) throws IOException {
		int receivedNumber = dataIn.readInt();
		System.out.println("[Server] Received from client: " + receivedNumber);
		int number = receivedNumber;
		number++;
		readOnce = true;
		System.out.println("[Server] Server calculated: " + number);
		return number;
	}

	private void sendMessage(int numberToSend) throws IOException {
		dataOutputSteam.writeInt(numberToSend);
		dataOutputSteam.flush();
		System.out.println("[Server] Number was sent: " + numberToSend);
	}

	private void closeGates() throws IOException {
		communicationSocket.close();
		dataInputStream.close();
		outputStreamWriter.close();
		System.out.println("[Server] All gates have been closed.");
	}

}
