package listCreationGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * TextFieldItem describes the text field item used within TextUI 
 * consisting of a text field and "remove" button contained in a HBox
 * 
 * Date created: 05/03/2019
 * Date last edited: 08/03/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Luke Fisher
 *
 */
public class TextFieldItem {
	// UI Components
	HBox newField;
	TextField newItem;
	Button newButton;
	
	int id;
	ManualTextScreenController controller;
	
	/**
	 * Constructor function instantiates components and add action listeners to relevant buttons
	 */
	public TextFieldItem(ManualTextScreenController controller, int index){
		
		newField = new HBox();
		newItem = new TextField();
		newButton = new Button("Remove");
		
		id = index;
		this.controller = controller;
		
		// Remove TextFieldItem when "remove" button is pressed
		newButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	controller.removeItem(id);
		        newField.getChildren().remove(newButton);
		        newField.getChildren().remove(newItem);
		    }
		});
		
		newField.getChildren().addAll(newItem,newButton);
	}
	
	// Return HBox for adding to TextFieldPane
	/**
	 * Method to return the HBox.
	 * @return
	 */
	public HBox getHBox(){
		return this.newField;
	}	
	
	/**
	 * Method to set the id of the text field.
	 * @param new_id
	 */
	public void setID(int new_id) {
		this.id = new_id;
	}
	
	/**
	 * Method to get the text from the text field item.
	 * @return
	 */
	public String getText() {
		return this.newItem.getText();
	}
	
}
