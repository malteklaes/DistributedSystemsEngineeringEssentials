package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import generalUtil.IProtocol;
import generalUtil.LogEntry;

public class RemoteObject implements IProtocol {
	private LogEntry[] storage = new LogEntry[0];
	private int nextEntryPointer = 0;

	@Override
	public void logSingleEntry(LogEntry entry) {
		assert (!Objects.isNull(entry));
		increaseLogStorage(1);
		storage[nextEntryPointer++] = entry;
	}

	@Override
	public void removeOldLogs(int amountToRemove) {
		assert (amountToRemove > 0);

		amountToRemove = Math.min(amountToRemove, nextEntryPointer);

		int preserveAtIndex = 0;
		for (int i = amountToRemove; i < nextEntryPointer; i++) {
			storage[preserveAtIndex++] = storage[i];
		}

		nextEntryPointer -= amountToRemove;
		for (int i = nextEntryPointer; i < storage.length; i++) {
			storage[i] = null;
		}
	}

	@Override
	public void addLogsInBulk(LogEntry[] logBulk) {
		assert (!Objects.isNull(logBulk));

		increaseLogStorage(logBulk.length);
		for (int i = 0; i < logBulk.length; i++) {
			logSingleEntry(logBulk[i]);
		}
	}

	@Override
	public LogEntry[] search(String searchTerm) {
		var matchingEntries = new ArrayList<>();

		for (int i = 0; i < nextEntryPointer; i++) {
			var logEntry = storage[i];
			if (logEntry.matchesSearchTerm(searchTerm)) {
				matchingEntries.add(logEntry);
			}
		}

		return matchingEntries.toArray(new LogEntry[matchingEntries.size()]);
	}

	private int increaseLogStorage(int increaseBy) {
		assert (increaseBy > 0);

		int oldStorageSpace = storage.length;
		int newStorageSpace = nextEntryPointer + increaseBy;

		if (oldStorageSpace >= newStorageSpace) {
			return 0;
		}

		var newStorage = new LogEntry[newStorageSpace];

		for (int i = 0; i < storage.length; i++) {
			newStorage[i] = storage[i];
		}

		this.storage = newStorage;
		return newStorageSpace - oldStorageSpace;
	}

	public LogEntry[] getStorage() {
		return storage;
	}

	@Override
	public String toString() {
		return "Stored=" + Arrays.toString(storage) + ", nextEntryPointer=" + nextEntryPointer;
	}
}