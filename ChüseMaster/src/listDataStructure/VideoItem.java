package listDataStructure;

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
	
	private String videoId;
	private String description;
	private String channel;
	
	/**
	 * Constructor method for a video item. Creates the item and stores all of its
	 * relevant properties.
	 * 
	 * @param title - the title of the video
	 * @param videoId - the watch ID of the video
	 * @param description - the video's description
	 * @param channel - the channel the video is from
	 */
	public VideoItem(String title, String videoId, String description, String channel) {
		super(title);
		this.videoId = videoId;
		this.description = description;
		this.channel = channel;
		this.type = "VideoItem";
	}
	
	/**
	 * Method to print the item.
	 * TODO remove in final release - only used for testing
	 */
	public void print() {
		System.out.println("-------------------------");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Channel: " + this.channel);
		//System.out.println("Description: " + this.description);
		System.out.println("Watch link: " + this.videoId);
		System.out.println("-------------------------");
	}
	
	/**
	 * Method to get the watch ID of the video.
	 * @return this.videoId - the watch ID of the YouTube video
	 */
	public String getPath() {
		return this.videoId;
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
	
}
