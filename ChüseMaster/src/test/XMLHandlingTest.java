package test;

import org.junit.Test;

import listDataStructure.ChuseList;
import listDataStructure.BasicItem;

import static org.junit.Assert.*;

import xmlHandling.*;

public class XMLHandlingTest {
	
	@Test
	public void readFile() throws Exception {
		
		ChuseList test_list = new ChuseList("animals");
		
		test_list = XMLHandler.buildListFromXML("test_file");
		
		System.out.println("Reading File:");
		
		test_list.printList();
		
		assertEquals("cat", test_list.getTitleAtIndex(0));
		assertEquals("dog", test_list.getTitleAtIndex(1));
		assertEquals("fish", test_list.getTitleAtIndex(2));
		assertEquals("rat", test_list.getTitleAtIndex(3));
		
		System.out.println("");
		
	}
	
	@Test
	public void createFile() throws Exception {
		
		ChuseList test_list = new ChuseList("animals");
		
		test_list.addItem(new BasicItem("Banana"));
		test_list.addItem(new BasicItem("Cake"));
		test_list.addItem(new BasicItem("Pancake"));
		test_list.addItem(new BasicItem("Tide Pods"));
		
		XMLHandler.buildXMLFromList(test_list, "test_file");
		
		test_list = XMLHandler.buildListFromXML("test_file");
		
		System.out.println("Writing File:");
		test_list.printList();
		
		assertEquals("Banana", test_list.getTitleAtIndex(0));
		assertEquals("Cake", test_list.getTitleAtIndex(1));
		assertEquals("Pancake", test_list.getTitleAtIndex(2));
		assertEquals("Tide Pods", test_list.getTitleAtIndex(3));
		
	}
	

}
