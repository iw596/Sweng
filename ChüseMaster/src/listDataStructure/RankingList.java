package listDataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RankingList{

	ArrayList<RankingItem> items;
	
	public RankingList() {
		this.items = new ArrayList<RankingItem>();
	}
	
	public RankingList(ArrayList<ChuseList> unranked_results) {
		
		this.items = new ArrayList<RankingItem>();
		
		addRankedResults(unranked_results);
		
		this.sort();
		
	}
	
	public RankingItem get(int index) {
		return items.get(index);
	}
	
	public void addRankedResults(ArrayList<ChuseList> ranked_results) {
		
		int i;
		int j;
		
		for(i=0; i < ranked_results.size(); i++) {
			
			for(j=0; j < ranked_results.get(i).getSize(); j++) {
				
				addItem(new RankingItem(ranked_results.get(i).get(j), ranked_results.size() - i));
				
			}
			
		}
		
		this.sort();
		
	}
	
	public void addItem(RankingItem item) {
		
		int index = searchForItem(item);
		
		if(index == -1) {
			items.add(item);
		} else {
			combineItems(item, index);
		}

		this.sort();
		
	}
	
	private int searchForItem(RankingItem item) {
		
		Boolean isFound = false;
		int index = -1;
		
		int i;
		
		for(i=0; i < items.size(); i++) {
			
			isFound = items.get(i).isItemEqualTo(item.getWrappedItem());
			
			if(isFound) {
				index = i;
				break;
			}
		}

		return index;
		
	}
	
	private void combineItems(RankingItem item, int item_index) {

		items.get(item_index).addToRanking(item.getRanking());
		
	}
	
	private void sort() {
		Collections.sort(items, new Comparator<RankingItem>() {
			@Override
			public int compare(RankingItem item_1, RankingItem item_2) {
				return item_1.getRanking() - item_2.getRanking();
			}
		});
/*		Collections.reverse(items);*/
	}
	
	public void print() {
		System.out.println("*****************");
		for(RankingItem item : items) {
			item.print();
		}
		System.out.println("*****************");
	}
	
}
