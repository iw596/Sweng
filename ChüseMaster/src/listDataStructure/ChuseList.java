package listDataStructure;

import java.util.ArrayList;

public class ChuseList {
	
	ArrayList<BasicItem> list = new ArrayList<BasicItem>();
	
	private String list_name;
	
	public ChuseList(String list_name) {
		this.list_name = list_name;
	}
	
	public void addItem(BasicItem item) {
		this.list.add(item);
	}
	
	public void removeItem(BasicItem item) {
		this.list.remove(item);
	}
	
	public void set(int index, BasicItem item) {
		this.list.set(index, item);
	}
	
	public void addItemArray(ArrayList<BasicItem> list_array) {
		
		int i;
		
		for(i = 0; i < list_array.size(); i++) {
			this.list.add(list_array.get(i));
		}
			
	}

	public String getName() {
		return list_name;
	}
	
	public int getSize() {
		return list.size();
	}
	
	// Test method to return item at given index
	public String getTitleAtIndex(int index){
		return list.get(index).getTitle();
	}
	
	public BasicItem get(int index) {
		return list.get(index);
	}
	
	public void printList() {
		
		int i;
		
		for(i = 0; i < list.size(); i++) {
			list.get(i).print();
		}
	}

}