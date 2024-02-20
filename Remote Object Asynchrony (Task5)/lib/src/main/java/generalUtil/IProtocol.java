package generalUtil;

/**
 * protocol for both client (clientProxy) and server (RemoteObject) to implement
 * 
 * @author Malte
 *
 */
public interface IProtocol {

	public void logSingleEntry(LogEntry entry);

	public void removeOldLogs(int amountToRemove);

	public void addLogsInBulk(LogEntry[] logBulk);

	public LogEntry[] search(String searchTerm);

}
