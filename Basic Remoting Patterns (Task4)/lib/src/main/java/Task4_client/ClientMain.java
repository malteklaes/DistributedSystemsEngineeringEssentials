package Task4_client;

import Task4_exceptions.GenericRemoteException;
import Task4_server.ServerMain;

public class ClientMain {

	public static void main(String[] args) {
		int port = ServerMain.PORT;
		String host = "localhost";
		System.out.println("[Client] Client is started on port: " + port + " and host: " + host + "...");

		String msghello = "hello;DSE";
		String msggoodbye = "goodbye;DSE";
		String msgWRONGFormat = "goodbye;WRONG;oneMoreParam";
		String msgWRONGMethod = "WRONGmethodname;anyparameter";

		// HELLO -----------------------
		try {
			ClientProxy cp = new ClientProxy(port, host);
			System.out.println("[Client] Answer from Server: " + cp.hello(msghello));
		} catch (GenericRemoteException e) {
			e.printStackTrace();
		}

		// GOODBYE -----------------------
		try {
			ClientProxy cp2 = new ClientProxy(port, host);
			System.out.println("[Client] Answer from Server: " + cp2.goodbye(msggoodbye));
		} catch (GenericRemoteException e) {
			e.printStackTrace();
		}

		// WRONG FORMAT -----------------------
		try {
			ClientProxy cp3 = new ClientProxy(port, host);
			System.out.println("[Client] Answer from Server: " + cp3.goodbye(msgWRONGFormat));
		} catch (GenericRemoteException e) {
			e.printStackTrace();
		}

		// WRONG METHODNAME -----------------------
		try {
			ClientProxy cp4 = new ClientProxy(port, host);
			System.out.println("[Client] Answer from Server: " + cp4.goodbye(msgWRONGMethod));
		} catch (GenericRemoteException e) {
			e.printStackTrace();
		}

		System.out.println("[Client] Client has been terminated.");

	}

}
