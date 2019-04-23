package cloudInteraction;

public class UserAccount {
	
	private int id;
	private String email;
	private String username;
	private String gender;
	private int age;
	
	public UserAccount(String email, String username, String age, String gender) {
		
		this.email = email;
		this.id = email.hashCode();
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
	
	public String getGender() {
		return gender;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void print() {
		System.out.println("Account ID: " + id);
		System.out.println("Username: " + username);
		System.out.println("Age: " + age);
		System.out.println("Gender: " + gender);
		
	}

}
