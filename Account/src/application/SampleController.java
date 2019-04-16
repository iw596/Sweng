package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class SampleController implements Initializable{

    @FXML
    private Text Username;

    @FXML
    private Text Email;

    @FXML
    private Text Gender;

    @FXML
    private Text Age;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	// users age
    	int number = 69;
    	
    	// display the users information
		Username.setText("TidePods69");
		Email.setText("email@mail.eu");
		Gender.setText("Male");
		Age.setText(Integer.toString(number));
	}

}
