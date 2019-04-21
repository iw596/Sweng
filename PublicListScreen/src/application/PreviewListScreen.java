package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import listDataStructure.ChuseList;
import xmlHandling.XMLHandler;

public class PreviewListScreen implements Initializable{

    @FXML
    private BorderPane root;

    @FXML
    private Text username_title;

    @FXML
    private Text list_title;

    @FXML
    private ScrollPane scroll_pane;

    @FXML
    private VBox items_pane;

    @FXML
    private JFXButton start_button;

    public PreviewListScreen() {
    	
    }
    
    
    @FXML
    void startComparison(ActionEvent event) {

    }
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		// Forces the V-box inside the scroll pane to fit the size of the scroll pane. 
		scroll_pane.setFitToHeight(true);
		scroll_pane.setFitToWidth(true);
		
		ChuseList PublicList = XMLHandler.buildListFromXML("Animal List.xml");
		
		for (int i=0;i<PublicList.getSize();i++) {
		
			PublicListItem item = new PublicListItem(PublicList.get(i).getTitle(),"");
			items_pane.getChildren().add(item.getHBox());
		
		}
		
		root.requestFocus();
		
	}
	
}
