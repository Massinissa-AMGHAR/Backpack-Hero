package fr.uge.inventory;
import java.util.Random; 
import java.util.ArrayList;
import java.util.Objects; 

/**
 * Manages a collection of items.
 */
public class Items {
	private final ArrayList<Item> items;
	private final Random rand;
	
  /**
   * Creates an empty item collection with a random generator.
   */
	public Items() {
		this.rand = new Random(); 
		this.items = new ArrayList<>(); 
	}
	
	/**
	 * Generates a random item rarity based on weighted chances.
	 *
	 * @return a random ItemRarity
	 */
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
	
	/**
	 * Selects a random item with the given rarity from the registry.
	 *
	 * @param rarity the desired item rarity
	 * @return a new Item of that rarity, or null if none found
	 */
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
	
	/**
	 * Adds a number of random items to the collection.
	 *
	 * @param count the number of items to generate
	 */
	public void randomItems(int count) {
		for(int i = 0; i < count; i++) {
			var rarity = randomRarity();
			var item = randomItemByRarity(rarity);
			if (item != null) {
				items.add(item);
			}
    }
	}
	
	/**
	 * Returns the index of the given item in the collection.
	 *
	 * @param item the item to find
	 * @return the index of the item, or -1 if not found
	 */
	public int index(Item item) {
		Objects.requireNonNull(item); 
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).equals(item)) return i; 
		}
		return -1; 
	}
	
	/**
	 * Adds an item to the collection.
	 *
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		Objects.requireNonNull(item); 
		items.add(item); 
	}
	
	/**
	 * Deletes the item at the specified index.
	 *
	 * @param index the index of the item to remove
	 */
	public void deleteItem(int index) {
		items.remove(index); 
	}
	
	/**
	 * Returns the list of all items in the collection.
	 *
	 * @return the list of items
	 */
	public ArrayList<Item> getItems(){
		return items; 
	}
}
