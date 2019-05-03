package multithreading;

/**
 * Interface for the Thread Termination Listener. Implemented by all classes that require notifying
 * when a new thread is terminated.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 */
public interface ThreadTerminationListener {
	
	public void notifyOfThreadTermination(final NotifyingThread thread);

}
