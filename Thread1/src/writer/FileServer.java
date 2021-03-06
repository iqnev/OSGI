
package writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author iqnev
 * @since 2015
 */
public class FileServer {
	private static final String FILE_PATH = "threadLog.txt";

	private static FileServer	instance;
	private  BufferedWriter	writer;
	private  File textFile;

	private FileServer() {
		 this.textFile = new File(FILE_PATH);
		
		if (!this.textFile.exists()) {
			try {
				this.textFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	/**
	 * Get instance.
	 * 
	 * @return the singleton instance of {@code FileServer}.
	 */
	public static FileServer getInstance() {
		if (instance == null) {
			instance = new FileServer();
		}
		return instance;
	}

	/**
	 * Get a random sleep time.
	 * 
	 * @return random sleep time in milliseconds.
	 */
	public  long getRandomSleepPeriod() {
		
		return (long)(Math.random() * 1000);
	}

	/**
	 * Writes the specified Thread information to the file server.
	 * 
	 * @param _threadName
	 *            name of the Thread.
	 * @param _threadId
	 *            id of the Thread.
	 * @throws IOException 
	 */
	 
	public synchronized void writeToFile(String _threadName,
			long _threadId) throws IOException {
		if (_threadName == null || _threadName.isEmpty()) {
			throw new IllegalArgumentException("Illegal Thread name");
		}
		try {
			this.writer = new BufferedWriter(new FileWriter(this.textFile, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 this.writer.write(_threadName + "-" + _threadId + "\n"); 
		 this.writer.close();

	}

	public  void close() throws IOException {
		this.writer.close();
	}
}
