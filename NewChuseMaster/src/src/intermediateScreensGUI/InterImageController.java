package intermediateScreensGUI;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class for the intermediate image screen controller. This class handles all button listeners and interactivity
 * and holds a reference to the back-end code of the program for communication.
 * 
 * Date created: 09/03/2019
 * Date last edited: 01/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Jack Small and Aeri Olsson
 *
 */
public class InterImageController {

    @FXML
    private BorderPane root;
	
    @FXML
    private JFXButton IFFSImage;

    private BackEndContainer back_end;
    
    public InterImageController(BackEndContainer back_end) {
    	this.back_end = back_end;
    }
    
    @FXML
    /**
     * Method to open the image file chooser dialogue when the button is clicked.
     * @param event
     */
    void importFromAFileImage(ActionEvent event) throws IOException {
    	back_end.loadImageFiles((Stage) root.getScene().getWindow());
    	
    	if(back_end.getCurrentListSize() < 2) {
    		System.out.println("Nothing to compare - only one item in list.");
    	} else {
        	FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
        	
        	ComparisonScreenController controller = new ComparisonScreenController(back_end);
        	
        	loader.setController(controller);
        	
        	BorderPane new_pane = loader.load();
        	
        	showInSelf(new_pane);
    	}
    	
    }
    
    /**
     * Method to display another .fxml file within the current screen.
     * @param new_pane
     */
    private void showInSelf(Pane new_pane) {
    	
    	new_pane.prefWidthProperty().bind(root.widthProperty());
    	new_pane.prefHeightProperty().bind(root.heightProperty());
    	new_pane.minWidthProperty().bind(root.minWidthProperty());
    	new_pane.minHeightProperty().bind(root.minHeightProperty());
    	new_pane.maxWidthProperty().bind(root.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(root.maxHeightProperty());
    	new_pane.setManaged(true);
    	root.setCenter(new_pane);
    	root.requestFocus();
	
    }

}

