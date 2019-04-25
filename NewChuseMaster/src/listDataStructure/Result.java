package listDataStructure;

import java.util.ArrayList;

import cloudInteraction.UserAccount;

public class Result{
	
	private UserAccount user;
	
	private ArrayList<RankingList> results;
	
	public Result(UserAccount user, ArrayList<RankingList> results){
		
		this.user = user;
		
		this.setResults(results);
		
		
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public ArrayList<RankingList> getRankingList() {
		return results;
	}

	public void setResults(ArrayList<RankingList> results) {
		this.results = results;
	}
		
}
