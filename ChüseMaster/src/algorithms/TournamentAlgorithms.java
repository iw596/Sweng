package algorithms;

import listDataStructure.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * TournamentAlgorithms is the abstract class in the algorithms package. This class handles the
 * handles the comparisons between items in a user generated list. It contains two algorithms
 * ,a single bracket algorithm to find the users favourite item and a double bracket
 * algorithm to find a users favourite item as well as 2nd favourite 3rd favourite etc.
 * 
 * Date created: 24/01/2019
 * Date last edited 01/03/2019
 * Last edited by: Isaac Watson
 *
 * @author Isaac Watson and Dan Jackson 
 */
public abstract class TournamentAlgorithms {
    // This abstract class handles running the tournament algorithms on a set of
	// data passed into one of the sorting algorithms (single or double) by 
	// the user
	private static Scanner consoleInput;

	public static ArrayList<ChuseList> rankingAlgorithm(ChuseList data_list){
		/**
		 *  Method to run singleBracket algorithm on set of data items passed into 
		 *  the method by the user. This method calls other methods which compare
		 *  data items in the list, this is done until there is a single item
		 *  remaining in the list. Once there is a single item remaining the
		 *  array containing this single item is returned. 
		 * 
		 *  @param data_list - list of items the user wants to compare
		 */
		
		int i = 0;
		int k;
		int j;
		
		ArrayList<ChuseList> result_list = new ArrayList<ChuseList>();
		
		ChuseList result = new ChuseList("result");
		result = data_list;
		
		BasicItem odd_item = null;
		
		result_list.add(result);
		
		// While loop calls methods which compare list items until there
		// is only one item left in the list
		while(result.getSize() > 1) {
			
			// If list has even number of elements then call evenCheck method
			// else call the odd check method
			if(evenCheck(data_list.getSize())){
				result = evenPass(result_list.get(i));
/*				System.out.println("RESULT:");
				result.printList();
				System.out.println("LIST " + i + ": ");
				result_list.get(i).printList();*/
			} else {
				odd_item = result_list.get(i).get(result_list.get(i).getSize() - 1);
				result_list.get(i).removeItem(result_list.get(i).get(result_list.get(i).getSize() - 1));
				result = evenPass(result_list.get(i));
			}
			
			result_list.add(result);
			
			i++;
			
		}
		
		if(odd_item != null) {
			
			ChuseList odd_fix = new ChuseList("odd_fix");
			odd_fix.addItem(result_list.get(result_list.size() - 1).get(0));
			odd_fix.addItem(odd_item);
			
			result = evenPass(odd_fix);
			
			//result.equals(result_list.get(result_list.size() - 1))
			
			if(result.get(0).getObjectValue().equals(result_list.get(result_list.size() - 1).get(0).getObjectValue())) {
				result_list.get(1).addItem(odd_item);
			} else {
				result_list.get(2).removeItem(result_list.get(result_list.size() - 1).get(0));
				result_list.get(2).addItem(odd_item);
			}
			
		}
		
		//result_list.get(2).printList();
		
		//result.printList();
		
		if(result_list.size() < 3) {

			for(k=0; k < result_list.get(1).getSize(); k++) {
				result_list.get(0).removeItem(result_list.get(1).get(k));
			}

		} else {
			
			for(i=0; i < result_list.size() - 1; i++) {
				
				for(j=0; j < result_list.get(i+1).getSize(); j++) {
					result_list.get(i).removeItem(result_list.get(i+1).get(j));
				}

			}

		}
		
		System.out.println("Number of lists: " + result_list.size());
		
		for(i=0; i < result_list.size(); i++) {
			System.out.println("List " + i + ": ");
			result_list.get(i).printList();
		}
		
		return result_list;
		
	}
	
	private static Boolean evenCheck(int value) {
		/*
		 * Method to determine if the length of a list is even or odd. This is done
		 * by seeing if the remainder when divided by two is zero. If list is
		 * of even length then true is returned. If list is off odd length then
		 * False is returned.
		 * 
		 *  @param value - length of data list
		 */
		
		
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
	
	public static ChuseList findLosers(ArrayList<ChuseList> unranked_results) {
		
		ChuseList losers = new ChuseList("losers");
		
		int i;
		int j;
		
		if(unranked_results != null) {

			for(i=0; i < unranked_results.size() - 1; i++) {
				for(j=0; j < unranked_results.get(i).getSize(); j++) {
					losers.addItem(unranked_results.get(i).get(j));
				}
			}
			
		}
		
		return losers;
		
	}
	
}