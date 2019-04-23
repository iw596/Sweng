package mediaFileImportHandling;

import java.util.ArrayList;

import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ImageItem;

/**
 * Abstract class used for importing image source files from a local file path. This class
 * extends the generic FileImportHandler.
 * 
 * Date created: 14/02/2018
 * Date last edited: 01/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public abstract class ImageFileHandler extends FileImportHandler {
	
	/**
	 * Method for opening multiple local audio files and returning an array list of audio items
	 * containing the local audio file path and metadata.
	 * 
	 * @param stage - the JavaFX stage the files are opened from
	 * @return audio_items - the array list of image items
	 */
	public static ArrayList<BasicItem> openMultipleImageFiles(Stage stage) {
		
		//opens JavaFX file chooser dialogue for multiple files and returns a
		//list of their file paths
		ArrayList<String> file_paths = openMultipleFiles(stage, "image");
		
		//initialises output array of image items
		ArrayList<BasicItem> output_items = new ArrayList<BasicItem>();
		
		//loops through every file path and creates a new image item based on the path,
		//and adds this to the list items
		for(String file_path : file_paths) {
			output_items.add(new ImageItem(file_path));
		}
		
		return output_items;
		
	}

}
