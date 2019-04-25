package homeScreenGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import cloudInteraction.DownloadingScreenController;
import comparisonScreenGUI.ComparisonScreenController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CloudFileListController implements Initializable {

    @FXML
    private ScrollPane cloud_scroll_pane;

    @FXML
    private VBox files_pane;
	
	private ArrayList<String> cloud_paths;
	
	private BackEndContainer back_end;
	
	private HomeScreenController home_controller;
	
	public CloudFileListController(BackEndContainer back_end, HomeScreenController home_controller) {
		this.back_end = back_end;
		this.home_controller = home_controller;
		
		cloud_paths = back_end.getLoggedInUsersLists(back_end.getLocalAccount().getUsername());
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cloud_scroll_pane.setFitToHeight(true);
		cloud_scroll_pane.setFitToWidth(true);
		
		createButtons();
		
	}
	
	private void createButtons() {
		
		if(cloud_paths == null) {
			return;
		}
		
		ArrayList<JFXButton> file_buttons = new ArrayList<JFXButton>();
		
		for(String path : cloud_paths) {
			
			System.out.println(path);
			
			String file_name = path.split("/")[2];
			
			if(path.contains("/public/")) {
				file_name += " - (Stored publicly)";
			}
			
    		//add a button with the text being the name of the file
    		file_buttons.add(new JFXButton(file_name));
    		file_buttons.get(file_buttons.size() - 1).setMnemonicParsing(false);
    		//give the button an id
    		file_buttons.get(file_buttons.size() - 1).setId("button" + (file_buttons.size() - 1));
    		//set some padding for the button
    		file_buttons.get(file_buttons.size() - 1).setPadding(new Insets(10, 10, 10, 15));
    		
    		file_buttons.get(file_buttons.size() - 1).setAlignment(Pos.CENTER_LEFT);
    		
    		file_buttons.get(file_buttons.size() - 1).setLayoutX(files_pane.getWidth());
    		
    		//give the button an action listener
    		//when a button is pressed, it opens up one of the saved files ready for comparison
    		file_buttons.get(file_buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					
				try {
					//load the comparison screen and start the tournament comparison algorithm
					FXMLLoader loader = new FXMLLoader(cloudInteraction.DownloadingScreenController.class.getResource("DownloadingScreen.fxml"));
					DownloadingScreenController controller = new DownloadingScreenController(back_end, path);
			    	loader.setController(controller);
					BorderPane new_pane = loader.load();
					home_controller.showInSelf(new_pane);
					System.gc();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
//					try {
//						if(!back_end.downloadList(path)) {
//							return;
//						}
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//
//					try {
//						//load the comparison screen and start the tournament comparison algorithm
//						FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
//				    	ComparisonScreenController controller = new ComparisonScreenController(back_end);
//				    	loader.setController(controller);
//						BorderPane new_pane = loader.load();
//						home_controller.showInSelf(new_pane);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}

				}
    			
    		});
			
		}
		
		files_pane.getChildren().addAll(file_buttons);
		
	}

}