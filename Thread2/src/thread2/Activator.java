package thread2;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import writer.FileServer;
import writer.StandardFileServer;
import writer.ThreadWorker;

public class Activator implements BundleActivator {

	private static BundleContext 	context;
	private FileServer           	fileServer;
	private Queue<ThreadWorker>  	runningThreads;

	static BundleContext getContext() {
		return context;
	}
	
	/**
	 * Default constructor.
	 * @throws IOException 
	 */
	public Activator() throws IOException {
	  this.fileServer     = new StandardFileServer();
	  this.runningThreads = new ConcurrentLinkedQueue<ThreadWorker>();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {	
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		this.startThreadWorker();
	
	}
	
	private void startThreadWorker() {
		  ThreadWorker threadWorker;
	    
	    threadWorker = new ThreadWorker(this.fileServer);
	    this.runningThreads.add(threadWorker);
	    threadWorker.start();
		}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		  ThreadWorker threadWorker;
		  
			this.fileServer.close();
			
			while((threadWorker = this.runningThreads.poll()) != null) {
			  threadWorker.closeThread();
			}
	}

}
