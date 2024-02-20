package subtask_2_TCP;

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

				objectOutputStream.writeObject(messageToSend);
				System.out.println("[Client] Client has send message to server: " + messageToSend);

				Message answerFromServer = (Message) objectInputStream.readObject();
				System.out.println("[Client] Client recieved message from server: " + answerFromServer);

				readOnce = true;
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
