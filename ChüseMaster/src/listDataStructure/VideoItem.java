package listDataStructure;

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
		System.out.println("Title: " + this.getTitle());
		System.out.println("Channel: " + this.channel);
		System.out.println("Description: " + this.description);
		System.out.println("Watch link: " + this.videoId);
	}
	
}
