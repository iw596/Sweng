package algorithms;

import listDataStructure.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * TournamentAlgorithms is the abstract class in the algorithms package. This class handles the
 * handles the comparisons between items in a user generated list. It contains the tournament 
 * algorithm method, as well as a method to find the losing items from a set of results.
 * 
 * Date created: 24/01/2019
 * Date last edited 01/03/2019
 * Last edited by: Dan Jackson
 *
 * @author Isaac Watson and Dan Jackson 
 */
public abstract class TournamentAlgorithms {
	
	//scanner for getting user input
	//TODO remove once GUI implementation working
	private static Scanner consoleInput;

	/**
	 * Method to perform one full round of the tournament algorithm on a list of data. This
	 * tournament algorithm works as any tournament algorithm, and is adapted to work with odd
	 * sets of data as well as even. This will give a winner, as well as a ranking of runners 
	 * ups, all returned as an array list of lists.
	 * 
	 * TODO fix overflow bug
	 * 
	 * @param data_list - list of items the user wants to compare
	 * @return result_list - the list of result lists
	 */
	public static ArrayList<ChuseList> rankingAlgorithm(ChuseList data_list){

		//counter varirables
		int i = 0;
		int k;
		int j;
		
		//the list of lists for storing the results
		ArrayList<ChuseList> result_list = new ArrayList<ChuseList>();
		
		//creates a new list to contain the result of each pass
		ChuseList result = new ChuseList("result");
		//sets the result equal to the original input list
		result = data_list;
		
		//creates an odd item variable but sets it to null
		BasicItem odd_item = null;
		
		//adds the results to the result list
		result_list.add(result);
		
		// While loop calls methods which compare list items until there
		// is only one item left in the list
		while(result.getSize() > 1) {
			
			// If list has even number of elements then call evenCheck method
			if(evenCheck(data_list.getSize())){
				//stores the even pass results
				result = evenPass(result_list.get(i));
			//otherwise remove the last item from the list and store it in the odd item variable,
			//then perform an even pass on the rest of the list
			} else {
				odd_item = result_list.get(i).get(result_list.get(i).getSize() - 1);
				result_list.get(i).removeItem(result_list.get(i).get(result_list.get(i).getSize() - 1));
				result = evenPass(result_list.get(i));
			}
			
			//adds this pass's results to the result list
			result_list.add(result);
			
			//increments counter
			i++;
			
		}
		
		//if the odd item has a value
		if(odd_item != null) {
			
			//creates a new list
			ChuseList odd_fix = new ChuseList("odd_fix");
			//adds the first item from the last result list
			odd_fix.addItem(result_list.get(result_list.size() - 1).get(0));
			//adds the odd item
			odd_fix.addItem(odd_item);
			
			//performs an even pass and stores the result
			result = evenPass(odd_fix);
			
			//if item 0 was chosen then add this to the results
			if(result.get(0).getObjectValue().equals(result_list.get(result_list.size() - 1).get(0).getObjectValue())) {
				result_list.get(1).addItem(odd_item);
			//if item 1 was chosen then add this to the results
			} else {
				result_list.get(2).removeItem(result_list.get(result_list.size() - 1).get(0));
				result_list.get(2).addItem(odd_item);
			}
			
		}
		
		//if the list of results contains less than three lists
		if(result_list.size() < 3) {

			//loops through every item in the result list 1 and removes them
			//from results list two
			for(k=0; k < result_list.get(1).getSize(); k++) {
				//removes duplicate items in higher index list from lower index list
				result_list.get(0).removeItem(result_list.get(1).get(k));
			}
		//if the list of results contains three or more lists
		} else {
			
			//loops through every result list
			for(i=0; i < result_list.size() - 1; i++) {
				
				//loops through every item in the current list
				for(j=0; j < result_list.get(i+1).getSize(); j++) {
					//removes duplicate items in higher index list from lower index list
					result_list.get(i).removeItem(result_list.get(i+1).get(j));
				}

			}

		}
		
		//loops through result list and prints the results
		//TODO remove soon - currently just here for easy, visible testing
		for(i=0; i < result_list.size(); i++) {
			System.out.println("List " + i + ": ");
			result_list.get(i).printList();
		}
		
		return result_list;
		
	}
	
	/**
	 * Method to determine if the length of a list is even or odd. This is done
	 * by seeing if the remainder when divided by two is zero. If list is
	 * of even length then true is returned. If list is off odd length then
	 * False is returned.
	 * 
	 *  @param value - length of data list
	 */
	private static Boolean evenCheck(int value) {

		// Modulo operator % checks remainder
		if(value % 2 == 0) {
			return true;
		}
		
		return false;
	
	}
	
	/**
	 * Function to implement a single even pass through an n-bracket elimination
	 * algorithm. Performs pair-wise comparisons of every item in the list.
	 * 
	 * @param data_list - the list of data to be compared
	 * @return
	 */
	private static ChuseList evenPass(ChuseList data_list) {
		
		int i;
		
		//gets console input
		//TODO remove the need for console input - integrate with UI
		consoleInput = new Scanner(System.in);
		
		//creates array list for the output
		ChuseList new_data_list = new ChuseList("new_data_list");
		
		//loops through half of the items in the list
		//only needs to loop through half due to comparing two items in each loop
		for(i = 0;i < data_list.getSize() / 2;i++) {
			
			//asks the user which item they prefer within the console
			//TODO remove the need for console input - integrate with UI
			System.out.println("Press 1 for object " + data_list.get(i * 2).getTitle() + " or 2 for object " + data_list.get((i * 2) + 1).getTitle());
			String input = consoleInput.next();
			
			//if the console input is 1, adds first item to output list
			if(input.equals("1")) {
				new_data_list.addItem(data_list.get(i*2));
			//if the console input is 2, adds second item to output list
			} else {
				new_data_list.addItem(data_list.get((i * 2) + 1));
			}
		}
		
		return new_data_list;
		
	}
	
	/**
	 * Method to find all of the non-winning items from an array list or result lists.
	 * @param unranked_results - the result list
	 * @return losers - the non-winning items
	 */
	public static ChuseList findLosers(ArrayList<ChuseList> unranked_results) {
		
		//creates and initialises a new list called losers
		ChuseList losers = new ChuseList("losers");
		
		int i;
		int j;
		
		//checks if the result lists exist
		if(unranked_results != null) {
			
			//if they do, loops through all result lists
			for(i=0; i < unranked_results.size() - 1; i++) {
				//loops through every item in the current result list
				for(j=0; j < unranked_results.get(i).getSize(); j++) {
					//adds the items to the list of losers
					losers.addItem(unranked_results.get(i).get(j));
				}
			}
			
		}
		
		return losers;
		
	}
	
}