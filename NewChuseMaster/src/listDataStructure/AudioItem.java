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
	 * Constructor function for an audio item. Takes a file path and gathers metadata about the file,
	 * as well as setting the item type to audio item.
	 * @param file_path
	 */
	public AudioItem(String file_path) {
		super(AudioFileHandler.getMetadata(file_path).get(0));
		this.file_location = file_path;
		this.metadata = AudioFileHandler.getMetadata(file_path);
		this.type = "AudioItem";
		replaceNulls();
	}
	
	/**
	 * Method to get the path to the audio file.
	 * @return absolute file path
	 */
	public String getPath() {
		return this.file_location;
	}

	/**
	 * Method to return the audio file's metadata.
	 * @return metadata
	 */
	public ArrayList<String> getMetadata() {
		return this.metadata;
	}
	
	/**
	 * Method to get the absolute value of the item.
	 * @return object_params
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
	
	/**
	 * Method to change the file path to a new path.
	 * @param new_path	the new file path
	 */
	public void changePath(String new_path) {
		this.file_location = new_path;
	}
	
	/**
	 * Method to replace all nulls in the metadata with an empty string.
	 */
	private void replaceNulls() {
		
		for(int i = 0; i < metadata.size(); i++) {
			if(metadata.get(i) == null) {
				metadata.set(i, "");
			}
		}
	}

}
