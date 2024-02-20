package Task4_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Task4_exceptions.InvalidMethodName;
import Task4_exceptions.MessageWrongFormatException;
import Task4_exceptions.ReceiveMessageException;
import Task4_exceptions.SendMessageException;

public class ServerRequestHandler {
	ServerSocket serverSocket = null;
	Socket socket = null;
	DataInputStream dataInputStream = null;
	DataOutputStream dataOutputStream = null;

	Invoker invoker;

	public ServerRequestHandler(int port) {
		invoker = new Invoker();
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listenToRequest() throws IOException {
		while (true) {
			System.out.println("\n[Server] Server is listening to new client-request...");
			socket = serverSocket.accept();

			// init dataInputStream, dataOutputStream
			this.setUpStreams();

			// receive data from client
			byte[] receivedData = this.receiveDataFromClient();

			// DEserialize data with invoker->marshall
			String[] msgFromClientDeserialized = invoker.marshall(receivedData);
			System.out.println("[Server] Server received this message with methodname: " + msgFromClientDeserialized[0]
					+ " and parameter: " + msgFromClientDeserialized[1]);

			// serialize response and send it
			this.sendResponse(this.formResponse(msgFromClientDeserialized));

		}
	}

	private void setUpStreams() throws IOException {
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
	}

	private byte[] receiveDataFromClient() throws IOException {
		try {
			int length = dataInputStream.readInt();
			byte[] receivedData = new byte[length];
			dataInputStream.readFully(receivedData);
			return receivedData;
		} catch (ReceiveMessageException e) {
			throw new ReceiveMessageException("ReceiveMessageException",
					"There has been a problem receiving the message in the server!");
		}
	}

	private void sendResponse(String response) throws IOException {
		try {
			byte[] responseBytes = invoker.unmarshall(response);
			System.out.println("[Server] Server send this message: " + responseBytes);
			dataOutputStream.writeInt(responseBytes.length);
			dataOutputStream.write(responseBytes);
			dataOutputStream.flush();
		} catch (SendMessageException e) {
			throw new SendMessageException("SendMessageException",
					"There has been a problem sending the message in the server!");
		}
	}

	/**
	 * forms response form by the invoker but also can return ErrorCode with (1)
	 * ErrorCode1 for wrong methodname or (2) ErrorCode2 for wrong message format
	 * 
	 * @param msgFromClientDeserialized
	 * @return String (not null and can contain ErrorCode as String)
	 */
	private String formResponse(String[] msgFromClientDeserialized) {
		String response = "";
		try {
			response = invoker.invoke(msgFromClientDeserialized[0], msgFromClientDeserialized[1]);
		} catch (InvalidMethodName e) {
			e.printStackTrace();
			response = "ErrorCode1";
		} catch (MessageWrongFormatException e) {
			e.printStackTrace();
			response = "ErrorCode2";
		}
		return response;
	}
}
