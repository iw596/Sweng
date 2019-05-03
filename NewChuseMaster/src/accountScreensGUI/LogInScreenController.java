package accountScreensGUI;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import cloudInteraction.RunnableLogIn;
import cloudInteraction.RunnableSignUp;
import javafx.application.Platform;
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
import multithreading.NotifyingThread;
import multithreading.ThreadTerminationListener;
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
public class LogInScreenController implements Initializable, ThreadTerminationListener {
	
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
    
    private RunnableLogIn login = null;
    
    private RunnableSignUp sign_up = null;
    
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
    	startCloudService();
    }
    
    /**
     * Method to start the cloud interaction handler. This launches the handler in a new
     * thread to make the UI more responsive.
     */
    private void startCloudService() {
    	Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				back_end.startCloudHandler();
			}
    	});
    	
    	thread.start();
    	
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
    	
    	//starts the login process in a new thread to make the UI more responsive
    	login = new RunnableLogIn(back_end, login_email.getText(), login_password.getText());
    	login.addTerminationListener(this);
    	Thread thread = new Thread(login);
    	thread.start();
    	
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

    	sign_up = new RunnableSignUp(back_end, create_email.getText(),
    			create_password.getText(), create_username.getText(),
    			gender.getSelectionModel().getSelectedItem(), Integer.parseInt(age.getText()));
    	
    	sign_up.addTerminationListener(this);
    	
    	Thread thread = new Thread(sign_up);
    	
    	thread.start();
    	
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

		root.requestFocus();
		
	}

	@Override
	/**
	 * Method to listen for threads terminated that were launched within this controller.
	 */
	public void notifyOfThreadTermination(NotifyingThread thread) {

		if(login != null && thread.toString().equals(login.toString())) {
			logIn();
		} else {
			signUp();
		}
	}
	
	/**
	 * Method to set the login comment and update the top corner "Log In" text with the user's username
	 * and show the logout button.
	 */
	private void logIn() {
		if(back_end.getLocalAccount() == null) {
			//sets the comment text to a failure message
			//waits for the main thread to be the JavaFX UI thread
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					login_comment.setText("Unable to log in. Email or password incorrect.");
				}
			});
			
			return;
		}
		
		//waits for the main thread to be the JavaFX UI thread
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				//if it can, removes all the entered text
				login_email.setText("");
				login_password.setText("");
				//sets the username text in the top right hand corner to the account's username
				sidebar.setUsernameText(back_end.getLocalAccount().getUsername());
				sidebar.showLogoutButton();
				//sets the comment text to a success message
				login_comment.setText("Logged in successfully!");
			}
			
		});
	}

	/**
	 * Method to update the sign up comment when an account is created, displaying either a success
	 * or error message.
	 */
	private void signUp() {
		
		if(!back_end.wasAccountCreated()) {
			//waits for the main thread to be the JavaFX UI thread
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					//displays an error message
		    		sign_up_comment.setText("Account with this username or email already in use.");
				}
			});
			return;
		}
		
		//waits for the main thread to be the JavaFX UI thread
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
	    		//displays success message
	    		sign_up_comment.setText("Account created successfully!");
	    		//removes all entered text and gender selection
	    		create_email.setText("");
	    		create_username.setText("");
	    		create_password.setText("");
	    		confirm_password.setText("");
	    		age.setText("");
	    		gender.getSelectionModel().clearSelection();
			}
			
		});
		
	}
	
}
