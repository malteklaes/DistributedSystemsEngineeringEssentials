package subtask_3_4_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClientThreadImplementation implements Runnable {

	private Message messageToSend;

	private Socket communicationSocket;
	private ObjectOutputStream objectOutputStream = null;
	private ObjectInputStream objectInputStream = null;

	private long t1 = 0;
	private long t2 = 0;

	private boolean readOnce = false;

	public TCPClientThreadImplementation(Message messageToSend, int portNumber)
			throws UnknownHostException, IOException, ClassNotFoundException {
		this.messageToSend = messageToSend;
		communicationSocket = new Socket("localhost", portNumber);
		System.out.println("[Client] Client is set up.");
	}

	@Override
	public void run() {
		System.out.println("[Client] Client is now running...");
		try {
			while (!readOnce) {
				objectOutputStream = new ObjectOutputStream(communicationSocket.getOutputStream());
				objectInputStream = new ObjectInputStream(communicationSocket.getInputStream());

				t1 = messageToSend.getTimestamp();
				objectOutputStream.writeObject(messageToSend);
				System.out.println("[Client] Client has send message to server: " + messageToSend);

				Message answerFromServer = (Message) objectInputStream.readObject();
				t2 = answerFromServer.getTimestamp();
				System.out.println("[Client] Client recieved message from server: " + answerFromServer);
				readOnce = true; // to close client-server connection immediately
				long totalTime = t2 - t1;
				System.out.println("[Client] transaction time is: " + totalTime + "ms.");
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeGates();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void closeGates() throws IOException {
		objectOutputStream.close();
		communicationSocket.close();
		if (communicationSocket != null) {
			communicationSocket.close();
		}
		System.out.println("[Client] All gates have been closed.");
	}

}
