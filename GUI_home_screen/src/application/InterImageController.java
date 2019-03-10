package application;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterImageController {

    @FXML
    private JFXButton IFFSImage;

    @FXML
    void importFromAFileIMage(ActionEvent event) {
    	System.out.println("Import from files");
    }

}

