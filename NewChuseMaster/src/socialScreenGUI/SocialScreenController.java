package socialScreenGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import backEnd.BackEndContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class SocialScreenController implements Initializable {
	
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
    
    ArrayList<HBox> button_boxes;
    
    ArrayList<JFXButton> buttons;
    
	private BackEndContainer back_end;

    public SocialScreenController(BackEndContainer back_end) {
    	this.back_end = back_end;
    	button_boxes = new ArrayList<HBox>();
    	buttons = new ArrayList<JFXButton>();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		UserPreviewDataStructure preview = new UserPreviewDataStructure();
		
		if(preview.getNumberOfUsers() < 1) {
			return;
		}
		
		int row;
		
		discover_scroll_pane.setFitToHeight(true);
		discover_scroll_pane.setFitToWidth(true);
		
		if(preview.getNumberOfUsers() % 2 != 0) {
			preview.remove(preview.getNumberOfUsers() - 1);
		}
		
		int rows = (int) (Math.ceil(preview.getNumberOfUsers()) / 2);

//		for(int i=0; i < extra_rows; i++) {
//			discover_grid.addRow(rows + extra_rows);
//		}
		
//		left_column.setPrefWidth(root.getPrefWidth() / 2);
//		left_column.setMinWidth(root.getMinWidth() / 2);
//		left_column.setMaxWidth(root.getMaxWidth() / 2);
//		
//		right_column.setPrefWidth(root.getPrefWidth() / 2);
//		right_column.setMinWidth(root.getMinWidth() / 2);
//		right_column.setMaxWidth(root.getMaxWidth() / 2);

		right_column.prefWidthProperty().bind(root.widthProperty().divide(2));
		left_column.prefWidthProperty().bind(root.widthProperty().divide(2));

//		int position = 0;
		
		System.out.println(root.getPrefWidth());
		
		System.out.println(rows);
		
		for(row = 0; row < preview.getNumberOfUsers(); row++) {
			
//			buttons.add(new JFXButton(preview.getUserName(row)));
//			
//			button_boxes.add(new HBox(buttons.get(row)));
//			
//			button_boxes.get(row).prefHeightProperty().bind(root.heightProperty().divide(rows));
//			button_boxes.get(row).setAlignment(Pos.CENTER);
			
			UserPreviewItem box = new UserPreviewItem(preview.getUserName(row), preview.getUserLists(row));
			
			if((row + 1) % 2 == 0) {
				System.out.println("column 2");
				right_column.getChildren().add(box);
			} else {
				System.out.println("column 1");
				left_column.getChildren().add(box);
			}

		}
		
//		ArrayList<String[]> identification = new ArrayList<String[]>();
//		
//		ArrayList<String[]> related_lists;
//		
//		for(String list : lists) {
//			String[] list_name_parts = list.split("/");
//			account_ids.add(list_name_parts[0]);
//		}
//		
//		for(String id : account_ids) {
//			identification.add(new String[] {id, back_end.getAccountNameFromId(Integer.parseInt(id))});
//		}
//		
//		for(String[] linked : identification) {
//			System.out.println("[" + linked[0] + ", " + linked[1] + "]");
//		}
//		
//		related_lists = new ArrayList<String[]>();
//		
//		for(int i = 0; i < identification.size() ; i++) { //String[] linked : identification
//			 
//			for(String list : lists) {
//				
//				if(list.contains(identification.get(i)[0] + "/public/")) {
//					
//				}
//				
//			}
//			
//		}
		
		//******************************************************************************************************
		
//		int column;
//		int row;
//		
//		ArrayList<String> usernames_list = new ArrayList<String>();
//		
//		discover_scroll_pane.setFitToHeight(true);
//		discover_scroll_pane.setFitToWidth(true);
//		
//
//		usernames_list.add("Phil");
//		usernames_list.add("Will");
//		usernames_list.add("Dan");
//		usernames_list.add("Alexei");
//		usernames_list.add("Ting");
//		usernames_list.add("Aeri");
//		usernames_list.add("Luke");
////		usernames.add("Jack");
//		
//		if(usernames_list.size() % 2 != 0) {
//			usernames_list.remove(usernames_list.size() - 1);
//		}
//		
//		int rows = (int) (Math.ceil(usernames_list.size()) / 2);
//
////		for(int i=0; i < extra_rows; i++) {
////			discover_grid.addRow(rows + extra_rows);
////		}
//		
////		left_column.setPrefWidth(root.getPrefWidth() / 2);
////		left_column.setMinWidth(root.getMinWidth() / 2);
////		left_column.setMaxWidth(root.getMaxWidth() / 2);
////		
////		right_column.setPrefWidth(root.getPrefWidth() / 2);
////		right_column.setMinWidth(root.getMinWidth() / 2);
////		right_column.setMaxWidth(root.getMaxWidth() / 2);
//
//		right_column.prefWidthProperty().bind(root.widthProperty().divide(2));
//		left_column.prefWidthProperty().bind(root.widthProperty().divide(2));
//
////		int position = 0;
//		
//		System.out.println(root.getPrefWidth());
//		
//		System.out.println(rows);
//		
//		for(row = 0; row < usernames_list.size(); row++) {
//			
//			buttons.add(new JFXButton(usernames_list.get(row)));
//			
//			button_boxes.add(new HBox(buttons.get(row)));
//			
//			button_boxes.get(row).prefHeightProperty().bind(root.heightProperty().divide(rows));
//			button_boxes.get(row).setAlignment(Pos.CENTER);
//			
//			
//			if((row + 1) % 2 == 0) {
//				System.out.println("column 2");
//				right_column.getChildren().add(button_boxes.get(row));
//			} else {
//				System.out.println("column 1");
//				left_column.getChildren().add(button_boxes.get(row));
//			}
//
//		}
//		
//		System.out.println(usernames_list.size());
//		
//		
//		for(column = 0; column < columns; column++) {
//			
//			for(row = 0; row < rows; row++) {
//				
//				RowConstraints row_constraint = new RowConstraints();
//				
//				row_constraint.setVgrow(Priority.ALWAYS);
//				row_constraint.setValignment(VPos.CENTER);
//				
//				discover_grid.getRowConstraints().add(row_constraint);
//				
//				HBox button_box = new HBox(new JFXButton(usernames.get(position)));
//
//				discover_grid.add(button_box, column, row);
//				
////				discover_grid.setVgap(50);
//				
//				position++;
//				
//			}
//			
//		}
		
	}
	
    private class UserPreviewDataStructure {
    	
    	ArrayList<ArrayList<String>> user_preview;
    	
    	private UserPreviewDataStructure() {
    		
    		ArrayList<String> lists = back_end.getRandomPublicLists();
    		
    		createDataStructure(lists);
    		
    		System.out.println(user_preview);
    		
    	}
    	
    	public String getUserName(int index) {
    		
    		return user_preview.get(index).get(1);
    		
    	}
    	
    	public ArrayList<String> getUserLists(int index) {
    		
    		ArrayList<String> lists = user_preview.get(index);
    		
    		lists.remove(0);
    		lists.remove(0);
    		
    		return lists;
    		
    	}
    	
    	public int getNumberOfUsers() {
    		return user_preview.size();
    	}
    	
    	public void remove(int index) {
    		user_preview.remove(index);
    	}
    	
    	private void createDataStructure(ArrayList<String> lists) {
    		
    		LinkedHashSet<String> account_ids_set = new LinkedHashSet<String>();
    		ArrayList<String> account_ids = new ArrayList<String>();
    		user_preview = new ArrayList<ArrayList<String>>();
    		ArrayList<ArrayList<String>> identification;
    		
    		for(String list : lists) {
    			String[] list_name_parts = list.split("/");
    			account_ids_set.add(list_name_parts[0]);
    		}
    		
    		account_ids.addAll(account_ids_set);
    		
    		identification = new ArrayList<ArrayList<String>>(account_ids.size());

    		for(int i = 0; i < account_ids.size(); i++) {
    			ArrayList<String> linked_id = new ArrayList<String>(2); 
    			linked_id.add(0, account_ids.get(i));
    			linked_id.add(1, back_end.getAccountNameFromId(Integer.parseInt(account_ids.get(i))));
    			identification.add(linked_id);
    		}
    		
    		user_preview.addAll(identification);
    		
    		for(int i = 0; i < identification.size(); i++) {
    			
    			for(String list : lists) {
    				if(list.contains(user_preview.get(i).get(0))) {
    					user_preview.get(i).add(list);
    				}
    			}
    		}
    		
    	}
    	
    }
    
}
