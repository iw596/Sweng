package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

public class RunnableRandomListFetcher extends NotifyingThread {
	
	private BackEndContainer back_end;
	
	public RunnableRandomListFetcher(BackEndContainer back_end) {
		this.back_end = back_end;
	}
	
	@Override
	public void doRun() {
		
		back_end.loadRandomPublicLists();
		
	}

}
