package Task1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

	final static int INITIALVALUE = 1;
	final static int RANGE = 10;
	static int ROUNDS = 30;

	public static void main(String[] args) {

		Thread copyThread = null;
		Thread multBy2Thread = null;
		Thread multBy3Thread = null;
		Thread multBy5Thread = null;
		Thread inMergeThread = null;
		Thread printThread = null;

		for (int i = 0; i < 1; i++) {
			BlockingQueue<Integer> throughput = new LinkedBlockingQueue<>();
			BlockingQueue<Integer> output = new LinkedBlockingQueue<>();
			CountDownLatch multByNCompletionLatch = new CountDownLatch(3);

			copyThread = new Thread(new Copy(throughput, output, multByNCompletionLatch, INITIALVALUE, RANGE, ROUNDS));
			multBy2Thread = new Thread(new MultByN(throughput, output, 2, multByNCompletionLatch));
			multBy3Thread = new Thread(new MultByN(throughput, output, 3, multByNCompletionLatch));
			multBy5Thread = new Thread(new MultByN(throughput, output, 5, multByNCompletionLatch));
			inMergeThread = new Thread(new InMerge(output, multByNCompletionLatch));
			printThread = new Thread(new Print(output));

			copyThread.start();
			multBy2Thread.start();
			multBy3Thread.start();
			multBy5Thread.start();
			inMergeThread.start();
			printThread.start();

			// just to see each round a little bit better and string "Programms terminated"
			// really at the end
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}

		copyThread.interrupt();
		multBy2Thread.interrupt();
		multBy3Thread.interrupt();
		multBy5Thread.interrupt();
		inMergeThread.interrupt();
		printThread.interrupt();
		printThread.interrupt();

		System.out.println("Programms terminated");

	}

}
