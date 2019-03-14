package listDataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * ChuseList is a class within the listDataStructure package. This class contains a list
 * of ranking items. It provides basic functionality required for the ranked results list,
 * including adding items, getting items, searching for items, sorting items and more. The list
 * of ranked items are stored as an array list within this object.
 * 
 * Date created: 21/02/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RankingList{

	ArrayList<RankingItem> items;
	
	/**
	 * Constructor method for a ranking list, initialising the array list of
	 * ranking items.
	 */
	public RankingList() {
		this.items = new ArrayList<RankingItem>();
	}
	
	/**
	 * Constructor method for a ranking list, taking an array list of unranked results and
	 * storing them within an array list.
	 * 
	 * @param unranked_results - the unranked results to rank
	 */
	public RankingList(ArrayList<ChuseList> unranked_results) {
		
		this.items = new ArrayList<RankingItem>();
		
		//adds all items to the ranking list
		addRankedResults(unranked_results);
		
		//sorts the ranking list so that the item with the smallest ranking
		//value is 1st in the list
		this.sort();
		
	}
	
	/**
	 * Method for getting a ranking item at a given index.
	 * @param index - the index to get an item at
	 * @return items.get(index) - the item at the given index
	 */
	public RankingItem get(int index) {
		return items.get(index);
	}
	
	/**
	 * Method to add an array list of unranked results to the ranking list, and rank
	 * these results appropriately.
	 * @param unranked_results - the unranked results to rank
	 */
	public void addRankedResults(ArrayList<ChuseList> unranked_results) {
		
		int i;
		int j;
		
		//cycles through each result list
		for(i=0; i < unranked_results.size(); i++) {
			//cycles through each item in each result list
			for(j=0; j < unranked_results.get(i).getSize(); j++) {
				//adds item to ranking list and gives it a ranking based on its lists index (i)
				addItem(new RankingItem(unranked_results.get(i).get(j), unranked_results.size() - i));
			}
		}
		
		//sorts the ranking list so that the item with the smallest ranking is
		//1st in the list
		this.sort();
		
	}
	
	/**
	 * Method to add a single ranking item to the ranking list.
	 * @param item - the item to add to the list
	 */
	public void addItem(RankingItem item) {
		
		//searches for an item and returns its index
		int index = searchForItem(item);
		
		//if the item is not found, adds the new item to the list
		if(index == -1) {
			items.add(item);
		//if the item is found, combines the two equal items
		} else {
			combineItems(item, index);
		}

		//sorts the ranking list so that the item with the lowest ranking
		//is the first item in the list
		this.sort();
		
	}
	
	/**
	 * Method to search for a given item within the ranking list.
	 * @param item - the item to search for
	 * @return index - the index of the item being searched for. If the item is not found, -1 is returned
	 */
	private int searchForItem(RankingItem item) {
		
		//variable checking if the item is found
		Boolean isFound = false;
		
		//item index variable
		int index = -1;
		
		int i;
		
		//loops through every item in the list
		for(i=0; i < items.size(); i++) {
			
			//checks if the item in the list is equal in value to the item
			//passed into the function. 
			isFound = items.get(i).isItemEqualTo(item.getWrappedItem());
			
			//if the item is found then sets the index and breaks out the for loop
			if(isFound) {
				index = i;
				break;
			}
		}

		return index;
		
	}
	
	/**
	 * Method to combine two items at a given index.
	 * @param item - the item to combine
	 * @param item_index - the index of the item to combine the new item with
	 */
	private void combineItems(RankingItem item, int item_index) {

		items.get(item_index).addToRanking(item.getRanking());
		
	}
	
	/**
	 * Method to sort the ranking list so that the item with the smallest ranking is first
	 * in the list.
	 */
	private void sort() {
		//creates a custom comparator
		Collections.sort(items, new Comparator<RankingItem>() {
			@Override
			//compares item 1 and item 2 by working out the ranking difference between them
			public int compare(RankingItem item_1, RankingItem item_2) {
				return item_1.getRanking() - item_2.getRanking();
			}
		});
	}
	
	/**
	 * Method for printing the ranking list.
	 */
	public void print() {
		System.out.println("*****************");
		//cycles through every item in the list, printing each one
		for(RankingItem item : items) {
			item.print();
		}
		System.out.println("*****************");
	}
	
	public int getSize(){
		return items.size();
	}
	
}
