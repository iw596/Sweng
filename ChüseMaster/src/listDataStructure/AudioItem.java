package listDataStructure;

public class AudioItem extends BasicItem implements ListInterface {

	private String url;
	
	public AudioItem(String url, String name) {
		super(name);
		this.url = url;
	}
	
	public String getUrl() {
		
		return this.url;
		
	}

}
