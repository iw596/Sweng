package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import apiHandlers.YouTubeAPIHandler;
import listDataStructure.BasicItem;

public class YouTubeAPIHandlerTest {
	
	@Test
	public void loadPlaylist() throws Exception {
		
		listDataStructure.ChuseList playlist = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=q6EoRBvdVPQ&list=PLFsQleAWXsj_4yDeebiIADdH5FMayBiJo");
		playlist.printList();
		
		ArrayList<BasicItem> for_algorithm = new ArrayList<BasicItem>();
		
		int i;
		
		for(i=0; i < playlist.getSize(); i++) {
			for_algorithm.add(playlist.get(i));
		}
		
		for_algorithm = algorithms.TournamentAlgorithms.singleBracketAlgorithm(for_algorithm);
		
		assertEquals("How It Feels To Chew 5 Gum", for_algorithm.get(0).getName());
		
	}

}
