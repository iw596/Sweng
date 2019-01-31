package main;

import list.*;

public class Main {
	

	public static void main(String[] args){
		
		List my_list = new List("cool list", "List_1.xml", 100);
		
		// get list from D drive location 
		// (file location in this example is D://List_1.xml")
		my_list.GetListFromXML();
		
		// display list in console
		my_list.displayList();
		
		// remove top item
		/*my_list.removeItem(0);
		
		// display list to see if item is gone
		System.out.println();
		my_list.displayList();
		
		// write the file back to xml file
		my_list.StoreListToXMLFile();*/
	}
}
