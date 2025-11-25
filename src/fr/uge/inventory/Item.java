package fr.uge.inventory;

import java.util.Objects;
import java.util.Random;

public class Item {
	private final String name; 
	private final String type; 
	private final String rarity;
	private int width;
	private int height;
	private final int damage;
	private final int defense;
	private final int energyCost;
	private final int goldCost; 
	private final int energyGained;
	private final int manaCost;
	private final int manaGained;
	private final int id;
	private static int counter = 0;
	private int goldAmount = 0; 
  
	public Item(String name, String type, String rarity, int width, int height, int damage, int defense, int energyNeeded, int energyGained, int manaNeeded, int manaGained, int goldCost, int goldAmount) {
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
		this.id = counter++;
		this.goldAmount = goldAmount; 
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
	public int id() { return id; }
	public int goldAmount() {return goldAmount;}
	
	public boolean isGold() {return type.equals("GOLD"); }
	
	public void addGold(int amount) {
		if(!isGold()) {
			throw new IllegalArgumentException("Trying to add gold on a no gold item "); 
		}
		goldAmount += amount; 
		IO.println(amount + "gold added !");
		IO.println("You have "+ goldAmount); 
	}
	
	public void spendGold(int amount) {
		if(!isGold()) {
			throw new IllegalArgumentException("Trying to spend gold on a no gold item"); 
		}
		goldAmount -= amount; 
		if(goldAmount < 0) goldAmount = 0; 
	}
	public void rotate() {
	  var pastWidth = width; 
	  width = height; 
	  height = pastWidth; 
	}
	
	public Item copy() {
		return new Item(this.name, this.type, this.rarity, this.width, this.height, this.damage, this.defense, this.energyCost, this.energyGained, this.manaCost, this.manaGained, this.goldCost, this.goldAmount);
	}
	
	public static final Item[] ALL_ITEM = {
	  new Item("Wooden Sword", "MELEE", "COMMON", 1, 3, 7, 0, 1, 0, 0, 0, 5, 0),
		new Item("Arrow", "ARROW", "COMMON", 2, 1, 2, 0, 0, 0, 0, 0, 1, 0),
		new Item("Rough Buckler", "SHIELD", "COMMON", 2, 2, 0, 7, 1, 0, 0, 0, 2, 0),
		new Item("Tunic", "ARMOR", "COMMON", 2, 2, 0, 5, 0, 0, 0, 0, 1, 0),
		new Item("Magic Wand", "MAGIC", "COMMON", 1, 2, 3, 0, 0, 0, 2, 0, 3, 0),
		new Item("Mana Stone", "MANA", "COMMON", 1, 1, 0, 0, 0, 0, 0, 2, 2, 0), 
	};
	
	@Override
	public String toString() {
		return this.name() + " | type : " + this.type() + " | rarity : " + this.rarity() + " | width : " + this.width() + " | height : " + this.height() + " | damage : " + this.damage() + " | defense : " + this.defense() + " | energy cost : " + this.energyCost() + " | energy gained" + this.energyGained() + " | mana cost : " + this.manaCost() + " | mana gained : "  + this.manaGained() + " | cost : " + this.goldCost() + " gold";  
	}
}
