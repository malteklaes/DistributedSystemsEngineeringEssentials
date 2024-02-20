package generalUtil;

import java.util.Arrays;

public class RemoteCallbackObject {

	private ResponseMessage<?> responseMsg;

	public RemoteCallbackObject(ResponseMessage<?> responseMsg) {
		this.responseMsg = responseMsg;
	}

	public void printMsg() {
		if (responseMsg != null) {
			System.out.println("Response: " + Arrays.toString((LogEntry[]) responseMsg.getResponseData()));
		}
	}

	public ResponseMessage<?> getResponseMsg() {
		return responseMsg;
	}

}
