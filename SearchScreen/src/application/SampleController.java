package application;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Sample controller holding scenebuilder UI functions and action listeners
 * 
 * Date created: 17/04/2019
 * Date last edited: 19/04/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Luke Fisher
 *
 */
public class SampleController {

	// UI Components
    @FXML
    private JFXTextField search_bar;
    @FXML
    private ImageView IV;
    @FXML
    private JFXButton search_button;
    
    // User entered text
    private String search_text;
    
    // Search when ENTER key is pressed
    @FXML
    void enter(KeyEvent event) {
    	
    	if (event.getCode().toString() == "ENTER"){
    		//System.out.print("TETS");
    		System.out.print(search_bar.getText());
    		search_text = search_bar.getText();
    	}
    	
    }
    
    // Search when search button is pressed
    @FXML
    void searchClicked(MouseEvent event) {
    	System.out.print(search_bar.getText());
    	search_text = search_bar.getText();
    }

}
