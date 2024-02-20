package subtask_2_UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClientThreadImplementation implements Runnable {

	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private InetAddress inetAddress;
	private byte[] buffer;
	private ByteArrayInputStream inputByteStream;
	private ObjectInputStream inputObjectStream;
	private ByteArrayOutputStream outputByteStream;
	private ObjectOutputStream outputObjectStream;
	private int portNumber;
	private Message msgToSend;
	private boolean readOnce;

	public UDPClientThreadImplementation(DatagramSocket datagramSocket, InetAddress inetAddress, int bufferLength,
			int portNumber, Message msgToSend) {
		this.datagramSocket = datagramSocket;
		this.inetAddress = inetAddress;
		this.buffer = new byte[bufferLength];
		this.portNumber = portNumber;
		this.msgToSend = msgToSend;
		this.readOnce = false;
		System.out.println("[Client] Client is set up.");
	}

	@Override
	public void run() {
		System.out.println("[Client] Client is now running ...");
		while (!readOnce) {
			try {
				this.sendMsg();
				this.receiveMsg();
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

	private void sendMsg() throws IOException {
		outputByteStream = new ByteArrayOutputStream();
		outputObjectStream = new ObjectOutputStream(outputByteStream);
		outputObjectStream.writeObject(msgToSend);
		buffer = outputByteStream.toByteArray();

		datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, portNumber);
		datagramSocket.send(datagramPacket);
		System.out.println("[Client] Client has sent msg: " + msgToSend);
		readOnce = true;
	}

	private void receiveMsg() throws IOException, ClassNotFoundException {
		datagramPacket = new DatagramPacket(buffer, buffer.length);
		datagramSocket.receive(datagramPacket);

		inputByteStream = new ByteArrayInputStream(buffer);
		inputObjectStream = new ObjectInputStream(inputByteStream);
		Message msgFromServer = (Message) inputObjectStream.readObject();

		System.out.println("[Client] Client has received msg from server: " + msgFromServer);
		if (msgFromServer.getNumber() == (msgToSend.getNumber() + 1)) {
			readOnce = true;
			System.out.println("[Client] Client has stopped.");
		}
	}

	private void closeDownGates() throws IOException {
		datagramSocket.close();
		inputByteStream.close();
		inputObjectStream.close();
		outputByteStream.close();
		outputObjectStream.close();
		System.out.println("[Client] All client-gates have been closed down.");
	}

}
