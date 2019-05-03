package cloudInteraction;

/**
 * Data Structure class for a User Account object. Contains the user account information, including: user ID,
 * email address, user name, gender and age.
 * 
 * Date created: 18/04/2019
 * Date last edited: 24/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class UserAccount {
	
	private int id;
	private String email;
	private String username;
	private String gender;
	private int age;
	
	/**
	 * Constructor method for a user account object. Takes the email, user name, age and gender and passes
	 * them to variables, as well as hashing the email address to give the ID number.
	 * 
	 * @param email		the user's email address
	 * @param username	the user's user name
	 * @param age		the user's age
	 * @param gender	the user's gender
	 */
	public UserAccount(String email, String username, String age, String gender) {
		
		this.email = email;
		this.id = email.hashCode();
		this.username = username;
		this.gender = gender;
		this.age = Integer.parseInt(age);
		
	}

	/**
	 * Method to get the user's ID number.
	 * 
	 * @return id	the user's ID number
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Method to get the user's user name.
	 * 
	 * @return username	the user's username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Method to get the user's gender.
	 * 
	 * @return gender	the user's gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * Method to get the user's age.
	 * 
	 * @return age	the user's age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Method to get the user's email address.
	 * 
	 * @return email	the user's email address
	 */
	public String getEmail() {
		return email;
	}

}
