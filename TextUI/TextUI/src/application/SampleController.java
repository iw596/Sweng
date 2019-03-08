package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Sample controller holding scenebuilder UI functions and action listeners
 * 
 * Date created: 05/03/2019
 * Date last edited: 08/03/2019
 * Last edited by: Harry Ogden
 * 
 * @author Harry Ogden & Luke Fisher
 *
 */
public class SampleController {
	
	// Panes to hold each of the two screen views
	@FXML
	private AnchorPane RootPane01;
	@FXML
	private AnchorPane RootPane02;
	
	// Pane to hold a list of text fields for manually inputting items
	@FXML
	VBox TextFieldPane;
		
	/**
	 * Action listener for pressing the "manual input" image
	 * When user selects manual input, load manual input screen view
	 * @param action
	 * @throws Exception
	 */
	@FXML
	public void ManualInput(MouseEvent action) throws Exception{		
		AnchorPane pane01 = FXMLLoader.load(getClass().getResource("ManualTextScreen.fxml"));
		RootPane01.getChildren().setAll(pane01);
	}
	
	/**
	 * Action listener for pressing the "import from a file" image
	 * @param action
	 */
	public void FileImport(MouseEvent action){
		System.out.println("Import from file pressed");
	}
	
	/**
	 * Action listener for pressing the "import from a files" image
	 * @param action
	 */
	public void FilesImport(MouseEvent action){
		System.out.println("Import from files pressed");
	}
	
	/**
	 * Action listener for pressing the "back" button from the manual input screen
	 * Reloads previous text list creation screen view
	 * @param action
	 * @throws Exception
	 */
	@FXML
	public void BackScreen(MouseEvent action) throws Exception{
		AnchorPane pane02 = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		RootPane02.getChildren().setAll(pane02);
	}
	
	/**
	 * Action listener for pressing the "Add a new item" button
	 * Adds a new text field for manual item input
	 * @param action
	 */
	public void NewItem(ActionEvent action){
		TextFieldItem TestItem = new TextFieldItem();
		TextFieldPane.getChildren().add(TestItem.getHBox());
	}
	
	/**
	 * Action listener for pressing the "Compare items" button
	 * Continue to compare current manually inputted items
	 * @param action
	 */
	public void CompareItems(MouseEvent action){
		System.out.println("Compare items pressed");
	}
	
}
