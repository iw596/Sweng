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
import listDataStructure.ListItem;

public class TournamentAlgorithmsTest {
	
	@Test
	public void sixItemList() throws Exception {
		
		ArrayList<ListItem> test_data = new ArrayList<ListItem>();
		
		test_data.add(new ListItem("Cat"));
		test_data.add(new ListItem("Mouse"));
		test_data.add(new ListItem("Dog"));
		test_data.add(new ListItem("Giraffe"));
		test_data.add(new ListItem("Penguin"));
		test_data.add(new ListItem("Octopus"));

		ArrayList<ListItem> output_value = new ArrayList<ListItem>();
		
		output_value = TournamentAlgorithms.doubleBracketAlgorithm(test_data);
		
		assertEquals(1, output_value.size());
		assertEquals("Dog", output_value.get(0).getName());
	}

}
