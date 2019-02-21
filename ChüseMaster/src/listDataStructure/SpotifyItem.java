package listDataStructure;

import java.util.ArrayList;

public class SpotifyItem extends BasicItem implements ListInterface {

	private String song_url;
	private ArrayList<String> metadata = new ArrayList<String>();
	
	public SpotifyItem(String spotify_url, ArrayList<String> track_metadata) {
		super(track_metadata.get(0));
		this.song_url= spotify_url;
		this.type = "SpotifyItem";
	}
	
	public String getPath() {
		return this.song_url;
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
