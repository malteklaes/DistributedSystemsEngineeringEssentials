package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import generalUtil.Colors;
import generalUtil.TaskAsyncStrategy;

public class Server {

	public static final String GENERAL_HOST = "localhost";
	public static final int FAF_UDP_SERVER_PORT = 3000;
	public static final int RC_UDP_SERVER_PORT = 3001;
	public static final int SWS_TCP_SERVER_PORT = 4001;
	public static final int PO_TCP_SERVER_PORT = 5001;

	public static final int SERVER_UPTIME = 20; // [seconds]
	public static boolean MAINSERVER_UP = true;
	public static final String SYSTEM_CONSOLE_PRINT_COLOR = Colors.CYAN_BOLD;
	public static final int AMOUNTTHREADS = 4;
	public static final int SYNCWORKROTATIONS = 10;
	public static final ExecutorService THREADPOOL = Executors.newFixedThreadPool(AMOUNTTHREADS);
	public static TaskAsyncStrategy TASK_ASYNC_STRATEGY = TaskAsyncStrategy.SYNCWITHSERVER;

	public static void main(String[] args) {
		System.out.println(Colors.printHighlighted(SYSTEM_CONSOLE_PRINT_COLOR,
				"Start general server which is up for " + SERVER_UPTIME + " seconds."));

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
		ExecutorService threadPool = Executors.newFixedThreadPool(AMOUNTTHREADS);
		// FAF strategy work
		threadPool.submit(fafWork());
		// SWS strategy work
		threadPool.submit(swsWork());
		// PO strategy work
		threadPool.submit(poWork());
		// RC strategy work
		threadPool.submit(rcWork());

		// meanwhile keep on shuffling async
		THREADPOOL.submit(syncWork());
		terminateThreadPool();
	}

	public static Runnable fafWork() {
		System.out.println("...start faf-async-work in server...");
		return new FAFServerRequestHandler(new Invoker());
	}

	public static Runnable swsWork() {
		System.out.println("...start sws-async-work in server...");
		return new SWSServerRequestHandler(new Invoker());
	}

	public static Runnable poWork() {
		System.out.println("...start po-async-work in server...");
		return new POServerRequestHandler(new Invoker());
	}

	public static Runnable rcWork() {
		System.out.println("...start rc-async-work in server...");
		return new RCServerRequestHandler(new Invoker());
	}

	public static Runnable syncWork() {
		Runnable syncTask = () -> {
			for (int i = 0; i < SYNCWORKROTATIONS; i++) {
				System.out.println(Colors.printHighlighted(SYSTEM_CONSOLE_PRINT_COLOR,
						"...keep on shuffling the " + i + " times in server..."));
				pauseTime(2000);
			}
		};
		return syncTask;
	}

	private static void pauseTime(int pauseLength) {
		try {
			Thread.sleep(pauseLength);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void terminateThreadPool() throws InterruptedException {
		if (!THREADPOOL.awaitTermination(SERVER_UPTIME, TimeUnit.SECONDS)) {
			THREADPOOL.shutdown();
			MAINSERVER_UP = false;
			System.out.println(Colors.printHighlighted(SYSTEM_CONSOLE_PRINT_COLOR,
					"Shutdown threadPool and terminate async work in server..."));
		}
	}

}
