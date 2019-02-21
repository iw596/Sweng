package mediaFileImportHandling;

import java.util.ArrayList;

import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ImageItem;

public class ImageFileHandler extends FileImportHandler {
	
	public static void openMultipleImageFiles(Stage stage) {
		
		ArrayList<String> file_paths = openMultipleFiles(stage, "image");
		
		ArrayList<BasicItem> output_items = new ArrayList<BasicItem>();
		
		for(String file_path : file_paths) {
			output_items.add(new ImageItem(file_path));
		}
		
	}

}
