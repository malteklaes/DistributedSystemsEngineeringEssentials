package subtask_3_4_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerThreadImplementation implements Runnable {

	private Message requestMsgFromClient;
	private Socket communicationSocket;
	private int portNumber;
	private ServerSocket serverSocket;

	private ObjectInputStream objectInputStream = null;
	private ObjectOutputStream objectOutputStream = null;

	public TCPServerThreadImplementation(int portNumber) throws IOException, ClassNotFoundException {
		this.portNumber = portNumber;
		this.serverSocket = new ServerSocket(portNumber);
		System.out.println("[Server] Server is set up.");
	}

	@Override
	public void run() {
		System.out.println("[Server] Server is now running...");
		try {
			while (true) {
				this.initializeGates();

				this.receiveMessage();

				this.sendMessage();

				this.resetGates();
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

//		finally {
//			try {
//				this.closeGates();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

	}

	private void receiveMessage() throws ClassNotFoundException, IOException {
		requestMsgFromClient = (Message) objectInputStream.readObject();
		System.out.println("[Server] Received message from client: " + requestMsgFromClient);
	}

	private void sendMessage() throws IOException {
		Message responseMsg = new Message(requestMsgFromClient.getNumber() + 1, requestMsgFromClient.getSender(),
				requestMsgFromClient.getBlobSize());
		objectOutputStream.writeObject(responseMsg);
		objectOutputStream.flush();
		System.out.println("[Server] Server has sent calculated object back to client: " + responseMsg + "\n \n");
	}

	private void initializeGates() throws IOException {
		communicationSocket = serverSocket.accept();
		objectOutputStream = new ObjectOutputStream(communicationSocket.getOutputStream());
		objectInputStream = new ObjectInputStream(communicationSocket.getInputStream());
	}

	private void resetGates() throws IOException {
		serverSocket.close();
		serverSocket = new ServerSocket(portNumber);
	}

	private void closeGates() throws IOException {
		communicationSocket.close();
		objectInputStream.close();
		objectOutputStream.close();
		System.out.println("[Server] All gates have been closed.");
	}

}
