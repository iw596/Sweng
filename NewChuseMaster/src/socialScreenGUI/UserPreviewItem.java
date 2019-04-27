package socialScreenGUI;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * UserPreviewItem class. A custom GUI object that extends a vbox. Contains a title and a 
 * scroll pane containing a set of buttons, each representing a list stored publicly on the 
 * user's account.
 * 
 * Date created: 27/04/2019
 * Date last edited: 27/04/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class UserPreviewItem extends VBox {

	private String name;
	private ArrayList<String> lists;
	
	private HBox title_box;
	private Text title;
	
	private ScrollPane list_scroll_pane;
	private VBox list_box;
	private ArrayList<JFXButton> buttons;
	private BackEndContainer back_end;
	private SocialScreenController parent;
	
	/**
	 * Constructor method for the User Preview Item. Creates the object and sets all variable values.
	 * 
	 * @param name	the username of the user
	 * @param lists	the public lists stored on the user's account
	 * @param back_end	reference to the back end code
	 * @param parent	the parent controller
	 */
	public UserPreviewItem(String name, ArrayList<String> lists, BackEndContainer back_end, SocialScreenController parent) {
		this.name = name;
		this.lists = lists;
		this.back_end = back_end;
		this.parent = parent;
		System.out.println(lists);
		//adds title to the object
		addTitle();
		//adds list buttons to the object
		addLists();
		//sets padding on the object
		this.setPadding(new Insets(25, 25, 25, 25));
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(title);
		this.getChildren().add(list_scroll_pane);
		//sets the minimum size of the scroll pane of the object
		list_scroll_pane.setMinViewportHeight(74);
	}
	
	/**
	 * Method to add the title to the object.
	 */
	private void addTitle() {
		title_box = new HBox();
		title = new Text("@" + name);
		title.setStyle("-fx-font-size: 18;");
		title_box.getChildren().add(title);
		title_box.setAlignment(Pos.CENTER);
		title.setTextAlignment(TextAlignment.CENTER);
	}
	
	/**
	 * Method to add the the list buttons to the object.
	 */
	private void addLists() {
		
		list_scroll_pane = new ScrollPane();
		
		list_box = new VBox();
		
		createButtons();
		list_scroll_pane.setContent(list_box);
		
		list_box.getChildren().addAll(buttons);
		
		//binds the size properties of the object to the button heights * 2 and the title box height
		this.prefHeightProperty().bind(buttons.get(0).heightProperty().multiply(2).add(title_box.getHeight()));
		this.minHeightProperty().bind(buttons.get(0).heightProperty().multiply(2).add(title_box.getHeight()));
		this.maxHeightProperty().bind(buttons.get(0).heightProperty().multiply(2).add(title_box.getHeight()));
		
	}
	
	/**
	 * Method create the buttons for each of the lists.
	 */
	private void createButtons() {
		
		buttons = new ArrayList<JFXButton>();
		
		//loops through every item in the array
		for(String list : lists) {
			
			//splits the cloud path at every "/"
			String[] list_parts = list.split("/");

			//adds a button with the title of the list and sets it's style
			buttons.add(new JFXButton(list_parts[2]));
			buttons.get(buttons.size() - 1).setMnemonicParsing(false);
			buttons.get(buttons.size() - 1).setPadding(new Insets(10, 10, 10, 15));
			
			//adds a handler method to the button
			buttons.get(buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				/**
				 * When the button is clicked, download the corresponding list and show the preview screen of the list.
				 */
				public void handle(ActionEvent event) {
					
					try {
						back_end.downloadList(list);
						parent.showPreviewListScreen(name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}
	
}
