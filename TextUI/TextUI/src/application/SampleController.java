package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SampleController {
	
	@FXML
	ImageView Image01;
	
	@FXML
	private AnchorPane RootPane01;
	@FXML
	private AnchorPane RootPane02;
	
	@FXML
	VBox TextFieldPane;
	
	ArrayList<TextFieldItem> TextFieldItems = new ArrayList<TextFieldItem>();
	
	
	@FXML
	public void ImagePress01(MouseEvent action) throws Exception{		
		AnchorPane pane01 = FXMLLoader.load(getClass().getResource("ManualTextScreen.fxml"));
		RootPane01.getChildren().setAll(pane01);
	}
	
	public void ImagePress02(MouseEvent action){
		System.out.println("Test02");
	}
	
	public void ImagePress03(MouseEvent action){
		System.out.println("Test03");
	}
	
	@FXML
	public void BackScreen(MouseEvent action) throws Exception{
		AnchorPane pane02 = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		RootPane02.getChildren().setAll(pane02);
	}
	
	public void NewItem(ActionEvent action){
		TextFieldItem TestItem = new TextFieldItem();

		//TextFieldItems.add(TestItem);
		TextFieldPane.getChildren().add(TestItem.getHBox());
	}
	
	public void CompareItems(MouseEvent action){
		
	}
	
}
