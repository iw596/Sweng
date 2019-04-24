package cloudInteraction;

import java.io.IOException;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

public class RunnableDownloader extends NotifyingThread {

	private String cloud_path;
	
	private BackEndContainer back_end;
	
	public RunnableDownloader(BackEndContainer back_end, String cloud_path) {
		this.back_end = back_end;
		this.cloud_path = cloud_path;
	}
	
	@Override
	public void doRun() {
		
		try {
			back_end.downloadList(cloud_path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
