package list;

public class ItemImage extends Item{
		
	private String image;
	
	public ItemImage(String name, String image){
		super(name);
		setImage(image);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public void displayItem() {
		System.out.println(this.getName() + "   " + this.getImage());
	}
}	

