package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

public class RunnableUserListFetcher extends NotifyingThread {

	private String username;

	private BackEndContainer back_end;
	
	public RunnableUserListFetcher(BackEndContainer back_end, String username) {
		this.back_end = back_end;
		this.username = username;

	}
	
	@Override
	public void doRun() {
		
		back_end.loadLoggedInUsersLists(username);
		
	}

}
