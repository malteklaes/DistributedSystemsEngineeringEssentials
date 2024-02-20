package Task4_client;

import Task4_util.IMarshallProtocol;

public class ClientMarshaller implements IMarshallProtocol {

	@Override
	public byte[] serialize(String msg) {
		return msg.getBytes();
	}

	@Override
	public String[] deSerialize(byte[] msg) {
		String deSezializedMsg = new String(msg);
		String[] splitUpMsg = IMarshallProtocol.splitMethodnameParamter(deSezializedMsg);
		return splitUpMsg;
	}

}
