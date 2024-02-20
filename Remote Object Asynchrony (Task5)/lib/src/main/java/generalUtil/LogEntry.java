package generalUtil;

import java.io.Serializable;

/**
 * LogEntry has to be made serializable in order to be transport over network
 * (interface Serializable, else java.io.NotSerializableException)
 * 
 * @author Malte
 *
 */
public class LogEntry implements Serializable {
	private final String logEntry;

	@Override
	public String toString() {
		return "logEntry=" + logEntry;
	}

	public LogEntry(String logEntry) {
		super();
		assert (logEntry != null);
		this.logEntry = logEntry;
	}

	public String getLogEntry() {
		return logEntry;
	}

	public boolean matchesSearchTerm(String searchTerm) {
		return logEntry.contains(searchTerm);
	}
}