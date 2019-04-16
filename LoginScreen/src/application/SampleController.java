package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;

public class SampleController implements Initializable{
	
	// List of Gender options for the choice box.
	ObservableList<String> GenderList = (ObservableList<String>)FXCollections.observableArrayList("Male", "Female", "Other", "Prefer not to say"); 
	
	// Tags for FXML and scene builder.
	@FXML
    private TextField LoginEmail;

    @FXML
    private TextField LoginPassword;

    @FXML
    private TextField SignUpUsername;

    @FXML
    private TextField SignUpEmail;

    @FXML
    private TextField SignUpPassword;

    @FXML
    private TextField SignUpRepeatPassword;

    @FXML
    private TextField SignUpAge;
	
	@FXML
	private ChoiceBox<String> gender; 

	
	//Initialisation method for the gender list (choice box). 
	//This adds the gender options defined in the list to the choice box.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		gender.setItems(GenderList);
		
	}
	
	
	
	/**
	 *Login Method to check valid log in entry. This method does not check the validity of the information,
	 *Only that the correct type of information is added. ie. if letters are entered instead of numbers for age.
	 *Or if required fields are left empty. 
	 *In the case of an event where invalid entry is submitted, the field will turn red and a prompt text with 
	 *Expected input is visible in the text field. 
	 * @param event
	 * 
	 * 
	 * Authors: Jack Small, Aeri Olsson
	 * Date: 16/04/2019
	 */ 
	@FXML
    void Login(ActionEvent event) {
		
		//Setting border colour of the Login text fields (the ones used by user) to black.
		LoginEmail.setStyle("-fx-border-color: black;");
		LoginPassword.setStyle("-fx-border-color: black;");
		
		//This if statement checks if any text is added to the text field at all.
		//If something is added it prints Check Login in the console window.
		if(LoginEmail.getText().length() != 0 && LoginPassword.getText().length() != 0) {
			System.out.println("check login");
		
			
		//If nothing if written in the text field and submit is entered then the border colour of the affected
		// field will turn red and the text "Enter Email Address" will appear in the field.
		}else{
			if(LoginEmail.getText().length() == 0) {
				LoginEmail.setText("*Enter email address");
				LoginEmail.setStyle("-fx-border-color: red;");
				
				
			}
			//Same as above but for the login field.
			if(LoginPassword.getText().length() == 0) {
				LoginPassword.setText("*Enter password");
				LoginPassword.setStyle("-fx-border-color: red;");
		}
			}
			
    }

	/**
	 * Sign Up Method that checks if anything has been entered to the 
	 * text fields and also that it is in the correct format.
	 * 
	 * @param event
	 * 
	 * Authors: Aeri Olsson and Jack Small
	 * Date: 16/04/2019
	 */
    @FXML
    void SignUp(ActionEvent event) {
    	
    	//Setting the border colour of all of the boxes to black for the Sign up fields.
    	SignUpUsername.setStyle("-fx-border-color: black;");
    	SignUpEmail.setStyle("-fx-border-color: black;");
		SignUpPassword.setStyle("-fx-border-color: black;");
		SignUpRepeatPassword.setStyle("-fx-border-color: black;");
		SignUpAge.setStyle("-fx-border-color: black;");
		gender.setStyle("-fx-border-color: black;");
		
		//As long as all required fields are filled in "Sign Up" is printed in the console window.
    	if(SignUpUsername.getText().length() != 0 && SignUpEmail.getText().length() != 0  
    	   && SignUpPassword.getText().length() !=0 && SignUpRepeatPassword .getText().length() != 0 
    	   && SignUpAge.getText().length() !=0 && gender.getValue() != null){
    		
    		
    		//Variable used to check whether something is a number.
    		boolean numeric = true;
    		
    		//Checks if entered age is a number, if not try method to prevent java from crashing and sets numerical to false.
    		try {
    			Double age = Double.parseDouble(SignUpAge.getText());
    		} catch (NumberFormatException e) {
    			numeric = false;
    		}
    		
    		//If the entered age is a number, the code will progress to checking if the passwords entered are the same.
    		if(numeric == true) {
    			
    			if(SignUpPassword.getText().equals(SignUpRepeatPassword.getText())) {
    				System.out.println("Sign up");
    				System.out.println("password match");
    			}else {
    				
    				//When the passwords do not match, the box colour turns red and "Passwords do not match" will be printed in the text field of the second password field only.
    				//The first password entered will still be in the first field to avoid users having to retype it again.
    				SignUpRepeatPassword.setText("*Passwords do not match");
    				SignUpRepeatPassword.setStyle("-fx-border-color: red;");
    			}
    		//If the age entered is not a number "Enter a number" will be printed in the text field and border will turn red.	
    		}else {
    			SignUpAge.setText("*Enter a number");
				SignUpAge.setStyle("-fx-border-color: red;");
    		}
    		
    	}else {
    		// Prints error messages in the case of fields being left empty when submit button has been pressed.
    		if(SignUpUsername.getText().length() == 0) {
    			SignUpUsername.setText("*Enter a username");
    			SignUpUsername.setStyle("-fx-border-color: red;");
    		}
			if(SignUpEmail.getText().length() == 0) {
				SignUpEmail.setText("*Enter email address");
				SignUpEmail.setStyle("-fx-border-color: red;");
			}
			if(SignUpPassword.getText().length() == 0) {
				SignUpPassword.setText("*Enter password");
				SignUpPassword.setStyle("-fx-border-color: red;");
			}
						
			if(SignUpRepeatPassword.getText().length() == 0) {
				SignUpRepeatPassword.setText("*Enter password");
				SignUpRepeatPassword.setStyle("-fx-border-color: red;");
			}
							
			if(SignUpAge.getText().length() == 0) {
				SignUpAge.setText("*Enter age");
				SignUpAge.setStyle("-fx-border-color: red;");
			}
			// For the choice box the border turns red but no error message is printed.	
			if(gender.getValue() == null) {
				gender.setStyle("-fx-border-color: red;");
			}
    	}
    	
    }

}
