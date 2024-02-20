package client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import generalUtil.Colors;
import generalUtil.LogEntry;

public class Client {

	public static final int RC_UDP_CLIENT_PORT = 3002;

	public static final int CLIENT_UPTIME = 15; // [seconds]
	public static final String SYSTEM_CONSOLE_PRINT_COLOR = Colors.YELLOW_BOLD;
	public static final int AMOUNTTHREADS = 4;
	public static final int SYNCWORKROTATIONS = 10;
	public static final ClientProxy cp = new ClientProxy();
	public static final ExecutorService THREADPOOL = Executors.newFixedThreadPool(AMOUNTTHREADS);

	public static void main(String[] args) {
		System.out.println(Colors.printHighlighted(SYSTEM_CONSOLE_PRINT_COLOR,
				"Start general client which is up for " + CLIENT_UPTIME + " seconds."));

		try {
			setupAsyncWork();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * start async work with treadpool
	 * 
	 * @param generalInvoker
	 * @throws InterruptedException
	 */
	public static void setupAsyncWork() throws InterruptedException {

		// FAF strategy work
		THREADPOOL.submit(fafStrategyWork());
		// SWS strategy work
		THREADPOOL.submit(swsStrategyWork());
		// PO strategy work
		THREADPOOL.submit(poStrategyWork());
		// RC strategy work
		THREADPOOL.submit(rcStrategyWork());

		// meanwhile keep on shuffling async
		THREADPOOL.submit(syncWork());
		terminateThreadPool();

	}

	/**
	 * meanwhile client is doing sth. work parallel (yellow console print)
	 * 
	 * @return Runnable (not null)
	 */
	public static Runnable syncWork() {
		Runnable syncTask = () -> {
			for (int i = 0; i < SYNCWORKROTATIONS; i++) {
				System.out.println(Colors.printHighlighted(SYSTEM_CONSOLE_PRINT_COLOR,
						"...keep on shuffling the " + i + " times in client..."));
				pauseTime(500);
			}
		};
		return syncTask;
	}

	/**
	 * strategy: Fire and Forget
	 * 
	 * task: adds a single log to server (usecase 1)
	 * 
	 * protocol: UDP
	 * 
	 * @return Runnable (not null)
	 */
	public static Runnable fafStrategyWork() {
		System.out.println("...start faf-async-work in client (task: add single log)...");
		return () -> {
			LogEntry log = new LogEntry("fireAndForgetStrategy");
			cp.logSingleEntry(log);
		};
	}

	/**
	 * strategy: Sync with Server
	 * 
	 * task: removes an amount (amountOfMsgToRmv) of array entries (usecase 2)
	 * 
	 * protocol: TCP
	 * 
	 * @return Runnable (not null)
	 */
	public static Runnable swsStrategyWork() {
		int amountOfMsgToRmv = 5;
		System.out.println(
				"...start sws-async-work in client (task: remove " + amountOfMsgToRmv + " entries in logs)...");
		return () -> {
			cp.removeOldLogs(amountOfMsgToRmv);
		};
	}

	/**
	 * strategy: Poll Object
	 * 
	 * task: send log messages in bulk (usecase 3)
	 * 
	 * protocol: TCP
	 * 
	 * @return
	 */
	public static Runnable poStrategyWork() {
		System.out.println("...start po-async-work in client (task: send log messages in bulk)...");
		return () -> {
			int amountOfLogs = 10;
			LogEntry[] bulkLogs = produceBulkLog(amountOfLogs);
			cp.addLogsInBulk(bulkLogs);
		};
	}

	/**
	 * strategy: Result Callback Object
	 * 
	 * task: search for a log (usecase 4)
	 * 
	 * protocol: UDP
	 * 
	 * @return
	 */
	public static Runnable rcStrategyWork() {
		System.out.println("...start rc-async-work in client (task: search for log)...");
		return () -> {
			String searchLogEntry = "testSearchLog";
			cp.search(searchLogEntry);
		};
	}

	public static void terminateThreadPool() throws InterruptedException {
		if (!THREADPOOL.awaitTermination(CLIENT_UPTIME, TimeUnit.SECONDS)) {
			THREADPOOL.shutdown();
			System.out.println(Colors.printHighlighted(SYSTEM_CONSOLE_PRINT_COLOR,
					"Shutdown threadPool and terminate async work in client..."));
		}
	}

	private static void pauseTime(int pauseLength) {
		try {
			Thread.sleep(pauseLength);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static LogEntry[] produceBulkLog(int amountOfLogs) {
		LogEntry[] bulkLog = new LogEntry[amountOfLogs];
		for (int i = 0; i < amountOfLogs; i++) {
			LogEntry logEntry = new LogEntry("testBulkLog-#" + i);
			bulkLog[i] = logEntry;
		}
		return bulkLog;
	}

}
