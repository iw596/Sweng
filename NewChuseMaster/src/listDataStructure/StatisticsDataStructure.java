package listDataStructure;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

import cloudInteraction.CloudInteractionHandler;
import cloudInteraction.UserAccount;
import xmlHandling.XMLHandler;

/**
 * *******PLEASE ADD CLASS DESCRIPTION***********
 * 
 * Date created: 25/04/2019
 * Date last edited: 02/05/2019
 * Last edited by: Dan Jackson
 * 
 * @author Jack and Luke
 *
 */
public class StatisticsDataStructure {
	
	private ChuseList list;
	
	private ArrayList<Result> result_list;
	
	/**
	 * Constructor for an empty StatisticsDataStructure object.
	 */
	public StatisticsDataStructure(){
		this.list = new ChuseList();
		this.result_list = new ArrayList<Result>();
	}
	
	/**
	 * This constructs all data on list across multiple files for the statistics
	 * 
	 * Date created: 25/04/2019
	 * Date last edited: 25/04/2019
	 * Last edited by: Jack and Luke
 	 * 
	 * @param file_path
	 */
	public StatisticsDataStructure(String file_path, String username){

		// set the chuse list from the list witch is being passed by file_path
		list = new ChuseList();
		list = XMLHandler.buildListFromXML(file_path);
		// set list name form name in file path
		list.setListName(setTitleFromFilePath(file_path));
		
		// holds the result form a file
		ArrayList<RankingList> temp = new ArrayList<RankingList>();
		
		// get results from the XML file given by file path
		temp = XMLHandler.buildResultsListFromXML(file_path);

		// set results array
		result_list = new ArrayList<Result>();
		
		if(list.getAuthor().equals("")) {
			list.setAuthor(username);
		}
		
		// This gets the information needed for the statistics from the cloud, using the username that is extracted from the XML
		String gender = CloudInteractionHandler.queryUserAccountByProperty("username", list.getAuthor())
				.next().getString("gender");
		String age = CloudInteractionHandler.queryUserAccountByProperty("username", list.getAuthor())
				.next().getString("age");
		
		// This creates the results file from the data previously collected
		addResult(new UserAccount("null", list.getAuthor(), age, gender), temp);
		// This is to extract the usernames from the XML results files name e.g XML-user.xml returns user
		ArrayList<String> results_files = getResultsFilesExtension(FilenameUtils.getFullPath(file_path).substring(0, FilenameUtils.getFullPath(file_path).lastIndexOf("/")), FilenameUtils.getBaseName((file_path)));

		// This stores the user account
		UserAccount temp_user;
		// For each result file get the information needed for statistics from the cloud and create the user account
		for (int j = 0; j < results_files.size(); j++){
			gender = CloudInteractionHandler.queryUserAccountByProperty("username", results_files.get(j))
					.next().getString("gender");
			age = CloudInteractionHandler.queryUserAccountByProperty("username", results_files.get(j))
					.next().getString("age");
			// Creates the user account class that will be stored in the results class
			temp_user = new UserAccount("null", results_files.get(j), age, gender);
			// reconstructs the file path in order to get the results list
			
			temp = XMLHandler.buildResultsListFromXML(file_path.substring(0, file_path.indexOf(".xml")) +"-"+ results_files.get(j)+".xml");
			// adds the results list and user account to the results class
			addResult(temp_user, temp);
			
		}
		
		
	}
	
	/**
	 * This extracts the name of the list from the file path
	 * 
	 * @param file_path
	 * @return
	 */
	public static String setTitleFromFilePath(String file_path){
		
		String Title = file_path.substring((System.getProperty("user.dir") + "\\saves\\").length());
		
		return Title;
		
	}
	
	/**
	 * This gets the usernames for the relevant results files
	 * 
	 * @param directory_name
	 * @param extension
	 * @return
	 */
	public static ArrayList<String> getResultsFilesExtension(String directory_name, String extension){

		File folder = new File(directory_name);
		// Finds all files within the saves folder
		String[] all_files = folder.list();
		// This stores the relevant files name
		ArrayList<String> files = new ArrayList<String>();
		int i;

		
		for (i = 0; i < folder.list().length; i++){
			// If the selected file is a results file for the list, then add to the list of the files
			if (all_files[i].contains(extension+"-")){
				files.add(all_files[i].substring(all_files[i].indexOf("-")+1, all_files[i].indexOf(".xml")));
			}
		}
		return files;
	}
	
	/**
	 * add new result to result list
	 * 
	 * 
	 * @param user
	 * @param results
	 */
	public void addResult(UserAccount user, ArrayList<RankingList> results){

		this.result_list.add(new Result(user, results));
		
	}
	
	/**
	 * get original list
	 * 
	 * @return
	 */
	public ChuseList getList(){
		return list;
	}
	
	/**
	 * get result list, which each result has user's information and their ranked lists 
	 * 
	 * @return
	 */
	public ArrayList<Result> getResultList(){
		return result_list;
	}
	
