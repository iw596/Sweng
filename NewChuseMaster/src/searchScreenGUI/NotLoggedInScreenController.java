package searchScreenGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import accountScreensGUI.LogInScreenController;
import backEnd.BackEndContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import sidebarContainerGUI.MasterScreenController;

public class NotLoggedInScreenController implements Initializable {
	

    @FXML
    private BorderPane root;

    @FXML
    private JFXButton login_button;
    
    private BackEndContainer back_end;
    
    private MasterScreenController master;
    
    public NotLoggedInScreenController(BackEndContainer back_end, MasterScreenController master) {
    	this.back_end = back_end;
    	this.master = master;
    }
    
    @FXML
    void loadLoginScreen(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(accountScreensGUI.LogInScreenController.class.getResource("LogInScreen.fxml"));
    	LogInScreenController controller = new LogInScreenController(back_end, master);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	bindSizeProperties(new_pane);
    	
    }
    
    /**
     * Method to bind the size of a contained .fxml pane to the same size as the root pane.
     * @param new_pane
     */
    private void bindSizeProperties(BorderPane new_pane) {

    	new_pane.prefWidthProperty().bind(root.widthProperty());
		new_pane.prefHeightProperty().bind(root.heightProperty());
		
		new_pane.minWidthProperty().bind(root.minWidthProperty());
		new_pane.minHeightProperty().bind(root.minHeightProperty());
		
		new_pane.maxWidthProperty().bind(root.maxWidthProperty());
		new_pane.maxHeightProperty().bind(root.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	root.setCenter(new_pane);
    	
    	System.gc();
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(master == null) {
			login_button.setVisible(false);
			login_button.setDisable(true);
			login_button.setMouseTransparent(true);
		}
		
	}

}
