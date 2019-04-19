package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import xmlHandling.FileLocator;

/**
 * Sample controller holding scenebuilder UI functions and action listeners
 * 
 * Date created: 18/04/2019
 * Date last edited: 19/04/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Luke Fisher
 *
 */
public class SampleController01 implements Initializable {
	
	// Pane to hold buttons relating to available public lists
	@FXML VBox lists_pane;
	
	@FXML BorderPane root_pane01;
	@FXML BorderPane root_pane02;
	
	BorderPane pane01;
	
	// Array to hold available list files
	File[] list_of_files;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Locate relevant list .xml files
		list_of_files = FileLocator.locateRootFiles();
		
		//create an array of JFX buttons
    	ArrayList<JFXButton> file_buttons = new ArrayList<JFXButton>();
    	
    	for(File file : list_of_files) {
    		
    		//add a button with the text being the name of the file
    		file_buttons.add(new JFXButton(file.getName().replace(file.getName().substring(file.getName().lastIndexOf(".")), "") ));
    		//give the button an id
    		file_buttons.get(file_buttons.size() - 1).setId("button" + (file_buttons.size() - 1));
    		//set some padding for the button
    		file_buttons.get(file_buttons.size() - 1).setPadding(new Insets(10, 10, 10, 15));
    	
    		//give the button an action listener
    		file_buttons.get(file_buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					try {
						// Load second screen with list details
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Screen02.fxml"));
						pane01 = fxmlLoader.load();
						SampleController02 controller = fxmlLoader.getController();
						// Pass clicked list filename to second sample controller
						controller.SetFilename(file.getName());
						root_pane01.getChildren().setAll(pane01);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
    		});
    	}
    	
    	// Add the relevant file buttons to screen
    	for(int i=0; i < file_buttons.size();i++){
    		// Add buttons to pane
    		lists_pane.getChildren().add(file_buttons.get(i));
    	}
    
	}
	
	
}
