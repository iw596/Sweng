package listDataStructure;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cloudInteraction.CloudInteractionHandler;
import cloudInteraction.UserAccount;
import xmlHandling.XMLHandler;

public class StatisticsDataStructure{
	
	private ChuseList list;
	
	private ArrayList<Result> result_list;
	
	/**
	 * 
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
	public StatisticsDataStructure(String file_path){
		
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
		
		// This gets the information needed for the statistics from the cloud, using the username that is extracted from the XML
		String gender = CloudInteractionHandler.queryUserAccountByProperty("username", list.getAuthor()).next().getString("gender");
		String age = CloudInteractionHandler.queryUserAccountByProperty("username", list.getAuthor()).next().getString("age");
		// This creates the results file from the data previously collected
		addResult(new UserAccount("null", list.getAuthor(), age, gender), temp);
		// This is to extract the usernames from the XML results files name e.g XML-user.xml returns user
		ArrayList<String> results_files = getResultsFilesExtension(file_path.substring(0, file_path.indexOf("\\saves")+6), list.getName().substring(0, list.getName().indexOf(".xml")));
		// This stores the user account
		UserAccount temp_user;
		// For each result file get the information needed for statistics from the cloud and create the user account
		for (int j = 0; j < results_files.size(); j++){
			gender = CloudInteractionHandler.queryUserAccountByProperty("username", results_files.get(j)).next().getString("gender");
			age = CloudInteractionHandler.queryUserAccountByProperty("username", results_files.get(j)).next().getString("age");
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
	
	public void addResult(UserAccount user, ArrayList<RankingList> results){

		this.result_list.add(new Result(user, results));
		
	}
	
	public ChuseList getList(){
		return list;
	}
	
	public ArrayList<Result> getResultList(){
		return result_list;
	}
	
	public StatisticsDataStructure getDataForGivenAgeRange(int low, int high){
		
		StatisticsDataStructure ageGroupResults = new StatisticsDataStructure();
		ageGroupResults.list = this.list;
		Result result;
		
		for(int i = 0; i < this.getResultList().size(); i++){
			if((this.getResultList().get(i).getUser().getAge() >= low) 
				&& (this.getResultList().get(i).getUser().getAge() <= high)){
				
				result = this.getResultList().get(i);
				ageGroupResults.getResultList().add(result);
			}
		}
		
		return ageGroupResults;
	}
	
	public StatisticsDataStructure getDataForGivenGender(String gender){
		
		StatisticsDataStructure genderGroupResults = new StatisticsDataStructure();
		genderGroupResults.list = this.list;
		
		for(int i = 0; i < this.getResultList().size(); i++){
			if(this.getResultList().get(i).getUser().getGender().equals(gender)){
				genderGroupResults.getResultList().add(this.getResultList().get(i));
			}
		}
		
		return genderGroupResults;
	}
	
	public StatisticsDataStructure getDataForGivenTimeRange(Date start, Date end){
		
		StatisticsDataStructure timeRangeResults = new StatisticsDataStructure();
		timeRangeResults.list = this.list;
		
		/*for(int i = 0; i < this.getResultList().size(); i++){
			timeRangeResults.getResultList().add(this.getResultList().get(i));
		}
		
		for(int i = 0; i < this.getResultList().size(); i++){
			for(int j = 0; j < this.getResultList().get(i).getRankingList().size(); j++){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Date temp;
				
				try {
					temp = sdf.parse(this.getResultList().get(i).getRankingList().get(j).getTime().substring(0, 10));
					if ((start.compareTo(temp) > 0) || (end.compareTo(temp) < 0)){
						timeRangeResults.getResultList().get(i).getRankingList().remove(j);
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}*/
		
		Result temp;
		
		for(int i = 0; i < this.getResultList().size(); i++){
			temp = new Result(this.getResultList().get(i).getUser(), new ArrayList<RankingList>());
			for(int j = 0; j < this.getResultList().get(i).getRankingList().size(); j++){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Date temp_date;
				
				try {
					temp_date = sdf.parse(this.getResultList().get(i).getRankingList().get(j).getTime().substring(0, 10));
					//if (!((start.compareTo(temp_date) > 0) || (end.compareTo(temp_date) < 0))){
					if (((start.compareTo(temp_date) <= 0) && end.compareTo(temp_date) >= 0)){
						temp.getRankingList().add(this.getResultList().get(i).getRankingList().get(j));
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(temp.getRankingList().size() != 0){
				timeRangeResults.getResultList().add(temp);
			}
			
		}
		
		return timeRangeResults;
	}
	
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
	
	public int getNumberOfResults(){
		int results = 0;
		
		for(int i = 0; i < this.getResultList().size(); i++){
			for(int j = 0; j < this.getResultList().get(i).getRankingList().size(); j++){
				results++;
			}
		}
		
		return results;
	}
	
	public double getAverageScore(String item){
		int number_of_results = this.getNumberOfResults();
		double score = 0;
		if(number_of_results != 0){
		
		int number_of_items = this.getList().getSize();
		
		for(int i = 0; i < this.getResultList().size(); i++){
			for(int j = 0; j < this.getResultList().get(i).getRankingList().size(); j++){
				for(int k = 0; k < this.getResultList().get(i).getRankingList().get(j).getSize(); k++){
					if(this.getResultList().get(i).getRankingList().get(j).get(k).getWrappedItem().getTitle().equals(item)){
						score += number_of_items - k;
						continue;
					}
				}
			}
		}
		
		score = score/this.getNumberOfResults();
		}
		
		return score;
	}
	
}

