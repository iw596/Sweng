package listDataStructure;

import java.util.ArrayList;

import cloudInteraction.UserAccount;

/**
 * class of holding a set of results from a pass of the comparison algorithm  
 * 
 * Date created: 22/04/2019
 * Date last edited: 22/04/2019
 * Last edited by: Jack and Luke
 * 
 * @author Jack and Luke
 *
 */
public class Result{
	
	// the user that the results are associated 
	private UserAccount user;
	
	// holds the sets of results the user made 
	private ArrayList<RankingList> results;
	
	public Result(UserAccount user, ArrayList<RankingList> results){
		
		this.user = user;
		this.results = results;
	}

	// get user
	public UserAccount getUser() {
		return user;
	}

	// get all the ranking list (results)
	public ArrayList<RankingList> getRankingList() {
		return results;
	}
}
