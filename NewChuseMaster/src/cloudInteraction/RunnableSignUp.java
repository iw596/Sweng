package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

public class RunnableSignUp extends NotifyingThread {

	private String email;
	private String password;
	private String username;
	private String gender;
	private int age;
	
	private BackEndContainer back_end;
	
	public RunnableSignUp(BackEndContainer back_end, String email, String password, String username, String gender, int age) {
		this.back_end = back_end;
		this.email = email;
		this.password = password;
		this.username = username;
		this.gender = gender;
		this.age = age;
	}
	
	@Override
	public void doRun() {
		back_end.createAccount(email, username, password, age, gender);
	}

}
