package listDataStructure;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * ListItem is class within the listDataStructurePackage. This class holds
 * a generic list item with a name. This class also implements the ListInterface
 * interface.
 * 
 * Date created: 30/01/2019
 * Date last edited: 31/01/2019
 * Last edited by: Isaac Watson & Dan Jackson
 * 
 * @author Isaac Watson & Dan Jackson
 *
 */
public class BasicItem implements ListInterface {
	
	//the name of the item
	private String title;
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
	
	public String getType() {
		return this.type;
	}
	
	public void print() {
		System.out.println("Name: " + title);
	}
	
	public ArrayList<String> getObjectValue() {

		ArrayList<String> object_params = new ArrayList<String>();
		
		object_params.add(title);
		object_params.add(type);
		
		return object_params;

	}
	
}
