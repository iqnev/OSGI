package writer;

import java.io.IOException;

/**
 * @author iqnev
 * @since 2015
 */
public class ThreadWorker extends Thread {
	private volatile boolean stopThread;
	private String 			 threadName;
	private long 			 threadId;
	private long 		     sleepPeriod;
	private long 			writesCounter;

	/**
	 * Default Constructor.
	 */
	public ThreadWorker() {
		this.threadName = this.getName();
		this.threadId = this.getId();
		FileServer.getInstance();
	}

	/**
	 * Stops the execution of the Thread.
	 */
	public void stopThread() {
		this.stopThread = true;
	}

	@Override
	public void run() {
	//	while (!this.stopThread) {
			try {
				FileServer.writeToFile(this.threadName, this.threadId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.writesCounter++;
			this.sleepPeriod = FileServer.getRandomSleepPeriod();
			this.sleepSilently(this.sleepPeriod);
	//	}
	}

	private void sleepSilently(long _sleepTime) {
		try {
			Thread.sleep(_sleepTime);
		} catch (InterruptedException _e) {
			/*
			 * Ignore the Exception. Nothing bad will happen if the Thread is
			 * interuppted.
			 */
			_e.printStackTrace();
		}
	}
}
