package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// the GUI home page action controller
public class SampleController implements Initializable{

	@FXML
	private Button I;
	@FXML
	private Button II;
	@FXML
	private Button III;
	@FXML
	private Button IV;
    @FXML
    private GridPane button_grid;

	// goto/laod text itermediate page
    @FXML
    void goToTextItermediatePage(ActionEvent event) throws IOException {
    	System.out.println("IntermediateTextPage");
    	Parent pane = FXMLLoader.load(getClass().getResource("Text_page.fxml"));
    	Scene text_page = new Scene(pane);
    	Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app.setScene(text_page);
    	app.show();
    }
    
	// goto/laod image itermediate page
    @FXML
    void goToImageIntermediatePage(ActionEvent event) throws IOException {
    	System.out.println("IntermediateImagePage");
    	Parent pane = FXMLLoader.load(getClass().getResource("IntermediateImagePage.fxml"));
    	Scene text_page = new Scene(pane);
    	Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app.setScene(text_page);
    	app.show();
    }
    
	// goto/laod audio itermediate page
    @FXML
    void goToAudioIntermediatePage(ActionEvent event) throws IOException {
    	System.out.println("IntermediateAudioPage");
    	Parent pane = FXMLLoader.load(getClass().getResource("IntermediateAudioPage.fxml"));
    	Scene text_page = new Scene(pane);
    	Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app.setScene(text_page);
    	app.show();
    }
    
	// goto/laod video itermediate page
    @FXML
    void goToVideoIntermediatePage(ActionEvent event) throws IOException {
    	System.out.println("IntermediateVideoPage");
    	Parent pane = FXMLLoader.load(getClass().getResource("IntermediateVideoPage.fxml"));
    	Scene text_page = new Scene(pane);
    	Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app.setScene(text_page);
    	app.show();
    	
    }
	
	// add buttons to the gird pane in the recent tap
	// note when calling this not form the initalize method it doent work as it says the grid id is null
    public void addButtonToGrid(Button button) {
	    // add pading so buttons are not set to the left of cells
    	button_grid.setPadding(new Insets(30));
	    // set spacing between cells in the grid
    	button_grid.setHgap(10);
    	button_grid.setVgap(10);
    	
    	
    	int j = 11;// number of buttons 
    	
    	int r;
    	// Checks whether j is dividable by 5 and ads one if the result has a decimal point in which case it ads one.
    	if(j % 5 == 0){
    		r = (j/5);
    	}
    	else
    	{
    		r = (j/5) + 1;
    	}
    	
    	// Ads a button starting from top left for j buttons, 5 in a row
    	for(int k = 0; k < r; k++){
		// add 5 buttons on the row
    		for(int i = 0; i < 5; i++){
    			button_grid.add(new Button("text"), i, k);
			// de-incerment when button is displayed  
    			j = j - 1;
			// when no more buttons are need break from looping
    			if(j == 0){
    				break;
    			}
    		}
    	}
    }
  
	// set when GUI home is built from FXML
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.addButtonToGrid(new Button());
	}
    
}
