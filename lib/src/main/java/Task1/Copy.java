package Task1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Copy implements Runnable {
	private BlockingQueue<Integer> throughput;
	private BlockingQueue<Integer> output;
	private CountDownLatch multByNCompletionLatch;
	private int initialValue;
	private final int range;
	private int rounds;

	public Copy(BlockingQueue<Integer> throughput, BlockingQueue<Integer> output, CountDownLatch multByNCompletionLatch,
			int initialValue, int range, int rounds) {
		this.throughput = throughput;
		this.output = output;
		this.multByNCompletionLatch = multByNCompletionLatch;
		this.initialValue = initialValue;
		this.range = range;
		this.rounds = rounds;
		try {
			for (int i = initialValue; i <= range; i++) {
				throughput.put(initialValue);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (rounds > 0) {
//				for (int i = initialValue; i <= initialValue + 2; i++) {
//					throughput.put(i);
//				}
				multByNCompletionLatch.await();

				// synchronize and notify InMerge
				synchronized (InMerge.class) {
					InMerge.class.notify();
				}

				synchronized (output) {
					for (int i = 0; i < output.size(); i++) {
						if (i > 2) {
							throughput.put(i);
						}
					}
					output.notify();
				}
				rounds--;
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}
}
