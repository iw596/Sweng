package algorithms;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class TournamentAlgorithms {

	private static Scanner consoleInput;

	public static ArrayList<String> singleBracketAlgorithm(ArrayList<String> data_list){
		
		while(data_list.size() > 1) {
			
			if(evenCheck(data_list.size())){
				data_list = evenPass(data_list);
			} else {
				data_list = oddPass(data_list);
			}
		}
		
		System.out.println(data_list);
		
		return data_list;
		
	}
	
	public static ArrayList<String> doubleBracketAlgorithm(ArrayList<String> data_list){
		
		int i;
		
		ArrayList<String> loser_list = data_list;
		
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
		
		if(value % 2 == 0) {
			return true;
		}
		
		return false;
	
	}
	
	private static ArrayList<String> evenPass(ArrayList<String> data_list) {
		
		int i;
		
		consoleInput = new Scanner(System.in);
		
		ArrayList<String> new_data_list = new ArrayList<String>();
		
		for(i = 0;i < data_list.size() / 2;i++) {
			
			System.out.println("Press 1 for object " + data_list.get(i * 2) + " or 2 for object " + data_list.get((i * 2) + 1));
			String input = consoleInput.next();
			
			if(input.equals("1")) {
				new_data_list.add(data_list.get(i*2));
			} else {
				new_data_list.add(data_list.get((i * 2) + 1));
			}
		}
		
		return new_data_list;
		
	}
	
	private static ArrayList<String> oddPass(ArrayList<String> data_list) {
		
		consoleInput = new Scanner(System.in);
		
		String last_data_item = data_list.get(data_list.size() - 1);
		data_list.remove(data_list.size() - 1);
		
		ArrayList<String> new_data_list = evenPass(data_list);
		
		System.out.println("Press 1 for object " + new_data_list.get(0) + " or 2 for object " + last_data_item);
		String input = consoleInput.next();
		
		if(input.equals("2")) {
			new_data_list.set(0, last_data_item);
		}
		
		return new_data_list;
		
	}
	
}
