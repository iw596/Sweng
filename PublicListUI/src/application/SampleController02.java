package application;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import listDataStructure.ChuseList;
import xmlHandling.XMLHandler;

/**
 * Sample controller holding scenebuilder UI functions and action listeners
 * 
 * Date created: 19/04/2019
 * Date last edited: 19/04/2019
 * Last edited by: Harry Ogden & Luke Fisher
 * 
 * @author Harry Ogden
 *
 */
public class SampleController02 implements Initializable {
	
	// Screen UI Components
	@FXML BorderPane root_pane02;
	@FXML HBox titles_pane;
	@FXML Text list_title;
	@FXML VBox items_pane;
	@FXML ScrollPane scroll_pane;
	@FXML JFXButton button;
	
	// Filename of list selected
	String filename;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Run once filename has been passed to controller 
		Platform.runLater(() -> {
			// Set title
			list_title.setText("Title: " + filename);
			
			// Forces the V-box inside the scroll pane to fit the size of the scroll pane. 
			scroll_pane.setFitToHeight(true);
			scroll_pane.setFitToWidth(true);
			
			// Create list of file items for display
			ChuseList PublicList = XMLHandler.buildListFromXML(System.getProperty("user.dir") + "/saves/" + filename);

			// Add file items to scroll pane
			for (int i=0;i<PublicList.getSize();i++){
				PublicListItem item = new PublicListItem(PublicList.get(i).getTitle(),"");
				items_pane.getChildren().add(item.getHBox());
			}
			
		});
	}
	
	// A setter for the filename to allow the other sample controller to set the variable appropriately
	public void SetFilename(String x){
		filename = x;
	}
	
	// On clicking the complete list button, call this function
	@FXML
	public void button_pressed(MouseEvent event) {
		System.out.println("Complete List button pressed!");
	}
	
}
