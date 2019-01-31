package user;

public class User {
	
	private String[] first_name;
	private String[] last_name;
	
	
	public User(String[] username, String[] first_name, String[] last_name, int age){
		this.setFirstName(first_name);
		this.setLastName(last_name);
	}
	

	public String[] getFirstName() {
		return first_name;
	}

	public void setFirstName(String[] first_name) {
		this.first_name = first_name;
	}

	public String[] getLastName() {
		return last_name;
	}

	public void setLastName(String[] last_name) {
		this.last_name = last_name;
	}

}
