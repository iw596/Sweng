package accountScreensGUI;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import sidebarContainerGUI.MasterScreenController;

/**
 * Class for the log in and create account screen controller. Handles all listeners for the log in and sign up buttons
 * and contains all of the text field variables. Holds a reference to the back end container.
 * 
 * Date created: 16/04/2019
 * Date last edited: 18/04/2019
 * Last edited by: Dan Jackson
 * 
 * @authors Aeri and Jack
 *
 */
public class LogInScreenController implements Initializable{
	
	//the list for the gender choice box
	private ObservableList<String> gender_list = (ObservableList<String>)FXCollections.observableArrayList("Male", "Female", "Other", "Prefer not to say"); 
	
	//reference to the back end
	private BackEndContainer back_end;
	
	//reference to the master controller (required for changing username text)
	private MasterScreenController sidebar;
	
	//the choice box for gender
	@FXML
	private ChoiceBox<String> gender; 

	//the root borderpane
	@FXML
    private BorderPane root;

	//the text field for the log in email address
    @FXML
    private TextField login_email;

    //the text field for the log in password
    @FXML
    private TextField login_password;
    
    //the log in button
    @FXML
    private JFXButton login_button;
    
    //the text field for the create account username
    @FXML
    private TextField create_username;

    //the text field for the create account email
    @FXML
    private TextField create_email;

    //the text field for the create account password
    @FXML
    private TextField create_password;
    
    //the text field for the confirm account password
    @FXML
    private TextField confirm_password;
    
    //the text field for age
    @FXML
    private TextField age;

    //the sign up button
    @FXML
    private JFXButton sign_up_button;
    
    //the comment that pops up with errors for creating an account
    @FXML
    private Text sign_up_comment;
    
    //the comment that pops up with errors for logging in
    @FXML
    private Text login_comment;
    
    /**
     * Constructor for the Log In Screen Controller, required to pass the back end and sidebar controller references
     * into the controller.
     * 
     * @param back_end	the back end container
     * @param sidebar	the master sidebar view
     */
    public LogInScreenController(BackEndContainer back_end, MasterScreenController sidebar) {
    	this.back_end = back_end;
    	this.sidebar = sidebar;
    }
    
    @FXML
    /**
     * Method to log into an account using the entered details.
     * @param event	button event
     */
    void login(ActionEvent event) {
    	
    	//checks that all login fields are filled
    	if(loginFieldsEmpty()) {
    		//if they are not asks to fill them
    		login_comment.setText("Please enter your email and password.");
    		//returns without completing
    		return;
    	}
    	
    	//logs in and checks that the account can be logged into successfully
    	if(this.back_end.logIn(login_email.getText(), login_password.getText())){
    		//if it can, removes all the entered text
    		login_email.setText("");
    		login_password.setText("");
    		//sets the username text in the top right hand corner to the account's username
    		sidebar.setUsernameText(back_end.getLocalAccount().getUsername());
    		//sets the comment text to a success message
    		login_comment.setText("Logged in successfully!");
    	} else {
    		//sets the comment text to a failure message
    		login_comment.setText("Unable to log in. Email or password incorrect.");
    	}
    	
    }

    @FXML
    /**
     * Method to create an account using the entered details.
     * 
     * @param event	button event
     */
    void signUp(ActionEvent event) {
    	
    	
    	//check that all the fields are filled
    	if(signUpFieldsEmpty()) {
    		//if not diplays error message and returns without completing
    		sign_up_comment.setText("Please fill in all the fields.");
    		return;
    	}
    	
    	//checks that the passwords match
    	if(!passwordsMatch(create_password.getText(), confirm_password.getText())) {
    		//if not displays error message and returns without completing
    		sign_up_comment.setText("Passwords do not match.");
    		return;
    	}

    	//creates an account and checks that the account can be created successfully
    	if(this.back_end.createAccount(create_email.getText(), create_username.getText(), create_password.getText(), Integer.parseInt(age.getText()), gender.getSelectionModel().getSelectedItem())) {
    		//displays success message
    		sign_up_comment.setText("Account created successfully!");
    		//removes all entered text and gender selection
    		create_email.setText("");
    		create_username.setText("");
    		create_password.setText("");
    		confirm_password.setText("");
    		age.setText("");
    		gender.getSelectionModel().clearSelection();
    	} else {
    		//displays an error message
    		sign_up_comment.setText("Account with this username or email already in use.");
    	}
    	
    }
    
    /**
     * Checks whether or not the two passwords match.
     * 
     * @param password_a
     * @param password_b
     * @return	true if they match, false if not
     */
    private Boolean passwordsMatch(String password_a, String password_b) {
    	if(password_a.equals(password_b)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * Method to check if all the create account fields are filled in.
     * 
     * @return	true if they are, false if not
     */
    private Boolean signUpFieldsEmpty() {
    	if(create_username.getText().length() < 1) {
    		return true;
    	} else if(create_email.getText().length() < 1) {
    		return true;
    	} else if(create_password.getText().length() < 1) {
    		return true;
    	} else if(confirm_password.getText().length() < 1) {
    		return true;
    	} else if(age.getText().length() < 1) {
    		return true;
    	} else if(gender.getSelectionModel().getSelectedItem() == null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * Method to check if all the log in fields are filled in.
     * 
     * @return	true if they are, false if not
     */
    private Boolean loginFieldsEmpty() {
    	if(login_email.getText().length() < 1) {
    		return true;
    	} else if(login_password.getText().length() < 1) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
	@Override
	/**
	 * Method called when the FXML file is loaded. Sets the list of items for the choice box and
	 * adds a listener to the age text field.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		gender.setItems(gender_list);
		
		//adds a listener to ensure that only integers can be added
		age.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String old_string, String new_string) {
				
				//checks if new string is not an integer
				if(!new_string.matches("\\d*")) {
					//if not, resets to old string
					age.setText(old_string);
				}
				
			}
			
		});
		
	}

}
