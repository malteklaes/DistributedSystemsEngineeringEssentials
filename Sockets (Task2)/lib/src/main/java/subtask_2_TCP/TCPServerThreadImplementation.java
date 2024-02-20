package subtask_2_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPServerThreadImplementation implements Runnable {

	private Message requestMsgFromClient;
	private Socket communicationSocket;

	private ObjectInputStream objectInputStream = null;
	private ObjectOutputStream objectOutputStream = null;

	private boolean readOnce = false;

	public TCPServerThreadImplementation(Socket communicationSocket) throws IOException, ClassNotFoundException {
		this.communicationSocket = communicationSocket;
		objectOutputStream = new ObjectOutputStream(communicationSocket.getOutputStream());
		objectInputStream = new ObjectInputStream(communicationSocket.getInputStream());
		System.out.println("[Server] Server is set up.");
	}

	@Override
	public void run() {
		System.out.println("[Server] Server is now running...");
		try {
			while (!readOnce) {
				requestMsgFromClient = (Message) objectInputStream.readObject();
				System.out.println("[Server] recieved message from client: " + requestMsgFromClient);

				Message responseMsg = new Message(requestMsgFromClient.getNumber() + 1,
						requestMsgFromClient.getSender());
				objectOutputStream.writeObject(responseMsg);
				System.out.println("[Server] Server has sent calculated object back to client: " + responseMsg);
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
		communicationSocket.close();
		objectInputStream.close();
		objectOutputStream.close();
		System.out.println("[Server] All gates have been closed.");
	}

}
