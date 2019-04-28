package socialScreenGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

import backEnd.BackEndContainer;
import cloudInteraction.RunnableRandomListFetcher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import multithreading.NotifyingThread;
import multithreading.ThreadTerminationListener;
import previewListScreenGUI.PreviewListController;

/**
 * Controller class for the Social Screen. Contains a collection of user preview panes with their publicly stored lists. 
 * Contains reference to the back end container.
 * 
 * Date created: 27/04/2019
 * Date last edited: 27/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class SocialScreenController implements Initializable, ThreadTerminationListener {
	
    @FXML
    private BorderPane root;
	
    @FXML
    private ScrollPane discover_scroll_pane;

    @FXML
    private HBox grid_container;

    @FXML
    private VBox left_column;
    
    @FXML
    private VBox right_column;
    
	private BackEndContainer back_end;
	
	private UserPreviewDataStructure preview_data;

	/**
	 * Constructor for the social screen controller.
	 * @param back_end
	 */
    public SocialScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
    	startListFetcherThread();
    }
    
    private void startListFetcherThread() {
    	RunnableRandomListFetcher fetcher = new RunnableRandomListFetcher(back_end);
    	fetcher.addTerminationListener(this);
    	Thread thread = new Thread(fetcher);
    	thread.start();
    }
    
	@Override
	/**
	 * Method called when the FXML file for the social screen is loaded. Initialises the preview panes
	 * and adds them to the screen.
	 */
	public void initialize(URL location, ResourceBundle resources) {

		discover_scroll_pane.setFitToHeight(true);
		discover_scroll_pane.setFitToWidth(true);

		showLoadingScreen();
		
		//binds the size properties of the columns to half the width of the window
		right_column.prefWidthProperty().bind(root.widthProperty().divide(2));
		right_column.minWidthProperty().bind(root.widthProperty().divide(2));
		right_column.maxWidthProperty().bind(root.widthProperty().divide(2));
		left_column.prefWidthProperty().bind(root.widthProperty().divide(2));
		left_column.minWidthProperty().bind(root.widthProperty().divide(2));
		left_column.maxWidthProperty().bind(root.widthProperty().divide(2));
		
		System.out.println(root.getPrefWidth());

	}
	
	/**
	 * Method to show a loading animation on the screen whilst the public lists are loaded.
	 */
	private void showLoadingScreen() {
		
		//creates the loading animation and sets its size
		ProgressIndicator loading_animation = new ProgressIndicator();
		loading_animation.setPrefSize(76, 81);
		loading_animation.setProgress(-1);
		
		//creates a container for the animation and text and sets the spacing and alignment for it
		VBox container = new VBox(loading_animation);
		container.setSpacing(24);
		container.setAlignment(Pos.CENTER);
		
		//creates loading text and sets its font size to 18
		Text loading_text = new Text("Loading...");
		loading_text.setStyle("-fx-font-size: 18");
		
		//adds the loading text to the container
		container.getChildren().add(loading_text);
		
		//sets the container as the content for the scroll pane
		discover_scroll_pane.setContent(container);
	}
	
	/**
	 * Method to create the user preview boxes with the relevant user and list
	 * data.
	 */
	private void createUserPreviews() {
		
		//loops through every user
		for(int row = 0; row < preview_data.getNumberOfUsers(); row++) {
			
			//creates a user preview item for the current username and set of lists
			UserPreviewItem box = new UserPreviewItem(preview_data.getUserName(row), preview_data.getUserLists(row), this.back_end, this);

			//if the row is even, add to right column
			if((row + 1) % 2 == 0) {
				System.out.println("column 2");
				right_column.getChildren().add(box);
			//if the row is odd, add to left column
			} else {
				System.out.println("column 1");
				left_column.getChildren().add(box);
			}

		}
	}
	
	/**
	 * Method to retrieve the user data and turn it into a usable preview data structure object.
	 * @param lists	the data to convert into the preview data structure object
	 * @return	the preview data structure object
	 */
	private UserPreviewDataStructure getUserPreviewData(ArrayList<String> lists) {
		
		UserPreviewDataStructure preview = new UserPreviewDataStructure(lists);
		
		//returns if there are no users retrieved
		if(preview.getNumberOfUsers() < 1) {
			return null;
		}
		
		//check if the number of users is even
		if(preview.getNumberOfUsers() % 2 != 0) {
			preview.remove(preview.getNumberOfUsers() - 1);
		}
		
		return preview;
		
	}
	
	@Override
	/**
	 * Method called when the thread fetching the random user accounts terminates.
	 */
	public void notifyOfThreadTermination(NotifyingThread thread) {
		// TODO Auto-generated method stub
		
		//fetches the random list data
		ArrayList<String> lists = back_end.getRandomPublicLists();
		
		//creates and stores the preview data structure from the fetched data
		preview_data = getUserPreviewData(lists);
		
		//checks that the list data and the data structure data both exist
		//and that there is more than one item in the list
		if(lists == null || preview_data == null || lists.size() == 1) {
			//if they don't then restart fetcher thread and return
			startListFetcherThread();
			return;
		}

		//runs once back on the main JavaFX application thread
		//this is due to all GUI modifications having to be run from the same thread
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				//creates the user preview GUI objects
				createUserPreviews();
				discover_scroll_pane.setContent(grid_container);
			}
			
		});
		
	}
	
	/**
	 * User preview data structure. Inner class. Stores an array list of array lists of strings, with the contained
	 * array list of strings storing the user ID, username and the user's public lists.
	 *  
	 * @author Dan
	 *
	 */
    private class UserPreviewDataStructure {
    	
    	//list of array lists of user id, username and lists
    	ArrayList<ArrayList<String>> user_preview;
    	
    	/**
    	 * Constructor for the user preview data structure.
    	 * @param 
    	 */
    	private UserPreviewDataStructure(ArrayList<String> lists) {

    		//randomly shuffles the array list of lists
    		Collections.shuffle(lists);
    		
    		//creates the data structure
    		createDataStructure(lists);
    		
    		System.out.println(user_preview);
    		
    	}
    	
    	/**
    	 * Method to get the user name of the data set at a given index.
    	 * 
    	 * @param index	the index at which to get the user name from.
    	 * @return
    	 */
    	public String getUserName(int index) {
    		
    		return user_preview.get(index).get(1);
    		
    	}
    	
    	/**
    	 * Method to get the lists of the data set at a given index.
    	 * 
    	 * @param index	the index at which to get the user name from.
    	 * @return
    	 */
    	public ArrayList<String> getUserLists(int index) {
    		
    		//gets the data set at the current index
    		ArrayList<String> lists = user_preview.get(index);
    		
    		//removes the indentification
    		lists.remove(0);
    		lists.remove(0);
    		
    		//returns the data set
    		return lists;
    		
    	}
    	
    	/**
    	 * Method to get the number of user's content stored.
    	 * 
    	 * @return
    	 */
    	public int getNumberOfUsers() {
    		return user_preview.size();
    	}
    	
    	/**
    	 * Method to remove a data set at a given index.
    	 * @param index
    	 */
    	public void remove(int index) {
    		user_preview.remove(index);
    	}
    	
    	/**
    	 * Method to create the data structure.
    	 * @param lists	the lists to build the data structure from
    	 */
    	private void createDataStructure(ArrayList<String> lists) {
    		
    		//initialises variables
    		LinkedHashSet<String> account_ids_set = new LinkedHashSet<String>();
    		ArrayList<String> account_ids = new ArrayList<String>();
    		user_preview = new ArrayList<ArrayList<String>>();
    		ArrayList<ArrayList<String>> identification;
    		
    		//loops through every list
    		for(String list : lists) {
    			//splits the cloud path to get the user ID
    			String[] list_name_parts = list.split("/");
    			account_ids_set.add(list_name_parts[0]);
    		}
    		
    		//adds the account IDs to the array list from the linked hash set
    		account_ids.addAll(account_ids_set);
    		
    		//creates a new array list the size of the number of account IDs stored
    		identification = new ArrayList<ArrayList<String>>(account_ids.size());

    		//loops through every account
    		for(int i = 0; i < account_ids.size(); i++) {
    			//creates an array list of size 2
    			ArrayList<String> linked_id = new ArrayList<String>(2); 
    			//adds the ID to the array list
    			linked_id.add(0, account_ids.get(i));
    			//adds the user name to the array list
    			linked_id.add(1, back_end.getAccountNameFromId(Integer.parseInt(account_ids.get(i))));
    			//adds the array list to the array list of array lists of strings
    			identification.add(linked_id);
    		}
    		
    		//adds all of the identification data to the main data variable
    		user_preview.addAll(identification);
    		
    		//loops through every item in the identification array list
    		for(int i = 0; i < identification.size(); i++) {
    			//loops through every cloud path in the identification list
    			for(String list : lists) {
    				//if the cloud path contains the user's id
    				//add the cloud path to the current array list in the 2d array list
    				if(list.contains(user_preview.get(i).get(0))) {
    					user_preview.get(i).add(list);
    				}
    			}
    		}
    		
    	}
    }
    
    /**
     * Method to show the preview list screen for the list within the main pane.
     */
    public void showPreviewListScreen(String username) throws IOException {
    	FXMLLoader loader = new FXMLLoader(previewListScreenGUI.PreviewListController.class.getResource("PreviewList.fxml"));
    	PreviewListController controller = new PreviewListController(back_end, username);
    	loader.setController(controller);
    	BorderPane new_pane = loader.load();
    	showInSelf(new_pane);
    }
    
    /**
     * Method to display another .fxml file within the current screen.
     * @param new_pane
     */
    void showInSelf(BorderPane new_pane) {
    	
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
    	
    	System.gc();
	
    }

}
