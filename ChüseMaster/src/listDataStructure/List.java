package listDataStructure;

import java.util.ArrayList;

public class List {
	
	ArrayList<ListItem> list = new ArrayList<ListItem>();
	
	private String list_type;
	private String list_name;
	
	public List(String list_type, String list_name) {
		this.list_type = list_type;
		this.list_name = list_name;
	}
	
	public void addItem(ListItem item) {
		this.list.add(item);
	}
	
	public void addItemArray(ArrayList<ListItem> list_array) {
		
		int i;
		
		for(i = 0; i < list_array.size(); i++) {
			this.list.add(list_array.get(i));
		}
			
	}
	
	public String getType() {
		return list_type;
	}

	public String getName() {
		return list_name;
	}
	
	// Test method to return item at given index
	public String getIndex(int index){
		return list.get(index).getName();
	}

}
