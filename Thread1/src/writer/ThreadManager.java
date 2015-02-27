package writer;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Stars and manages the working threads.
 * 
 * @author iqnev
 * @since 2015
 */
public class ThreadManager {
	private static final int DEFAULT_THREAD_NUMBER = 10;

	private Queue<ThreadWorker> threadsWorking;

	/**
	 * Default Constructor.
	 */
	public ThreadManager() {
		this.threadsWorking = new ConcurrentLinkedQueue<ThreadWorker>();
	}

	/**
	 * Start the working threads.
	 * 
	 * @throws IOException
	 */
	public void startAllThreads() throws IOException {
		for (int i = 0; i < ThreadManager.DEFAULT_THREAD_NUMBER; i++) {
			this.startThread();
		}
	}

	private void startThread() {
		ThreadWorker threadWorker;

		if (this.threadsWorking.size() >= ThreadManager.DEFAULT_THREAD_NUMBER) {
			return;
		}

		threadWorker = new ThreadWorker();
		this.threadsWorking.add(threadWorker);
		threadWorker.start();
	}

	/**
	 * Abort all working threads.
	 */
	public void abortAllThreads() {
		ThreadWorker threadWorker;

		while ((threadWorker = this.threadsWorking.poll()) != null) {
			threadWorker.stopThread();
			threadWorker.start();
		}
	}
}
