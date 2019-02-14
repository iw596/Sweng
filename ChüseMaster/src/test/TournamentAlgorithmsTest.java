package test;
/*
 * Test to check that tournament algorithm produces desired output by asserting that
 * output string should be equal to "Dog". A list filled with animal names including dog
 * has been created and the tester selects dog everytime he/she has the option if the
 * test is succesfull dog should be the last item left and the assert should be 
 * true.
 * 
 */

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import algorithms.TournamentAlgorithms;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;

public class TournamentAlgorithmsTest {
	
	@Test
	public void sixItemList() throws Exception {
		
		ChuseList test_data = new ChuseList("ChuseList","Test");
		
		test_data.addItem(new BasicItem("Cat"));
		test_data.addItem(new BasicItem("Mouse"));
		test_data.addItem(new BasicItem("Dog"));
		test_data.addItem(new BasicItem("Giraffe"));
		test_data.addItem(new BasicItem("Penguin"));
		test_data.addItem(new BasicItem("Octopus"));

		ChuseList output_value = new ChuseList("ChuseList","OutputTest");
		
		output_value = TournamentAlgorithms.doubleBracketAlgorithm(test_data);
		
		assertEquals(1, output_value.getSize());
		assertEquals("Dog", output_value.get(0).getName());
	}

}
