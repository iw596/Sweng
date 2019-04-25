package cloudInteraction;

import java.io.IOException;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

public class RunnableUploader extends NotifyingThread {

	private String local_path;
	
	private BackEndContainer back_end;
	
	private Boolean makeListPublic;
	
	public RunnableUploader(BackEndContainer back_end, String local_path, Boolean makeListPublic) {
		this.back_end = back_end;
		this.local_path = local_path;
		this.makeListPublic = makeListPublic;
	}
	
	@Override
	public void doRun() {
		back_end.uploadList(local_path, makeListPublic);
	}

}
