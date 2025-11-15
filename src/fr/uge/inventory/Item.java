package fr.uge.inventory;

import java.util.Objects;
import java.util.Random;

public class Item {
	private final String name; 
	private final String type; 
	private final String rarity;
	private final int width;
	private final int height;
	private final int damage;
	private final int defense;
	private final int energyCost;
	private final int goldCost; 
	private final int energyGained;
	private final int manaCost;
	private final int manaGained;
  
	public Item(String name, String type, String rarity, int width, int height, int damage, int defense, int energyNeeded, int energyGained, int manaNeeded, int manaGained, int goldCost) {
		Objects.requireNonNull(name, "name can't be null.");
		Objects.requireNonNull(type, "type can't be null.");
		Objects.requireNonNull(rarity, "rarity can't be null.");
	    if (damage < 0 || defense < 0 || energyNeeded < 0 || energyGained < 0|| manaNeeded < 0 || manaGained < 0 || width <= 0 || height <= 0) 
	    	throw new IllegalArgumentException("Invalid values for the item");
		this.name = name; 
		this.type = type; 
		this.rarity = rarity;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.defense = defense;
		this.energyCost = energyNeeded;
		this.goldCost = goldCost; 
		this.energyGained = energyGained;
		this.manaCost = manaNeeded;
		this.manaGained = manaGained;
	}
	  
	public String name() { return name; }
	public String type() { return type; }
	public String rarity() { return rarity; }
	public int width() { return width; }
	public int height() { return height; }
	public int damage() { return damage; }
	public int defense() { return defense; }
	public int energyCost() { return energyCost; }
	public int energyGained() { return energyGained; }
	public int manaCost() { return manaCost; }
	public int manaGained() { return manaGained; }
	public int goldCost() { return goldCost; }
	
	
	public static final Item[] ALL_ITEM = {
			new Item("Wooden Sword", "MELEE", "COMMON", 1, 3, 7, 0, 1, 0, 0, 0, 5),
			new Item("Arrow", "ARROW", "COMMON", 2, 1, 2, 0, 0, 0, 0, 0, 1),
			new Item("Rough Buckler", "SHIELD", "COMMON", 2, 2, 0, 7, 1, 0, 0, 0, 2),
			new Item("Tunic", "ARMOR", "COMMON", 2, 2, 0, 5, 0, 0, 0, 0, 1),
			new Item("Magic Wand", "MAGIC", "COMMON", 1, 2, 3, 0, 0, 0, 2, 0, 3),
			new Item("Mana Stone", "MANA", "COMMON", 1, 1, 0, 0, 0, 0, 0, 2, 2)
	};
}
