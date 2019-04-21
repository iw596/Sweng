package publicListsScreenGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Controller class for the user name search screen. Handles button interactivity and holds variable
 * for the text field, as well as reference to the root for instantiating a view within itself.
 * 
 * Date created: 18/04/2019
 * Date last edited: 18/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Harry and Luke
 *
 */
public class PublicListsScreenController implements Initializable{
	
	@FXML VBox lists_pane;
	@FXML BorderPane root;
	@FXML BorderPane root_pane02;
	
	@FXML JFXButton complete_list;
	@FXML Text list_title;
	
	BorderPane pane01;
	
	BackEndContainer back_end;
	
    /**
     * Constructor for the public lists screen controller. Passes the reference to the back
     * end into the controller.
     * 
     * @param back_end	the reference to the back end container
     */
	public PublicListsScreenController(BackEndContainer back_end) {
		this.back_end = back_end;
	}

	@Override
	/**
	 * Method called when the FXML file is loaded. Gets the names of the user's public lists, 
	 * and creates buttons for each one.
	 */
	public void initialize(URL location, ResourceBundle resources){
	
		//gets public lists
		ArrayList<String> public_lists = this.back_end.getPublicLists();

		//method to create buttons for each of the public list names
		createButtons(public_lists);
		
	}
	
	/**
	 * Method to create an array of buttons with the titles of the lists on the
	 * user's account.
	 * 
	 * @param titles	the titles of the lists
	 */
	private void createButtons(ArrayList<String> titles) {
		
		//creates an array of JFX buttons
		ArrayList<JFXButton> file_buttons = new ArrayList<JFXButton>();
		
		//loops through every list
    	for(String list: titles) {
    		
    		//add a button with the text being the name of the file
    		file_buttons.add(new JFXButton(FilenameUtils.getBaseName(list)));
    		//give the button an id
    		file_buttons.get(file_buttons.size() - 1).setId("button" + (file_buttons.size() - 1));
    		//set some padding for the button
    		file_buttons.get(file_buttons.size() - 1).setPadding(new Insets(10, 10, 10, 15));
    	
    		//give the button an action listener
    		//when a button is pressed, it opens up one of the saved files ready for comparison
    		file_buttons.get(file_buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println(list);
				}
    			
    		});
    	}
    	
    	//adds the buttons to the root pane.
    	for(JFXButton button : file_buttons) {
    		lists_pane.getChildren().add(button);
    	}
		
	}
	
}
