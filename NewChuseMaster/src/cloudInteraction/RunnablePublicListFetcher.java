package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

/**
 * Class for a runnable public list fetcher. This extends the notifying thread class,
 * and allows a public list fetch request to be triggered within its own thread and notify
 * a class when the lists are fetched.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RunnablePublicListFetcher extends NotifyingThread {

	private String username;

	private BackEndContainer back_end;
	
	/**
	 * Constructor method for the Runnable Public List Fetcher. Passes in reference to the back end
	 * and the user's user name.
	 * 
	 * @param back_end	reference to the back end container
	 * @param username	the user name of the account
	 */
	public RunnablePublicListFetcher(BackEndContainer back_end, String username) {
		this.back_end = back_end;
		this.username = username;
	}
	
	@Override
	/**
	 * Overrides the doRun method. Loads the public lists from the profile with
	 * the stored username.
	 */
	public void doRun() {
		
		back_end.loadPublicListsFromUser(username);
		
	}

}
