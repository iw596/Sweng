package searchScreenGUI;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import backEnd.BackEndContainer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class UsernameSearchScreenController {

    @FXML
    private BorderPane root;
	
    @FXML
    private JFXTextField search_bar;
    
    @FXML
    private ImageView IV;
    
    @FXML
    private JFXButton search_button;

    private BackEndContainer back_end;
    
    public UsernameSearchScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
    }
    
    @FXML
    void enter(KeyEvent event) {
    	
    	if (event.getCode().toString() == "ENTER"){
    		System.out.println(search_bar.getText());
    	}
    	
    }
    
    @FXML
    void searchClicked(MouseEvent event) {
    	System.out.println(search_bar.getText());
    }

}
