package sidebarContainerGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import accountScreensGUI.AccountInfoScreenController;
import accountScreensGUI.LogInScreenController;
import backEnd.BackEndContainer;
import homeScreenGUI.HomeScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import publicListsScreenGUI.PublicListsScreenController;
import searchScreenGUI.NotLoggedInScreenController;
import searchScreenGUI.UsernameSearchScreenController;
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
public class MasterScreenController implements Initializable {

    @FXML
    private JFXTextField search_bar;

    @FXML
    private JFXButton search_button;

    @FXML
    private BorderPane content_pane;

    @FXML
    private JFXButton account_button;

    @FXML
    private ImageView home_button;

    @FXML
    private ImageView settings_button;

    @FXML
    private ImageView social_button;
    
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
    void searchClicked(ActionEvent event) {
    	
    	search_text = search_bar.getText();
    	search(search_text);
    	
    }
    
    @FXML
    void searchEntered(KeyEvent event) {
    	
    	if (event.getCode().toString() == "ENTER"){
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
    	
    	if(back_end.getLocalAccount() == null) {
    		try {
				loadNotLoggedInScreen();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return;
    	}
    	
    	//loads the titles of all public lists from the user's account
    	this.back_end.loadPublicListsFromUser(username);
    	
    	//checks whether there are any public lists - if not, returns
    	if(this.back_end.getPublicLists() == null) {
    		try {
				loadNoAccountsScreen();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return;
    	}
    	
    	back_end.setListOwner(username);
    	
    	//loads the public lists screen
    	try {
			loadPublicListsScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	search_text = "";
    	this.search_bar.setText("");
    	
    }

    @FXML
    /**
     * Method to show the user account screen when the button is clicked.
     * @param event
     */
    void showAccountScreen(ActionEvent event) throws IOException {
    	System.out.println("Account Screen Button Pressed");
    	
    	if(back_end.getLocalAccount() == null) {
    		FXMLLoader loader = new FXMLLoader(accountScreensGUI.LogInScreenController.class.getResource("LogInScreen.fxml"));
        	LogInScreenController controller = new LogInScreenController(back_end, this);
        	loader.setController(controller);
        	BorderPane new_pane = loader.load();
        	bindSizeProperties(new_pane);
    	} else {
    		FXMLLoader loader = new FXMLLoader(accountScreensGUI.AccountInfoScreenController.class.getResource("AccountInfoScreen.fxml"));
    		AccountInfoScreenController controller = new AccountInfoScreenController(back_end, this);
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
    	
    	back_end.setListOwner(null);
    	
    	FXMLLoader loader = new FXMLLoader(homeScreenGUI.HomeScreenController.class.getResource("HomeScreen.fxml"));
    	HomeScreenController controller = new HomeScreenController(back_end);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	bindSizeProperties(new_pane);
    	
    	back_end.setComparingLosers(false);
    	
    	//controller.initialize(null, null);

    }

    /**
     * Method to bind the size of a contained .fxml pane to the same size as the main content window.
     * @param new_pane
     */
    private void bindSizeProperties(BorderPane new_pane) {
    	
		
    	new_pane.prefWidthProperty().bind(content_pane.widthProperty());
		new_pane.prefHeightProperty().bind(content_pane.heightProperty());
		
		new_pane.minWidthProperty().bind(content_pane.minWidthProperty());
		new_pane.minHeightProperty().bind(content_pane.minHeightProperty());
		
		new_pane.maxWidthProperty().bind(content_pane.maxWidthProperty());
		new_pane.maxHeightProperty().bind(content_pane.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	content_pane.setCenter(new_pane);
    	
    }

	@FXML
	/**
	 * Method to show the settings screen when the button is clicked.
	 * @param event
	 */
    void showSettingsScreen(ActionEvent event) {
		System.out.println("Settings Button Pressed");
		
		back_end.getRandomPublicLists();
		
    }

    @FXML
    /**
     * Method to show the social screen when the button is clicked.
     * @param event
     */
    void showSocialScreen(ActionEvent event) throws IOException {
    	System.out.println("Social Button Pressed");
    	
    	back_end.setListOwner(null);
    	
    	if(this.back_end.getLocalAccount() != null) {
    		FXMLLoader loader = new FXMLLoader(socialScreenGUI.SocialScreenController.class.getResource("SocialScreen.fxml"));
    		SocialScreenController controller = new SocialScreenController(back_end);
        	loader.setController(controller);
        	BorderPane new_pane = loader.load();
        	bindSizeProperties(new_pane);
    	} else {
    		FXMLLoader loader = new FXMLLoader(searchScreenGUI.NotLoggedInScreenController.class.getResource("NotLoggedInScreen.fxml"));
    		NotLoggedInScreenController controller = new NotLoggedInScreenController(back_end, this);
        	loader.setController(controller);
        	BorderPane new_pane = loader.load();
        	bindSizeProperties(new_pane);
    	}
    	
    	
    	
    }
    
    public void setUsernameText(String username) {
    	account_button.setText(username);
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
//    	NotLoggedInScreenController controller = new NotLoggedInScreenController(back_end, this);
//    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	bindSizeProperties(new_pane);
    	
    }

	@Override
	/**
	 * Method to initialise the master screen by displaying the home screen as soon as the application is loaded.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		try {
			showHomeScreen(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
