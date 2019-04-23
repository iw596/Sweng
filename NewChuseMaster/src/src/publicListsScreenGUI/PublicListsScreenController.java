package publicListsScreenGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import previewListScreenGUI.PreviewListController;


/**
 * Controller class for the user name search screen. Handles button interactivity and holds variable
 * for the text field, as well as reference to the root for instantiating a view within itself.
 * 
 * Date created: 18/04/2019
 * Date last edited: 18/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Harry and Luke
 *
 */
public class PublicListsScreenController implements Initializable{
	
    @FXML
    private BorderPane root;

    @FXML
    private HBox title_hbox;

    @FXML
    private Text username_title;

    @FXML
    private VBox lists_pane;
	
	private BackEndContainer back_end;
	
	private String username;
	
    /**
     * Constructor for the public lists screen controller. Passes the reference to the back
     * end into the controller.
     * 
     * @param back_end	the reference to the back end container
     */
	public PublicListsScreenController(BackEndContainer back_end, String username) {
		this.back_end = back_end;
		this.username = username;
	}

	@Override
	/**
	 * Method called when the FXML file is loaded. Gets the names of the user's public lists, 
	 * and creates buttons for each one.
	 */
	public void initialize(URL location, ResourceBundle resources){
	
		//gets public lists
		ArrayList<String> public_lists = this.back_end.getPublicLists();

		//method to create buttons for each of the public list names
		createButtons(public_lists);
		
	}
	
	/**
	 * Method to create an array of buttons with the titles of the lists on the
	 * user's account.
	 * 
	 * @param titles	the titles of the lists
	 */
	private void createButtons(ArrayList<String> titles) {
		
		//creates an array of JFX buttons
		ArrayList<JFXButton> file_buttons = new ArrayList<JFXButton>();
		
		username_title.setText(username);
		
		//loops through every list
    	for(String list: titles) {
    		
    		//add a button with the text being the name of the file
    		file_buttons.add(new JFXButton(FilenameUtils.getBaseName(list)));
    		//give the button an id
    		file_buttons.get(file_buttons.size() - 1).setId("button" + (file_buttons.size() - 1));
    		//set some padding for the button
    		file_buttons.get(file_buttons.size() - 1).setPadding(new Insets(10, 10, 10, 15));
    	
    		//give the button an action listener
    		//when a button is pressed, it opens up one of the saved files ready for comparison
    		file_buttons.get(file_buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println(list);
					System.out.println(FilenameUtils.getName(list));
					try {
						showPreviewListScreen(list);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
    			
    		});
    	}
    	
    	//adds the buttons to the root pane.
    	for(JFXButton button : file_buttons) {
    		lists_pane.getChildren().add(button);
    	}
		
	}
	
	/**
	 * Method to show the list preview screen within the current pane.
	 * @param cloud_path	the path to the list stored on the cloud
	 * @throws IOException
	 */
	private void showPreviewListScreen(String cloud_path) throws IOException {
		
    	FXMLLoader loader = new FXMLLoader(previewListScreenGUI.PreviewListController.class.getResource("PreviewList.fxml"));
    	PreviewListController controller = new PreviewListController(back_end, this.username, cloud_path);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
		
	}
	
    /**
     * Method to display another .fxml file within the current screen.
     * @param new_pane
     */
    private void showInSelf(BorderPane new_pane) {
    	
    	new_pane.prefWidthProperty().bind(root.widthProperty());
    	new_pane.prefHeightProperty().bind(root.heightProperty());
    	new_pane.minWidthProperty().bind(root.minWidthProperty());
    	new_pane.minHeightProperty().bind(root.minHeightProperty());
    	new_pane.maxWidthProperty().bind(root.maxWidthProperty());
    	new_pane.maxHeightProperty().bind(root.maxHeightProperty());
    	
    	new_pane.setManaged(true);
    	
    	root.getChildren().removeAll();
    	
    	root.setCenter(new_pane);
    	
    	root.requestFocus();
	
    }
	
}
