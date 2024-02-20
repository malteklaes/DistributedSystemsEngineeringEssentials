package subtask_2_TCP;

import java.io.Serializable;

public class Message implements Serializable {
	private int number;
	private String sender;
	private long timestamp;

	public Message(int number, String sender) {
		this.number = number;
		this.sender = sender;
		this.timestamp = System.currentTimeMillis();
	}

	public Message(int number) {
		this.number = number;
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

	@Override
	public String toString() {
		return "Message [number=" + number + ", sender=" + sender + ", timestamp=" + timestamp + "]";
	}

}
