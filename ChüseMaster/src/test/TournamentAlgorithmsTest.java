package test;

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
		test_data.add(new ListItem("Penguin"));
		test_data.add(new ListItem("Octopus"));
		test_data.add(new ListItem("Cactus"));

		
		ArrayList<ListItem> output_value = new ArrayList<ListItem>();
		
		output_value = TournamentAlgorithms.doubleBracketAlgorithm(test_data);
		
		assertEquals(1, output_value.size());
		assertEquals("Cat", output_value.get(0).getName());
	}

}
