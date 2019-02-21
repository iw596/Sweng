package cloudStorage;

public class UserAccount {
	
	private int id;
	private Boolean logged_in;
	
	public UserAccount(int account_id, Boolean logged_in) {
		
		this.id = account_id;
		this.logged_in = logged_in;
		
	}

	public int getId() {
		return id;
	}
	
	public Boolean isLoggedIn() {
		return logged_in;
	}

}
