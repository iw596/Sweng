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
	
	public static ArrayList<String> openMultipleFiles(Stage stage) {
		
		FileChooser file_chooser = new FileChooser();
		
		file_chooser.setTitle("Load Files");
		file_chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
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

}
