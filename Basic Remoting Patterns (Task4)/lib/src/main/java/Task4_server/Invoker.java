package Task4_server;

import Task4_exceptions.InvalidMethodName;
import Task4_exceptions.MessageWrongFormatException;

public class Invoker {

	byte[] input;
	String methodName = null;
	private RemoteObject remoteObject;
	ServerMarshaller marshaller;

	public Invoker() {
		this.remoteObject = new RemoteObject();
		marshaller = new ServerMarshaller();
	}

	public String[] marshall(byte[] receivedData) {
		return marshaller.deSerialize(receivedData);
	}

	public byte[] unmarshall(String receivedData) {
		return marshaller.serialize(receivedData);
	}

	/**
	 * 
	 * @param methodName
	 * @param parameter
	 * @return String (null if there is an exception so there is no appropriate
	 *         return string)
	 * @throws InvalidMethodName
	 * @throws MessageWrongFormatException
	 */
	public String invoke(String methodName, String parameter) throws InvalidMethodName, MessageWrongFormatException {
		if (methodName != null) {
			switch (methodName) {
			case "hello":
				return remoteObject.hello(parameter);
			case "goodbye":
				return remoteObject.goodbye(parameter);
			case "wrongFormat":
				throw new MessageWrongFormatException("False message layout",
						"Suggested msg to send has wrong format.");
			default:
				throw new InvalidMethodName("InvalidMethodName",
						"No matching method was found with given methodname: " + methodName + "!");
			}
		} else {
			return null;
		}
	}

}
