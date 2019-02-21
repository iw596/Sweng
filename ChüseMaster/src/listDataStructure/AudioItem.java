package listDataStructure;

import java.util.ArrayList;

import mediaFileImportHandling.AudioFileHandler;

public class AudioItem extends BasicItem implements ListInterface {

	private String file_location;
	
	private ArrayList<String> metadata = new ArrayList<String>();
	
	public AudioItem(String file_path) {
		super(AudioFileHandler.getMetadata(file_path).get(0));
		this.file_location = file_path;
		this.metadata = AudioFileHandler.getMetadata(file_path);
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
