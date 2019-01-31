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

	public static ArrayList<ListItem> singleBracketAlgorithm(ArrayList<ListItem> data_list){
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
	
	public static ArrayList<ListItem> doubleBracketAlgorithm(ArrayList<ListItem> data_list){
		
		int i;
		
		ArrayList<ListItem> loser_list = data_list;
		
		if(data_list.size() > 2) {
			
			while(data_list.size() > 1) {
				
				if(evenCheck(data_list.size())){
					data_list = evenPass(data_list);
					for(i=0; i < data_list.size(); i++) {
						loser_list.remove(data_list.get(i));
					}
				} else {
					data_list = oddPass(data_list);
					for(i=0; i < data_list.size(); i++) {
						loser_list.remove(data_list.get(i));
					}
				}
			}
			
			loser_list = singleBracketAlgorithm(loser_list);
			data_list.add(loser_list.get(0));
			
		}
	
		data_list = singleBracketAlgorithm(data_list);
		
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
	
	private static ArrayList<ListItem> evenPass(ArrayList<ListItem> data_list) {
		
		int i;
		
		consoleInput = new Scanner(System.in);
		
		ArrayList<ListItem> new_data_list = new ArrayList<ListItem>();
		
		for(i = 0;i < data_list.size() / 2;i++) {
			
			System.out.println("Press 1 for object " + data_list.get(i * 2).getName() + " or 2 for object " + data_list.get((i * 2) + 1).getName());
			String input = consoleInput.next();
			
			if(input.equals("1")) {
				new_data_list.add(data_list.get(i*2));
			} else {
				new_data_list.add(data_list.get((i * 2) + 1));
			}
		}
		
		return new_data_list;
		
	}
	
	private static ArrayList<ListItem> oddPass(ArrayList<ListItem> data_list) {
		
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
		ListItem last_data_item = data_list.get(data_list.size() - 1);
		data_list.remove(data_list.size() - 1);
		
		// Use evenPass() to get new list
		ArrayList<ListItem> new_data_list = evenPass(data_list);
		
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
