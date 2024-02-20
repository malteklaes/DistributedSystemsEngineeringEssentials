package Task4_client;

import java.io.IOException;

import Task4_exceptions.ReceiveMessageException;
import Task4_exceptions.SendMessageException;

public class Requestor {

	private byte[] serializedString;
	private ClientMarshaller marshaller;
	private ClientRequestHandler crh;

	public Requestor(int port, String host) {
		marshaller = new ClientMarshaller();
		serializedString = null;
		crh = new ClientRequestHandler(port, host);
	}

	public String invoke(String msgAsString) throws ReceiveMessageException, SendMessageException {
		this.serializedString = marshaller.serialize(msgAsString);
		try {
			this.send();
		} catch (SendMessageException e) {
			throw new SendMessageException("SendMessageException", "[Client] Msg has not been yet serialized.");
		}
		return this.recieveAnswer();
	}

	private void send() throws SendMessageException {
		if (serializedString != null) {
			try {
				crh.sendMessage(serializedString);
			} catch (IOException e) {
				throw new SendMessageException("SendMessageException", "[Client] Msg has not been yet serialized.");
			}
		}

	}

	private String recieveAnswer() throws ReceiveMessageException {
		String oneResponse = "";
		try {
			byte[] receivedData = crh.receiveAnswer();
			String[] response = marshaller.deSerialize(receivedData);
			for (int i = 0; i < response.length; i++) {
				oneResponse += response[i];
			}
		} catch (IOException e) {
			throw new ReceiveMessageException("ReceiveMessageException",
					"[Client] There is problem with recieving the message!");
		}
		return checkForErrorCodes(oneResponse);
	}

	/**
	 * checks for a received Error-Code received from server: (1) ErrorCode1 ==
	 * "wrongMethodName" (else) original response string
	 * 
	 * @param receivedMsg
	 * @return String (not null)
	 */
	private String checkForErrorCodes(String receivedMsg) {
		switch (receivedMsg) {
		case "ErrorCode1":
			return "ERRORCODE 1: wrong method-name";
		case "ErrorCode2":
			return "ERRORCODE 2: wrong msg-format";
		default:
			return receivedMsg;
		}
	}

}
