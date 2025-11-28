package fr.uge.inventory;
import java.util.Random; 
import java.util.ArrayList;
import java.util.Objects; 
public class Items {
	private final ArrayList<Item> items;
	private final Random rand;
	public Items() {
		this.rand = new Random(); 
		this.items = new ArrayList<>(); 
	
	}
	
	public void randomItems(int count) {
		for(int i = 0; i < count; i++) {
      int position = rand.nextInt(Item.ALL_ITEM.length); 
      items.add(Item.ALL_ITEM[position].copy()); 
    }
	}
	
	public void addItem(Item item) {
		Objects.requireNonNull(item); 
		items.add(item); 
	}
	
	public void deleteItem(int index) {
		items.remove(index); 
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
