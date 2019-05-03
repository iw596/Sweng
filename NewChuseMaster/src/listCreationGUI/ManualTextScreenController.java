package listCreationGUI;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Sample controller holding SceneBuilder UI functions and action listeners
 * 
 * Date created: 05/03/2019
 * Date last edited: 14/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Harry Ogden & Luke Fisher
 *
 */
public class ManualTextScreenController {
	
	// Panes to hold each of the two screen views
    @FXML
    private AnchorPane root;
	
	private ArrayList<TextFieldItem> text_fields;
	
	@FXML
	// Pane to hold a list of text fields for manually inputting items
	VBox TextFieldPane;
	
    @FXML
    private JFXButton save_list_button;
	
	private BackEndContainer back_end;
	
	/**
	 * Constructor function for the manual list creation screen's controller.
	 */
	public ManualTextScreenController(BackEndContainer back_end) {
		this.text_fields = new ArrayList<TextFieldItem>();
		this.back_end = back_end;
	}
	
	/**
	 * Action listener for pressing the "Add a new item" button
	 * Adds a new text field for manual item input
	 * @param action
	 */
	public void newItem(ActionEvent action){
		TextFieldItem TestItem = new TextFieldItem(this, text_fields.size());
		text_fields.add(TestItem);
		TextFieldPane.getChildren().add(TestItem.getHBox());
	}
	
	/**
	 * Action listener for pressing the "Compare items" button
	 * Continue to compare current manually inputted items
	 * @param action
	 * @throws IOException 
	 */
	public void compareItems(MouseEvent action) throws IOException{
		
		createList();
		
		if(text_fields.size() < 2) {
			//TODO ADD ERROR ABOUT ONLY 1 ITEM
		} else {
			FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
			
			ComparisonScreenController controller = new ComparisonScreenController(back_end);
			
			loader.setController(controller);

			BorderPane new_pane = loader.load();
			
			showInSelf(new_pane);
		}

	}
    
	/**
	 * Method to create a list of text items from the items typed in the on-screen text fields.
	 */
    private void createList() {
    	
		ArrayList<String> text_field_content = new ArrayList<String>();
		
		for(TextFieldItem item : text_fields) {
			text_field_content.add(item.getText());
		}
		
		back_end.createBasicItemList(text_field_content);
    }
	
	/**
	 * Method to remove a text field item from the array of text field items, and then
	 * update all of the ID numbers of the text fields.
	 * 
	 * @param index
	 */
	public void removeItem(int index) {
		
		//removes the item at the specified index
		if(index < text_fields.size()) {
			text_fields.remove(index);
		}
		
		//updates the ID numbers of all the remaining text fields
		for(int i=0; i < text_fields.size();i++) {
			text_fields.get(i).setID(i);
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
    	root.getChildren().removeAll();
    	root.getChildren().add(new_pane);
    	root.requestFocus();
    	
    	System.gc();
	
    }
	
}
