/**
 * 
 */
package writer;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * @author iqnev
 *
 */
public class FileServer implements Closeable {

	private static final String FILE_PATH = "/Users/iqnev/Documents/workspace/Thread2/src/writer/threadLog.txt";

	private static FileServer	instance;
	private File				f = null;
	private FileInputStream 	fis = null;
	private PrintWriter 		out = null;
	private BufferedReader 		in = null;

	private Semaphore lock;

	private FileServer() {
		// initialize the Semaphore
		this.lock = new Semaphore(1);
		try {
			this.f = File.createTempFile("/Users/iqnev/Documents/workspace/Thread2/src/writer/tmp", ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * 
	 * @param _threadName
	 * @throws InterruptedException
	 */
	public void writeToMiddle(String _threadName)
			throws InterruptedException {
		int numLine = 0;
		String line;
		ArrayList<String>	fileLines = new ArrayList<String>();
		try {
			this.lock.acquire();
			
			this.fis = new FileInputStream(FILE_PATH);
			
			// output
			this.out = new PrintWriter(new FileOutputStream(this.f));
			this.in = new BufferedReader(new InputStreamReader(this.fis));
			
			try {
				while ((line = this.in.readLine()) != null) {
					fileLines.add(line);
				}
				
				if (!fileLines.isEmpty()) {
					int middleLine = (int) Math.round(fileLines.size() / 2);
					fileLines.add(middleLine, _threadName);

					for (int i = 0; i < fileLines.size(); i++) {
						this.out.println(fileLines.get(i));
					}
					
					this.out.flush();
					this.out.close();					
					this.in.close();
					new File(FILE_PATH).delete();
					this.f.renameTo(new File(FILE_PATH));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.lock.release();

		}

	}

	/**
	 * Get a random sleep time.
	 * 
	 * @return random sleep time in milliseconds.
	 */
	public long getRandomSleepPeriod() {
		// add random generation of sleep period.
		return 2000;
	}

	@Override
	public void close() throws IOException {
		this.fis.close();
		this.out.close();
	}

}
