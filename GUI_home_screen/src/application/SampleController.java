package application;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class SampleController {

    @FXML
    void hello(MouseEvent event) {
    	System.out.println("hello");
    }

    @FXML
    void Image_icon(MouseEvent event) {
    	System.out.println("Image_icon");
    }

    @FXML
    void Music_icon(MouseEvent event) {
    	System.out.println("Music_icon");
    }

    @FXML
    void Text_icon(MouseEvent event) {
    	System.out.println("Text_icon");
    }

    @FXML
    void Video_icon(MouseEvent event) {
    	System.out.println("Video_icon");
    }
    
    @FXML
    void user_icon(MouseEvent event) {
    	System.out.println("user_icon");
    }

}