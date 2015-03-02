/**
 * 
 */
package writer;

/**
 * @author iqnev
 *
 */
public class ThreadWorker extends Thread {
	  
		private FileServer       fileServer;
		private volatile boolean stopThread;
		private long             sleepPeriod;
		
		/**
		 * Default Constructor.
		 * @param _fileServer the {@link StandardFileServer} in which to write.
		 */
		@SuppressWarnings("nls")
	  public ThreadWorker(FileServer _fileServer) {
		  if (_fileServer == null) {
		    throw new NullPointerException("File Server is null");
		  }
		  
			this.fileServer = _fileServer;
		}

		/**
		 * Stops the execution of the Thread.
		 */
		public void stopThread() {
			this.stopThread = true;
		}

		@Override
		public void run() {
		  boolean success;
		  
		  while (!this.stopThread) {
		    success = this.fileServer.writeToFile(this.getName());
		    
		    if (!success) {
		      this.closeThread();
		    }
		    
	      this.sleepPeriod = this.fileServer.getDefaultSleepPeriod();
	      this.sleepSilently(this.sleepPeriod);
		  }
				
		}
		
		/**
		 * Stops the thread.
		 */
		public void closeThread() {
		  this.stopThread = true;
		}

		private boolean sleepSilently(long _sleepTime) {
			try {
				Thread.sleep(_sleepTime);
			} catch (InterruptedException _e) {
				return false;
			}
			return true;
		}
	}
