package list;

// standered text item for list
public class Item {
	
	// text value
	private String name;
	
	// constructor
	public Item(String name){
		this.setName(name);
	}
	
	// get text value for the item
	public String getName() {
		return name;
	}
	
	// set text value for the item
	public void setName(String name) {
		this.name = name;
	}
	
	//
	public void displayItem() {
		System.out.println(getName());
	}
	
}