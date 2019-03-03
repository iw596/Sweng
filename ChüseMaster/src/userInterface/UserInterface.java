package userInterface;

import java.io.IOException;
import java.util.ArrayList;

import apiHandlers.YouTubeAPIHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import listDataStructure.BasicItem;
import listDataStructure.ChuseList;
import mediaFileImportHandling.AudioFileHandler;
import mediaFileImportHandling.ImageFileHandler;
import mediaFileImportHandling.TextFileHandler;
import xmlHandling.XMLHandler;

public class UserInterface extends Application {

	@Override
    public void start(final Stage stage) {
		
        stage.setTitle("File Chooser Sample");
        
        final Button openMultipleButton = new Button("Open files...");
 
        openMultipleButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                	
                	ChuseList test_list = new ChuseList("test list");
                	
                	/** ONLY UNCOMMENT ONE FILE GETTER AT A TIME **/
                	
						
                	//test_list.addItemArray(ImageFileHandler.openMultipleImageFiles(stage));
                	//test_list.addItemArray(AudioFileHandler.openMultipleAudioFiles(stage));
						 
                	
                	test_list.addItemArray(TextFileHandler.openMultipleTextFiles(stage));
                	test_list.printList();
                	
                }
            });
 
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(openMultipleButton, 1, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openMultipleButton);
        
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
 
    public static void main(String[] args) {
        Application.launch(args);
    }

}
