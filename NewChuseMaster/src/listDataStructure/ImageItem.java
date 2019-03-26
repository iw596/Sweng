package listDataStructure;

import java.io.File;
import java.util.ArrayList;

/**
 * ImageItem is a class within the listDataStructure package. This class holds
 * an image item within the list by storing the images file location. This class 
 * extends the generic BasicItem superclass. This class also implements the ListInterface
 * interface.
 * 
 * Date created: 30/01/2019
 * Date last edited: 31/01/2019
 * Last edited by: Isaac Watson and Dan Jackson
 * 
 * @author Isaac Watson and Dan Jackson
 *
 */
public class ImageItem extends BasicItem implements ListInterface {
	
	private String file_location;
	
	/**
	 * Constructor function for the image item class. Sets the items
	 * name and opens an image file with the given name.
	 * 
	 * @param file_location - the location reference of the image's storage location
	 */
	public ImageItem(String file_location) {
		super(new File(file_location).getName());
		this.file_location = file_location;
		this.type = "ImageItem";
	}
	
	/**
	 * Method to return the full file path of the image.
	 * 
	 * @return this.file_location - the location of the image file
	 */
	public String getPath() {
		return this.file_location;
	}

	/**
	 * Function to return the object's real value. This function is designed to be used
	 * when checking if two items are equal, as it checks that the item content is
	 * equivalent, rather than that they have the same memory address.
	 */
	public ArrayList<String> getObjectValue() {

		//a list containing all of the item's content in String form
		ArrayList<String> object_params = new ArrayList<String>();
		
		//adds all data
		object_params.add(this.getTitle());
		object_params.add(this.getType());
		object_params.add(this.getPath());
		
		return object_params;

	}
	
	/**
	 * A method for printing the image item's file location in the console.
	 * TODO remove in final release - only useful for testing
	 */
	public void print() {
		System.out.println("File Location: " + this.file_location);
	}
	
}
