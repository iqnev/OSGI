package firstosgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import writer.FileServer;
import writer.ThreadManager;

public class Activator implements BundleActivator {
	
	private ThreadManager threadMenager;
	private FileServer       fileServer;
	
	public Activator() {
		  this.threadMenager = new ThreadManager();
		  this.fileServer = FileServer.getInstance();
		}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		this.threadMenager.startAllThreads();
		 System.out.println("ok");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		this.threadMenager.abortAllThreads();
		this.fileServer.close();
	}

}
