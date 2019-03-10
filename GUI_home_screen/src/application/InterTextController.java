package application;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterTextController {

    @FXML
    private JFXButton MIText;

    @FXML
    private JFXButton IFFText;

    @FXML
    private JFXButton IFStext;

    @FXML
    void importFromAFileText(ActionEvent event) {
    	System.out.println("Import from a file");
    }

    @FXML
    void importFromFilesText(ActionEvent event) {
    	System.out.println("Import from files");
    }

    @FXML
    void manualInput(ActionEvent event) {
    	System.out.println("Manual Input");
    }

}