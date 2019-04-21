package test;

import org.junit.Test;

import listDataStructure.BasicItem;

public class ListObjectTest {

	@Test
	public void equalityTest() {
		
		BasicItem item_1 = new BasicItem("hello");
		BasicItem item_2 = new BasicItem("hello");
		
		if(item_1.getObjectValue().equals(item_2.getObjectValue())) {
			System.out.println("These are the same.");
		} else {
			System.out.println("These are not the same.");
		}
		
	}
	

	
}
