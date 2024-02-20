package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import generalUtil.Colors;
import generalUtil.ResponseMessage;

public class SWSServerRequestHandler implements Runnable {

	private Invoker invoker;
	private int amountLogsToBeRemoved;

	public SWSServerRequestHandler(Invoker invoker) {
		this.invoker = invoker;
		amountLogsToBeRemoved = 0;
	}

	@Override
	public void run() {
		Socket socket = null;
		try {
			ServerSocket ss = new ServerSocket(Server.SWS_TCP_SERVER_PORT);
			System.out.println("SWS-Server(RequestHandler) is up and running on port: " + Server.SWS_TCP_SERVER_PORT);
			while (Server.MAINSERVER_UP) {
				System.out.println("SWS-Server is now listening...");
				socket = ss.accept();
				byte[] recievedInput = null;
				byte[] buffer = new byte[1024];
				recievedInput = this.getInput(socket, buffer);
				this.formulateAcknowledgement(socket);
				invoker.invoke(recievedInput, "SWS");
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
				System.out.println(Colors.printHighlighted(Server.SYSTEM_CONSOLE_PRINT_COLOR, "SWS-Server closed."));
			}
		}

	}

	/**
	 * recieves input with given socket
	 * 
	 * @param socket
	 * @param buffer
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private byte[] getInput(Socket socket, byte[] buffer) throws IOException, ClassNotFoundException {
		InputStream inputStream = socket.getInputStream();
		int bufferEntries = inputStream.read(buffer);
		return Arrays.copyOf(buffer, bufferEntries);
	}

	/**
	 * formulates acknowledgement as reply to client
	 * 
	 * @param socket
	 * @throws Exception
	 */
	private void formulateAcknowledgement(Socket socket) throws Exception {
		OutputStream outputStream = socket.getOutputStream();
		String acknowledgement = "Server successfully received log removal request!";
		ResponseMessage<String> acknowledgeMessage = new ResponseMessage<String>(acknowledgement);
		outputStream.write(acknowledgeMessage.marshall());
		outputStream.flush();
		System.out.println("SWS-Server has send acknowledgement...");
	}

}
