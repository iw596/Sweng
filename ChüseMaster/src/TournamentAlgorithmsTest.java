package test;
/**
 * Test class to test if the tournament algoirthm produces the correct winners
 * and rankings of items. The text,video and image items are tested.
 * 
 * Date created: 01/03/2019
 * Date last edited 01/03/2019
 * Last edited by: Isaac Watson and Harry Ogden 
 *
 * @author Isaac Watson and Harry Ogden 
 */
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import algorithms.TournamentAlgorithms;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.ImageItem;
import listDataStructure.RankingList;
import listDataStructure.VideoItem;

public class TournamentAlgorithmsTest {
	/** This method tests that the ranking algortithm produces the
	 * right ranking for a list of 4 text items, the algoirthm is called
	 * once and the overall winner is checked then the algorithm is called
	 * again and the ranking checked
	 * @throws Exception
	 */
	
	@Test
	public void fourTextItemTest() throws Exception {
		
		// Create list to store items
		ChuseList test_list = new ChuseList("test");
		
		// Generate list of test items
		test_list.addItem(new BasicItem("Cat"));
		test_list.addItem(new BasicItem("Mouse"));
		test_list.addItem(new BasicItem("Dog"));
		test_list.addItem(new BasicItem("Giraffe"));

		
		// Store the results
		ArrayList<ChuseList> results = TournamentAlgorithms.rankingAlgorithm(test_list);
		
		//Store the ranking
		RankingList ranks = new RankingList(results);
		
		// Assert that we want the winner to be cat
		assertEquals("Cat",ranks.get(0).getWrappedItem().getTitle());
		
		test_list = TournamentAlgorithms.findLosers(results);
		results = TournamentAlgorithms.rankingAlgorithm(test_list);
		ranks.addRankedResults(results);
		

		// Check second and last
		assertEquals("Dog",ranks.get(1).getWrappedItem().getTitle());
		//assertEquals("Mouse",ranks.get(2).getWrappedItem().getTitle());
		assertEquals("Giraffe",ranks.get(3).getWrappedItem().getTitle());
	}
	
	public void threeTextItemTest() throws Exception {
		
		// Create list to store items
		ChuseList test_list = new ChuseList("test");
		
		// Generate list of test items
		test_list.addItem(new BasicItem("Cat"));
		test_list.addItem(new BasicItem("Mouse"));
		test_list.addItem(new BasicItem("Dog"));

		
		// Store the results
		ArrayList<ChuseList> results = TournamentAlgorithms.rankingAlgorithm(test_list);
		
		//Store the ranking
		RankingList ranks = new RankingList(results);
		// Assert that we want the winner to be cat
		assertEquals("Mouse",ranks.get(0).getWrappedItem().getTitle());
	}
	
	public void fiveImageItemTest() throws Exception {
		
		// Create list to store items
		ChuseList test_list = new ChuseList("test");
		
		// Generate list of test items
		test_list.addItem(new ImageItem("Image1"));
		test_list.addItem(new ImageItem("Image2"));
		test_list.addItem(new ImageItem("Image3"));
		test_list.addItem(new ImageItem("Image4"));
		test_list.addItem(new ImageItem("Image5"));

		
		// Store the results
		ArrayList<ChuseList> results = TournamentAlgorithms.rankingAlgorithm(test_list);
		
		//Store the ranking
		RankingList ranks = new RankingList(results);
		// Assert that we want the winner to be image1
		assertEquals("Image1",ranks.get(0).getWrappedItem().getTitle());
	}
	
	
	public void sixVideoItemTest() throws Exception {
		
		// Create list to store items
		ChuseList test_list = new ChuseList("test");
		
		// Generate list of test items
		test_list.addItem(new VideoItem("title01","Id01","Desc01","pewdiepie"));
		test_list.addItem(new VideoItem("title02","Id02","Desc02","Channel02"));
		test_list.addItem(new VideoItem("title03","Id03","Desc03","Channel03"));
		test_list.addItem(new VideoItem("title04","Id04","Desc04","Channel04"));
		test_list.addItem(new VideoItem("title05","Id05","Desc05","Channel05"));
		test_list.addItem(new VideoItem("title06","Id06","Desc06","Channel06"));
		
		// Store the results
		ArrayList<ChuseList> results = TournamentAlgorithms.rankingAlgorithm(test_list);
		
		//Store the ranking
		RankingList ranks = new RankingList(results);
		// Assert that we want the winner to be title03
		assertEquals("title01",ranks.get(0).getWrappedItem().getTitle());
	}
	
	public void threeVideoItemTest() throws Exception {
		
		// Create list to store items
		ChuseList test_list = new ChuseList("test");
		
		// Generate list of test items
		test_list.addItem(new VideoItem("title01","Id01","Desc01","pewdiepie"));
		test_list.addItem(new VideoItem("title02","Id02","Desc02","Channel02"));
		test_list.addItem(new VideoItem("title03","Id03","Desc03","Channel03"));

		// Store the results
		ArrayList<ChuseList> results = TournamentAlgorithms.rankingAlgorithm(test_list);
		
		//Store the ranking
		RankingList ranks = new RankingList(results);
		// Assert that we want the winner to be title03
		System.out.println("test");
		assertEquals("title03",ranks.get(0).getWrappedItem().getTitle());
	}

}
