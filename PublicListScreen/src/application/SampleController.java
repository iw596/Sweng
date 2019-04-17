package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listDataStructure.ChuseList;
import xmlHandling.XMLHandler;

public class SampleController implements Initializable{
	
	@FXML VBox items_pane;
	@FXML ScrollPane scroll_pane;
	@FXML JFXButton button;
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		// Forces the V-box inside the scroll pane to fit the size of the scroll pane. 
		scroll_pane.setFitToHeight(true);
		scroll_pane.setFitToWidth(true);
		
		ChuseList PublicList = XMLHandler.buildListFromXML("Animal List.xml");
		
		for (int i=0;i<PublicList.getSize();i++){
		
		PublicListItem item = new PublicListItem(PublicList.get(i).getTitle(),"");
		items_pane.getChildren().add(item.getHBox());
		
		}
	}
	
	@FXML
	public void button_pressed(MouseEvent event) {
		System.out.println("Complete List button pressed!");
	}
	
}
