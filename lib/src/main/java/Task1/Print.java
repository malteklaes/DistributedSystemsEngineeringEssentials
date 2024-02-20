package Task1;

import java.util.concurrent.BlockingQueue;

public class Print implements Runnable {
	private BlockingQueue<Integer> output;

	public Print(BlockingQueue<Integer> output) {
		this.output = output;
	}

	@Override
	public void run() {
		try {
			synchronized (output) {
				output.wait();
			}
			for (int i = 0; i < 20; i++) {
				int value = output.take();
				System.out.println("Hamming-Numbers-Output: " + value);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
