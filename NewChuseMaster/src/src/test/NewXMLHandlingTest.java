package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.RankingItem;
import listDataStructure.RankingList;
import xmlHandling.XMLHandler;

/**
 * Class for testing the New XML Handler's functionality.
 * 
 * Date created: 22/04/2019
 * Date last edited: 22/04/2019
 * Last edited by: Jack and Dan
 * 
 * @author Jack and Luke
 *
 */
public class NewXMLHandlingTest {
	
	@Test
	/**
	 * The aim of this test is to see whether it can store and load with one type of class in a list.
	 * The reason we create two lists and load the previously created list into an empty one, is because 
	 * the list made holds the data even after storing it in an XML file.
	 * 
	 * @throws Exception
	 */
	public void createFileWithBasicItem() throws Exception {

		ChuseList test_list = new ChuseList("Test_1_XML");
		
		ChuseList test_list_new = new ChuseList("Example");
		
		// adding items to first list
		test_list.addItem(new BasicItem("Banana"));
		test_list.addItem(new BasicItem("Cake"));
		test_list.addItem(new BasicItem("Pancake"));
		test_list.addItem(new BasicItem("Tide Pods"));
		
		// Below we are testing that a new file is not created when another file name is passed. 
		XMLHandler.buildXMLFromList(test_list, System.getProperty("user.dir") + "\\saves\\Test_1_XML.xml");
		
		// build the list that was stored in the XML file
		test_list_new = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "\\saves\\Test_1_XML.xml");
		
		System.out.println("Test 1");
		System.out.println("-----------------");
		test_list.printList();
		System.out.println("-----------------");
		test_list_new.printList();
		System.out.println("-----------------");
		
		// check first item in list against what type is expected and that name expected
		assertEquals("Banana", test_list_new.getTitleAtIndex(0));
		assertEquals("BasicItem", test_list_new.get(0).getType());
		
		// check second item in list against what type is expected and that name expected
		assertEquals("Cake", test_list_new.getTitleAtIndex(1));
		assertEquals("BasicItem", test_list_new.get(1).getType());
		
		// check third item in list against what type is expected and that name expected
		assertEquals("Pancake", test_list_new.getTitleAtIndex(2));
		assertEquals("BasicItem", test_list_new.get(2).getType());
		
