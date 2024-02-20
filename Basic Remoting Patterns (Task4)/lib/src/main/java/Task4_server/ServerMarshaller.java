package Task4_server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Task4_util.IMarshallProtocol;

public class ServerMarshaller implements IMarshallProtocol {

	@Override
	public byte[] serialize(String msg) {
		return msg.getBytes();
	}

	@Override
	public String[] deSerialize(byte[] msg) {
		String deSezializedMsg = new String(msg);
		if (!this.validateMsgFormat(deSezializedMsg)) {
			return new String[] { "wrongFormat", "" };
		} else {
			String[] splitUpMsg = IMarshallProtocol.splitMethodnameParamter(deSezializedMsg);
			return splitUpMsg;
		}
	}

	private boolean validateMsgFormat(String msg) {
		System.out.println("hier msg im invoker: " + msg);
		String regex = "[a-zA-Z]+;[a-zA-Z]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(msg);
		return matcher.matches();
	}

}