	/**
	 * 
	 * Gets the results corresponding to an age range
	 * 
	 * @param low
	 * @param high
	 * @return
	 */
	public StatisticsDataStructure getDataForGivenAgeRange(int low, int high){
		
		// Build new statistics data structure
		StatisticsDataStructure ageGroupResults = new StatisticsDataStructure();
		// set chuse list
		ageGroupResults.list = this.list;
		// temp result for checking 
		Result result;
		
		// look at all users
		for(int i = 0; i < this.getResultList().size(); i++){
			// if users age is between the age bounds add them to the new data structure 
			if((this.getResultList().get(i).getUser().getAge() >= low) 
				&& (this.getResultList().get(i).getUser().getAge() <= high)){
				
				// set them to the result
				result = this.getResultList().get(i);
				// add the user and the ranking list to the new data structure
				ageGroupResults.getResultList().add(result);
			}
		}
		// return new data structure
		return ageGroupResults;
	}
	
	/**
	 * 
	 * Gets the results corresponding to a gender
	 * 
	 * @param gender
	 * @return
	 */
	public StatisticsDataStructure getDataForGivenGender(String gender){
		
		// Build new statistics data structure
		StatisticsDataStructure genderGroupResults = new StatisticsDataStructure();
		// set chuse list
		genderGroupResults.list = this.list;
		
		// look at all users 
		for(int i = 0; i < this.getResultList().size(); i++){
			// checks that the user is the set gender
			if(this.getResultList().get(i).getUser().getGender().equals(gender)){
				// add user to the new statistics data structure with all there information and ranking lists 
				genderGroupResults.getResultList().add(this.getResultList().get(i));
			}
		}
		
		// return new data structure
		return genderGroupResults;
	}
	
	/**
	 * 
	 * Gets the results for a given time range
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public StatisticsDataStructure getDataForGivenTimeRange(Date start, Date end){
		
		// Build new statistics data structure
		StatisticsDataStructure timeRangeResults = new StatisticsDataStructure();
		// set chuse list
		timeRangeResults.list = this.list;
		
		Result temp;
		
		// look at all users
		for(int i = 0; i < this.getResultList().size(); i++){
			// created a temporary results structure, containing the user and an empty array for the results.
			temp = new Result(this.getResultList().get(i).getUser(), new ArrayList<RankingList>());
			// Loops through all results
			for(int j = 0; j < this.getResultList().get(i).getRankingList().size(); j++){
				// Date formatting
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Date temp_date;
				
				try {
					// get the date that this partular set of results was created on
					temp_date = sdf.parse(this.getResultList().get(i).getRankingList().get(j).getTime().substring(0, 10));
					// If the set of results was made within the time frame, then add it to the temporary results structure
					if (((start.compareTo(temp_date) <= 0) && end.compareTo(temp_date) >= 0)){
						temp.getRankingList().add(this.getResultList().get(i).getRankingList().get(j));
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			// Check if any results are added to the temporary structure
			if(temp.getRankingList().size() != 0){
				// If results were added,then add this user to the data structure
				timeRangeResults.getResultList().add(temp);
			}
			
		}
		
		// return new data structure
		return timeRangeResults;
	}
	
	/**
	 * 
	 * Gets how many times an item has won
	 * 
	 * @param item
	 * @return
	 */
	public int getNumberOfWins(String item){
		int wins = 0;
		
		for(int i = 0; i < this.getResultList().size(); i++){
			for(int j = 0; j < this.getResultList().get(i).getRankingList().size(); j++){
				if (this.getResultList().get(i).getRankingList().get(j).get(0).getWrappedItem().getTitle().equals(item)){
					wins++;
				}
			}
		}
		
		
		return wins;
	}
	
	/**
	 * 
	 * Gets how many sets of results there are for a given list
	 * 
	 * @return
	 */
	public int getNumberOfResults(){
		int results = 0;
		
		// goes through the result list
		for(int i = 0; i < this.getResultList().size(); i++){
			// goes through the sets of results within the ranking list
			for(int j = 0; j < this.getResultList().get(i).getRankingList().size(); j++){
				results++;
			}
		}
		
		return results;
	}
	
	/**
	 * 
	 * Gets the average score (mean) of the items in a list
	 * 
	 * scores it based on the following formula:
	 * (size of list - (position-1))
	 * and then finds the mean over all ranking lists
	 * 
	 * @param item
	 * @return
	 */
	public double getAverageScore(String item){
		// Get number of results
		int number_of_results = this.getNumberOfResults();
		// tracking score
		double score = 0;
		// makes sure that there are actually results (avoid dividing by 0)
		if(number_of_results != 0){
			
		// gets how many items that are being compared
		int number_of_items = this.getList().getSize();
			
		// Goes through all the results
		for(int i = 0; i < this.getResultList().size(); i++){
			//Goes through each ranking list for each result
			for(int j = 0; j < this.getResultList().get(i).getRankingList().size(); j++){
				// Goes through each item in the ranking list
				for(int k = 0; k < this.getResultList().get(i).getRankingList().get(j).getSize(); k++){
					// checks if the item in the list is the one that is being looked for
					if(this.getResultList().get(i).getRankingList().get(j).get(k).getWrappedItem().getTitle().equals(item)){
						// sets the score
						score += number_of_items - k;
						continue;
					}
				}
			}
		}
		// gets the mean overall
		score = score/this.getNumberOfResults();
		}
		
		return score;
	}
	
}
