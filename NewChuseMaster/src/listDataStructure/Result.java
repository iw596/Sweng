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
	
	/**
	 * Constructor for a the result object. Passes the user account and the results in.
	 * @param user
	 * @param results
	 */
	public Result(UserAccount user, ArrayList<RankingList> results){
		this.user = user;
		this.results = results;
	}

	/**
	 * Method to get the user account.
	 * @return user		the user account
	 */
	public UserAccount getUser() {
		return user;
	}

	/**
	 * Method to get the results.
	 * @return results 	the results ranking list
	 */
	public ArrayList<RankingList> getRankingList() {
		return results;
	}
}
