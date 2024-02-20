package Task4_client;

import Task4_exceptions.MessageWrongFormatException;
import Task4_exceptions.ReceiveMessageException;
import Task4_exceptions.SendMessageException;
import Task4_util.IProtocol;

public class ClientProxy implements IProtocol {

	private Requestor requestor;

	public ClientProxy(int port, String host) throws MessageWrongFormatException {
		this.requestor = new Requestor(port, host);
	}

	@Override
	public String hello(String msg) {
		String response = "";
		try {
			response = requestor.invoke(msg);
		} catch (ReceiveMessageException e) {
			response = "ERRORCODE 0: General transmission error";
			e.printStackTrace();
		} catch (SendMessageException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String goodbye(String msg) {
		String response = "";
		try {
			response = requestor.invoke(msg);
		} catch (ReceiveMessageException e) {
			response = "ERRORCODE 0: General transmission error";
			e.printStackTrace();
		} catch (SendMessageException e) {
			e.printStackTrace();
		}
		return response;
	}

}
