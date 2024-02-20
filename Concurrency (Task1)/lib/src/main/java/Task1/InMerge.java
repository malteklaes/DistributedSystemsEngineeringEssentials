package Task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class InMerge implements Runnable {
	private BlockingQueue<Integer> output;
	private CountDownLatch multByNCompletionLatch;

	public InMerge(BlockingQueue<Integer> output, CountDownLatch multByNCompletionLatch) {
		this.output = output;
		this.multByNCompletionLatch = multByNCompletionLatch;
	}

	@Override
	public void run() {
		try {
			while (true) {
				// Latch, when all Multiplication is done, then sort everything
				multByNCompletionLatch.await();
				ArrayList<Integer> helpList = new ArrayList<>();
				helpList.add(output.take());

				for (int i = 0; i < 2; i++) {
					helpList.add(output.take());
				}

				Collections.sort(helpList);

				for (int value : helpList) {
					output.put(value);
				}

				// synchronize and wait for Copy to complete
				synchronized (InMerge.class) {
					InMerge.class.wait();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
