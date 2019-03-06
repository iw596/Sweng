package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SampleController {
	
	@FXML
	Text TestText;
	
	@FXML
	ImageView Image01;
	
	@FXML
	private AnchorPane RootPane;
	
	@FXML
	public void ImagePress01(MouseEvent action) throws Exception{
		System.out.println("Test01");
		//FXMLLoader loader = FXMLLoader.load(getClass().getResource("ManualTextScreen.fxml"));
		//Stage test = (Stage) Image01.getScene().getWindow();
		//Scene scene02 = new Scene(loader.getRoot());
		//test.setScene(scene02);
		
		AnchorPane pane = FXMLLoader.load(getClass().getResource("ManualTextScreen.fxml"));
		RootPane.getChildren().setAll(pane);
	}
	
	public void ImagePress02(MouseEvent action){
		System.out.println("Test02");
	}
	
	public void ImagePress03(MouseEvent action){
		System.out.println("Test03");
	}
	
}
