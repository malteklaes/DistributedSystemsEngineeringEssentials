package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import generalUtil.Colors;

/**
 * 
 * @author Malte
 *
 */
public class FAFServerRequestHandler implements Runnable {

	private Invoker invoker;

	public FAFServerRequestHandler(Invoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public void run() {
		// TODO extract FAFServer
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(Server.FAF_UDP_SERVER_PORT);
			System.out.println("FAF-Server(RequestHandler) is up and running on port: " + Server.FAF_UDP_SERVER_PORT);
			while (Server.MAINSERVER_UP) {
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				invoker.invoke(buffer, "FAF");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println(Colors.printHighlighted(Server.SYSTEM_CONSOLE_PRINT_COLOR, "FAF-Server closed."));
			// socket.close();
		}
	}

}
