package previewListScreenGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import comparisonScreenGUI.ComparisonScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * PreviewListController is a controller class for the list preview GUI screen. This displays a preview of the
 * items stored in a list and is used when viewing a public list downloaded from the cloud.
 * 
 * Date created: 18/04/2019
 * Date last edited: 15/03/2019
 * Last edited by: Dan Jackson
 * 
 * @author Harry and Luke
 *
 */
public class PreviewListController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Text username_label;

    @FXML
    private Text list_title;

    @FXML
    private ScrollPane scroll_pane;

    @FXML
    private VBox items_pane;

    @FXML
    private JFXButton start_button;
    
    @FXML
    private Text failure_comment;

    private BackEndContainer back_end;
    
    private String list_name;
   
    private String username;
    
    private String cloud_path;
    
    /**
     * Constructor for the PreviewListController, takes reference to the back end, the lists username, 
     * the path to it on the cloud and stores all of these variables. It also determines the name of the
     * list based on the cloud path and stores this as well.
     * 
     * @param back_end		reference to the back end container
     * @param username		the user name of the user the list belongs to
     * @param cloud_path	the path to the list XML file on the cloud
     */
    public PreviewListController(BackEndContainer back_end, String username, String cloud_path) {
    	this.back_end = back_end;
    	this.username = username;
    	this.cloud_path = cloud_path;
    	this.list_name = FilenameUtils.getName(cloud_path);
    }
    
    
    @FXML
    void startComparison(ActionEvent event) throws IOException {
    	
		FXMLLoader loader = new FXMLLoader(comparisonScreenGUI.ComparisonScreenController.class.getResource("ComparisonScreen.fxml"));
		
		ComparisonScreenController controller = new ComparisonScreenController(back_end);
		
		loader.setController(controller);

		BorderPane new_pane = loader.load();
		
		showInSelf(new_pane);
    	
    }
	
	@Override
	/**
	 * Method called upon the loading of the FXML file. Updates text label parameters and downloads the list to preview,
	 * then displays all the items contained within the list.
	 */
	public void initialize(URL url, ResourceBundle rb){
		// Forces the V-box inside the scroll pane to fit the size of the scroll pane. 
		scroll_pane.setFitToHeight(true);
		scroll_pane.setFitToWidth(true);
		
		username_label.setText(username);
		list_title.setText(list_name);
		
		try {
			
			System.out.println(FilenameUtils.getPath(cloud_path));
			
			if(!back_end.downloadList(FilenameUtils.getPath(cloud_path))) {
				failure_comment.setText("Failed to download list.");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<back_end.getCurrentListSize();i++) {
			PublicListItem item = new PublicListItem(back_end.getCurrentList().get(i).getTitle(),"");
			items_pane.getChildren().add(item.getHBox());
		
		}
		
		root.requestFocus();
		
	}
	
    /**
     * Method to display another .fxml file within the current screen.
     * @param new_pane
     */
    private void showInSelf(Pane new_pane) {
    	
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