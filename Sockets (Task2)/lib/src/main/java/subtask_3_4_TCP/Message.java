package subtask_3_4_TCP;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable {
	private int number;
	private String sender;
	private long timestamp;
	private final byte[] dataBlob;
	private int blobSize;

	public Message(int number, String sender, int blobSize) {
		this.number = number;
		this.sender = sender;
		this.blobSize = blobSize;
		this.dataBlob = setupDataBlob(blobSize);
		this.timestamp = System.currentTimeMillis();
	}

	private byte[] setupDataBlob(int size) {
		byte[] dummyDataArray = new byte[size];
		Arrays.fill(dummyDataArray, (byte) 32);
		return dummyDataArray;
	}

	public int getNumber() {
		return number;
	}

	public String getSender() {
		return sender;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public byte[] getDataBlob() {
		return dataBlob;
	}

	public int getBlobSize() {
		return blobSize;
	}

	@Override
	public String toString() {
		return "Message [number=" + number + ", sender=" + sender + ", timestamp=" + timestamp + ", dataBlob-blobSize="
				+ blobSize + "]";
	}

}