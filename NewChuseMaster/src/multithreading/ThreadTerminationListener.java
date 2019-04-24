package multithreading;

public interface ThreadTerminationListener {
	
	public void notifyOfThreadTermination(final NotifyingThread thread);

}
