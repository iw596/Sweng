package listDataStructure;

import java.util.ArrayList;

public class AudioItem extends BasicItem implements ListInterface {

	private String file_location;
	
	private ArrayList<String> metadata = new ArrayList<String>();
	
	public AudioItem(String file_path, ArrayList<String> track_metadata) {
		super(track_metadata.get(0));
		this.file_location = file_path;
		this.metadata = track_metadata;
		this.type = "AudioItem";
	}
	
	public String getPath() {
		return this.file_location;
	}

	public ArrayList<String> getMetadata() {
		return this.metadata;
	}
	
	public void print() {
		System.out.println("Title: " + this.getTitle());
		System.out.println("Artist: " + this.metadata.get(1));
		System.out.println("Album: " + this.metadata.get(2));
		System.out.println("Date: " + this.metadata.get(3));
		System.out.println("Genre: " + this.metadata.get(4));
	}

}
