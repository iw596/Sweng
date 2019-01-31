package test;

/*
 * Test to determine if list correctly stores list namemlist type and if added items
 * are correctly stored
 * 
 */

import org.junit.Test;
import listDataStructure.List;
import listDataStructure.ListItem;

import static org.junit.Assert.*;
public class ListTest {
	
	
	
	@Test
	public void fourItemList() throws Exception{
		List test_list = new List("Video","funnyVideoList");
		
		assertEquals("Video", test_list.getType());
		assertEquals("funnyVideoList", test_list.getName());
		
		test_list.addItem(new ListItem("funny1"));
		test_list.addItem(new ListItem("funny2"));
		test_list.addItem(new ListItem("funny3"));
		test_list.addItem(new ListItem("funny4"));
		
		assertEquals("funny1",test_list.getIndex(0));
		assertEquals("funny2",test_list.getIndex(1));
		assertEquals("funny3",test_list.getIndex(2));
		assertEquals("funny4",test_list.getIndex(3));
		
		
	}

}
