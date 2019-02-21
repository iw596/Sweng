package listDataStructure;

public class RankingItem {
	
	private BasicItem item;
	protected int ranking;
	
	public RankingItem(BasicItem item, int ranking) {
		this.item = item;
		this.ranking = ranking;
	}
	
	public void addToRanking(int ranking) {
		this.ranking += ranking;
	}
	
	public int getRanking() {
		return this.ranking;
	}
	
	public Boolean isItemEqualTo(BasicItem item) {
		
		Boolean result = false;
		
		if(this.item.getObjectValue().equals(item.getObjectValue())) {
			result = true;
		}
		
		return result;
		
	}
	
	public BasicItem getWrappedItem() {
		return this.item;
	}
	
	public void print() {
		System.out.println("Rank points: " + this.getRanking() + "");
		item.print();
	}
	
}
