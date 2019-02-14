package algorithms;

import java.util.ArrayList;
import listDataStructure.*;
import java.util.Scanner;
/**
 * TournamentAlgorithms is the abstract class in the algorithms package. This class handles the
 * handles the comparisons between items in a user generated list. It contains two algorithms
 * ,a single bracket algorithm to find the users favourite item and a double bracket
 * algorithm to find a users favourite item as well as 2nd favourite 3rd favourite etc.
 * 
 * Date created: 24/01/2019
 * Date last edited 31/01/2019
 * Last edited by: Isaac Watson and Dan Jackson
 *
 * @author Isaac Watson and Dan Jackson 
 */
public abstract class TournamentAlgorithms {
    // This abstract class handles running the tournament algorithms on a set of
	// data passed into one of the sorting algorithms (single or double) by 
	// the user
	private static Scanner consoleInput;

	public static ArrayList<BasicItem> singleBracketAlgorithm(ArrayList<BasicItem> data_list){
		/**
		 *  Method to run singleBracket algorithm on set of data items passed into 
		 *  the method by the user. This method calls other methods which compare
		 *  data items in the list, this is done until there is a single item
		 *  remaining in the list. Once there is a single item remaining the
		 *  array containing this single item is returned. 
		 * 
		 *  @param data_list - list of items the user wants to compare
		 */
		
		// While loop calls methods which compare list items until there
		// is only one item left in the list
		while(data_list.size() > 1) {
			
			// If list has even number of elements then call evenCheck method
			// else call the odd check method
			if(evenCheck(data_list.size())){
				data_list = evenPass(data_list);
			} else {
				data_list = oddPass(data_list);
			}
		}
		
		System.out.println(data_list.get(0).getName());
		// Return array containing final item
		return data_list;
		
	}
	
	/**
	 * Function to implement a double bracket tournament-style elimination
	 * algorithm. This algorithm compares a list of items in pairs, and at
	 * each comparison records the winner, as well as keeping track of the loser.
	 * It then compares the list of losers amongst themselves, and finally compares
	 * the winner of the loser-list with the original winner.
	 * 
	 * @param data_list - the list of items to compare
	 * @return data_list - a list containing the winner of the overall algorithm
	 */
	public static ArrayList<BasicItem> doubleBracketAlgorithm(ArrayList<BasicItem> data_list){
		
		int i;
		
		//the list of loser items starts as a copy of the original data list
		//this is because the winning items are gradually removed from the list
		//leaving only the losers
		ArrayList<BasicItem> loser_list = data_list;
		
		//checks that there are more than two items within the list
		if(data_list.size() > 2) {
			
			//loop while there is more than one item within the list
			while(data_list.size() > 1) {
				
				//if the number of items in the list is even, perform an
				//even pass
				if(evenCheck(data_list.size())){
					data_list = evenPass(data_list);
					//loops for every item in the loser list,
					//removing the winning items
					for(i=0; i < data_list.size(); i++) {
						loser_list.remove(data_list.get(i));
					}
				//if the number of items in the list is odd, perform an
				//odd pass	
				} else {
					data_list = oddPass(data_list);
					//loops for every item in the loser list,
					//removing the winning items
					for(i=0; i < data_list.size(); i++) {
						loser_list.remove(data_list.get(i));
					}
				}
			}
			
			//performs the single bracket tournament-style algorithm on the
			//list of losing items to find the winner of the losing bracket
			loser_list = singleBracketAlgorithm(loser_list);
			
			//adds the winner of the losing bracket to the data list
			//at this point list contains:winner of first bracket, winner of 
			//losing bracket
			data_list.add(loser_list.get(0));
			
		}
	
		//performs single bracket algorithm on the list again to find the overall winner
		data_list = singleBracketAlgorithm(data_list);
		
		//returns the list containing the winner
		return data_list;
		
	}
	
	private static Boolean evenCheck(int value) {
		/*
		 * Method to determine if the length of a list is even or odd. This is done
		 * by seeing if the remainder when divided by two is zero. If list is
		 * of even length then true is returned. If list is off odd lenght then
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
	private static ArrayList<BasicItem> evenPass(ArrayList<BasicItem> data_list) {
		
		int i;
		
		//gets console input
		//TODO remove the need for console input - integrate with UI
		consoleInput = new Scanner(System.in);
		
		//creates array list for the output
		ArrayList<BasicItem> new_data_list = new ArrayList<BasicItem>();
		
		//loops through half of the items in the list
		//only needs to loop through half due to comparing two items in each loop
		for(i = 0;i < data_list.size() / 2;i++) {
			
			//asks the user which item they prefer within the console
			//TODO remove the need for console input - integrate with UI
			System.out.println("Press 1 for object " + data_list.get(i * 2).getName() + " or 2 for object " + data_list.get((i * 2) + 1).getName());
			String input = consoleInput.next();
			
			//if the console input is 1, adds first item to output list
			if(input.equals("1")) {
				new_data_list.add(data_list.get(i*2));
			//if the console input is 2, adds second item to output list
			} else {
				new_data_list.add(data_list.get((i * 2) + 1));
			}
		}
		
		return new_data_list;
		
	}
	
	private static ArrayList<BasicItem> oddPass(ArrayList<BasicItem> data_list) {
		
		/*
		 * Method to perform a pass comparing all objects in the list if
		 * the length of the list is odd. This method works by first removing
		 * the last item in the list and storing it, this makes the length of
		 * the list even. After this the evenPass() method can be called on
		 * the datalist before comparing the first item in this new datalist to
		 * the item removed at the start. The final data_list is then returned.
		 * 
		 * 
		 *  @param data_list - the list of data items to be compared
		 */
		
		
		consoleInput = new Scanner(System.in);
		
		// Remove last item and store it
		BasicItem last_data_item = data_list.get(data_list.size() - 1);
		data_list.remove(data_list.size() - 1);
		
		// Use evenPass() to get new list
		ArrayList<BasicItem> new_data_list = evenPass(data_list);
		
		// Compare first item in new list with removed item from old list. 
		System.out.println("Press 1 for object " + new_data_list.get(0).getName() + " or 2 for object " + last_data_item.getName());
		String input = consoleInput.next();
		
		if(input.equals("2")) {
			new_data_list.set(0, last_data_item);
		}
		
		// Return the new data list
		return new_data_list;
		
	}
	
}
