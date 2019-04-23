package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import listDataStructure.AudioItem;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.ImageItem;
import listDataStructure.SpotifyItem;
import listDataStructure.VideoItem;

/**
 * Class for unit testing each of the classes in the data structure module.
 * 
 * Date created: 05/03/2019
 * Date last edited: 05/03/2019
 * Last edited by: Dan Jackson
 * @author Dan Jackson
 *
 */
public class DataStructureTest {

	@Test
	/**
	 * Method to test that a basic text item can be created successfully and the
	 * text content returned successfully.
	 */
	public void createTextItem() {
		
		BasicItem test_item = new BasicItem("Test Item");
		assertEquals("Test Item", test_item.getTitle());
		
	}
	
	@Test
	/**
	 * Method to test that an image item can be created successfully and the image
	 * file path returned.
	 */
	public void createImageItem() {
		
		ImageItem test_item = new ImageItem("example\\path\\to\\file.jpg");
		assertEquals("example\\path\\to\\file.jpg", test_item.getPath());
		
	}
	
	@Test
	/**
	 * Method to test that a video item can be created successfully and the video information
	 * returned.
	 */
	public void createVideoItem() {
		
		VideoItem test_item = new VideoItem("Video Title", "Video ID", "Video Description", "Video Channel");
		assertEquals("Video Title", test_item.getTitle());
		assertEquals("Video ID", test_item.getPath());
		assertEquals("Video Description", test_item.getMetadata().get(0));
		assertEquals("Video Channel", test_item.getMetadata().get(1));
	
	}
	
	@Test
	/**
	 * Method to test that an audio item can be created successfully and the audio item's
	 * properties successfully returned.
	 */
	public void createAudioItem() {
		
		AudioItem test_item = new AudioItem(System.getProperty("user.dir") + "\\test_audio.wav");
		assertEquals(System.getProperty("user.dir") + "\\test_audio.wav", test_item.getPath());
		assertEquals("test_audio.wav", test_item.getTitle());
		assertEquals(null, test_item.getMetadata().get(1));
		assertEquals(null, test_item.getMetadata().get(2));
		assertEquals(null, test_item.getMetadata().get(3));
		assertEquals(null, test_item.getMetadata().get(4));
		
	}
	
	@Test 
	/**
	 * Method to test that a Spotify item can be created successfully and that the item's
	 * properties can be successfully returned.
	 */
	public void createSpotifyItem() {
		
		ArrayList<String> song_metadata = new ArrayList<String>();
		
		song_metadata.add("Title");
		song_metadata.add("Artist");
		song_metadata.add("Album");
		song_metadata.add("Date");
		song_metadata.add("Genre");
		
		SpotifyItem test_item = new SpotifyItem("Song URL", song_metadata);
		
		assertEquals("Title", test_item.getTitle());
		assertEquals("Song URL", test_item.getPath());
		assertEquals("Artist", test_item.getMetadata().get(1)); 
		assertEquals("Album", test_item.getMetadata().get(2)); 
		assertEquals("Date", test_item.getMetadata().get(3)); 
		assertEquals("Genre", test_item.getMetadata().get(4));
		 
		
	}
	
	@Test
	/**
	 * Method to test that it is possible to check when two items are equal as opposed
	 * to just checking that they are the same object.
	 */
	public void testItemEquality() {
		
		Boolean result = false;
		
		BasicItem item_a = new BasicItem("Test Item");
		BasicItem item_b = new BasicItem("Test Item");
		
		if(item_a.getObjectValue().equals(item_b.getObjectValue())) {
			result = true;
		}
		
		assertEquals(true, result);
		
	}

	@Test
	/**
	 * Method to test whether it is possible to create a list of text items.
	 */
	public void testTextListCreation() {
		
		ChuseList test_list = new ChuseList();
		
		//add 10 list items
		for(int i=0; i < 10; i++) {
			test_list.addItem(new BasicItem("Item " + Integer.toString(i + 1)));
		}
		
		assertEquals(10, test_list.getSize());
		assertEquals("Item 1", test_list.get(0).getTitle());
		
	}
	
	@Test
	/**
	 * Method to test whether it is possible to create a list of image items.
	 */
	public void testImageListCreation() {
		
		ChuseList test_list = new ChuseList();
		
		//adds 10 image items
		for(int i=0; i < 10; i++) {
			test_list.addItem(new ImageItem("Path to Item " + Integer.toString(i + 1)));
		}
		
		assertEquals(10, test_list.getSize());
		assertEquals("Path to Item 1", test_list.get(0).getPath());
		
	}
	
