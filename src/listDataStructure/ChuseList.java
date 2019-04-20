package listDataStructure;

import java.util.ArrayList;

/**
 * ChuseList a is class within the listDataStructure package. This class contains a list
 * of data items, as well as the name of the list. It provides basic functionality required
 * for the list, including adding items, removing items, adding many items at once and 
 * printing the list.
 * 
 * Date created: 31/01/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * Date last edited: 20/04/2019
 * Last edited by: Jack Small
 * 
 * @author Isaac Watson and Dan Jackson
 *
 */
public class ChuseList {
	
	//the list of items
	ArrayList<BasicItem> list = new ArrayList<BasicItem>();
	
	//the name of the list
	private String list_name;
	
	//Author NEW
	private String Author;
	
	/**
	 * Constructor for the list. Sets the name of the list.
	 * @param list_name - the name of the list
	 */
	public ChuseList(String list_name) {
		this.list_name = list_name;
	}
	
	/**
	 * Constructor for the list. Does not set the name of the list.
	 */
	public ChuseList() {
		this.list_name = null;
	}
	
	/**
	 * Method to add an item to the list.
	 * @param item - the item to add to the list.
	 */
	public void addItem(BasicItem item) {
		this.list.add(item);
	}
	
	/**
	 * Method to remove a specified item from the list.
	 * @param item - the item to remove
	 */
	public void removeItem(BasicItem item) {
		this.list.remove(item);
	}
	
	/**
	 * Method to replace an item at a given index with a new item.
	 * @param index - the index to replace the item at
	 * @param item - the new item to replace the old item there
	 */
	public void set(int index, BasicItem item) {
		this.list.set(index, item);
	}
	
	/**
	 * Method to add an array of items to the list.
	 * @param list_array - the list to add the items to
	 */
	public void addItemArray(ArrayList<BasicItem> list_array) {
		
		int i;
		
		//loops through the array of items and adds them to the list
		for(i = 0; i < list_array.size(); i++) {
			this.list.add(list_array.get(i));
		}
			
	}

	/**
	 * Method to get the name of the list.
	 * @return list_name - the name of the list
	 */
	public String getName() {
		return list_name;
	}
	
	/**
	 * Method to get the number of items in the list.
	 * @return list.size() - the number of items in the list
	 */
	public int getSize() {
		return list.size();
	}
	

	/**
	 * Method to get the title of an item in the list at a given index.
	 * TODO remove in final release - just used for testing
	 * @param index - the index of the item to get the title of
	 * @return list.get(index).getTitle() - the title of the item
	 */
	public String getTitleAtIndex(int index){
		return list.get(index).getTitle();
	}
	
	/**
	 * Method to get an item at a given index.
	 * @param index - the index to get the item from
	 * @return list.get(index) - the item at the given index
	 */
	public BasicItem get(int index) {
		return list.get(index);
	}
	
	/**
	 * Method to print the values of every item within the list.
	 */
	public void printList() {
		
		int i;
		
		//loops through every item in the list, printing each item
		for(i = 0; i < list.size(); i++) {
			list.get(i).print();
		}
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

}