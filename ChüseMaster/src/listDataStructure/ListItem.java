package listDataStructure;

public class ListItem implements ListInterface{
	
	protected String name;
	
	public ListItem(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUrl() {
		return "";
	}
	
}
