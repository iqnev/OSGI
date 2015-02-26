
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
	private static BufferedWriter	writer;
	private static File textFile;

	private FileServer() {
		 textFile = new File(FILE_PATH);
		
		if (!textFile.exists()) {
			try {
				textFile.createNewFile();
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
	public static long getRandomSleepPeriod() {
		
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
	 
	public static synchronized void writeToFile(String _threadName,
			long _threadId) throws IOException {
		if (_threadName == null || _threadName.isEmpty()) {
			throw new IllegalArgumentException("Illegal Thread name");
		}
		try {
			writer = new BufferedWriter(new FileWriter(textFile, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 writer.write(_threadName + "-" + _threadId + "\n"); 
		 writer.close();

	}

	public static void close() throws IOException {
		writer.close();
	}
}
