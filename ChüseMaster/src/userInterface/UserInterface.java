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
 
        final Button openButton = new Button("Open a file...");
        final Button openMultipleButton = new Button("Open files...");
 
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                	
                	//AudioFileHandler.openAudioFile(stage);
                	//TextFileHandler.openTextFile(stage);
                	
                }
            });
 
        openMultipleButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                	
                	//AudioFileHandler.openMultipleAudioFiles(stage);
                	//TextFileHandler.openMultipleTextFiles(stage);
                	ArrayList<BasicItem> songs = AudioFileHandler.openMultipleAudioFiles(stage);
                	ArrayList<BasicItem> pictures = ImageFileHandler.openMultipleImageFiles(stage);
                	ArrayList<BasicItem> text = TextFileHandler.openMultipleTextFiles(stage);
                	ChuseList videos = null;
					try {
						videos = YouTubeAPIHandler.getPlaylistData("https://www.youtube.com/watch?v=q6EoRBvdVPQ&list=PLFsQleAWXsj_4yDeebiIADdH5FMayBiJo");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	
                	ArrayList<BasicItem> video_array = new ArrayList<BasicItem>();
                	
                	for(int i = 0; i < videos.getSize(); i++) {
                		video_array.add(videos.get(i));
                	}
                			
                	ChuseList list = new ChuseList("name");
                	
                	list.addItemArray(songs);
                	list.addItemArray(pictures);
                	list.addItemArray(text);
                	list.addItemArray(video_array);
                	
                	XMLHandler.buildXMLFromList(list, "new new xml mixed file");
                	
                	list = XMLHandler.buildListFromXML("new new xml audio file");
                	
                	list.printList();
                	
                	//ImageFileHandler.openMultipleImageFiles(stage);
                	
                }
            });
 
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(openButton, 0, 0);
        GridPane.setConstraints(openMultipleButton, 1, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton, openMultipleButton);
        
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
