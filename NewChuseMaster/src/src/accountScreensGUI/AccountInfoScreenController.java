package accountScreensGUI;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import sidebarContainerGUI.MasterScreenController;

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
    
    @FXML
    private JFXButton log_out_button;
    
    private MasterScreenController master;
    
    private BackEndContainer back_end;
    
    public AccountInfoScreenController(BackEndContainer back_end, MasterScreenController master) {
    	this.back_end = back_end;
    	this.master = master;
    }
    
    @FXML
    void logOut(ActionEvent event) {
    	master.setUsernameText("Log In");
    	master.initialize(null, null);
    	back_end.logOut();
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	// display the users information
    	username.setText(back_end.getLocalAccount().getUsername());
    	email.setText(back_end.getLocalAccount().getEmail());
    	gender.setText(back_end.getLocalAccount().getGender());
		age.setText(Integer.toString(back_end.getLocalAccount().getAge()));
		
		root.requestFocus();
	}

}
