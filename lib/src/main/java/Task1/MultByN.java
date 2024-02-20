package Task1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class MultByN implements Runnable {
	private BlockingQueue<Integer> throughput;
	private BlockingQueue<Integer> output;
	private int factor;
	private CountDownLatch multByNCompletionLatch;

	public MultByN(BlockingQueue<Integer> throughput, BlockingQueue<Integer> output, int factor,
			CountDownLatch multByNCompletionLatch) {
		this.throughput = throughput;
		this.output = output;
		this.factor = factor;
		this.multByNCompletionLatch = multByNCompletionLatch;
	}

	@Override
	public void run() {
		try {
			while (true) {
				int value = throughput.take();
				// output.put(value * factor);
				output.put((int) Math.pow(factor, value));
				multByNCompletionLatch.countDown();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
		}
	}
}
