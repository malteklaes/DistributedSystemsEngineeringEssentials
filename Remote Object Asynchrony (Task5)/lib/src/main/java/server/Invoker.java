package server;

import generalUtil.Colors;
import generalUtil.IMarshall;
import generalUtil.KnownMethods;
import generalUtil.LogEntry;
import generalUtil.RequestMessage;
import generalUtil.ResponseMessage;

public class Invoker {

	private final RemoteObject remoteObject;

	public Invoker() {
		this.remoteObject = new RemoteObject();
		this.createSampleInitialLogs();
	}

	public byte[] invoke(byte[] request, String strategyServer) {
		try {
			RequestMessage<?> requestMsg = IMarshall.unmarshall(request);
			String response = this.invokeRemoteObjectMethod(requestMsg);
			System.out.println(Colors.printHighlighted(Server.SYSTEM_CONSOLE_PRINT_COLOR,
					"Message from " + strategyServer + "-Server: " + response));
			byte[] responseMsg = (new ResponseMessage<Object>(response)).marshall();
			return responseMsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[] {};

	}

	/**
	 * invoke the message demanded from requestMsg (requestMsg.getMethodName())
	 * 
	 * @param requestMsg (not null)
	 * @return String (not null)
	 */
	private String invokeRemoteObjectMethod(RequestMessage<?> requestMsg) {
		assert (requestMsg != null) : "requestMsg was null!";
		KnownMethods methodName = requestMsg.getMethodName();
		Object requestData = requestMsg.getRequestData();

		switch (methodName) {
		case singleLog:
			LogEntry logEntry = (LogEntry) requestData;
			remoteObject.logSingleEntry(logEntry);
			return ("Logged logEntry: " + logEntry.toString());
		case removeOldLogs:
			int amountLogsToBeRemoved = (int) requestData;
			remoteObject.removeOldLogs(amountLogsToBeRemoved);
			return ("Removed " + amountLogsToBeRemoved + " LogEntries.");
		case addLogsInBulk:
			LogEntry[] logBulkData = (LogEntry[]) requestData;
			remoteObject.addLogsInBulk(logBulkData);

			// PROCESSTIME ... (bulkLogs.length/10) seconds
			int bulkLogsLengthProcessTime = 100 * logBulkData.length;
			try {
				Thread.sleep(bulkLogsLengthProcessTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// PROCESSTIME ...

			System.out.println(("Add a bulk of data: " + logBulkData.length + " size."));
			return ("Add a bulk of data: " + logBulkData.length + " size.");
		case searchLogs:
			String logsSearch = (String) requestData;
			String result = logEntryArrayToString(remoteObject.search(logsSearch), logsSearch);
			return result;
		default:
			return "no method found";
		}
	}

	/**
	 * ERROR1: no log-entry was found in log-book (of remote object)
	 * 
	 * @param searchResult
	 * @param requestData
	 * @return
	 */
	private String logEntryArrayToString(LogEntry[] searchResult, String requestData) {
		if (searchResult != null && searchResult.length > 0 && searchResult[0] != null) {
			return searchResult[0].getLogEntry();
		}
		return "ERROR1";
	}

	/**
	 * for the sake of testing just initialize some/one single log
	 */
	private void createSampleInitialLogs() {
		LogEntry logEntry = new LogEntry("testSearchLog");
		remoteObject.logSingleEntry(logEntry);
	}

	private void printLogs() {
		for (int i = 0; i < remoteObject.getStorage().length; i++) {
			System.out.println("Log-Book: " + remoteObject.getStorage()[i]);
		}
	}

}
