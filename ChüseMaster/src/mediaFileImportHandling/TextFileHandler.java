package mediaFileImportHandling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.stage.Stage;
import listDataStructure.BasicItem;

public abstract class TextFileHandler extends FileImportHandler{
	
	
	public static BasicItem openTextFile(Stage stage) {
		
		String file_path = openFile(stage);
		
		String file_text = null;
		
		try {
			//read from the file given by the file path and place content into a string
			file_text = read(file_path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BasicItem new_item = new BasicItem(file_text);
		
		return new_item;
		
	}
	
	public static ArrayList<BasicItem> openMultipleTextFiles(Stage stage) {
		
		ArrayList<String> file_paths = openMultipleFiles(stage, "text");
		
		ArrayList<BasicItem> output_items = new ArrayList<BasicItem>();
		
		for(String file_path : file_paths) {
			
			String file_text = null;
			
			try {
				// read from the file given by the file path and place content into a string
				file_text = read(file_path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			output_items.add(new BasicItem(file_text));
		}
		
		for(BasicItem item : output_items) {
			item.print();
		}
		return output_items;
		
	}
	
	private static String read(String file_path) throws FileNotFoundException{
		
		// get file content and place in BufferedReader class
		BufferedReader br = new BufferedReader(new FileReader(file_path));
		
		// local variables
		// the string of the current line
		String line;
		// the whole content of the file in a string with \n to separate each line
		String output = "";
		
		try {
			// read while there is still something there
			while((line = br.readLine()) != null) {
				// add the read line to the whole string with \n to separate each line in the string
				output = output + line + "\n"; 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}

}
