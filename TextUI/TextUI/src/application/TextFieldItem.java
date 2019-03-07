package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TextFieldItem {
	HBox newField;
	TextField newItem;
	Button newButton;
	
	
	public TextFieldItem(){
		newField = new HBox();
		newItem = new TextField();
		newButton = new Button();
		
		newButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        newField.getChildren().remove(newButton);
		        newField.getChildren().remove(newItem);
		    }
		});
		newField.getChildren().addAll(newItem,newButton);
	}
	
	public HBox getHBox(){
		return this.newField;
	}	
	
}
