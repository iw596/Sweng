package algorithms;

import java.util.ArrayList;

import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.RankingList;

/**
 * TournamentAlgorithms is a class in the algorithms package. This class handles 
 * the comparisons between items in a user generated list. It contains the various methods
 * and variables required to carry out a tournament algorithm, as well as a method to find 
 * the losing items from a set of results. 
 * 
 * Date created: 14/03/2019
 * Date last edited 14/03/2019
 * Last edited by: Dan Jackson
 *
 * @author Dan Jackson 
 */
public class TournamentAlgorithms {
	
	//variable to track the position of the algorithm
	private int position;
	
	//variable to hold the list currently being compared
	private ChuseList current_list;
	
	//the current rounds results
	private ChuseList results;
	
	//the list of results lists from each round
	private ArrayList<ChuseList> results_list;
	
	//variable to hold the odd item
	private BasicItem odd_item;
	
	private RankingList ranked_results;
	
	private ChuseList losers;
	
	public TournamentAlgorithms(ChuseList list) {
		position = 0;
		current_list = list;
		results_list = new ArrayList<ChuseList>();
		results_list.add(current_list);
		results = new ChuseList();
		odd_item = null;
		ranked_results = null;
	}
	
	/**
	 * Method to determine if the length of a list is even or odd. This is done
	 * by seeing if the remainder when divided by two is zero. If list is
	 * of even length then true is returned. If list is off odd length then
	 * False is returned.
	 * 
	 *  @param value - length of data list
	 */
	public Boolean evenCheck() {

		// Modulo operator % checks remainder
		if(current_list.getSize() % 2 == 0) {
			return true;
		}
		
		return false;
	
	}
	
	/**
	 * Method to choose between item 0 or item 1 based on the argument passed to the method.
	 * Adds the winning item to the current sets of results and removes the loser from the current list. 
	 * 
	 * @param id
	 */
	public void chooseItem(int id) {
		
		if(id == 0) {
			//add the first item to the results
			results.addItem(current_list.get((position) * 2));
			//TODO UNSURE WHETHER TO remove the second item from the current list
		} else {
			//add the second item to the results
			results.addItem(current_list.get(((position) * 2) + 1));
			//TODO UNSURE WHETHER TO remove the first item from the current list
			
		}
		
	}
	
	/**
	 * Method to store the current result list within the list of results.
	 */
	public void storeResults() {
		results_list.add(results);
		current_list = results;
		results = new ChuseList();
	}
	
	/**
	 * Method to advance the comparison position within the list.
	 */
	public void advancePosition() {
		this.position++;
	}
	
	/**
	 * Method to reset the position tracker variable to 0.
	 */
	public void resetPosition() {
		this.position = 0;
	}
	
	/**
	 * Method to check whether or not the comparison is finished.
	 * @return
	 */
	public Boolean isFinished() {
		
		Boolean result = false;
		
		//checks if the list has a size of 1 or less
		if(current_list.getSize() < 2) {
			result = true;
		}
		
		return result;
		
	}
	
