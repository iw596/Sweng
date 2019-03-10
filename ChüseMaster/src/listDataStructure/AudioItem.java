package listDataStructure;

import java.util.ArrayList;

import mediaFileImportHandling.AudioFileHandler;

/**
 * AudioItem is a class within the listDataStructure package. This class holds
 * an audio item with a title, type, file location and a collection of metadata.
 * The metadata includes: title; artist; album; date; genre. This item extends
 * the BasicItem superclass. This class also implements the ListInterface interface.
 * 
 * Date created: 30/01/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * @author Isaac Watson and Dan Jackson
 *
 */
public class AudioItem extends BasicItem implements ListInterface {

	private String file_location;
	
	private ArrayList<String> metadata = new ArrayList<String>();
	
	/**
	 * Constructor function for the audio item class. Sets the items
	 * name and opens an audio file with the given name.
	 * 
	 * @param file_path - the file path reference of the file's storage location
	 */
	public AudioItem(String file_path) {
		super(AudioFileHandler.getMetadata(file_path).get(0));
		this.file_location = file_path;
		this.metadata = AudioFileHandler.getMetadata(file_path);
		this.type = "AudioItem";
	}
	
	/**
	 * Method to return the full file path of the image.
	 * 
	 * @return this.file_location - the location of the audio file
	 */
	public String getPath() {
		return this.file_location;
	}

	/**
	 * Method to return the metadata of the file.
	 * 
	 * @return this.metadata - the corresponding metadata of the audio file
	 */
	public ArrayList<String> getMetadata() {
		return this.metadata;
	}
	
	/**
	 * A method for printing the audio item's metadata in the console.
	 * TODO remove in final release - only useful for testing
	 */
	public void print() {
		System.out.println("Title: " + this.getTitle());
		System.out.println("Artist: " + this.metadata.get(1));
		System.out.println("Album: " + this.metadata.get(2));
		System.out.println("Date: " + this.metadata.get(3));
		System.out.println("Genre: " + this.metadata.get(4));
	}
	
	/**
	 * Function to return the object's real value. This function is designed to be used
	 * when checking if two items are equal, as it checks that the item content is
	 * equivalent, rather than that they have the same memory address.
	 */
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
