package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import server.Server;

public class ClientRequestHandler {

	/**
	 * task to add single log with no response expected
	 * 
	 * protocol: UDP
	 * 
	 * @param serializedMsg (not null and not empty)
	 */
	public void sendFAFMessage(byte[] serializedMsg) {
		assert serializedMsg != null && serializedMsg.length > 0 : "serializedMsg is null or empty";
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
			InetAddress host = InetAddress.getByName(Server.GENERAL_HOST);
			int port = Server.FAF_UDP_SERVER_PORT;
			System.out.println("FAF-Client is running on port: " + Server.FAF_UDP_SERVER_PORT);
			DatagramPacket packet = new DatagramPacket(serializedMsg, serializedMsg.length, host,
					Server.FAF_UDP_SERVER_PORT);
			socket.send(packet);
			System.out.println("FAF-Client has send packet...");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
				System.out.println("FAF-Client has terminated...");
			}
		}
	}

	/**
	 * task to remove n amount of logs in server with ack expected
	 * 
	 * protocol: TCP
	 * 
	 * @param serializedMsg
	 * @return
	 */
	public byte[] sendSWSMessage(byte[] serializedMsg) {
		assert serializedMsg != null && serializedMsg.length > 0 : "serializedMsg is null or empty";
		Socket socket = null;
		try {
			socket = new Socket(Server.GENERAL_HOST, Server.SWS_TCP_SERVER_PORT);
			System.out.println("SWS-Client is running on port: " + Server.SWS_TCP_SERVER_PORT);
			this.formulateRequest(socket, serializedMsg);
			byte[] buffer = new byte[1024];
			return this.getAcknowledgement(socket, buffer);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("SWS-Client has been terminated...");
			}
		}
		return new byte[] {};
	}

	/**
	 * auxiliar function for sendSWSMessage()
	 * 
	 * protocol: UDP
	 * 
	 * @param socket
	 * @param serializedMsg
	 * @throws IOException
	 */
	private void formulateRequest(Socket socket, byte[] serializedMsg) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(serializedMsg);
		System.out.println("SWS-Client has send request...");
	}

	private byte[] getAcknowledgement(Socket socket, byte[] buffer) throws IOException, ClassNotFoundException {
		InputStream inputStream = socket.getInputStream();
		int howManyLogsWereRemoved = inputStream.read(buffer);
		byte[] acknowledgement = Arrays.copyOf(buffer, howManyLogsWereRemoved);
		return acknowledgement;
	}

	public byte[] sendPOMessage(byte[] serializedMsg) {
		assert serializedMsg != null && serializedMsg.length > 0 : "serializedMsg is null or empty";
		Socket socket = null;
		try {
			socket = new Socket(Server.GENERAL_HOST, Server.PO_TCP_SERVER_PORT);
			System.out.println("PO-Client is running on port: " + Server.PO_TCP_SERVER_PORT);
			this.formulatePORequest(socket, serializedMsg);
			byte[] buffer = new byte[1024];
			return this.getPOResponse(socket, buffer);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("PO-Client has been terminated...");
			}
		}
		return new byte[] {};
	}

	private void formulatePORequest(Socket socket, byte[] serializedMsg) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(serializedMsg);
		System.out.println("PO-Client has send request...");
	}

	private byte[] getPOResponse(Socket socket, byte[] buffer) throws IOException, ClassNotFoundException {
		InputStream inputStream = socket.getInputStream();
		int responseOnBulkLogInput = inputStream.read(buffer);
		byte[] poResponseBuffer = Arrays.copyOf(buffer, responseOnBulkLogInput);
		return poResponseBuffer;
	}

	/**
	 * task to search for a log in server and expect a response with search result
	 * 
	 * protocol: UDP
	 * 
	 * @param serializedMsg
	 * @return
	 */
	public byte[] sendRCMessage(byte[] serializedMsg) {
		DatagramSocket socket = null;

		try {
			// send request
			socket = new DatagramSocket(Client.RC_UDP_CLIENT_PORT);
			InetAddress host = InetAddress.getByName("localhost");
			DatagramPacket packet = new DatagramPacket(serializedMsg, serializedMsg.length, host,
					Server.RC_UDP_SERVER_PORT);
			System.out.println("RC-Client is running on port: " + Server.RC_UDP_SERVER_PORT);
			socket.send(packet);
			System.out.println("RC-Client has send search-request...");

			// get response
			byte[] buffer = new byte[1024];
			DatagramPacket response = new DatagramPacket(buffer, buffer.length);
			socket.receive(response);
			System.out.println("RC-Client has received msg...");
			return response.getData();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
				System.out.println("Client has terminated...");
			}
		}
		return serializedMsg;
	}

}
