package mediaFileImportHandling;

import java.util.ArrayList;

import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.VideoItem;

/**
 * Abstract class used for importing video source files from a local file path. This class
 * extends the generic FileImportHandler.
 * 
 * Date created: 23/04/2019
 * Date last edited: 23/04/2019
 * Last edited by: Isaac Watson
 * 
 * @author Isaac Watson
 *
 */
public abstract class VideoFileHandler  extends FileImportHandler {
	/**
	 * Method for opening multiple local video files and returning an array list of video items
	 * containing the local audio file path and metadata.
	 * 
	 * @param stage - the JavaFX stage the files are opened from
	 * @return audio_items - the array list of image items
	 */
	public static ArrayList<BasicItem> openMultipleVideoFiles(Stage stage) {
		
		//opens JavaFX file chooser dialogue for multiple files and returns a
		//list of their file paths
		ArrayList<String> file_paths = openMultipleFiles(stage, "video");
		
		//initialises output array of image items
		ArrayList<BasicItem> output_items = new ArrayList<BasicItem>();
		
		//loops through every file path and creates a new image item based on the path,
		//and adds this to the list items
		for(String file_path : file_paths) {
			System.out.println("Adding: " + file_path);
			output_items.add(new VideoItem(file_path));
		}
		System.out.println("Number of videos: " + output_items.size());
		
		return output_items;
		
	}

}
