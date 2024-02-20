package subtask_2_UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServerThreadImplementation implements Runnable {

	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private InetAddress inetAddressFromClient;
	private ByteArrayInputStream inputByteStream;
	private ObjectInputStream inputObjectStream;
	private ByteArrayOutputStream outputByteStream;
	private ObjectOutputStream outputObjectStream;
	private byte[] buffer;
	private int portNumber;

	private boolean readOnce;

	public UDPServerThreadImplementation(DatagramSocket datagramSocket, int bufferLength, int portNumber) {
		this.datagramSocket = datagramSocket;
		this.portNumber = portNumber;
		this.buffer = new byte[bufferLength];
		this.readOnce = false;
		System.out.println("[Server] Server is set up.");
	}

	@Override
	public void run() {
		System.out.println("[Server] Server is now running...");
		while (!readOnce) {
			try {
				Message msgFromClient = this.receiveMsg();
				Message processedMsgFromClient = this.processInput(msgFromClient);
				this.sendMsgToClient(processedMsgFromClient);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					this.closeDownGates();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private Message receiveMsg() throws IOException, ClassNotFoundException {
		datagramPacket = new DatagramPacket(buffer, buffer.length);
		datagramSocket.receive(datagramPacket); // waits until it recieves
		inetAddressFromClient = datagramPacket.getAddress(); // important: must set up once again
		portNumber = datagramPacket.getPort(); // important: must set up once again

		inputByteStream = new ByteArrayInputStream(buffer);
		inputObjectStream = new ObjectInputStream(inputByteStream);
		Message msgFromClient = (Message) inputObjectStream.readObject();
		System.out.println("[Server] Server has received msg from client: " + msgFromClient);
		return msgFromClient;
	}

	private Message processInput(Message msgFromClient) {
		int processedNumber = msgFromClient.getNumber() + 1;
		Message processedMessage = new Message(processedNumber, msgFromClient.getSender());
		System.out.println("[Server] Server has calculated new number: " + processedMessage);
		return processedMessage;
	}

	private void sendMsgToClient(Message msgToClient) throws IOException {
		outputByteStream = new ByteArrayOutputStream();
		outputObjectStream = new ObjectOutputStream(outputByteStream);
		outputObjectStream.writeObject(msgToClient);
		buffer = outputByteStream.toByteArray();

		datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddressFromClient, portNumber);
		datagramSocket.send(datagramPacket);
		System.out.println("[Server] Server has send calculated data: " + msgToClient);
		readOnce = true;
	}

	private void closeDownGates() throws IOException {
		datagramSocket.close();
		inputByteStream.close();
		inputObjectStream.close();
		outputByteStream.close();
		outputObjectStream.close();
		System.out.println("[Server] All server-gates have been closed down.");
	}

}