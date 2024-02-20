package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import client.Client;

public class RCServerRequestHandler implements Runnable {

	private Invoker invoker;

	public RCServerRequestHandler(Invoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public void run() {
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket(Server.RC_UDP_SERVER_PORT);
			System.out.println("RC-Server is now up and running on port: " + Server.RC_UDP_SERVER_PORT);
			byte[] buffer = new byte[1024];
			while (true) {

				// receive searchRequest
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				System.out.println("RC-Server recieved search-request.");
				byte[] bufferResearchResponse = invoker.invoke(packet.getData(), "RC");

				// respond
				DatagramPacket packetResponse = new DatagramPacket(bufferResearchResponse,
						bufferResearchResponse.length, packet.getAddress(), Client.RC_UDP_CLIENT_PORT);
				System.out.println("RC-Server sending back: " + packetResponse.toString());
				socket.send(packetResponse);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
				System.out.println("RC-Server has terminated...");
			}
		}

	}
}
