package application;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterVideoController {

    @FXML
    private JFXButton IFYvideo;

    @FXML
    private JFXButton IFFSVideo;

    @FXML
    void importFromFilesVideo(ActionEvent event) {
    	System.out.println("Import from files");
    }

    @FXML
    void importFromYouTubeVideo(ActionEvent event) {
    	System.out.println("Import from YouTube");
    }

}