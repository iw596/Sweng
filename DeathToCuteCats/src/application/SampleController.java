package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;

public class SampleController implements Initializable{
	
	ObservableList<String> GenderList = (ObservableList<String>)FXCollections.observableArrayList("Male", "Female", "Other", "Prefer not to say"); 
	
	@FXML
	private ChoiceBox<String> gender; 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gender.setItems(GenderList);
	}

}
