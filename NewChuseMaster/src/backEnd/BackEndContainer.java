package backEnd;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;

import algorithms.TournamentAlgorithms;
import apiHandlers.YouTubeAPIHandler;
import cloudInteraction.CloudInteractionHandler;
import cloudInteraction.UserAccount;
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import listDataStructure.RankingList;
import listDataStructure.StatisticsDataStructure;
import mediaFileImportHandling.AudioFileHandler;
import mediaFileImportHandling.ImageFileHandler;
import mediaFileImportHandling.TextFileHandler;
import mediaFileImportHandling.VideoFileHandler;
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
 * Date last edited: 27/04/2019
 * Last edited by: Harry Ogden and Isaac Watson
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
	
	private ArrayList<String> logged_in_users_lists;
	
	private String current_list_file_name;
	
	private String list_owner;
	
	private Boolean wasAccountCreated = false;

	private ArrayList<String> random_public_lists;
	
	private StatisticsDataStructure statistics_data;
	
	/**
	 * Constructor function for the BackEndContainer object. Sets the current list and
	 * current results equal to null.
	 */
	public BackEndContainer() {
		current_list = null;
		current_results = null;
		current_list_file_name = null;
		list_owner = null;
		logged_in_users_lists = null;
		random_public_lists = null;
		statistics_data = null;
	}
	
	/**
	 * Method to create a new list of text items by selecting a collection of text items from
	 * a local file browser.
	 * @param stage
	 */
	public void loadTextFiles(Stage stage) {
		
		this.current_list_file_name = null;
		this.statistics_data = null;
		
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
		
		this.current_list_file_name = null;
		this.statistics_data = null;
		
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
		
		this.current_list_file_name = null;
		this.statistics_data = null;
		
		current_list = new ChuseList();
		original_list = new ChuseList();
		
		ArrayList<BasicItem> audio_items = AudioFileHandler.openMultipleAudioFiles(stage);
		
		current_list.addItemArray(audio_items);
		original_list.addItemArray(audio_items);
		
	}
	
	/** Method to create a new list of video items by selecting a collection of video items from
	 * a local file browser.
	 * @param stage
	 */
	public void loadVideoFiles(Stage stage) {
		
		this.statistics_data = null;
		
		current_list = new ChuseList();
		original_list = new ChuseList();
		
		ArrayList<BasicItem> video_items = VideoFileHandler.openMultipleVideoFiles(stage);
		
		current_list.addItemArray(video_items);
		original_list.addItemArray(video_items);
	}
	
	public void loadSpotifyItems(ArrayList<BasicItem> spotify_items){
		current_list = new ChuseList();
		original_list = new ChuseList();
		this.statistics_data = null;
		
		current_list.addItemArray(spotify_items);
		original_list.addItemArray(spotify_items);
		
		for(int i = 0; i < current_list.getSize();i++){
			System.out.println(current_list.get(i).getTitle());
		}
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
		
		this.current_list_file_name = null;
		this.statistics_data = null;
		
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
		
		if(loggedIn == false) {
			// save list with null for username on attribute 
			XMLHandler.buildXMLFromList(this.original_list, file_path, this.current_results, null);
		}else {
			// save list with username on attribute 
			XMLHandler.buildXMLFromList(this.original_list, file_path, this.current_results, CloudInteractionHandler.getUserAccount().getUsername());
		}
		
		current_list_file_name = file_path;
		
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
		if(loggedIn == true) {
			// if its there list append results
			if(CloudInteractionHandler.getUserAccount().getUsername().equals(this.original_list.getAuthor()) || this.list_owner == null) {
				XMLHandler.appendResults(file_path, this.current_results);
			} else if(this.list_owner != null){
				// add there username to the file name before .xml
				file_path = file_path.replace(".xml", "-" + CloudInteractionHandler.getUserAccount().getUsername() + ".xml");
				// Build results xml 
				XMLHandler.buildXMLFromListWithResults(file_path, this.current_results);
				this.current_list_file_name = file_path;
			}	
		} else {
			// append result as it must be there as its in the local and there not log on to search for a public list
			XMLHandler.appendResults(file_path, this.current_results);
		}
		
	}
	
	/**
	 * Method to read an XML file into a list variable and then store this as the current and original
	 * list within the back end.
	 * 
	 * @param file_path	the path to the XML file
	 */
	public void loadXMLForComparison(String file_path) {
		this.statistics_data = null;
		this.current_list_file_name = file_path;
		current_list = XMLHandler.buildListFromXML(file_path);
		original_list = XMLHandler.buildListFromXML(file_path);
	}
	
	/**
	 * Method to create a list of YouTube video items when given a playlist URL. 
	 * @param playlist_url
	 * @throws IOException
	 */
	public void createYouTubeList(String playlist_url) throws IOException {
		this.statistics_data = null;
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
		
		if(this.cloud_handler == null) {
			this.cloud_handler = new CloudInteractionHandler();
		}
		
	}
	
	/**
	 * Method to create an account with a given email, username and password.
	 * 
	 * @param email		the email address of the account
	 * @param username	the username of the account
	 * @param password	the password of the account
	 */
	public Boolean createAccount(String email, String username, String password, int age, String gender) {
		
		wasAccountCreated = false;
		
		if(CloudInteractionHandler.createAccount(email, username, password, age, gender)) {
			System.out.println("Successfully created account!");
			wasAccountCreated = true;
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
	
	//*******************************************************************
	//--------------------------- NEW ----------------------------------
	//*******************************************************************
	/**
	 * Method to download a list XML from the cloud and read it into the current and original list
	 * variables.
	 * @param cloud_path	the path to the list folder on the cloud
	 * @return				true if downloads successfully, false if does not download successfully
	 * @throws IOException
	 */
	public Boolean downloadList(String cloud_path) throws IOException {
		
		//downloads the list and gets the local file path
		String local_path = CloudInteractionHandler.downloadList(cloud_path);
		
		//checks if the local file path exists
		if(local_path == null) {
			//if not returns false
			return false;
		} else {
			//if it does, read the XML and save variables, then return true
			loadXMLForComparison(local_path);
			return true;
		}
	}
	
	/**
	 * Method to upload a list to the user's cloud area, either public or private
	 * depending on the access type selected.
	 * 
	 * @param filepath	the path to the local XML file
	 * @return
	 */
	public Boolean uploadList(String filepath, Boolean shareListPublicly) {
		
		if(shareListPublicly) {
			CloudInteractionHandler.setAccessType(1);
		} else {
			CloudInteractionHandler.setAccessType(0);
		}
		
		return CloudInteractionHandler.uploadList(filepath);
	}
	
	/**
	 * Method to get the currently stored list's XML file. This will have a null value if
	 * the list has not yet been saved.
	 * @return
	 */
	public String getCurrentListFileName() {
		return this.current_list_file_name;
	}
	
	/**
	 * Method to set the owner of the currently active list.
	 * 
	 * @param username
	 */
	public void setListOwner(String username) {
		this.list_owner = username;
	}
	
	public String getListOwner() {
		return this.list_owner;
	}
	
	/**
	 * Method to get an user account's ID from their user name.
	 * 
	 * @param username	the user name of the account
	 * @return
	 */
	public String getAccountId(String username) {
		
		QueryResults<Entity> results = CloudInteractionHandler.queryUserAccountByProperty("username", username);
		
		String account_id = null;
		
		if(results != null) {
			Entity result = results.next();
			
			account_id = Long.toString(result.getKey().getId());
			
			System.out.println(result.toString());
			
			System.out.println(result.getKey().getId());

			
		}
		
		return account_id;
		
	}
	
	/**
	 * Method to upload the currently logged in user's results to another user's public list.
	 */
	public void uploadResults() {
		
		String xml_file = getCurrentListFileName().replaceFirst("-" + getLocalAccount().getUsername() + ".xml", ".xml");
		
		String folder_name = FilenameUtils.getBaseName(xml_file);
		
		String account_id = getAccountId(getListOwner());
		
		CloudInteractionHandler.uploadResults(current_list_file_name, account_id + "/public/" + folder_name + "/" + FilenameUtils.getName(getCurrentListFileName()));

	}
	
	/**
	 * Method to get all of the currently logged in user's lists.
	 * 
	 * @param username	the username of the user
	 * @return
	 */
	public void loadLoggedInUsersLists(String username) {
		
		this.logged_in_users_lists = CloudInteractionHandler.getAllProfileContent(username);
		
	}
	
	/**
	 * Method to get all of the currently logged in user's lists.
	 * 
	 * @param username	the username of the user
	 * @return
	 */
	public ArrayList<String> getLoggedInUsersLists() {
		
		return this.logged_in_users_lists;
		
	}
	
	/**
	 * Method to check whether or not an account was recently created successfully.
	 * @return
	 */
	public Boolean wasAccountCreated() {
		return wasAccountCreated;
	}
	
	/**
	 * Method to load a random selection of public lists.
	 * @return
	 */
	public void loadRandomPublicLists() {
		this.random_public_lists = CloudInteractionHandler.getRandomPublicLists();
	}
	
	/**
	 * Method to get a pre-loaded random selection of public lists.
	 * @return
	 */
	public ArrayList<String> getRandomPublicLists() {
		return this.random_public_lists;
	}
	
	/**
	 * Method to get an account's user name from it's ID number.
	 * @param Id
	 * @return
	 */
	public String getAccountNameFromId(int Id) {
		
		Entity account = CloudInteractionHandler.getUserAccountEntity(Id);
		
		if(account == null) {
			return null;
		}
		
		return account.getString("username");
		
	}
	
	/**
	 * Method for creating a collection of statistics data from the currently stored XML file.
	 * @return	true if successfully creates statistics, false if fails.
	 */
	public Boolean createCurrentListStatistics(String username) {
		
		//checks if the current list is saved as an XML file
		if(current_list_file_name == null) {
			//if it is not then returns
			return false;
		}
		
		//creates a new set of statistics data
		statistics_data = new StatisticsDataStructure(current_list_file_name, username);
		
		return true;
		
	}
	
	/**
	 * Method to get the current lists statistics data that are stored.
	 * @return	the current lists statistics data.
	 */
	public StatisticsDataStructure getCurrentListStatistics() {
		return statistics_data;
	}
	
//	/**
//	 * Method to get a user's age given their user name.
//	 * @param username	the user name of the account
//	 * @return	the user's age
//	 */
//	public String getAccountAge(String username) {
//		
//		return CloudInteractionHandler.queryUserAccountByProperty("username", username)
//				.next().getString("age");
//		
//	}
//	
//	/**
//	 * Method to get a user's gender given their user name.
//	 * @param username	the user name of the account
//	 * @return	the user's gender
//	 */
//	public String getAccountGender(String username) {
//		return CloudInteractionHandler.queryUserAccountByProperty("username", username)
//				.next().getString("gender");
//	}

}
