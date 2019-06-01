package sidebarContainerGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import accountScreensGUI.AccountInfoScreenController;
import accountScreensGUI.LogInScreenController;
import backEnd.BackEndContainer;
import cloudInteraction.RunnablePublicListFetcher;
import homeScreenGUI.HomeScreenController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import multithreading.NotifyingThread;
import multithreading.ThreadTerminationListener;
import publicListsScreenGUI.PublicListsScreenController;
import searchScreenGUI.NotLoggedInScreenController;
import socialScreenGUI.SocialScreenController;

/**
 * Class for the sidebar container screen controller. This class handles all button listeners and interactivity
 * and holds a reference to the back-end code of the program for communication.
 * 
 * Date created: 09/03/2019
 * Date last edited: 01/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class MasterScreenController implements Initializable, ThreadTerminationListener {

    @FXML
    private VBox sidebar;
	
    @FXML
    private JFXTextField search_bar;

    @FXML
    private JFXButton search_button;

    @FXML
    private BorderPane content_pane;

    @FXML
    private JFXButton account_button;

    @FXML
    private JFXButton home_button;
    
    @FXML
    private JFXButton social_button;
    
    @FXML
    private JFXButton logout_button;
    
    private BackEndContainer back_end;
    
    private String search_text;
    
    /**
     * Constructor function for the master screen's constructor.
     */
    public MasterScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
    	this.search_text = null;
    }
    
    @FXML
    /**
     * Method called when the search button is clicked. Triggers a user search for the 
     * text within the search bar.
     * 
     * @param event
     */
    void searchClicked(ActionEvent event) {
    	
    	//gets the text from the search bar and searches for a user of that name
    	search_text = search_bar.getText();
    	search(search_text);
    	
    }
    
    @FXML
    /**
     * Method called when the enter button is hit whilst typing within the search bar. Triggers
     * a user search for the text within the search bar.
     * @param event
     */
    void searchEntered(KeyEvent event) {
    	
    	//tests whether or not the key pressed was the enter key
    	if (event.getCode().toString() == "ENTER"){
    		//gets the text from the search bar and searches for a user of that name
    		search_text = search_bar.getText();
    		search(search_text);
    	}
    	
    }
    
    /**
     * Method for performing a search for the user name string.
     * 
     * @param username	the user name to search for
     */
    public void search(String username) {
    	
    	//checks if the user is logged in or not
    	if(back_end.getLocalAccount() == null) {
    		//if the user is not logged in then shows the not logged in screen
    		//and returns
    		try {
				loadNotLoggedInScreen();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return;
    	}
    	
    	//loads the titles of all public lists from the user's account in a new thread
    	//this keeps the UI active and responsive and stops the program from freezing if the 
    	//request time is particularly long
    	RunnablePublicListFetcher fetcher = new RunnablePublicListFetcher(back_end, username);
    	fetcher.addTerminationListener(this);
    	Thread thread = new Thread(fetcher);
    	thread.start();
    	
    }

    /**
     * Method to show the results from the search.
     * 
     * @param username
     */
    private void showSearchResults(String username) {
    	
    	//checks whether there are any public lists
    	if(this.back_end.getPublicLists() == null) {
    		 //if there are no public lists returns and shows the no accounts screen
    		try {
				loadNoAccountsScreen();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return;
    	}
    	
    	//sets the list owner equal to the searched for username
    	back_end.setListOwner(username);
    	
    	//loads the public lists screen
    	try {
			loadPublicListsScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	//resets the search text and the text in the search bar
    	search_text = "";
    	this.search_bar.setText("");
    }
    
    @FXML
    /**
     * Method to show the user account screen when the button is clicked.
     * @param event
     */
    void showAccountScreen(ActionEvent event) throws IOException {
    	
    	//sets the current list owner to null
    	back_end.setListOwner(null);

    	//if not logged in, show the login/create account screen
    	if(back_end.getLocalAccount() == null) {
    		FXMLLoader loader = new FXMLLoader(accountScreensGUI.LogInScreenController.class.getResource("LogInScreen.fxml"));
        	LogInScreenController controller = new LogInScreenController(back_end, this);
        	loader.setController(controller);
        	BorderPane new_pane = loader.load();
        	bindSizeProperties(new_pane);
        //if logged in, show the account info screen
    	} else {
    		FXMLLoader loader = new FXMLLoader(accountScreensGUI.AccountInfoScreenController.class.getResource("AccountInfoScreen.fxml"));
    		AccountInfoScreenController controller = new AccountInfoScreenController(back_end);
        	loader.setController(controller);
        	BorderPane new_pane = loader.load();
        	bindSizeProperties(new_pane);
    	}

    }

    @FXML
    /**
     * Method to show the home screen when the button is clicked.
     * @param event
     * @throws IOException
     */
    void showHomeScreen(ActionEvent event) throws IOException {
    	
    	//sets the current list owner to null
    	back_end.setListOwner(null);
    	
    	//load the home screen
    	FXMLLoader loader = new FXMLLoader(homeScreenGUI.HomeScreenController.class.getResource("HomeScreen.fxml"));
    	HomeScreenController controller = new HomeScreenController(back_end);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	bindSizeProperties(new_pane);
    	
    	//tell the back end that losers are not being compared anymore
    	back_end.setComparingLosers(false);

    }

    /**
     * Method to bind the size of a contained .fxml pane to the same size as the main content window.
     * @param new_pane
     */
    private void bindSizeProperties(BorderPane new_pane) {
    	
		//binds the new panes width and height to the widht and height of the content (main) pane
    	new_pane.prefWidthProperty().bind(content_pane.widthProperty());
		new_pane.prefHeightProperty().bind(content_pane.heightProperty());
		new_pane.minWidthProperty().bind(content_pane.minWidthProperty());
		new_pane.minHeightProperty().bind(content_pane.minHeightProperty());
		new_pane.maxWidthProperty().bind(content_pane.maxWidthProperty());
		new_pane.maxHeightProperty().bind(content_pane.maxHeightProperty());
    	new_pane.setManaged(true);
    	
    	//displays the new pane in the content pane's centre panel
    	content_pane.setCenter(new_pane);
    	
    }

    @FXML
    /**
     * Method to show the social screen when the button is clicked.
     * @param event
     */
    void showSocialScreen(ActionEvent event) throws IOException {
    	
    	//sets the current list owner to null
    	back_end.setListOwner(null);

    	//if the user is not logged in, shows the not logged in screen and returns
    	if(this.back_end.getLocalAccount() == null) {
    		FXMLLoader loader = new FXMLLoader(searchScreenGUI.NotLoggedInScreenController.class.getResource("NotLoggedInScreen.fxml"));
    		NotLoggedInScreenController controller = new NotLoggedInScreenController(back_end, this);
        	loader.setController(controller);
        	BorderPane new_pane = loader.load();
        	bindSizeProperties(new_pane);
        	return;
    	}
    	
    	//shows the social screen
		FXMLLoader loader = new FXMLLoader(socialScreenGUI.SocialScreenController.class.getResource("SocialScreen.fxml"));
		SocialScreenController controller = new SocialScreenController(back_end);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	bindSizeProperties(new_pane);

    }
    
    @FXML
    /**
     * Method to log out of the currently logged in account. Resets the username text to
     * "Log In" and hides the log out button.
     * @param event
     */
    void logOut(ActionEvent event) {
    	back_end.logOut();
    	setUsernameText("Log In");
    	hideLogoutButton();
    	
    	//shows the home screen
    	try {
			showHomeScreen(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Method to set the username button's currently displayed text to the user's user name.
     * 
     * @param username	the user name to display on the button
     */
    public void setUsernameText(String username) {
    	if(username.equals("Log In")) {
    		account_button.setText(username);
    	} else {
    		account_button.setText("@" + username);
    	}
    }
    
    /**
     * Method for loading the public lists screen
     * @throws IOException
     */
    private void loadPublicListsScreen() throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(publicListsScreenGUI.PublicListsScreenController.class.getResource("PublicListsScreen.fxml"));
    	PublicListsScreenController controller = new PublicListsScreenController(back_end, search_text);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	bindSizeProperties(new_pane);
    	
    }
    
    /**
     * Method for loading the not logged in screen
     * @throws IOException
     */
    private void loadNotLoggedInScreen() throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(searchScreenGUI.NotLoggedInScreenController.class.getResource("NotLoggedInScreen.fxml"));
    	NotLoggedInScreenController controller = new NotLoggedInScreenController(back_end, this);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	bindSizeProperties(new_pane);
    	
    }
    
    /**
     * Method for loading the no accounts found screen
     * @throws IOException
     */
    private void loadNoAccountsScreen() throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(searchScreenGUI.NotLoggedInScreenController.class.getResource("NoAccountsScreen.fxml"));
    	BorderPane new_pane = loader.load();
    	bindSizeProperties(new_pane);
    	
    }

    /**
     * Method to show the log out button.
     */
    public void showLogoutButton() {
		logout_button.setVisible(true);
		logout_button.setMouseTransparent(false);
    }
    
    /**
     * Method to hide the log out button.
     */
    public void hideLogoutButton() {
    	logout_button.setVisible(false);
    	logout_button.setMouseTransparent(true);
    }
    
	@Override
	/**
	 * Method to initialise the master screen by displaying the home screen as soon as the application is loaded.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//if the user is logged in, show the logout button
		if(back_end.getLocalAccount() == null) {
			hideLogoutButton();
		}
		
		try {
			showHomeScreen(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	/**
	 * Method called when a notifying thread launched from this controller terminates. Used to display the search
	 * results after the search thread has found the results.
	 */
	public void notifyOfThreadTermination(NotifyingThread thread) {
		
		//wait for the program to be back on the main JavaFX thread
		//as handling an GUI updates from another thread crashes the program
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				//displays the search results (or lack of search results)
				showSearchResults(search_text);
			}
			
		});
		
	}

}
