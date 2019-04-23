package homeScreenGUI;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.filechooser.FileFilter;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import intermediateScreensGUI.InterAudioController;
import intermediateScreensGUI.InterImageController;
import intermediateScreensGUI.InterTextController;
import intermediateScreensGUI.InterVideoController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import searchScreenGUI.NotLoggedInScreenController;
import xmlHandling.FileLocator;

/**
 * Class for the home screen screen controller. This class handles all button listeners and interactivity
 * and holds a reference to the back-end code of the program for communication.
 * 
 * Date created: 08/03/2019
 * Date last edited: 01/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Jack Small and Aeri Olsson
 *
 */
public class HomeScreenController implements Initializable {

    @FXML
    private BorderPane root;
	@FXML
	private Button I;
	@FXML
	private Button II;
	@FXML
	private Button III;
	@FXML
	private Button IV;

    @FXML
    private VBox files_pane;
    
    @FXML
    private ScrollPane recent_scroll_pane;
    
    @FXML
    private AnchorPane recent_anchor_pane;

    @FXML
    private ScrollPane cloud_scroll_pane;

    @FXML
    private VBox cloud_files_pane;
    
    @FXML
    private AnchorPane cloud_anchor_pane;

    private BackEndContainer back_end;

    /**
     * Constructor for the home screen controller. 
     * @param back_end
     */
    public HomeScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
    }

    @FXML
    /**
     * Method to navigate to the intermediate page for a text list when the button is pressed.
     * @param event
     * @throws IOException
     */
    void goToTextItermediatePage(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(intermediateScreensGUI.InterTextController.class.getResource("IntermediateTextPage.fxml"));
    	InterTextController controller = new InterTextController(back_end);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
    	
    }
    
    @FXML
    /**
     * Method to navigate to the intermediate page for an image list when the button is pressed.
     * @param event
     * @throws IOException
     */
    void goToImageIntermediatePage(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(intermediateScreensGUI.InterImageController.class.getResource("IntermediateImagePage.fxml"));
    	InterImageController controller = new InterImageController(back_end);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
    	
    }
    
    @FXML
    /**
     * Method to navigate to the intermediate page for an audio list when the button is pressed.
     * @param event
     * @throws IOException
     */
    void goToAudioIntermediatePage(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(intermediateScreensGUI.InterAudioController.class.getResource("IntermediateAudioPage.fxml"));
    	InterAudioController controller = new InterAudioController(back_end);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
    	
    }
    
    @FXML
    /**
     * Method to navigate to the intermediate page for a video list when the button is pressed.
     * @param event
     * @throws IOException
     */
    void goToVideoIntermediatePage(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(intermediateScreensGUI.InterVideoController.class.getResource("IntermediateVideoPage.fxml"));
    	InterVideoController controller = new InterVideoController(back_end);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
    	
    }
    

    @FXML
    void loadOnlineLists(ActionEvent event) {
    	
    	
    	

    }
	
    /**
     * Method to display another .fxml file within the current screen.
     * @param new_pane
     */
    public void showInSelf(BorderPane new_pane) {
    	
    	new_pane.prefWidthProperty().bind(root.widthProperty());
    	new_pane.prefHeightProperty().bind(root.heightProperty());
    	new_pane.minWidthProperty().bind(root.minWidthProperty());
    	new_pane.minHeightProperty().bind(root.minHeightProperty());
    	new_pane.maxWidthProperty().bind(root.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(root.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	root.setCenter(new_pane);
    	
    	root.requestFocus();
	
    }
    
    private void showInCloudPane(ScrollPane new_pane) {
    	
    	new_pane.prefWidthProperty().bind(cloud_anchor_pane.widthProperty());
    	new_pane.prefHeightProperty().bind(cloud_anchor_pane.heightProperty());
    	new_pane.minWidthProperty().bind(cloud_anchor_pane.minWidthProperty());
    	new_pane.minHeightProperty().bind(cloud_anchor_pane.minHeightProperty());
    	new_pane.maxWidthProperty().bind(cloud_anchor_pane.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(cloud_anchor_pane.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	cloud_anchor_pane.getChildren().clear();
    	cloud_anchor_pane.getChildren().add(new_pane);
    	
    	//root.setCenter(new_pane);
    	
    }
    
    private void showInCloudPane(Pane new_pane) {
    	
    	new_pane.prefWidthProperty().bind(cloud_anchor_pane.widthProperty());
    	new_pane.prefHeightProperty().bind(cloud_anchor_pane.heightProperty());
    	new_pane.minWidthProperty().bind(cloud_anchor_pane.minWidthProperty());
    	new_pane.minHeightProperty().bind(cloud_anchor_pane.minHeightProperty());
    	new_pane.maxWidthProperty().bind(cloud_anchor_pane.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(cloud_anchor_pane.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	cloud_anchor_pane.getChildren().clear();
    	cloud_anchor_pane.getChildren().add(new_pane);
    	
    	//root.setCenter(new_pane);
    	
    }
    
    /**
     * Method to locate files stored within the program's workspace directory.
     */
    private void locateFiles() {
    	
    	//locate the files
    	File[] list_of_files = FileLocator.locateRootFiles();
    	
    	//create an array of JFX buttons
    	ArrayList<JFXButton> file_buttons = new ArrayList<JFXButton>();
    	
    	//loop through every file discovered
    	for(File file : list_of_files) {
    		
    		//add a button with the text being the name of the file
    		file_buttons.add(new JFXButton(file.getName().replace(file.getName().substring(file.getName().lastIndexOf(".")), "") ));
    		file_buttons.get(file_buttons.size() - 1).setMnemonicParsing(false);
    		//give the button an id
    		file_buttons.get(file_buttons.size() - 1).setId("button" + (file_buttons.size() - 1));
    		//set some padding for the button
    		file_buttons.get(file_buttons.size() - 1).setPadding(new Insets(10, 10, 10, 15));
    		
    		//give the button an action listener
    		//when a button is pressed, it opens up one of the saved files ready for comparison
    		file_buttons.get(file_buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println(file.getAbsolutePath());
					
					//reads the XML file at the given location and stores the contents in the current list
					back_end.loadXMLForComparison(file.getAbsolutePath());

					try {
						//load the comparison screen and start the tournament comparison algorithm
						FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
				    	ComparisonScreenController controller = new ComparisonScreenController(back_end);
				    	loader.setController(controller);
						BorderPane new_pane = loader.load();
						showInSelf(new_pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
    			
    		});
    		
    	}
    	
    	//add all of the buttons to the vbox in the scroll pane
    	files_pane.getChildren().addAll(file_buttons);
    	
    }
  
    @Override
	/**
	 * Initialization function for the controller. Adds the buttons to the scroll pane.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		//this.addButtonToGrid(new Button());
    	recent_scroll_pane.setFitToHeight(true);
    	recent_scroll_pane.setFitToWidth(true);
    	
    	files_pane.setPadding(new Insets(5, 0, 0, 0));
    	
    	if(back_end.getLocalAccount() != null) {
    		try {
    	    	FXMLLoader loader = new FXMLLoader(CloudFileListController.class.getResource("CloudFileList.fxml"));
    	    	CloudFileListController controller = new CloudFileListController(back_end, this);
    	    	loader.setController(controller);
    	    	ScrollPane new_pane = loader.load();
    			showInCloudPane(new_pane);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	} else {
    		try {
    	    	FXMLLoader loader = new FXMLLoader(NotLoggedInScreenController.class.getResource("NotLoggedInScreen.fxml"));
    	    	NotLoggedInScreenController controller = new NotLoggedInScreenController(back_end, null);
    	    	loader.setController(controller);
    	    	Pane new_pane = loader.load();
    			showInCloudPane(new_pane);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    	}
    	
		locateFiles();
		
	}
    
}
