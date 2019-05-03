package listDataStructure;

import java.util.ArrayList;

/**
 * BasicItem is a class within the listDataStructure package. This class holds
 * a generic list item with a title and a type. This class also implements the 
 * ListInterface interface.
 * 
 * Date created: 30/01/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * @author Isaac Watson and Dan Jackson
 *
 */
public class BasicItem implements ListInterface {
	
	//the name of the item
	private String title;
	
	//the type of item it is
	protected String type;
	
	/**
	 * Constructor function for a generic list item.
	 * @param name - the name of the item
	 */
	public BasicItem(String name) {
		this.title = name;
		this.type = "BasicItem";
	}
	
	/**
	 * Function to get the name of the list item.
	 * @return this.name - the name of the item
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Function to get the type of item.
	 * @return this.type - the type of the item ("BasicItem")
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Function to return the object's real value. This function is designed to be used
	 * when checking if two items are equal, as it checks that the item content is
	 * equivalent, rather than that they have the same memory address.
	 */
	public ArrayList<String> getObjectValue() {

		//a list containing all of the items content in String form
		ArrayList<String> object_params = new ArrayList<String>();
		
		object_params.add(title);
		object_params.add(type);
		
		return object_params;

	}
	
}