		// check fourth item in list against what type is expected and that name expected
		assertEquals("Tide Pods", test_list_new.getTitleAtIndex(3));
		assertEquals("BasicItem", test_list_new.get(3).getType());
	}
	
	
	@Test
	/**
	 * aim to test saving and loading a chuse list with one results set list  
	 * @throws Exception
	 */
	public void createFileWithResultsAndReads() throws Exception {
		
		ChuseList test_list = new ChuseList("Test_9_XML");
		RankingList results = new RankingList(); 
		
		ChuseList test_list_new = new ChuseList("Example");
		
		ArrayList<RankingList> results_new = new ArrayList<RankingList>();
		
		// adding items to first list
		test_list.addItem(new BasicItem("Banana"));
		test_list.addItem(new BasicItem("Cake"));
		test_list.addItem(new BasicItem("Pancake"));
		test_list.addItem(new BasicItem("Tide Pods"));
		
		results.addItem(new RankingItem(new BasicItem("Banana"), 1));
		results.addItem(new RankingItem(new BasicItem("Cake"), 2));
		results.addItem(new RankingItem(new BasicItem("Pancake"), 3));
		results.addItem(new RankingItem(new BasicItem("Tide Pods"), 4));
		
		XMLHandler.buildXMLFromList(test_list, System.getProperty("user.dir") + "\\saves\\Test_9_XML.xml", results, "mr Tide Pods");
		
		test_list_new = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "\\saves\\Test_9_XML.xml");
		
		results_new = XMLHandler.buildResultsListFromXML(System.getProperty("user.dir") + "\\saves\\Test_9_XML.xml");
		
		System.out.println("Test 2");
		System.out.println("-----------------");
		test_list.printList();
		System.out.println("-----------------");
		test_list_new.printList();
		System.out.println("-----------------");
		
		// check first item in list against what type is expected and that name expected
		assertEquals("Banana", test_list_new.getTitleAtIndex(0));
		assertEquals("BasicItem", test_list_new.get(0).getType());
		
		// check second item in list against what type is expected and that name expected
		assertEquals("Cake", test_list_new.getTitleAtIndex(1));
		assertEquals("BasicItem", test_list_new.get(1).getType());
		
		// check third item in list against what type is expected and that name expected
		assertEquals("Pancake", test_list_new.getTitleAtIndex(2));
		assertEquals("BasicItem", test_list_new.get(2).getType());
		
		// check fourth item in list against what type is expected and that name expected
		assertEquals("Tide Pods", test_list_new.getTitleAtIndex(3));
		assertEquals("BasicItem", test_list_new.get(3).getType());
		
		assertEquals(1, results_new.get(0).get(0).getRanking());
		assertEquals(2, results_new.get(0).get(1).getRanking());
		assertEquals(3, results_new.get(0).get(2).getRanking());
		assertEquals(4, results_new.get(0).get(3).getRanking());
		
		assertEquals("mr Tide Pods", test_list_new.getAuthor());
		
	}
	
	@Test
	/**
	 * aim to test saving and loading a chuse list with multiple results set list   
	 * 
	 * @throws Exception
	 */
	public void createFileWithMultipleResultsAndReads() throws Exception {
		
		ChuseList test_list = new ChuseList("Test_10_XML");
		RankingList results_1 = new RankingList(); 
		RankingList results_2 = new RankingList();
		
		ChuseList test_list_new = new ChuseList("Example");
		
		ArrayList<RankingList> results_new = new ArrayList<RankingList>();
		
		// adding items to first list
		test_list.addItem(new BasicItem("Banana"));
		test_list.addItem(new BasicItem("Cake"));
		test_list.addItem(new BasicItem("Pancake"));
		test_list.addItem(new BasicItem("Tide Pods"));
		
		results_1.addItem(new RankingItem(new BasicItem("Banana"), 1));
		results_1.addItem(new RankingItem(new BasicItem("Cake"), 2));
		results_1.addItem(new RankingItem(new BasicItem("Pancake"), 3));
		results_1.addItem(new RankingItem(new BasicItem("Tide Pods"), 4));
		
		results_2.addItem(new RankingItem(new BasicItem("what"), 1));
		results_2.addItem(new RankingItem(new BasicItem("is"), 2));
		results_2.addItem(new RankingItem(new BasicItem("living"), 3));
		results_2.addItem(new RankingItem(new BasicItem("for"), 4));
		
		XMLHandler.buildXMLFromList(test_list, System.getProperty("user.dir") + "\\saves\\Test_10_XML.xml", results_1, "Dr Tide Pods");
		
		XMLHandler.appendResults(System.getProperty("user.dir") + "\\saves\\Test_10_XML.xml", results_2);
		
		test_list_new = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "\\saves\\Test_10_XML.xml");
		
		results_new = XMLHandler.buildResultsListFromXML(System.getProperty("user.dir") + "\\saves\\Test_10_XML.xml");
		
		System.out.println("Test 3");
		System.out.println("-----------------");
		test_list.printList();
		System.out.println("-----------------");
		test_list_new.printList();
		System.out.println("-----------------");
		
		// check first item in list against what type is expected and that name expected
		assertEquals("Banana", test_list_new.getTitleAtIndex(0));
		assertEquals("BasicItem", test_list_new.get(0).getType());
		
		// check second item in list against what type is expected and that name expected
		assertEquals("Cake", test_list_new.getTitleAtIndex(1));
		assertEquals("BasicItem", test_list_new.get(1).getType());
		
		// check third item in list against what type is expected and that name expected
		assertEquals("Pancake", test_list_new.getTitleAtIndex(2));
		assertEquals("BasicItem", test_list_new.get(2).getType());
		
		// check fourth item in list against what type is expected and that name expected
		assertEquals("Tide Pods", test_list_new.getTitleAtIndex(3));
		assertEquals("BasicItem", test_list_new.get(3).getType());
		
		assertEquals(1, results_new.get(0).get(0).getRanking());
		assertEquals("Banana", results_new.get(0).get(0).getWrappedItem().getTitle());
		
		assertEquals(2, results_new.get(0).get(1).getRanking());
		assertEquals("Cake", results_new.get(0).get(1).getWrappedItem().getTitle());
		
		assertEquals(3, results_new.get(0).get(2).getRanking());
		assertEquals("Pancake", results_new.get(0).get(2).getWrappedItem().getTitle());
		
		assertEquals(4, results_new.get(0).get(3).getRanking());
		assertEquals("Tide Pods", results_new.get(0).get(3).getWrappedItem().getTitle());
		
		//------------------
		assertEquals(1, results_new.get(1).get(0).getRanking());
		assertEquals("what", results_new.get(1).get(0).getWrappedItem().getTitle());
		
		assertEquals(2, results_new.get(1).get(1).getRanking());
		assertEquals("is", results_new.get(1).get(1).getWrappedItem().getTitle());
		
		assertEquals(3, results_new.get(1).get(2).getRanking());
		assertEquals("living", results_new.get(1).get(2).getWrappedItem().getTitle());
		
		assertEquals(4, results_new.get(1).get(3).getRanking());
		assertEquals("for", results_new.get(1).get(3).getWrappedItem().getTitle());
		
		assertEquals("Dr Tide Pods", test_list_new.getAuthor());
		
	}
	/**
	 * aim to test writing and reading results to a XML file with no chuse list data 
	 * 
	 * @throws Exception
	 */
	@Test
	public void createResultsFileAndRead() throws Exception {
		
		RankingList results = new RankingList(); 
		
		ArrayList<RankingList> results_new = new ArrayList<RankingList>();
		
		results.addItem(new RankingItem(new BasicItem("Banana"), 1));
		results.addItem(new RankingItem(new BasicItem("Cake"), 2));
		results.addItem(new RankingItem(new BasicItem("Pancake"), 3));
		results.addItem(new RankingItem(new BasicItem("Tide Pods"), 4));
		
		XMLHandler.buildXMLFromListWithResults(System.getProperty("user.dir") + "\\saves\\Test_11_XML.xml", results);
		
		results_new = XMLHandler.buildResultsListFromXML(System.getProperty("user.dir") + "\\saves\\Test_11_XML.xml");
		
		assertEquals(1, results_new.get(0).get(0).getRanking());
		assertEquals("Banana", results_new.get(0).get(0).getWrappedItem().getTitle());
		
		assertEquals(2, results_new.get(0).get(1).getRanking());
		assertEquals("Cake", results_new.get(0).get(1).getWrappedItem().getTitle());
		
		assertEquals(3, results_new.get(0).get(2).getRanking());
		assertEquals("Pancake", results_new.get(0).get(2).getWrappedItem().getTitle());
		
		assertEquals(4, results_new.get(0).get(3).getRanking());
		assertEquals("Tide Pods", results_new.get(0).get(3).getWrappedItem().getTitle());
		
	}
}
