package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

public class RunnableLogIn extends NotifyingThread {

	private String email;
	private String password;
	
	private BackEndContainer back_end;
	
	public RunnableLogIn(BackEndContainer back_end, String email, String password) {
		this.back_end = back_end;
		this.email = email;
		this.password = password;
	}
	
	@Override
	public void doRun() {
		
		back_end.logIn(email, password);
		
	}

}