	/**
	 * Method to check if the odd item exists or not.
	 * @return
	 */
	public Boolean oddItemExists() {
		
		Boolean result = false;
		
		//checks whether the odd item is null or not
		if(this.odd_item != null) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Method to add the odd item to the list and set the odd item equal to null.
	 */
	public void addOddItemToList() {
		current_list.addItem(odd_item);
		odd_item = null;
	}
	
	/**
	 * Method to remove the odd item from the list.
	 */
	public void removeOddItemFromList() {
		current_list.removeItem(odd_item);
	}
	
	/**
	 * Method to store an odd item from the end of the list within the odd item variable.
	 */
	public void storeOddItem() {
		odd_item = current_list.get(current_list.getSize() - 1);
	}
	
	/**
	 * Method to check whether or not a tournament round is over.
	 * @return true/false
	 */
	public Boolean roundIsOver() {
		
		Boolean result = false;
		
		//checks if the position is equal to half the size of the list
		if(position == (current_list.getSize() / 2)) {
			result = true;
		}
		
		return result;
		
	}
	
	/**
	 * Method to remove duplicated items in the list of results lists.
	 */
	public void removeDuplicates() {
		
//		int z = 0;
//		
//		for(ChuseList result : results_list) {
//			System.out.println("List " + z + " is: ");
//			result.printList();
//			z++;
//		}
		
		if(results_list.size() >= 3) {
			//loops through every result list
			for(int i=0; i < results_list.size() - 1; i++) {
				
				//loops through every item in the current list
				for(int j=0; j < results_list.get(i+1).getSize(); j++) {
					//removes duplicate items in higher index list from lower index list
					results_list.get(i).removeItem(results_list.get(i+1).get(j));
				}

			}
		}
		
		//if the list of results contains less than three lists
		if(results_list.size() < 3) {
			//loops through every item in the result list 1 and removes them
			//from results list two
			for(int i=0; i < results_list.get(1).getSize(); i++) {
				//removes duplicate items in higher index list from lower index list
				results_list.get(0).removeItem(results_list.get(1).get(i));
			}
		//if the list of results contains three or more lists
		} else {
			
			//compares every result list below the first place one with each other to remove duplicate items
			
			//cycles through the list of result lists from lowest to second-to-highest
			for(int i=0; i < results_list.size() - 1; i++) {
				//cycles through the list of result lists from second-to-highest to lowest
				for(int j=results_list.size() - 1; j >-1 ; j--) {
					//cycles through every item in the current index result list
					for(int k=0; k < results_list.get(j).getSize(); k++) {
						//if the lists being compared are the same, then skip this iteration of the for loop
						if(i == j) {
							continue;
						}
						
						//removes duplicate items from other lists
						results_list.get(i).removeItem(results_list.get(j).get(k));
						
					}
					
				}

			}

		}
	}
	
	/**
	 * Method to get the titles of the pair of items currently being compared.
	 * @return
	 */
	public ArrayList<String> getCurrentPairTitles() {
		
		ArrayList<String> titles = new ArrayList<String>();
		
		titles.add(current_list.get(position*2).getTitle());
		titles.add(current_list.get((position*2) + 1).getTitle());
		
		return titles;
		
	}
	
	/**
	 * Method to get the pair of items currently being compared.
	 * @return
	 */
	public ArrayList<BasicItem> getCurrentPair() {
		
		ArrayList<BasicItem> items = new ArrayList<BasicItem>();
		
		items.add(current_list.get(position*2));
		items.add(current_list.get((position*2) + 1));
		
		return items;
		
	}
	
	/**
	 * Method to rank the results once a tournament has been completed.
	 */
	public void rankResults() {
		findLosers(this.results_list);
		ranked_results = new RankingList(this.results_list);
	}
	
	/**
	 * Method to get the ranked results after a tournament has been completed.
	 * @return
	 */
	public RankingList getRankedResults() {
		return this.ranked_results;
	}
	
	/**
	 * Method to get the unranked results that are output by the tournament comparison.
	 * @return
	 */
	public ArrayList<ChuseList> getUnrankedResults() {
		return this.results_list;
	}
	
	/**
	 * Method to return the losers from the most recent tournament comparison.
	 * @return losers
	 */
	public ChuseList getLosers() {
		return this.losers;
	}
	
	public void printResults() {

		ranked_results.print();
		
	}
	
	/**
	 * Method to find all of the non-winning items from an array list or result lists.
	 * @param unranked_results - the result list
	 * @return losers - the non-winning items
	 */
	public void findLosers(ArrayList<ChuseList> unranked_results) {
		
		//creates and initialises a new list called losers
		this.losers = new ChuseList();
		
		int i;
		int j;
		
		//checks if the result lists exist
		if(unranked_results != null) {
			
			//if they do, loops through all result lists
			for(i=0; i < unranked_results.size() - 1; i++) {
				//loops through every item in the current result list
				for(j=0; j < unranked_results.get(i).getSize(); j++) {
					//adds the items to the list of losers
					this.losers.addItem(unranked_results.get(i).get(j));
				}
			}
		}
	}

}
