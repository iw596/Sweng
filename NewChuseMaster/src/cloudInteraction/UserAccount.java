package cloudInteraction;

public class UserAccount {
	
	private int id;
	private String username;
	private String gender;
	private int age;
	
	public UserAccount(int account_id, String username, String age, String gender) {
		
		this.id = account_id;
		this.username = username;
		this.gender = gender;
		this.age = Integer.parseInt(age);
		
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
		System.out.println("Age: " + age);
		System.out.println("Gender: " + gender);
		
	}

}
