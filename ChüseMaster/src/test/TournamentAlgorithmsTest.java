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
import listDataStructure.RankingList;

public class TournamentAlgorithmsTest {
	
	@Test
	public void sixItemList() throws Exception {
		
		ChuseList test_data = new ChuseList("test");
		
		test_data.addItem(new BasicItem("Cat"));
		test_data.addItem(new BasicItem("Mouse"));
		test_data.addItem(new BasicItem("Dog"));
		test_data.addItem(new BasicItem("Giraffe"));
		test_data.addItem(new BasicItem("Penguin"));
		test_data.addItem(new BasicItem("Octopus"));
		test_data.addItem(new BasicItem("Rat"));
		test_data.addItem(new BasicItem("Bird"));
/*		test_data.addItem(new BasicItem("Cow"));
		test_data.addItem(new BasicItem("Pig"));
		test_data.addItem(new BasicItem("Monkey"));
		test_data.addItem(new BasicItem("Gorilla"));
		test_data.addItem(new BasicItem("Human"));
		test_data.addItem(new BasicItem("Snake"));
		test_data.addItem(new BasicItem("Insect"));
		test_data.addItem(new BasicItem("Bee"));*/
		
		
/*		test_data = TournamentAlgorithms.doubleBracketAlgorithm(test_data);*/
		
		ArrayList<ChuseList> results = TournamentAlgorithms.singleBracketAlgorithm(test_data);
		
		RankingList ranks = new RankingList(results);
		ranks.print();
		
		for(int i = 0; i < 3; i++) {
			test_data = TournamentAlgorithms.findLosers(results);
			results = TournamentAlgorithms.singleBracketAlgorithm(test_data);
			ranks.addRankedResults(results);
			ranks.print();
		}
		
		
		
/*		assertEquals(1, test_data.getSize());
		assertEquals("Dog", test_data.getTitleAtIndex(0));*/
	}

}
