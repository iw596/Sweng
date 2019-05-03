package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

/**
 * Class for a runnable sign up request. This extends the notifying thread class,
 * and allows a sign up request to be triggered within its own thread and notify
 * a class when the sign up request is complete.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RunnableSignUp extends NotifyingThread {

	private String email;
	private String password;
	private String username;
	private String gender;
	private int age;
	
	private BackEndContainer back_end;
	
	/**
	 * Constructor method for the Runnable Sign Up object. Passes in reference to the back end
	 * container as well as the user's email, password, user name, gender and age. 
	 * 
	 * @param back_end		reference to the back end container
	 * @param email			the user's email
	 * @param password		the user's password
	 * @param username		the user's user name
	 * @param gender		the user's gender
	 * @param age			the user's age
	 */
	public RunnableSignUp(BackEndContainer back_end, String email,
			String password, String username, String gender, int age) {
		this.back_end = back_end;
		this.email = email;
		this.password = password;
		this.username = username;
		this.gender = gender;
		this.age = age;
	}
	
	@Override
	/**
	 * Overrides the doRun method. Creates a user account on the Cloud with the stored 
	 * parameters.
	 */
	public void doRun() {
		back_end.createAccount(email, username, password, age, gender);
	}

}
