package accountScreensGUI;

import java.net.URL;
import java.util.ResourceBundle;

import backEnd.BackEndContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 * Controller class fot he account info screen. Displays the currently logged in
 * account's information. Does not have any user-interactable elements.
 * 
 * Date created: 25/04/2019
 * Date last edited: 28/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class AccountInfoScreenController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Text username;

    @FXML
    private Text email;

    @FXML
    private Text gender;

    @FXML
    private Text age;
    
    private BackEndContainer back_end;
    
    /**
     * Constructor for account info screen controller. Passes reference to the back
     * end container into the controller.
     * 
     * @param back_end
     */
    public AccountInfoScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
    }
    
    @Override
    /**
     * Method called when the FXML is loaded, sets the appropriate text for the user name,
     * email, gender and age text items.
     */
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	// display the users information
    	username.setText(back_end.getLocalAccount().getUsername());
    	email.setText(back_end.getLocalAccount().getEmail());
    	gender.setText(back_end.getLocalAccount().getGender());
		age.setText(Integer.toString(back_end.getLocalAccount().getAge()));
		
		//focus the main pane
		root.requestFocus();
	}

}
