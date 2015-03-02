/**
 * 
 */
package writer;

import java.io.Closeable;

/**
 *  Describes what a {@code FileServer} implementations must be capable of.
 * @author iqnev
 *
 */
public interface FileServer extends Closeable {
	  
	  /**
	   * Write the specified Thread name to the file.
	   * @param _threadName the name of the Thread.
	   * @return {@code true} on success, otherwise {@code false} if writing to the
	   *     File is not possible. 
	   */
	  public boolean writeToFile(String _threadName);
	  
	  /**
	   * @return the default time in milisecond that a Thread must sleep before
	   *     writing to the File again.
	   */
	  public long getDefaultSleepPeriod();

	}