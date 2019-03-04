package test;

import org.junit.Test;

import listDataStructure.*;


import static org.junit.Assert.*;

import xmlHandling.*;

/**
 * Class for testing the XML Handler's functionality.
 * 
 * Date created: 03/03/2019
 * Date last edited: 04/03/2019
 * Last edited by:
 * @author Jack
 *
 */
public class XMLHandlingTest {

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
		
		ChuseList test_list_new = new ChuseList("ghfutfu");
		
		// adding items to first list
		test_list.addItem(new BasicItem("Banana"));
		test_list.addItem(new BasicItem("Cake"));
		test_list.addItem(new BasicItem("Pancake"));
		test_list.addItem(new BasicItem("Tide Pods"));
		
		// Below we are testing that a new file is not created when another file name is passed. 
		XMLHandler.buildXMLFromList(test_list);
		
		// build the list that was stored in the XML file
		test_list_new = XMLHandler.buildListFromXML(test_list.getName());
		
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
		
		// check that list name matches the name it was stored with
		assertEquals("Test_1_XML", test_list_new.getName());
		
	}
	

	@Test
	/**
	 * The aim of this test is to see whether it can store and load with multiple types of classes in a list.
	 * @throws Exception
	 */
	public void createFileWithImageItem() throws Exception {
		
		ChuseList test_list = new ChuseList("Test_2_XML");
		
		ChuseList test_list_new;
		
		// adding items to first list
		test_list.addItem(new ImageItem("//userfs//js2401//w2k//My Pictures//ffff.PNG"));
		test_list.addItem(new BasicItem("Tide Pods"));
		test_list.addItem(new AudioItem("//userfs//js2401//w2k//Downloads//SampleAudio_0.4mb.mp3"));
		test_list.addItem(new VideoItem("to", "many", "strings", "DAN!!!!!"));
		
		// Below we are testing that a new file is not created when another file name is passed.
		XMLHandler.buildXMLFromList(test_list);
		
		// build the list that was stored in the XML file
		test_list_new = XMLHandler.buildListFromXML("Test_2_XML");
		
		// check first item in list against what type is expected and that name expected
		assertEquals("//userfs//js2401//w2k//My Pictures//ffff.PNG", test_list_new.getTitleAtIndex(0));
		assertEquals("ImageItem", test_list_new.get(0).getType());
		
		// check second item in list against what type is expected and that name expected
		assertEquals("Tide Pods", test_list_new.getTitleAtIndex(1));
		assertEquals("BasicItem", test_list_new.get(1).getType());
		
		// check third item in list against what type is expected and that name expected
		assertEquals("//userfs//js2401//w2k//Downloads//SampleAudio_0.4mb.mp3", test_list_new.getTitleAtIndex(2));
		assertEquals("AudioItem", test_list_new.get(2).getType());
		
		// check fourth item in list against what type is expected and that name expected
		assertEquals("to", test_list_new.getTitleAtIndex(3));
		assertEquals("VideoItem", test_list_new.get(3).getType());
		
		// code for printing out the test results
		/*
		System.out.println("XML handling test 2 console print out");
		System.out.println("Writing File: \n");
		System.out.println(test_list.getTitleAtIndex(0) + " : \t is class type " + test_list.get(0).getType());
		System.out.println(test_list.getTitleAtIndex(1) + " : \t is class type " + test_list.get(1).getType());
		System.out.println(test_list.getTitleAtIndex(2) + " : \t is class type " + test_list.get(2).getType());
		System.out.println(test_list.getTitleAtIndex(3) + " : \t is class type " + test_list.get(3).getType());
		System.out.println("\n");
		*/
	}
	
	
	
	@Test
	/**
	 * Method to test an XML file can read multimedia items from multiple valid page tags.
	 * 
	 * @throws Exception
	 */
	public void XMLFileWithMultiplePages() throws Exception {
		
		ChuseList test_list = XMLHandler.buildListFromXML("Test_3_XML");
		
		// check first item in list against what type is expected and that name expected
		assertEquals("Tide Pods", test_list.getTitleAtIndex(0));
		assertEquals("BasicItem", test_list.get(0).getType());
				
		// check second item in list against what type is expected and that name expected
		assertEquals("Big sausage", test_list.getTitleAtIndex(1));
		assertEquals("BasicItem", test_list.get(1).getType());
				
		// check third item in list against what type is expected and that name expected
		assertEquals("C is life C is love", test_list.getTitleAtIndex(2));
		assertEquals("BasicItem", test_list.get(2).getType());
				
		// check fourth item in list against what type is expected and that name expected
		assertEquals("fluffy kitten", test_list.getTitleAtIndex(3));
		assertEquals("BasicItem", test_list.get(3).getType());
		
		// check fifth item in list against what type is expected and that name expected
		assertEquals("Humoungus Catman", test_list.getTitleAtIndex(4));
		assertEquals("BasicItem", test_list.get(4).getType());
		
	}
	
	@Test
	/**
	 * Method to test that an XML with a partially invalid structure can still read the 
	 * valid parts successfully.
	 * @throws Exception
	 */
	public void XMLFileWithInvalidStructureA() throws Exception {
		
		ChuseList test_list = XMLHandler.buildListFromXML("Test_4_XML");
		
		// check first item in list against what type is expected and that name expected
		assertEquals("Tide Pods", test_list.getTitleAtIndex(0));
		assertEquals("BasicItem", test_list.get(0).getType());
		
		// check second item in list against what type is expected and that name expected
		assertEquals("Big sausage", test_list.getTitleAtIndex(1));
		assertEquals("BasicItem", test_list.get(1).getType());
		
	}
	
	//invalid structure - both tags below id incorrect
	
	@Test
	/**
	 * Method to test that an XML file with a fully invalid structure does not crash the
	 * XML reader.
	 * @throws Exception
	 */
	public void XMLFileWithInvalidStructureB() throws Exception {
		
		ChuseList test_list = XMLHandler.buildListFromXML("Test_5_XML");
		
		assertEquals(null, test_list);
		
	}
	
	//invalid structure - 1st tag incorrect, second tag correct
	
	@Test
	/**
	 * Method to test that an XML file with a partially invalid data structure can still read the
	 * valid parts successfully, even when they are after the invalid parts.
	 * @throws Exception
	 */
	public void XMLFileWithInvalidStructureC() throws Exception {
		
		ChuseList test_list = XMLHandler.buildListFromXML("Test_6_XML");
		
		// check third item in list against what type is expected and that name expected
		assertEquals("C is life C is love", test_list.getTitleAtIndex(0));
		assertEquals("BasicItem", test_list.get(0).getType());
				
		// check fourth item in list against what type is expected and that name expected
		assertEquals("fluffy kitten", test_list.getTitleAtIndex(1));
		assertEquals("BasicItem", test_list.get(1).getType());
		
		// check fifth item in list against what type is expected and that name expected
		assertEquals("Humoungus Catman", test_list.getTitleAtIndex(2));
		assertEquals("BasicItem", test_list.get(2).getType());
		
	}
	
	//invalid structure - no ID tag
	
	/**
	 * Method to test that an XML file with no ID tag does not crash the reader.
	 * @throws Exception
	 */
	@Test
	public void XMLFileWithInvalidStructureD() throws Exception {
		
		ChuseList test_list = XMLHandler.buildListFromXML("Test_7_XML");
		
		assertEquals(null, test_list);
		
	}
	
	/**
	 * Method to test that a multimedia object in an XML is not read if it has an invalid type.
	 * @throws Exception
	 */
	@Test
	public void XMLFileWithInvalidStructureE() throws Exception {
		
		ChuseList test_list = XMLHandler.buildListFromXML("Test_8_XML");
		
		// check third item in list against what type is expected and that name expected
		assertEquals("Tide Pods", test_list.getTitleAtIndex(0));
		assertEquals("BasicItem", test_list.get(0).getType());
				
		// check fourth item in list against what type is expected and that name expected
		assertEquals("Big sausage", test_list.getTitleAtIndex(1));
		assertEquals("BasicItem", test_list.get(1).getType());
		
		
	}

}
