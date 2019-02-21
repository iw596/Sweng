package listDataStructure;

import java.util.ArrayList;

public class VideoItem extends BasicItem implements ListInterface {
	
	private String videoId;
	private String description;
	private String channel;
	
	public VideoItem(String title, String videoId, String description, String channel) {
		super(title);
		this.videoId = videoId;
		this.description = description;
		this.channel = channel;
		this.type = "VideoItem";
	}
	
	public void print() {
		System.out.println("-------------------------");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Channel: " + this.channel);
		//System.out.println("Description: " + this.description);
		System.out.println("Watch link: " + this.videoId);
		System.out.println("-------------------------");
	}
	
	public String getPath() {
		return this.videoId;
	}
	
	public ArrayList<String> getMetadata() {
		
		ArrayList<String> metadata = new ArrayList<String>();
		
		metadata.add(description);
		metadata.add(channel);
		
		return metadata;

	}
	
	public ArrayList<String> getObjectValue() {

		ArrayList<String> object_params = new ArrayList<String>();
		
		object_params.add(this.getTitle());
		object_params.add(this.getType());
		object_params.add(this.getPath());
		
		for(String content : this.getMetadata()) {
			object_params.add(content);
		}
		
		return object_params;

	}
	
}
