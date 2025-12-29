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
      int position = rand.nextInt(ItemRegistry.ALL_ITEMS.length); 
      items.add(ItemRegistry.create(ItemRegistry.ALL_ITEMS[position])); 
    }
	}
	public int index(Item item) {
		Objects.requireNonNull(item); 
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).equals(item)) return i; 
		}
		return -1; 
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
