package fr.uge.inventory;
import java.util.Random; 
import java.util.ArrayList; 
public class Items {
	private final ArrayList<Item> items;
	private final Random rand;
	public Items() {
		this.rand = new Random(); 
		this.items = new ArrayList<>(); 
		for(int i = 0; i < 3; i++) {
      int position = rand.nextInt(Item.ALL_ITEM.length); 
      items.add(Item.ALL_ITEM[position]); 
    }
	}
	
	public ArrayList<Item> getItems(){
		return items; 
	}
	
	@Override 
	public String toString() {
		var builder = new StringBuilder(); 
		for(var i : items) {
			builder.append(i.toString()).append("\n"); 
		}
		return builder.toString();
	}
}
