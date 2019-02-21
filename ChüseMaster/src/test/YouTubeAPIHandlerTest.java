package test;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import algorithms.TournamentAlgorithms;
import apiHandlers.YouTubeAPIHandler;
import listDataStructure.ChuseList;
import listDataStructure.RankingList;
import xmlHandling.XMLHandler;

public class YouTubeAPIHandlerTest {
	
	@Test
	public void loadPlaylist() throws Exception {
		
		ChuseList playlist = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=q6EoRBvdVPQ&list=PLFsQleAWXsj_4yDeebiIADdH5FMayBiJo");
		//playlist.printList();
		
		XMLHandler.buildXMLFromList(playlist, "youtube list");
		
		playlist = XMLHandler.buildListFromXML("youtube list");
		
		playlist.printList();
		
		RankingList ranked_list;
		
/*		ranked_list = new RankingList(TournamentAlgorithms.singleBracketAlgorithm(playlist));
		
		ranked_list.print();*/
		
		//assertEquals("How It Feels To Chew 5 Gum", playlist.get(0).getTitle());
		
	}
	
	
/*	@Test 
	public void mixedDataTypeLoading() throws Exception {
		
		listDataStructure.ChuseList playlist = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=q6EoRBvdVPQ&list=PLFsQleAWXsj_4yDeebiIADdH5FMayBiJo");
		
		playlist.addItem(new BasicItem("Dog"));
		playlist.addItem(new BasicItem("Cat"));
		
		ArrayList<BasicItem> for_algorithm = new ArrayList<BasicItem>();
		
		int i;
		
		for(i=0; i < playlist.getSize(); i++) {
			for_algorithm.add(playlist.get(i));
		}
		
		for_algorithm = algorithms.TournamentAlgorithms.singleBracketAlgorithm(for_algorithm);
		assertEquals("Dog", for_algorithm.get(0).getName());
		
	}*/

}
