package application;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterAudioController {

    @FXML
    private JFXButton IFSAudio;

    @FXML
    private JFXButton IFFAudio;

    @FXML
    void importFromFilesAudio(ActionEvent event) {
    	System.out.println("Import from files");
    }

    @FXML
    void importFromSpotifyAudio(ActionEvent event) {
    	System.out.println("Import from spotify");
    }

}