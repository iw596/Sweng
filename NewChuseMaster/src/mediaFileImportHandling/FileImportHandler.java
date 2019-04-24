package mediaFileImportHandling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Abstract class used for importing files from a local file path. 
 * 
 * Date created: 14/02/2019
 * Date last edited: 23/04/2019
 * Last edited by: Isaac Watson
 * 
 * @author Dan Jackson
 *
 */
public abstract class FileImportHandler {
	
	/**
	 * Method for opening n files of a given type.
	 * 
	 * @param stage - the JavaFX stage the files are opened from
	 * @param type - the type of file to open (eg. audio, text, image)
	 * @return file_locations - an array list containing the path(s) to the file(s)
	 */
	public static ArrayList<String> openMultipleFiles(Stage stage, String type) {
		
		//creates a JavaFX file chooser
		FileChooser file_chooser = new FileChooser();
		
		//sets the title and root directory of the file chooser
		file_chooser.setTitle("Load Files");
		file_chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		//checks the media type
		//if the media type is text, only allows opening of .txt files
		if(type == "text") {
			file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
		//if the media type is audio, only allows opening of .mp3, .m4a, .flac, .wav files
		} else if(type == "audio") {

				List<String> audio_extensions = new ArrayList<String>();
				
				//allowed file extensions
				audio_extensions.add("*.mp3");
				audio_extensions.add("*.m4a");
				audio_extensions.add("*.flac");
				audio_extensions.add("*.wav");
					
				//sets allowed file extensions
				file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
					"Audio files", audio_extensions));
		//if the media type is image, only allows opening .jpg, .jpeg, .png, .gif
		} else if(type == "image") {
			
			List<String> image_extensions = new ArrayList<String>();
			
			//allowed file extensions
			image_extensions.add("*.jpg");
			image_extensions.add("*.jpeg");
			image_extensions.add("*.png");
			image_extensions.add("*.gif");
			
			//sets allowed file extensions
			file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
					"Image files", image_extensions));
			
			//if the media type is video, only allows opening .mp4	
		} else if(type == "video") {
			
			List<String> video_extensions = new ArrayList<String>();
			
			//allowed file extensions
			video_extensions.add("*.mp4");
			
			//sets allowed file extensions
			file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
					"Video files", video_extensions));
			
		}

		//opens the file chooser's multi-file dialogue box
		List<File> files = file_chooser.showOpenMultipleDialog(stage);
		
		//initialises array list of file locations
		ArrayList<String> file_locations = new ArrayList<String>();
		
		//checks the files exist
		if(files != null) {
			
			//loops through every file and adds the file's path to the array list
			for(File file: files) {
				file_locations.add(file.getPath());
			}
			
		}
		
		return file_locations;
		
	}
	
	/**
	 * Method for opening n files of any type type.
	 * 
	 * @param stage - the JavaFX stage the files are opened from
	 * @return file_locations - an array list containing the path(s) to the file(s)
	 */
	public static ArrayList<String> openMultipleFiles(Stage stage) {
		return openMultipleFiles(stage, null);
	}

}
