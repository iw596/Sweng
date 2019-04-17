package application;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SampleController {

    @FXML
    private JFXTextField search_bar;
    @FXML
    private ImageView IV;
    @FXML
    private JFXButton search_button;
    
    private String search_text;
    
    @FXML
    void enter(KeyEvent event) {
    	
    	if (event.getCode().toString() == "ENTER"){
    		//System.out.print("TETS");
    		System.out.print(search_bar.getText());
    		search_text = search_bar.getText();
    	}
    	
    }
    
    @FXML
    void searchClicked(MouseEvent event) {
    	System.out.print(search_bar.getText());
    	search_text = search_bar.getText();
    }

}
