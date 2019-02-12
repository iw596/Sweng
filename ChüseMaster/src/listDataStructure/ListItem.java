package listDataStructure;

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
public class ListItem implements ListInterface{
	
	//the name of the item
	private String name;
	protected String item_type = "ListItem";
	
	/**
	 * Constructor function for a generic list item.
	 * @param name - the name of the item
	 */
	public ListItem(String name) {
		this.name = name;
	}
	
	/**
	 * Function to get the name of the list item.
	 * @return this.name - the name of the item
	 */
	public String getName() {
		return this.name;
	}
	
	public void print() {
		System.out.println("Name: " + name + "; Item Type: " + item_type);
	}
	
}
