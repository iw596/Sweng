package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

/**
 * Class for a runnable random list fetcher. This extends the notifying thread class,
 * and allows a random list fetch request to be triggered within its own thread and notify
 * a class when the lists are fetched.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RunnableRandomListFetcher extends NotifyingThread {
	
	private BackEndContainer back_end;
	
	/**
	 * Constructor method for the Runnable Random List Fetcher. Passes in reference to the back end
	 * container.
	 * 
	 * @param back_end	reference to the back end container
	 */
	public RunnableRandomListFetcher(BackEndContainer back_end) {
		this.back_end = back_end;
	}
	
	@Override
	/**
	 * Overrides the doRun method. Loads the public lists from the profile with
	 * the stored username.
	 */
	public void doRun() {
		
		back_end.loadRandomPublicLists();
		
	}

}
