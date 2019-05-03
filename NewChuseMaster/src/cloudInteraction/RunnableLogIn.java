package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

/**
 * Class for a runnable log in request. This extends the notifying thread class,
 * and allows a log in request to be triggered within its own thread and notify
 * a class when the log in request is complete.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RunnableLogIn extends NotifyingThread {

	private String email;
	private String password;
	
	private BackEndContainer back_end;
	
	/**
	 * Constructor for the Runnable Log In object. Passes in reference to the back end as well
	 * as the user's email and password.
	 * 
	 * @param back_end		reference to the back end container
	 * @param email			the user's email address
	 * @param password		the user's password
	 */
	public RunnableLogIn(BackEndContainer back_end, String email, String password) {
		this.back_end = back_end;
		this.email = email;
		this.password = password;
	}
	
	@Override
	/**
	 * Overrides the do run method. Triggers a login request with the user's email and password.
	 */
	public void doRun() {
		
		back_end.logIn(email, password);
		
	}

}
