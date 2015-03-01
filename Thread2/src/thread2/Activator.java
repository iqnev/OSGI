package thread2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import writer.FileServer;
import writer.ThreadWorker;

public class Activator implements BundleActivator {

	private static BundleContext 	context;
	private FileServer       		fileServer;

	static BundleContext getContext() {
		return context;
	}
	
	public Activator() {
		  this.fileServer = FileServer.getInstance();
		}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		
		
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
		  @Override
		  public void run() {
			  ThreadWorker threadWorker = new ThreadWorker();
			  threadWorker.start();
			  System.out.print("OK");
		  }
		}, 0, 5, TimeUnit.SECONDS);
		
		
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		this.fileServer.close();
		System.out.print("Out");
	}

}
