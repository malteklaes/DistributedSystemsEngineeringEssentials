package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import generalUtil.Colors;

public class POServerRequestHandler implements Runnable {
	private Invoker invoker;

	public POServerRequestHandler(Invoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public void run() {
		Socket socket = null;
		try {
			ServerSocket ss = new ServerSocket(Server.PO_TCP_SERVER_PORT);
			System.out.println("PO-Server(RequestHandler) is up and running on port: " + Server.PO_TCP_SERVER_PORT);
			while (Server.MAINSERVER_UP) {
				System.out.println("PO-Server is now listening...");
				socket = ss.accept();
				byte[] recievedInput = null;
				byte[] buffer = new byte[1024];
				recievedInput = this.getInput(socket, buffer);
				byte[] responseBulkWrite = invoker.invoke(recievedInput, "PO");
				OutputStream outputStream = socket.getOutputStream();
				outputStream.write(responseBulkWrite);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(Colors.printHighlighted(Server.SYSTEM_CONSOLE_PRINT_COLOR, "PO-Server closed."));
			}
		}

	}

	private byte[] getInput(Socket socket, byte[] buffer) throws IOException, ClassNotFoundException {
		InputStream inputStream = socket.getInputStream();
		int bufferEntries = inputStream.read(buffer);
		return Arrays.copyOf(buffer, bufferEntries);
	}

}
