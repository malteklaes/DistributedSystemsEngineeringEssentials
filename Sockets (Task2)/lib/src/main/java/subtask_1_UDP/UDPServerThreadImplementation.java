package subtask_1_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class UDPServerThreadImplementation implements Runnable {

	private DatagramSocket datagramSocket;
	private byte[] buffer;
	private int portNumber;
	private InetAddress inetAddressFromClient;
	private DatagramPacket datagramPacket;
	private ByteBuffer byteBuffer;

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
				int msgFromClient = this.receiveMsg();
				int processedMsgFromClient = this.processInput(msgFromClient);
				this.sendMsgToClient(processedMsgFromClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private int receiveMsg() throws IOException {
		datagramPacket = new DatagramPacket(buffer, buffer.length);
		datagramSocket.receive(datagramPacket); // waits until it recieves
		inetAddressFromClient = datagramPacket.getAddress();
		portNumber = datagramPacket.getPort();
		byteBuffer = ByteBuffer.wrap(datagramPacket.getData(), 0, datagramPacket.getLength());
		int msgFromClient = byteBuffer.getInt();
		System.out.println("[Server] Server has received msg from client: " + msgFromClient);
		return msgFromClient;
	}

	private int processInput(int msgInteger) {
		int processedNumber = msgInteger + 1;
		System.out.println("[Server] Server has calculated new number: " + processedNumber);
		return processedNumber;
	}

	private void sendMsgToClient(int msgToClient) throws IOException {
		// overwrites existing datagramPacket
		datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddressFromClient, portNumber);
		byteBuffer.clear();
		byteBuffer.putInt(msgToClient);
		byte[] data = byteBuffer.array();
		datagramPacket.setData(data);
		datagramPacket.setLength(data.length);
		datagramSocket.send(datagramPacket);
		readOnce = true;
		System.out.println("[Server] Server has send calculated data: " + msgToClient);
	}

}
