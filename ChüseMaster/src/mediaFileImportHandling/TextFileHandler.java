package mediaFileImportHandling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.stage.Stage;
import listDataStructure.BasicItem;

/**
 * Method for opening multiple local audio files and returning an array list of audio items
 * containing the local audio file path and metadata.
 * @param stage - the JavaFX stage the files are opened from
 * @return audio_items - the array list of audio items
 */
public abstract class TextFileHandler extends FileImportHandler{
	
	/**
	 * Method for opening multiple text files from a local file system.
	 * @param stage - the JavaFX stage the files are opened from
	 * @return output_items - an array list of basic items opened from the txt files
	 */
	public static ArrayList<BasicItem> openMultipleTextFiles(Stage stage) {
		
		//opens a JavaFX file chooser dialogue to open multiple text files
		ArrayList<String> file_paths = openMultipleFiles(stage, "text");
		
		//initialises the output array list
		ArrayList<BasicItem> output_items = new ArrayList<BasicItem>();
		
		//loops through every file path in the array list
		for(String file_path : file_paths) {
			
			String file_text = null;
			
			try {
				// read from the file given by the file path and place content into a string
				file_text = read(file_path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//adds a new basic item with the file's text in it
			output_items.add(new BasicItem(file_text));
		}
		
		return output_items;
		
	}
	
	/**
	 * Method to read all of the text from within a text file.
	 * 
	 * @param file_path - the path to the file
	 * @return output - a string containing the text content of the file
	 * @throws FileNotFoundException
	 */
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
