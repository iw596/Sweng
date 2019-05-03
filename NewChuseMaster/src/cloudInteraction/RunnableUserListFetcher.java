package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

/**
 * Class for a runnable user account list fetcher. This extends the notifying thread class,
 * and allows a user account list fetch request to be triggered within its own thread and notify
 * a class when the user account lists are fetched.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RunnableUserListFetcher extends NotifyingThread {

	private String username;

	private BackEndContainer back_end;
	
	/**
	 * Constructor method for the Runnable User List Fetcher. Passes in reference to the back end
	 * and the user's user name.
	 * 
	 * @param back_end	reference to the back end container
	 * @param username	the user name of the account
	 */
	public RunnableUserListFetcher(BackEndContainer back_end, String username) {
		this.back_end = back_end;
		this.username = username;

	}
	
	@Override
	/**
	 * Overrides the doRun method. Loads all lists from the user's account with
	 * the stored user name.
	 */
	public void doRun() {
		
		back_end.loadLoggedInUsersLists(username);
		
	}

}
