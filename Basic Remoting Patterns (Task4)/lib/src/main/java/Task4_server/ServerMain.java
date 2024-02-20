package Task4_server;

import java.io.IOException;

public class ServerMain {
	public static final int PORT = 9000;

	public static void main(String[] args) {
		System.out.println("[Server] Server is started on port: " + PORT + "...");
		ServerRequestHandler srh = new ServerRequestHandler(PORT);
		try {
			srh.listenToRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
