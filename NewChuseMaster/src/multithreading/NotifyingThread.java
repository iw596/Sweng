package multithreading;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Abstract class implementing the Runnable interface. This contains a set of Thread Termination Listeners
 * as well as implementations for adding and removing thread termination listeners. It also overrides the
 * Runnable class's run() method, adding a try -> finally block, so that the termination listeners can be
 * triggered once the code has completed running.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 * 
 */
public abstract class NotifyingThread implements Runnable {

	//uses a copy on write array set as this is thread safe
	private final Set<ThreadTerminationListener> termination_listeners 
							= new CopyOnWriteArraySet<ThreadTerminationListener>();
	
	/**
	 * Method to add a termination listener to the object.
	 * 
	 * @param termination_listener	a class implementing the termination listener interface
	 */
	public final void addTerminationListener(final ThreadTerminationListener termination_listener) {
		termination_listeners.add(termination_listener);
	}
	
	/**
	 * Method to remove a termination listener from the object.
	 * 
	 * @param termination_listener	a class implementing the termination listener interface
	 */
	public final void removeTerminationListener(final ThreadTerminationListener termination_listener) {
		termination_listeners.remove(termination_listener);
	}
	
	/**
	 * Method to trigger the termination notification to all the listeners.
	 */
	private final void notifyListeners() {
		
		for(ThreadTerminationListener termination_listener : termination_listeners) {
			termination_listener.notifyOfThreadTermination(this);
		}
		
	}
	
	/**
	 * Prototype of doRun() method - to be implemented by subclasses.
	 */
	public abstract void doRun();
	
	@Override
	/**
	 * Override of the Runnable run() method. Puts the doRun method in a try -> finally block such that
	 * the termination listeners are triggered in the finally block which is only reached once the code in the
	 * try block is completed.
	 */
	public final void run() {

		try {
			doRun();
		} finally {
			notifyListeners();
		}
	}

}
