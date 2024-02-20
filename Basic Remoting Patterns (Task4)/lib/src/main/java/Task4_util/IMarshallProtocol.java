package Task4_util;

import java.io.UnsupportedEncodingException;

public interface IMarshallProtocol {

	public static final char DELIMITER = ';';

	public byte[] serialize(String msg) throws UnsupportedEncodingException;

	public String[] deSerialize(byte[] msg);

	static String[] splitMethodnameParamter(String msg) {
		char delimiter = IMarshallProtocol.DELIMITER;
		String[] msgParts = msg.split(String.valueOf(delimiter));
		return msgParts;
	}

}
