package listDataStructure;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import backEnd.BackEndContainer;
import cloudInteraction.CloudInteractionHandler;
import cloudInteraction.UserAccount;
import xmlHandling.XMLHandler;

public class StatisticsDataStructure{
	
	private ChuseList list;
	
	private ArrayList<Result> result_list;
	
	/**
	 * This constructs all data on list across multiple files for the statistics
	 * 
	 * @param file_path
	 */
	public StatisticsDataStructure(String file_path){
		
		// 1 get name from file path DONE
		// 2 chuse list from file path DONE
		// 3 get results from file path DONE 
		
		// 4 search for files with same name (title) DONE
		// 5 add each users results with user details DONE
		
		list = new ChuseList();
		list = XMLHandler.buildListFromXML(file_path);
		list.setListName(setTitleFromFilePath(file_path));
		
		ArrayList<RankingList> temp = new ArrayList<RankingList>();
		
		temp = XMLHandler.buildResultsListFromXML(file_path);

		//result_list.add(new Result(BackEndContainer.getLocalAccount(),temp)); 
		//result_list.add(new Result(new UserAccount("Jack@mail.com", "Jack", "22", "Male"), temp)); 
		result_list = new ArrayList<Result>();
		
		// This is for testing purposes
		String gender = CloudInteractionHandler.queryUserAccountByProperty("username", list.getAuthor()).next().getString("gender");
		String age = CloudInteractionHandler.queryUserAccountByProperty("username", list.getAuthor()).next().getString("age");
		addResult(new UserAccount("null", list.getAuthor(), age, gender), temp);
		// The actual code when in use with the application
		// addResult(BackEndContainer.getLocalAccount(), temp);
		
		ArrayList<String> results_files = getResultsFilesExtension(file_path.substring(0, file_path.indexOf("\\saves")+6), list.getName().substring(0, list.getName().indexOf(".xml")));
		
		UserAccount temp_user;
		
		for (int j = 0; j < results_files.size(); j++){
			gender = CloudInteractionHandler.queryUserAccountByProperty("username", results_files.get(j)).next().getString("gender");
			age = CloudInteractionHandler.queryUserAccountByProperty("username", results_files.get(j)).next().getString("age");
			
			temp_user = new UserAccount("null", results_files.get(j), age, gender);
			
			temp = XMLHandler.buildResultsListFromXML(file_path.substring(0, file_path.indexOf(".xml")) +"-"+ results_files.get(j)+".xml");
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
		String[] all_files = folder.list();
		ArrayList<String> files = new ArrayList();
		int i;

		for (i = 0; i < folder.list().length; i++){
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
	
}
