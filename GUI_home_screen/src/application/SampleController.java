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

    @FXML
    void goToTextItermediatePage(ActionEvent event) throws IOException {
    	System.out.println("IntermediateTextPage");
    	Parent pane = FXMLLoader.load(getClass().getResource("Text_page.fxml"));
    	Scene text_page = new Scene(pane);
    	Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app.setScene(text_page);
    	app.show();
    }
    
    @FXML
    void goToImageIntermediatePage(ActionEvent event) throws IOException {
    	System.out.println("IntermediateImagePage");
    	Parent pane = FXMLLoader.load(getClass().getResource("IntermediateImagePage.fxml"));
    	Scene text_page = new Scene(pane);
    	Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app.setScene(text_page);
    	app.show();
    }
    
    @FXML
    void goToAudioIntermediatePage(ActionEvent event) throws IOException {
    	System.out.println("IntermediateAudioPage");
    	Parent pane = FXMLLoader.load(getClass().getResource("IntermediateAudioPage.fxml"));
    	Scene text_page = new Scene(pane);
    	Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app.setScene(text_page);
    	app.show();
    }
    
    @FXML
    void goToVideoIntermediatePage(ActionEvent event) throws IOException {
    	System.out.println("IntermediateVideoPage");
    	Parent pane = FXMLLoader.load(getClass().getResource("IntermediateVideoPage.fxml"));
    	Scene text_page = new Scene(pane);
    	Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app.setScene(text_page);
    	app.show();
    	
    }

    public void addButtonToGrid(Button button) {
    	button_grid.setPadding(new Insets(30));
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
    		for(int i = 0; i < 5; i++){
    			button_grid.add(new Button("text"), i, k);
    			j = j - 1;
    			if(j == 0){
    				break;
    			}
    		}
    	}
    }
  
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.addButtonToGrid(new Button());
	}
    
}
