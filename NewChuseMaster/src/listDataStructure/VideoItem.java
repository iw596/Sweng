package listDataStructure;

import java.io.File;
import java.util.ArrayList;

/**
 * VideoItem is a class within the listDataStructure package. This class holds
 * a video item within the list by storing the YouTube video ID, as well as storing
 * the description, channel and title of the video. This class extends the generic 
 * BasicItem superclass. This class also implements the ListInterface interface.
 * 
 * Date created: 30/01/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * @author Isaac Watson and Dan Jackson
 *
 */
public class VideoItem extends BasicItem implements ListInterface {
	
	private String video_id;
	private String description;
	private String channel;
	private String file_path;
	
	/**
	 * Constructor method for a youtube video item. Creates the item and stores all of its
	 * relevant properties.
	 * 
	 * @param title - the title of the video
	 * @param videoId - the watch ID of the video
	 * @param description - the video's description
	 * @param channel - the channel the video is from
	 */
	public VideoItem(String title, String video_id, String description, String channel) {
		super(title);
		this.video_id = video_id;
		this.description = description;
		this.channel = channel;
		this.type = "YouTubeItem";
		this.file_path = "https://www.youtube.com/watch?v=" + video_id;
	}
	
	/** Contructor for local videos where only file path string is needed. Creates the item and stores all of its
	 * relevant properties.
	 * 
	 * @param file_path
	 */
	public VideoItem(String file_path) {
		// TODO Auto-generated constructor stub
		super(new File(file_path).getName());
		this.file_path = file_path;
		this.video_id = "";
		this.description = "";
		this.channel = "";
		this.type = "video";
	}
	
	/**
	 * Method to get the filepath or the url of a video.
	 * @return this.videoId - either filepath of the local video or URL of Youtube video
	 */
	public String getPath() {
		return this.file_path;
	}
	
	/**
	 * Function to get the description and channel of a video.
	 * @return metadata - the metadata of the video
	 */
	public ArrayList<String> getMetadata() {
		
		ArrayList<String> metadata = new ArrayList<String>();
		
		metadata.add(description);
		metadata.add(channel);
		
		return metadata;

	}
	
	/**
	 * Function to return the object's real value. This function is designed to be used
	 * when checking if two items are equal, as it checks that the item content is
	 * equivalent, rather than that they have the same memory address.
	 */
	public ArrayList<String> getObjectValue() {

		//list of the item's parameters
		ArrayList<String> object_params = new ArrayList<String>();
		
		//adds all main properties
		object_params.add(this.getTitle());
		object_params.add(this.getType());
		object_params.add(this.getPath());
		for(String content : this.getMetadata()) {
			object_params.add(content);
		}
		
		return object_params;

	}
	
	@Override
	/**
	 * Method for changing the file path to the video of a video item.
	 */
	public void changePath(String file_path) {
		this.file_path = file_path;
	}
	
}