	@Test
	/**
	 * Method to test whether it is possible to create a list of video items.
	 */
	public void testVideoListCreation() {
		
		ChuseList test_list = new ChuseList();
		
		//adds 10 items
		for(int i=0; i < 10; i++) {
			test_list.addItem(new VideoItem("Video Title " + Integer.toString(i+1),
					"Video ID " + Integer.toString(i+1),
					"Video Description " + Integer.toString(i+1),
					"Channel Name"));
		}
		
		assertEquals(10, test_list.getSize());
		assertEquals("Video Title 1", test_list.get(0).getTitle());
		assertEquals("Video Title 10", test_list.get(9).getTitle());
		
	}
	
	@Test
	/**
	 * Method to test whether it is possible to create a list of audio items. Also tests
	 * whether it is possible to create a list of size 1.
	 */
	public void testAudioListCreation() {
		
		ChuseList test_list = new ChuseList();
		
		for(int i=0; i < 1; i++) {
			test_list.addItem(new AudioItem(System.getProperty("user.dir") + "\\test_audio.wav"));
		}
		
		assertEquals(1, test_list.getSize());
		assertEquals("test_audio.wav", test_list.get(0).getTitle());
		
	}
	
	@Test
	/**
	 * Method to test if it is possible to create a list of Spotify items.
	 */
	public void testSpotifyListCreation() {
		
		ChuseList test_list = new ChuseList();
		
		ArrayList<String> shared_metadata = new ArrayList<String>();
		
		shared_metadata.add("Track");
		shared_metadata.add("Artist");
		shared_metadata.add("Album");
		shared_metadata.add("Date");
		shared_metadata.add("Genre");
		
		//adds 10 items to list
		for(int i=0; i < 10; i++) {
			test_list.addItem(new SpotifyItem("Song URL " + (i+1), shared_metadata));
		}
		
		assertEquals(10, test_list.getSize());
		assertEquals("Song URL 1", test_list.get(0).getPath());
		assertEquals("Song URL 10", test_list.get(9).getPath());
		
	}
	
	@Test
	/**
	 * Method to test whether or not it is possible to create a very large list.
	 * In this test, the list contains 500 items.
	 */
	public void testLargeListCreation() {
		
		ChuseList test_list = new ChuseList();
		
		for(int i=0; i < 500; i++) {
			test_list.addItem(new BasicItem("Item " + (i+1)));
		}
		
		assertEquals("Item 1", test_list.get(0).getTitle());
		assertEquals("Item 500", test_list.get(499).getTitle());
		
	}
	
	@Test
	/**
	 * Method to test whether or not it is possible to create a list with different item
	 * types within it.
	 */
	public void testMixedListCreation() {
		
		ChuseList test_list = new ChuseList();
		
		ArrayList<String> song_metadata = new ArrayList<String>();
		
		song_metadata.add("Title");
		song_metadata.add("Artist");
		song_metadata.add("Album");
		song_metadata.add("Date");
		song_metadata.add("Genre");
		
		VideoItem video_test_item = new VideoItem("Video Title", "Video ID", "Video Description", "Video Channel");
		BasicItem basic_test_item = new BasicItem("Test Item");
		ImageItem image_test_item = new ImageItem("example\\path\\to\\file.jpg");
		AudioItem audio_test_item = new AudioItem(System.getProperty("user.dir") + "\\test_audio.wav");
		SpotifyItem spotify_test_item = new SpotifyItem("Song URL", song_metadata);
		
		test_list.addItem(video_test_item);
		test_list.addItem(basic_test_item);
		test_list.addItem(image_test_item);
		test_list.addItem(audio_test_item);
		test_list.addItem(spotify_test_item);
		
		assertEquals(5, test_list.getSize());
		assertEquals("Video Title", test_list.get(0).getTitle());
		assertEquals("Test Item", test_list.get(1).getTitle());
		assertEquals("example\\path\\to\\file.jpg", test_list.get(2).getPath());
		assertEquals("test_audio.wav", test_list.get(3).getTitle());
		assertEquals("Song URL", test_list.get(4).getPath());
		
	}
	
}
