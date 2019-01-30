package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import algorithms.TournamentAlgorithms;

public class TournamentAlgorithmsTest {
	
	@Test
	public void sixItemList() throws Exception {
		
		ArrayList<String> test_data = new ArrayList<String>();
		
		test_data.add("Dog");
		test_data.add("Cat");
		test_data.add("Hamster");
		test_data.add("Rat");
		test_data.add("Fish");
		test_data.add("Bird");
		test_data.add("Elephant");
		test_data.add("Giraffe");
		test_data.add("Wolf");
		test_data.add("Squirrel");
		test_data.add("Moose");
		
		ArrayList<String> output_value = new ArrayList<String>();
		
		output_value = TournamentAlgorithms.doubleBracketAlgorithm(test_data);
		
		assertEquals(1, output_value.size());
		assertEquals("Cat", output_value.get(0));
	}

}
