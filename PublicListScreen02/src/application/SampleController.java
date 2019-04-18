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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import xmlHandling.FileLocator;

public class SampleController implements Initializable{
	
	@FXML VBox lists_pane;
	@FXML BorderPane root_pane01;
	@FXML BorderPane root_pane02;
	
	@FXML JFXButton complete_list;
	@FXML Text list_title;
	
	BorderPane pane01;

	@Override
	public void initialize(URL location, ResourceBundle resources){
		/*if (CurrentScreen == 1) {
			System.out.println("Current Screen = 1");
			System.out.println(CurrentScreen);
			
			JFXButton next = new JFXButton("Next");
			
			lists_pane.getChildren().add(next);
			
			next.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent arg0) {
				}
	    	});
		}
		else{
			System.out.println("Current Screen = 2");
		} */
		
	
			File[] list_of_files = FileLocator.locateRootFiles();
			System.out.println(list_of_files[0].getName());
	    	
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
	    		//when a button is pressed, it opens up one of the saved files ready for comparison
	    		file_buttons.get(file_buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						System.out.println(file.getAbsolutePath());
					}
	    			
	    		});
	    	}
	    	
	    	for(int i=0; i < file_buttons.size();i++){
	    		lists_pane.getChildren().add(file_buttons.get(i));
	    	}
	    
    	
    	/*for(int i=0; i< 1;i++){
    		//give the button an action listener
    		//when a button is pressed, it opens up one of the saved files ready for comparison
    		file_buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					try {
						pane01 = FXMLLoader.load(getClass().getResource("Sample02.fxml"));
						root_pane01.getChildren().setAll(pane01);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
    		});
    		
    	}
    	*/	
	}
	
	@FXML
    public void NextScreen(MouseEvent event) throws Exception {
		BorderPane pane01 = FXMLLoader.load(getClass().getResource("Sample02.fxml"));
		root_pane01.getChildren().setAll(pane01);
    }
	
	public void InitializeScreen02(){
		list_title.setText("test");
	}
	
	@FXML
    public void complete_list(MouseEvent event) {
		System.out.println("Test");
		list_title.setText("test");
    }
	
	public void loadScreen() throws Exception {
		pane01 = FXMLLoader.load(getClass().getResource("Sample02.fxml"));
		root_pane01.getChildren().setAll(pane01);
	}
	
}
