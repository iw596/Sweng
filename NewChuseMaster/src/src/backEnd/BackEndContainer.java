package backEnd;

import java.io.IOException;
import java.util.ArrayList;

import algorithms.TournamentAlgorithms;
import apiHandlers.YouTubeAPIHandler;
import cloudInteraction.CloudInteractionHandler;
import cloudInteraction.UserAccount;
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
 * Date last edited: 19/04/2019
 * Last edited by: Jack Small
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
	
	private CloudInteractionHandler cloud_handler;
	
	private static Boolean loggedIn = false;
	
	private ArrayList<String> public_lists;
	
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
	 * 
	 * Date Update: 19/04/2019 
	 * Last edited by: Jack Small
	 * 
	 */
	public void saveCurrentListToXML(String file_path) {
		if(this.loggedIn == false) {
			// save list with null for username on attribute 
			XMLHandler.buildXMLFromList(this.original_list, file_path, this.current_results, null);
		}else {
			// save list with username on attribute 
			XMLHandler.buildXMLFromList(this.original_list, file_path, this.current_results, this.cloud_handler.getUserAccount().getUsername());
		}
	}
	
	/**
	 * method to update the save of the file dependent on user and list being updated
	 * @param file_path
	 * 
	 * Date created: 19/04/2019 
	 * Date update: 19/04/2019 
	 * Last edited by: Jack Small
	 * 
	 */
	
	public void updateSaveListToXML(String file_path) {
		if(this.loggedIn == true) {
			// if its there list append results
			if(this.cloud_handler.getUserAccount().getUsername() == this.original_list.getAuthor()) {
				XMLHandler.appendResults(file_path, this.current_results);
			}else {
				
				
				// add there username to the file name before .xml
				file_path = file_path.replace(".xml", "-" + this.cloud_handler.getUserAccount().getUsername() + ".xml");
				// Build results xml 
				
				XMLHandler.buildResultsXML(file_path, this.current_results);
			}	
		}else {
			// append result as it must be there as its in the local and there not log on to search for a public list
			XMLHandler.appendResults(file_path, this.current_results);
		}
		
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
	
	/**
	 * Method to start the back-end Google Cloud Platform Interaction handler.
	 */
	public void startCloudHandler() {
		this.cloud_handler = new CloudInteractionHandler();
	}
	
	/**
	 * Method to create an account with a given email, username and password.
	 * 
	 * @param email		the email address of the account
	 * @param username	the username of the account
	 * @param password	the password of the account
	 */
	public Boolean createAccount(String email, String username, String password, int age, String gender) {
		if(CloudInteractionHandler.createAccount(email, username, password, age, gender)) {
			System.out.println("Successfully created account!");
			return true;
		} else {
			System.out.println("Account with this email or username already exists.");
			return false;
		}
	}
	
	/**
	 * Method to log into an account with a given email and password.
	 * 
	 * @param email		the email address of the account
	 * @param password	the passsword of the account
	 */
	public Boolean logIn(String email, String password) {
		if(CloudInteractionHandler.logIn(email, password)) {
			System.out.println("Logged in!");
			loggedIn = true;
			return true;
		} else {
			System.out.println("Not logged in.");
			return false;
		}
	}
	
	/**
	 * Method to log out of the currently logged in account.
	 */
	public void logOut() {
		CloudInteractionHandler.logOut();
		loggedIn = false;
	}
	
	
	/**
	 * Method to return the locally stored account that is currently logged in.
	 * @return	the account currently logged in, or null if not logged in
	 */
	public UserAccount getLocalAccount() {
		return CloudInteractionHandler.getUserAccount();
	}
	
	/**
	 * Method to store the name of the public lists from a specific account with a given
	 * user name.
	 * 
	 * @param username	the account's username
	 */
	public void loadPublicListsFromUser(String username) {
		public_lists = CloudInteractionHandler.getPublicProfileContent(username);
	}
	
	/**
	 * Method to get the currently stored names of the public lists.
	 * 
	 * @return	
	 */
	public ArrayList<String> getPublicLists() {
		return public_lists;
	}
	

	
	
}
