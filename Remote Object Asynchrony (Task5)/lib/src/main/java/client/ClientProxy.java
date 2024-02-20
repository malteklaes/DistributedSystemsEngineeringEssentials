package client;

import generalUtil.IProtocol;
import generalUtil.KnownMethods;
import generalUtil.LogEntry;
import generalUtil.PollObject;

public class ClientProxy implements IProtocol {

	private Requestor requestor;

	public ClientProxy() {
		this.requestor = new Requestor();
	}

	@Override
	public void logSingleEntry(LogEntry entry) {
		requestor.invokeFAF(entry, KnownMethods.singleLog);

	}

	@Override
	public void removeOldLogs(int amountToRemove) {
		String acknowledgement = requestor.invokeSWS(amountToRemove, KnownMethods.removeOldLogs);
		System.out.println("SWS-Client has recieved this acknowledgement from server: " + acknowledgement);

	}

	@Override
	public void addLogsInBulk(LogEntry[] logBulk) {
		requestor.invokePO(logBulk, KnownMethods.addLogsInBulk);
		PollObject resultSessionPollObject;
		// busy waiting for result in pollObject
		while (true) {
			int timeBusyWaiting = 300; // 1 sec for busy waiting
			try {
				Thread.sleep(timeBusyWaiting);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			resultSessionPollObject = requestor.getPollObject();
			if (!resultSessionPollObject.isResultAvailable()) {
				// System.out.println("PO-Client: Result is NOT available, still waiting...");
			} else {
				System.out.println("Hey PO-Client, sessionPollObject here, result is now AVAILABLE with msg: "
						+ resultSessionPollObject.getResult());
				break;
			}
		}

	}

	@Override
	public LogEntry[] search(String searchTerm) {
		String logEntryString = requestor.invokeRC(searchTerm, KnownMethods.searchLogs);
		System.out.println("RS-Client has for this search-term '" + searchTerm + "' received this result from server: "
				+ logEntryString);
		return new LogEntry[] { new LogEntry(logEntryString) };
	}

}
