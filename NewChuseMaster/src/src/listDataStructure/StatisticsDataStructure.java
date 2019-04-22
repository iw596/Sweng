package listDataStructure;

import java.util.ArrayList;

import cloudInteraction.UserAccount;
import xmlHandling.XMLHandler;

public class StatisticsDataStructure{
	
	private ChuseList list;
	
	private ArrayList<Result> result_list;
	
	public StatisticsDataStructure(String file_path){
		
		// 1 get name from file path DONE
		// 2 chuse list from file path DONE
		// 3 get results from file path
		
		// 4 search for files with same name (title)
		// 5 add each users results with user details 
		
		list = new ChuseList(setTitleFromFilePath(file_path));
		
		list = XMLHandler.buildListFromXML(file_path);
		
		ArrayList<RankingList> temo = new ArrayList<RankingList>();
		temo = XMLHandler.buildResultsListFromXML(file_path);
		
		result_list.add(new Result(new UserAccout(BackEndContainer.),temo)); 
	}
	
	public static String setTitleFromFilePath(String file_path){
		
		String Title = file_path.substring((System.getProperty("user.dir") + "\\saves\\").length());
		
		return Title;
		
	}
	
}
