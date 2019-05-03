package searchScreenGUI;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import backEnd.BackEndContainer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import publicListsScreenGUI.PublicListsScreenController;

/**
 * Controller class for the user name search screen. Handles button interactivity and holds variable
 * for the text field, as well as reference to the root for instantiating a view within itself.
 * 
 * Date created: 17/04/2019
 * Date last edited: 18/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Harry and Luke
 *
 */
public class UsernameSearchScreenController {
	
    @FXML
    private BorderPane root;
	
    @FXML
    private JFXTextField search_bar;
    
    @FXML
    private ImageView IV;
    
    @FXML
    private JFXButton search_button;
    
    @FXML
    private Text search_comment;

    //container of the back end code
    private BackEndContainer back_end;
    
    private String search_text;
    
    /**
     * Constructor for the search screen controller. Passes the reference to the back
     * end into the controller.
     * 
     * @param back_end	the reference to the back end container
     */
    public UsernameSearchScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
    }
    
    @FXML
    /**
     * Method called when a key press event is triggered in the search bar. If it
     * is the enter key, perform a search for the currently contained string.
     * 
     * @param event
     */
    void enter(KeyEvent event) {
    	
    	if (event.getCode().toString() == "ENTER"){
    		search_text = search_bar.getText();
    		search(search_text);
    	}

    }
    
    @FXML
    /**
     * Method called when the search button is pressed. Performs a search for the currently
     * contained string.
     * 
     * @param event
     */
    void searchClicked(MouseEvent event) {
    	search_text = search_bar.getText();
    	search(search_text);
    }
    
    /**
     * Method for performing a search for the user name string.
     * 
     * @param username	the user name to search for
     */
    public void search(String username) {
    	
    	//loads the titles of all public lists from the user's account
    	this.back_end.loadPublicListsFromUser(username);
    	
    	//checks whether there are any public lists - if not, returns
    	if(this.back_end.getPublicLists() == null) {
    		search_comment.setText("Sorry we could not find an account by the name \"" + username + "\"." );
    		return;
    	}
    	
    	back_end.setListOwner(username);
    	
    	//loads the public lists screen
    	try {
			loadPublicListsScreen();
		} catch (IOException e) {
			e.printStackTrace();
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
     * Method to bind the size of a contained .fxml pane to the same size as the main content window.
     * 
     * @param new_pane
     */
    private void bindSizeProperties(BorderPane new_pane) {

    	new_pane.prefWidthProperty().bind(root.widthProperty());
		new_pane.prefHeightProperty().bind(root.heightProperty());
		
		new_pane.minWidthProperty().bind(root.minWidthProperty());
		new_pane.minHeightProperty().bind(root.minHeightProperty());
		
		new_pane.maxWidthProperty().bind(root.maxWidthProperty());
		new_pane.maxHeightProperty().bind(root.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	root.requestFocus();
    	
    	root.setCenter(new_pane);
    	
    	System.gc();
    	
    }

}
