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
	
	private ItemRarity randomRarity() {
		int roll = rand.nextInt(100) + 1;
		if (roll <= 40) {
			return ItemRarity.COMMON;
		}
		if (roll <= 70) {
			return ItemRarity.UNCOMMON;
		}
		if (roll <= 90) {
			return ItemRarity.RARE;
		}
		return ItemRarity.LEGENDARY;
	}
	
	private Item randomItemByRarity(ItemRarity rarity) {
		ArrayList<ItemData> candidates = new ArrayList<>();
		for (var data : ItemRegistry.ALL_ITEMS) {
			if (data.rarity() == rarity) {
				candidates.add(data);
			}
		}
		if (candidates.isEmpty()) {
			return null;
		}
		var index = rand.nextInt(candidates.size());
		return ItemRegistry.create(candidates.get(index));
	}
	
	public void randomItems(int count) {
		for(int i = 0; i < count; i++) {
			var rarity = randomRarity();
			var item = randomItemByRarity(rarity);
			if (item != null) {
				items.add(item);
			}
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
}
