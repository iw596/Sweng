package multithreading;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class NotifyingThread implements Runnable {

	private final Set<ThreadTerminationListener> termination_listeners = new CopyOnWriteArraySet<ThreadTerminationListener>();
	
	public final void addTerminationListener(final ThreadTerminationListener termination_listener) {
		termination_listeners.add(termination_listener);
	}
	
	public final void removeTerminationListener(final ThreadTerminationListener termination_listener) {
		termination_listeners.remove(termination_listener);
	}
	
	private final void notifyListeners() {
		
		for(ThreadTerminationListener termination_listener : termination_listeners) {
			termination_listener.notifyOfThreadTermination(this);
		}
		
	}
	
	public abstract void doRun();
	
	@Override
	public final void run() {

		try {
			doRun();
		} finally {
			notifyListeners();
		}
	}

}
