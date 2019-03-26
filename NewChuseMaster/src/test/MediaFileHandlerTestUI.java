package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
import listDataStructure.ChuseList;
import mediaFileImportHandling.AudioFileHandler;
import mediaFileImportHandling.ImageFileHandler;
import mediaFileImportHandling.TextFileHandler;

public class MediaFileHandlerTestUI extends Application {

	@Test
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
                	//System.out.println(test_list.get(test_list.getSize() - 1).getPath() + "  " + test_list.get(test_list.getSize() - 1).getType());
                	//test_list.addItemArray(AudioFileHandler.openMultipleAudioFiles(stage));
                	//System.out.println(test_list.get(test_list.getSize() - 1).getPath() + "  " + test_list.get(test_list.getSize() - 1).getType());
                	test_list.addItemArray(TextFileHandler.openMultipleTextFiles(stage));
                	
                	//test_list.printList();
                	//System.out.println(test_list.get(test_list.getSize() - 1).getPath() + "  " + test_list.get(test_list.getSize() - 1).getType());
                	
                	test_list.printList();
                	
                	//assertEquals("\\\\userfs\\js2401\\w2k\\Downloads\\Testing Files\\shot1.png", test_list.get(0).getPath());
                	
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
