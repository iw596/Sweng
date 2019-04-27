package socialScreenGUI;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class UserPreviewItem extends VBox {

	private String name;
	private ArrayList<String> lists;
	
	private HBox title_box;
	private Text title;
	
	private ScrollPane list_scroll_pane;
	private VBox list_box;
	private ArrayList<JFXButton> buttons;
	
	public UserPreviewItem(String name, ArrayList<String> lists) {
		this.name = name;
		this.lists = lists;
		System.out.println(lists);
		addTitle();
		addLists();
		this.setPadding(new Insets(25, 25, 25, 25));
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(title);
		this.getChildren().add(list_scroll_pane);
		list_scroll_pane.setMinViewportHeight(74);
	}
	
	private void addTitle() {
		title_box = new HBox();
		title = new Text("@" + name);
		title.setStyle("-fx-font-size: 18;");
		title_box.getChildren().add(title);
		title_box.setAlignment(Pos.CENTER);
		title.setTextAlignment(TextAlignment.CENTER);
	}
	
	private void addLists() {
		
		list_scroll_pane = new ScrollPane();
		
		list_box = new VBox();
		
		createButtons();
		list_scroll_pane.setContent(list_box);
		
		list_box.getChildren().addAll(buttons);
		
		this.prefHeightProperty().bind(buttons.get(0).heightProperty().multiply(2).add(title_box.getHeight()));
		this.minHeightProperty().bind(buttons.get(0).heightProperty().multiply(2).add(title_box.getHeight()));
		this.maxHeightProperty().bind(buttons.get(0).heightProperty().multiply(2).add(title_box.getHeight()));
		
	}
	
	private void createButtons() {
		
		buttons = new ArrayList<JFXButton>();
		
		for(String list : lists) {
			
			String[] list_parts = list.split("/");

			buttons.add(new JFXButton(list_parts[2]));
			buttons.get(buttons.size() - 1).setMnemonicParsing(false);
			buttons.get(buttons.size() - 1).setPadding(new Insets(10, 10, 10, 15));
			
			buttons.get(buttons.size() - 1).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					//System.out.println(buttons.get(buttons.size() - 1).getHeight());
					System.out.println(list);
				}
				
			});
			
		}
		
	}
	
}
