package listDataStructure;

/**
 * RankingItem is a class within the listDataStructure package. This class is a wrapper
 * for a generic list item, as well as providing the item with a ranking value. 
 * 
 * Date created: 21/02/2019
 * Date last edited: 28/02/2019
 * Last edited by: Dan Jackson
 * 
 * @author Dan Jackson
 *
 */
public class RankingItem {
	
	private BasicItem item;
	protected int ranking;
	
	/**
	 * Constructor function for a ranking item. Takes the item and gives it a ranking.
	 * @param item - the item to wrap
	 * @param ranking - the ranking of the item (lower is better)
	 */
	public RankingItem(BasicItem item, int ranking) {
		this.item = item;
		this.ranking = ranking;
	}
	
	/**
	 * Method to update the ranking value of the item.
	 * @param ranking - the ranking value to add to the item
	 */
	public void addToRanking(int ranking) {
		this.ranking += ranking;
	}
	
	/**
	 * Method to get the ranking value of the item
	 * @return this.ranking - the ranking value of the item
	 */
	public int getRanking() {
		return this.ranking;
	}
	
	/**
	 * Method to check if an item is equivalent in value to another item.
	 * @param item - the item to check if it is equal to
	 * @return true/false - depending on the outcome of the equality check
	 */
	public Boolean isItemEqualTo(BasicItem item) {
		
		Boolean result = false;
		
		//finds the objects real values and compares them
		if(this.item.getObjectValue().equals(item.getObjectValue())) {
			result = true;
		}
		
		return result;
		
	}
	
	/**
	 * Method to get the generic item wrapped in this class.
	 * @return this.item - the wrapped item
	 */
	public BasicItem getWrappedItem() {
		return this.item;
	}
	
}
