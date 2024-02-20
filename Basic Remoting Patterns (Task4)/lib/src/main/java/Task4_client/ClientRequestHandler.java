package Task4_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import Task4_exceptions.ReceiveMessageException;
import Task4_exceptions.SendMessageException;

public class ClientRequestHandler {
	private Socket socket = null;
	private InputStreamReader inputStreamReader = null;
	private OutputStreamWriter outputStreamWriter = null;

	private DataOutputStream dataOutputStream = null;
	private DataInputStream dataInputStream = null;

	public ClientRequestHandler(int port, String hostname) {
		try {
			socket = new Socket(hostname, port);
			inputStreamReader = new InputStreamReader(socket.getInputStream());
			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Msg layout: "methodname;parameter" -> byte[]
	 * 
	 * @param serializedMsg
	 * @throws IOException
	 */
	public void sendMessage(byte[] serializedMsg) throws IOException {
		try {
			dataOutputStream.writeInt(serializedMsg.length);
			dataOutputStream.write(serializedMsg);
			dataOutputStream.flush();
		} catch (SendMessageException e) {
			throw new SendMessageException("SendMessageException",
					"There has been a problem sending the message in the client!");
		}
	}

	/**
	 * Msg layout: byte[] -> "normalString"
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] receiveAnswer() throws IOException {
		try {
			int length = dataInputStream.readInt();
			byte[] receivedData = new byte[length];
			dataInputStream.readFully(receivedData);
			return receivedData;
		} catch (ReceiveMessageException e) {
			throw new ReceiveMessageException("ReceiveMessageException",
					"There has been a problem receiving the message in the client!");
		}
	}
}
