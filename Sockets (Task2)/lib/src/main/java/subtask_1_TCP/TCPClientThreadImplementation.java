package subtask_1_TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClientThreadImplementation implements Runnable {

	private int numberToSend;

	private Socket communicationSocket;
	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;

	private boolean readOnce = false;

	public TCPClientThreadImplementation(int numberToSend, int portNumber) throws UnknownHostException, IOException {
		this.numberToSend = numberToSend;
		communicationSocket = new Socket("localhost", portNumber);
	}

	@Override
	public void run() {
		try {
			dataInputStream = new DataInputStream(communicationSocket.getInputStream());
			while (!readOnce) {
				dataOutputStream = new DataOutputStream(communicationSocket.getOutputStream());
				this.sendMessage();
				this.printServerResponse();
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

	private void sendMessage() throws IOException {
		dataOutputStream.writeInt(numberToSend);
		dataOutputStream.flush();
		System.out.println("[Client] Number was sent: " + numberToSend);
	}

	private void printServerResponse() throws IOException {
		int receivedNumber = dataInputStream.readInt();
		readOnce = true;
		System.out.println("[Client] Received from server: " + receivedNumber);
	}

	private void closeGates() throws IOException {
		if (communicationSocket != null) {
			communicationSocket.close();
		}
		if (dataInputStream != null) {
			dataInputStream.close();
		}
		if (dataOutputStream != null) {
			dataOutputStream.close();
		}
		System.out.println("[Client] All gates have been closed.");
	}

}
