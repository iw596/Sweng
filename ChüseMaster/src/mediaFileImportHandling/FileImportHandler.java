package mediaFileImportHandling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public abstract class FileImportHandler {
	
	public static String openFile(Stage stage) {
		
		FileChooser file_chooser = new FileChooser();
		
		file_chooser.setTitle("Load File");
		file_chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		String file_location = null;
		
		File file = file_chooser.showOpenDialog(stage);
		
        if (file != null) {
        	file_location = file.getPath();
        	System.out.println(file_location);
        }
		
		return file_location;
		
	}
	
	public static ArrayList<String> openMultipleFiles(Stage stage, String type) {
		
		FileChooser file_chooser = new FileChooser();
		
		file_chooser.setTitle("Load Files");
		file_chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		if(type == "text") {
			file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
		} else if(type == "audio") {

				List<String> audio_extensions = new ArrayList<String>();
				
				audio_extensions.add("*.mp3");
				audio_extensions.add("*.m4a");
				audio_extensions.add("*.flac");
				audio_extensions.add("*.aac");
					
				file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
					"Audio files", audio_extensions));
		} else if(type == "image") {
			
			List<String> image_extensions = new ArrayList<String>();
			
			image_extensions.add("*.jpg");
			image_extensions.add("*.jpeg");
			image_extensions.add("*.png");
			image_extensions.add("*.bmp");
			image_extensions.add("*.gif");
			
			file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
					"Image files", image_extensions));
			
		}

		List<File> files = file_chooser.showOpenMultipleDialog(stage);
		
		ArrayList<String> file_locations = new ArrayList<String>();
		
		if(files != null) {
			
			for(File file: files) {
				file_locations.add(file.getPath());
				System.out.println(file.getPath());
			}
			
		}
		
		return file_locations;
		
	}
	
	public static ArrayList<String> openMultipleFiles(Stage stage) {
		return openMultipleFiles(stage, null);
	}

}
