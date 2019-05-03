package cloudInteraction;

import java.io.IOException;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

/**
 * Class for a runnable downloader. This extends the notifying thread class,
 * and allows a download to be triggered within its own thread and notify
 * a class when the download is complete.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RunnableDownloader extends NotifyingThread {

	private String cloud_path;
	
	private BackEndContainer back_end;
	
	/**
	 * Constructor for the Runnable Downloader. Passes in reference to the back end
	 * and the path to the Cloud directory to download.
	 * 
	 * @param back_end		reference to the back end
	 * @param cloud_path	the path to the directory on the cloud
	 */
	public RunnableDownloader(BackEndContainer back_end, String cloud_path) {
		this.back_end = back_end;
		this.cloud_path = cloud_path;
	}
	
	@Override
	/**
	 * Override of the do run method. Triggers the start of the download.
	 */
	public void doRun() {
		
		try {
			back_end.downloadList(cloud_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
