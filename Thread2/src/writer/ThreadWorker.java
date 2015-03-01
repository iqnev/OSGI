/**
 * 
 */
package writer;

/**
 * @author iqnev
 *
 */
public class ThreadWorker extends Thread {
	private FileServer fileServer;
	private volatile boolean stopThread;
	private String threadName;
	private long             sleepPeriod;
	private long threadId;

	/**
	 * Default Constructor.
	 */
	public ThreadWorker() {
		this.threadName = this.getName();
		this.threadId = this.getId();
		this.fileServer = FileServer.getInstance();
	}

	/**
	 * Stops the execution of the Thread.
	 */
	public void stopThread() {
		this.stopThread = true;
	}

	@Override
	public void run() {		
			try {
				this.fileServer.writeToMiddle(this.threadName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.sleepPeriod = this.fileServer.getRandomSleepPeriod();
			this.sleepSilently(this.sleepPeriod);
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
