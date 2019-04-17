package backEnd;

import java.io.IOException;
import java.util.ArrayList;

import algorithms.TournamentAlgorithms;
import apiHandlers.YouTubeAPIHandler;
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.RankingList;
import mediaFileImportHandling.AudioFileHandler;
import mediaFileImportHandling.ImageFileHandler;
import mediaFileImportHandling.TextFileHandler;
import xmlHandling.XMLHandler;

/**
 * 
 * BackEndContainer is a class which contains instantiations of all the back-end elements of the program, such as 
 * the lists, the tournament algorithm and the methods associated with these things. This container can be distributed
 * amongst all the different GUI controllers and allows for the program to follow the Model View Controller paradigm.
 * 
 * Date created: 13/03/2019
 * Date last edited: 15/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class BackEndContainer {
	
	private ChuseList current_list;
	
	//variable to hold the list as it was created - this NEVER changes for the same set of items
	private ChuseList original_list;
	
	//instantiation of the tournament comparison algorithm
	private TournamentAlgorithms comparison;
	
	//current set of results
	private RankingList current_results;
	
	//most recent set of losers
	private ChuseList losers;
	
	//boolean check as to whether it is a fresh, uncompared list or whether losers are being compared to 
	private Boolean comparingLosers;
	
	/**
	 * Constructor function for the BackEndContainer object. Sets the current list and
	 * current results equal to null.
	 */
	public BackEndContainer() {
		current_list = null;
		current_results = null;
	}
	
	/**
	 * Method to create a new list of text items by selecting a collection of text items from
	 * a local file browser.
	 * @param stage
	 */
	public void loadTextFiles(Stage stage) {
		
		current_list = new ChuseList();
		original_list = new ChuseList();
		
		ArrayList<BasicItem> text_items = TextFileHandler.openMultipleTextFiles(stage);
		
		current_list.addItemArray(text_items);
		original_list.addItemArray(text_items);
	}
	
	/**
	 * Method to create a new list of image items by selecting a collection of image items from
	 * a local file browser.
	 * @param stage
	 */
	public void loadImageFiles(Stage stage) {
		
		current_list = new ChuseList();
		original_list = new ChuseList();
		
		ArrayList<BasicItem> image_items = ImageFileHandler.openMultipleImageFiles(stage);
		
		current_list.addItemArray(image_items);
		original_list.addItemArray(image_items);
	}
	
	/**
	 * Method to create a new list of audio items by selecting a collection of audio items from
	 * a local file browser.
	 * @param stage
	 */
	public void loadAudioFiles(Stage stage) {
		current_list = new ChuseList();
		original_list = new ChuseList();
		
		ArrayList<BasicItem> audio_items = AudioFileHandler.openMultipleAudioFiles(stage);
		
		current_list.addItemArray(audio_items);
		original_list.addItemArray(audio_items);
		
	}
	
	/**
	 * Method to return the current list.
	 * @return current_list
	 */
	public ChuseList getCurrentList() {
		return this.current_list;
	}
	
	/**
	 * Method to create a list of basic items from an array list of strings.
	 * @param content
	 */
	public void createBasicItemList(ArrayList<String> content) {
		current_list = new ChuseList();
		original_list = new ChuseList();
		for(String item_title : content) {
			current_list.addItem(new BasicItem(item_title));
			original_list.addItem(new BasicItem(item_title));
		}
	}
	
	/**
	 * Method to get the ranked results.
	 * @return current_results
	 */
	public RankingList getRankedResults() {
		
		if(comparingLosers) {
			current_results.addRankedResults(comparison.getUnrankedResults());
		} else {
			current_results = comparison.getRankedResults();
		}
		
		return current_results;
		
	}
	
	/**
	 * Method to get the losers from the most recent comparison.
	 * @return
	 */
	public ChuseList getLosers() {
		losers = comparison.getLosers();
		return this.losers;
	}
	
	/**
	 * Method to save the current list to an XML file.
	 * @param file_path
	 */
	public void saveCurrentListToXML(String file_path) {
		// XMLHandler.buildXMLFromList(this.original_list, file_path);
		// NEW
		XMLHandler.buildXMLFromList(this.original_list, file_path, this.current_results);
	}
	
	public void loadXMLForComparison(String file_path) {
		current_list = XMLHandler.buildListFromXML(file_path);
		original_list = XMLHandler.buildListFromXML(file_path);
	}
	
	/**
	 * Method to create a list of YouTube video items when given a playlist URL. 
	 * @param playlist_url
	 * @throws IOException
	 */
	public void createYouTubeList(String playlist_url) throws IOException {
		current_list = YouTubeAPIHandler.getPlaylistData(playlist_url);
		original_list = YouTubeAPIHandler.getPlaylistData(playlist_url);
	}
	
	/**
	 * Method to start a tournament comparison with the current list.
	 */
	public void startComparison() {
		comparison = new TournamentAlgorithms(this.current_list);
	}
	
	/**
	 * Method to set the current list equal to the losers, ready to perform a 
	 * tournament comparison of the losers to further define the results of a 
	 * comparison.
	 */
	public void compareLosers() {
		this.current_list = this.losers;
	}
	
	/**
	 * Method to get the tournament comparison object.
	 * @return
	 */
	public TournamentAlgorithms getComparison() {
		return comparison;
	}
	
	/**
	 * Method to set the boolean comparing losers check.
	 * @param state - true/false
	 */
	public void setComparingLosers(Boolean state) {
		this.comparingLosers = state;
	}
	
	/**
	 * Method to get the size of the current list.
	 * @return
	 */
	public int getCurrentListSize() {
		return this.current_list.getSize();
	}
}
