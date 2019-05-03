package listDataStructure;

import java.util.ArrayList;

import com.wrapper.spotify.model_objects.specification.Image;

/**
 * SpotifyItem is a class within the listDataStructure package. This class holds
 * a song taken from Spotify by storing the song's URL, as well as it's metadata.
 * This class extends the generic BasicItem superclass. This class also implements the 
 * ListInterface interface.
 * 
 * Date created: 21/02/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class SpotifyItem extends BasicItem implements ListInterface {

	private String song_url;
	private ArrayList<String> metadata = new ArrayList<String>();
	private String preview_url;
	private Image artwork;
	
	/**
	 * Constructor function for a Spotify song item. Sets the song's URL and the track
	 * metadata.
	 * @param spotify_url - the listen URL for the song
	 * @param track_metadata - the metadata associated with the song
	 */
	public SpotifyItem(String spotify_url, ArrayList<String> track_metadata, String preview_url, Image album_art) {
		super(track_metadata.get(0));
		this.metadata = track_metadata;
		this.song_url= spotify_url;
		this.preview_url = preview_url;
		this.type = "SpotifyItem";
		this.artwork = album_art;
	}
	
	/**
	 * Method to get the Spotify listen URL of the song.
	 * @return this.song_url - the listen URL of the song
	 */
	public String getPath() {
		return this.song_url;
	}

	/**
	 * Method to get the metadata of the song.
	 * @return this.metadata - the song's metadata
	 */
	public ArrayList<String> getMetadata() {
		return this.metadata;
	}
	
	/**
	 * Method to get the album artwork of a Spotify track.
	 * @return this.artwork		the album artwork image
	 */
	public Image getImage(){
		return this.artwork;
	}
	
	/**
	 * Method to get the audio preview of a Spotify track.
	 * @return this.preview_url		the URL to the preview audio 
	 */
	public String getPreview(){
		return this.preview_url;
	}
	
	/**
	 * Function to return the object's real value. This function is designed to be used
	 * when checking if two items are equal, as it checks that the item content is
	 * equivalent, rather than that they have the same memory address.
	 */
	public ArrayList<String> getObjectValue() {

		ArrayList<String> object_params = new ArrayList<String>();
		
		//adds main properties to the item
		object_params.add(this.getTitle());
		object_params.add(this.getType());
		object_params.add(this.getPath());
		
		for(String content : this.getMetadata()) {
			object_params.add(content);
		}
		
		return object_params;
	}


	
}
