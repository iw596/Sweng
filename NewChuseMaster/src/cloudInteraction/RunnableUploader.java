package cloudInteraction;

import backEnd.BackEndContainer;
import multithreading.NotifyingThread;

/**
 * Class for a runnable uploader. This extends the notifying thread class,
 * and allows an upload to be triggered within its own thread and notify
 * a class when the upload is complete.
 * 
 * Date created: 24/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RunnableUploader extends NotifyingThread {

	private String local_path;
	
	private BackEndContainer back_end;
	
	private Boolean make_list_public;
	
	/**
	 * Constructor method for the Runnable Uploader object. Passes reference to the back end
	 * container, as well as the path to a local file and whether or not to make the list
	 * public.
	 * 
	 * @param back_end				reference to the back end container
	 * @param local_path			the path to a local file
	 * @param make_list_public		whether or not to make the list public
	 */
	public RunnableUploader(BackEndContainer back_end, String local_path, Boolean make_list_public) {
		this.back_end = back_end;
		this.local_path = local_path;
		this.make_list_public = make_list_public;
	}
	
	@Override
	/**
	 * Overrides the doRun method. Uploads the list to the Cloud.
	 */
	public void doRun() {
		back_end.uploadList(local_path, make_list_public);
	}

}
