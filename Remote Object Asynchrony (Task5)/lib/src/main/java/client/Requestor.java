package client;

import generalUtil.IMarshall;
import generalUtil.KnownMethods;
import generalUtil.LogEntry;
import generalUtil.PollObject;
import generalUtil.RequestMessage;
import generalUtil.ResponseMessage;

public class Requestor {
	private ClientRequestHandler crh;
	private PollObject sessionPollObject;

	public Requestor() {
		crh = new ClientRequestHandler();
		sessionPollObject = new PollObject();
	}

	/**
	 * marshall content and methodName and pass it over to ClientRequestHandler
	 * 
	 * task: add single log in server
	 * 
	 * @param entry      (not null)
	 * @param methodName (not null)
	 */
	public void invokeFAF(LogEntry entry, KnownMethods methodName) {
		assert entry != null : "Message content was null";
		assert methodName != null : "Message methodName was null";
		try {
			byte[] marshalledMsg = this.prepareRequestForFAF(entry, methodName);
			crh.sendFAFMessage(marshalledMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * auxiliar function for invokeFAF()to marshall and prepare the request by
	 * bundle entry and methodname to one RequestMessage-object/byte-Array
	 * 
	 * @param entry
	 * @param methodName
	 * @return byte[] (not null)
	 * @throws Exception
	 */
	private byte[] prepareRequestForFAF(LogEntry entry, KnownMethods methodName) throws Exception {
		RequestMessage<LogEntry> unmarshalledMsg = new RequestMessage<>(methodName, entry);
		byte[] marshalledMsg = unmarshalledMsg.marshall();
		return marshalledMsg;
	}

	/**
	 * marshall content and methodName and pass it over to ClientRequestHandler
	 * 
	 * task: remove certain amount of log-entries in server
	 * 
	 * @param entry      (not null)
	 * @param methodName (not null)
	 */
	public String invokeSWS(int amountToRemove, KnownMethods methodName) {
		assert methodName != null : "Message methodName was null";
		try {
			byte[] marshalledMsg = this.prepareRequestForSWS(amountToRemove, methodName);
			byte[] responseOfRemoval = crh.sendSWSMessage(marshalledMsg);
			ResponseMessage<String> unmarshalledRemovalResponse = IMarshall.unmarshall(responseOfRemoval);
			return unmarshalledRemovalResponse.getResponseData();
		} catch (Exception e) {
			e.printStackTrace();
			return "nothing was send from SWS-Client";
		}
	}

	/**
	 * auxiliar function for invokeSWS() to marshall and prepare the request by
	 * bundle entry and methodname to one RequestMessage-object/byte-Array
	 * 
	 * @param entry
	 * @param methodName
	 * @return byte[] (not null)
	 * @throws Exception
	 */
	private byte[] prepareRequestForSWS(int amountToRemove, KnownMethods methodName) throws Exception {
		RequestMessage<Integer> unmarshalledMsg = new RequestMessage<>(methodName, amountToRemove);
		byte[] marshalledMsg = unmarshalledMsg.marshall();
		return marshalledMsg;
	}

	public void invokePO(LogEntry[] bulkLogs, KnownMethods methodName) {
		sessionPollObject = new PollObject();
		Runnable runSessionPollObject = () -> {
			try {
				byte[] marshalledMsg = this.prepareRequestForPO(bulkLogs, methodName);
				byte[] responseOfBulkLogsSend = crh.sendPOMessage(marshalledMsg);
				ResponseMessage<String> responseOnBulkLogSend = IMarshall.unmarshall(responseOfBulkLogsSend);
				// update sessionPollObject
				sessionPollObject.setResult(responseOnBulkLogSend.getResponseData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		new Thread(runSessionPollObject).start();
	}

	/**
	 * auxiliar function for invokePO() to marshall and prepare the request ... it
	 * can take some time!
	 * 
	 * @param bulkLogs
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	private byte[] prepareRequestForPO(LogEntry[] bulkLogs, KnownMethods methodName) throws Exception {
		RequestMessage<LogEntry[]> unmarshalledMsg = new RequestMessage<>(methodName, bulkLogs);
		byte[] marshalledMsg = unmarshalledMsg.marshall();

		// PROCESSTIME ... (bulkLogs.length/10) seconds
		int bulkLogsLengthProcessTime = 100 * bulkLogs.length;
		Thread.sleep(bulkLogsLengthProcessTime);
		// PROCESSTIME ...

		return marshalledMsg;
	}

	public PollObject getPollObject() {
		return sessionPollObject;
	}

	/**
	 * marshall searchLogEntry and methodName and pass it over to
	 * ClientRequestHandler
	 * 
	 * @param searchLogEntry
	 * @param methodName
	 * @return String (might be null)
	 */
	public String invokeRC(String searchLogEntry, KnownMethods methodName) {
		assert searchLogEntry != null : "No search term was requested";
		assert methodName != null : "Message methodName was null";
		try {
			byte[] marshalledMsg = this.prepareRequestForRC(searchLogEntry, methodName);
			byte[] response = crh.sendRCMessage(marshalledMsg);
			ResponseMessage<String> searchResponse = IMarshall.unmarshall(response);
			String searchResponseString = searchResponse.getResponseData().toString();
			return this.checkForErrorCodes(searchResponseString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private byte[] prepareRequestForRC(String searchLogEntry, KnownMethods methodName) throws Exception {
		RequestMessage<String> unmarshalledMsg = new RequestMessage<>(methodName, searchLogEntry);
		byte[] marshalledMsg = unmarshalledMsg.marshall();
		return marshalledMsg;
	}

	/**
	 * checks for a received Error-Code received from server: (1) ErrorCode1 ==
	 * "nothing found" (else) original response string
	 * 
	 * @param receivedMsg
	 * @return String (not null)
	 */
	private String checkForErrorCodes(String receivedMsg) {
		switch (receivedMsg) {
		case "ERROR1":
			return "ERRORCODE 1: no log-entry found in server";
		default:
			return receivedMsg;
		}
	}

}
