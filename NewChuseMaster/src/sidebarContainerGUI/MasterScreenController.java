package sidebarContainerGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import accountScreensGUI.AccountInfoScreenController;
import accountScreensGUI.LogInScreenController;
import backEnd.BackEndContainer;
import homeScreenGUI.HomeScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import searchScreenGUI.NotLoggedInScreenController;
import searchScreenGUI.UsernameSearchScreenController;

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
    
    /**
     * Constructor function for the master screen's constructor.
     */
    public MasterScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
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
    		FXMLLoader loader = new FXMLLoader(searchScreenGUI.UsernameSearchScreenController.class.getResource("UsernameSearchScreen.fxml"));
        	UsernameSearchScreenController controller = new UsernameSearchScreenController(back_end);
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
