package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import apiHandlers.YouTubeAPIHandler;
import listDataStructure.ChuseList;

/**
 * Test method for testing the YouTube data API Handler module's ability
 * to pull playlist data from YouTube.
 * 
 * Date created: 03/03/2019
 * Date last edited: 03/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class YouTubeAPIHandlerTest {

	@Test
	/**
	 * Test method to get the first 50 videos from a long playlist (311 videos).
	 * The method then puts these videos into a list and checks that the item
	 * values are as expected.
	 * 
	 * @throws IOException
	 */
	public void getLongPlaylist() throws IOException {
		
		ChuseList test_list = new ChuseList();
		
		//gets the playlists data
		test_list = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=q6EoRBvdVPQ&list=PLFsQleAWXsj_4yDeebiIADdH5FMayBiJo");
		
		//checks video one's title and watch ID are as expected
		assertEquals("Yee", test_list.get(0).getTitle());
		assertEquals("q6EoRBvdVPQ", test_list.get(0).getPath());
		
		//checks video two's title and watch ID are as expected
		assertEquals("color red", test_list.get(1).getTitle());
		assertEquals("8YWl7tDGUPA", test_list.get(1).getPath());
		
		//checks that video two's description is as expected
		assertEquals("Instagram: @jimmynez", test_list.get(1).getMetadata().get(0));
		
		//checks video fifty's title and watch ID are as expected
		assertEquals("thomas kinkade PREVIEW.MOV", test_list.get(49).getTitle());
		assertEquals("mdsS7dLR-d8", test_list.get(49).getPath());
		
	}
	
	@Test
	/**
	 * Method for getting a short playlist (one item) and verifying its content.
	 * @throws IOException
	 */
	public void getShortPlaylist() throws IOException {
		
		ChuseList test_list = new ChuseList();
		
		//gets the playlists data
		test_list = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=qFZSXc6wH0A&list=PLvqZs6AtlBKRqwho3juXYqcsQOf0acuqQ");
		
		//checks the video's title and watch ID are as expected
		assertEquals("Built to Spill - Keep It Like a Secret - FULL ALBUM - 1999", test_list.get(0).getTitle());
		assertEquals("qFZSXc6wH0A", test_list.get(0).getPath());
		
	}
	
	@Test
	/**
	 * Method to get an average playlist (7 items), add the video data to a list
	 * and verify each item's content.
	 * 
	 * @throws IOException
	 */
	public void getAveragePlaylist() throws IOException {
		
		ChuseList test_list = new ChuseList();
		
		//gets the playlist data
		test_list = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=fbCmkFVCs5I&list=PL8mG-RkN2uTwe2V5XMm7mncNGnEqBbfXg");
		
		//checks video 1 is as expected
		assertEquals("Fixing the DISASTER - Server Room Vlog Pt. 1", test_list.get(0).getTitle());
		assertEquals("fbCmkFVCs5I", test_list.get(0).getPath());
		
		//printed watch ID to verify the returned link is correct
		System.out.println("Video 1 has a video ID of: " + test_list.get(0).getPath());
		
		//verifies the channel name is being pulled correctly
		assertEquals("Linus Tech Tips", test_list.get(0).getMetadata().get(1));
		
		//checks last video (7) is as expected 
		assertEquals("Preparing for 10 GIGABIT Internet! What Could Go Wrong?", test_list.get(6).getTitle());
		assertEquals("aGq8uJSco1o", test_list.get(6).getPath());
		
		//printed watch ID to verify the returned link is correct
		System.out.println("Video 7 has a video ID of: " + test_list.get(6).getPath());
		
	}
	
}
