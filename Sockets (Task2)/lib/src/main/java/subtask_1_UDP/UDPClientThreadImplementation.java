package subtask_1_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class UDPClientThreadImplementation implements Runnable {

	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private InetAddress inetAddress;
	private byte[] buffer;
	private int bufferLength;
	private int portNumber;
	private int numberToSend;
	private boolean readOnce;
	private ByteBuffer byteBuffer;

	public UDPClientThreadImplementation(DatagramSocket datagramSocket, InetAddress inetAddress, int bufferLength,
			int portNumber, int numberToSend) {
		this.datagramSocket = datagramSocket;
		this.inetAddress = inetAddress;
		this.buffer = new byte[bufferLength];
		this.bufferLength = bufferLength;
		this.portNumber = portNumber;
		this.numberToSend = numberToSend;
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendMsg() throws IOException {
		buffer = ByteBuffer.allocate(bufferLength).putInt(numberToSend).array();
		datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, portNumber);
		datagramSocket.send(datagramPacket);
		System.out.println("[Client] Client has sent msg: " + numberToSend);
	}

	private void receiveMsg() throws IOException {
		datagramPacket = new DatagramPacket(buffer, buffer.length);
		datagramSocket.receive(datagramPacket); // waits until it recieves
		byteBuffer = ByteBuffer.wrap(datagramPacket.getData(), 0, datagramPacket.getLength());
		int msgFromServer = byteBuffer.getInt();
		System.out.println("[Client] Client has received msg from server: " + msgFromServer);
		if (msgFromServer == (numberToSend + 1)) {
			readOnce = true;
			System.out.println("[Client] Client has stopped.");
		}
	}

}
