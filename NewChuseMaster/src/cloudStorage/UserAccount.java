package cloudStorage;

public class UserAccount {
	
	private int id;
	private String username;
	
	public UserAccount(int account_id, String username) {
		
		this.id = account_id;
		this.username = username;
		
	}

	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void print() {
		System.out.println("Account ID: " + id);
		System.out.println("Username: " + username);
	}

}
