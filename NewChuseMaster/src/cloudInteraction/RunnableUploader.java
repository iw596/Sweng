package cloudInteraction;

import java.io.IOException;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

public class RunnableUploader extends NotifyingThread {

	private String local_path;
	
	private BackEndContainer back_end;
	
	private Boolean makePublic;
	
	public RunnableUploader(BackEndContainer back_end, String local_path, Boolean makePublic) {
		this.back_end = back_end;
		this.local_path = local_path;
		this.makePublic = makePublic;
	}
	
	@Override
	public void doRun() {
		back_end.uploadList(local_path, makePublic);
	}

}
