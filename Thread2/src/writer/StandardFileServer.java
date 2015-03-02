package writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Represents a File server, which each Thread accesses in order to write in the
 * file.
 * 
 * @author iqnev
 */
public class StandardFileServer implements FileServer {

	@SuppressWarnings("nls")
	private static final String FILE_PATH = "/Users/iqnev/Downloads/workspace/Thread2/src/writer/threadLog.txt";

	private File 				file;
	private FileInputStream 	is;
	private PrintWriter 		out;
	private BufferedReader 		in;
	private Semaphore 			lock;

	/**
	 * Default Constructor.
	 * 
	 * @throws IOException
	 */
	public StandardFileServer() throws IOException {
		this.lock = new Semaphore(1);
		this.file = File.createTempFile(
				"/Users/iqnev/Downloads/workspace/Thread2/src/writer/tmp",
				".txt");
	}

	@Override
	public boolean writeToFile(String _threadName) {
		int numLine = 0;
		String line;
		ArrayList<String> fileLines = new ArrayList<String>();
		try {
			this.lock.acquire();

			this.is = new FileInputStream(FILE_PATH);

			// output
			this.out = new PrintWriter(new FileOutputStream(this.file));
			this.in = new BufferedReader(new InputStreamReader(this.is));
			while ((line = this.in.readLine()) != null) {
				fileLines.add(line);
			}

			if (!fileLines.isEmpty()) {
				int middleLine = Math.round(fileLines.size() / 2);
				fileLines.add(middleLine, _threadName);

				for (int i = 0; i < fileLines.size(); i++) {
					this.out.println(fileLines.get(i));
				}

				this.out.flush();
				this.out.close();
				this.in.close();
				new File(FILE_PATH).delete();
				this.file.renameTo(new File(FILE_PATH));
			}

		} catch (Exception ie) {
			return false;
		} finally {
			this.lock.release();
		}

		return true;
	}

	@Override
	public long getDefaultSleepPeriod() {
		return 5000;
	}

	@Override
	public void close() throws IOException {
		if (this.in != null) {
			this.in.close();
		}

		if (this.out != null) {
			this.out.close();
		}
	}

}
