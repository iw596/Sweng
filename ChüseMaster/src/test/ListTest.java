package test;

/*
 * Test to determine if list correctly stores list name list type and if added items
 * are correctly stored
 * 
 */

import org.junit.Test;
import listDataStructure.ChuseList;
import listDataStructure.BasicItem;

import static org.junit.Assert.*;
public class ListTest {
	
	@Test
	public void fourItemList() throws Exception{
		ChuseList test_list = new ChuseList("Video","funnyVideoList");
		
		assertEquals("Video", test_list.getType());
		assertEquals("funnyVideoList", test_list.getName());
		
		test_list.addItem(new BasicItem("funny1"));
		test_list.addItem(new BasicItem("funny2"));
		test_list.addItem(new BasicItem("funny3"));
		test_list.addItem(new BasicItem("funny4"));
		
		assertEquals("funny1",test_list.getNameAtIndex(0));
		assertEquals("funny2",test_list.getNameAtIndex(1));
		assertEquals("funny3",test_list.getNameAtIndex(2));
		assertEquals("funny4",test_list.getNameAtIndex(3));
		
	}

}
