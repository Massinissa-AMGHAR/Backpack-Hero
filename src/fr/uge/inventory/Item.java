package fr.uge.inventory;

import java.util.Objects;


public class Item {
	private final String name; 
	private final String type; 
	private final String rarity;
	private boolean[][] shape; 
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
	public int leftPlacementX = -1; 
	public int leftPlacementY = -1; 
  
	public Item(String name, String type, String rarity, boolean[][] shape, int damage, int defense, int energyNeeded, int energyGained, int manaNeeded, int manaGained, int goldCost, int goldAmount) {
		Objects.requireNonNull(name, "name can't be null.");
		Objects.requireNonNull(type, "type can't be null.");
		Objects.requireNonNull(rarity, "rarity can't be null.");
	    if (damage < 0 || defense < 0 || energyNeeded < 0 || energyGained < 0|| manaNeeded < 0 || manaGained < 0 ) 
	    	throw new IllegalArgumentException("Invalid values for the item");
		this.name = name; 
		this.type = type; 
		this.rarity = rarity;
		this.shape = shape; 
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
	public boolean[][] shape() {return shape; }
	public int width() { return shape[0].length; }
	public int height() { return shape.length; }
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
		IO.println(amount + " gold added !");
		IO.println("You have "+ goldAmount + " gold"); 
	}
	
	public void spendGold(int amount) {
		if(!isGold()) {
			throw new IllegalArgumentException("Trying to spend gold on a no gold item"); 
		}
		goldAmount -= amount; 
		if(goldAmount < 0) goldAmount = 0; 
	}
	
	private boolean[][] transpose() {
		boolean[][] shapeTranspose = new boolean[width()][height()]; 
		for(int i = 0; i < height(); i++) {
			for(int j = 0; j < width(); j++) {
				shapeTranspose[j][i] = shape[i][j]; 
			}
		}
		return shapeTranspose; 
	}
	
	private boolean[] reverse(boolean[] line) {
		boolean[] reverse = new boolean[line.length]; 
		for(int i = 0; i < line.length; i++) {
			reverse[i] = line[line.length - 1 - i]; 
		}
		return reverse; 
	}
	
	private boolean[][] rotate() {
		boolean[][] newShape = transpose(); 
		for(int i = 0; i < newShape.length; i++) {
			newShape[i] = reverse(newShape[i]); 
		}
		return newShape;  
	}
	
	public void applyRotation() {
		this.shape = rotate(); 
	}

	public Item copy() {
		boolean[][] newShape = new boolean[shape.length][]; 
		for(int i = 0; i < shape.length; i++) {
			newShape[i] = new boolean[shape[i].length]; 
			for(int j = 0; j < shape[i].length; j++) {
				newShape[i][j] = shape[i][j]; 
			}
		}
		return new Item(this.name, this.type, this.rarity, newShape, this.damage, this.defense, this.energyCost, this.energyGained, this.manaCost, this.manaGained, this.goldCost, this.goldAmount);
	}
	
	private static final boolean[][] SHAPE_WOODEN_SWORD = {
			{true}, 
			{true}, 
			{true}
	}; 
	
	private static final boolean[][] SHAPE_ARROW = {
			{true, true}, 
			{true, false}, 
			{true, true}
	}; 
	
	private static final boolean[][] SHAPE_ROUGH_BUCKLER = {
			{true, true}, 
			{true, true}, 
	}; 
	
	private static final boolean[][] SHAPE_TUNIC = {
			{true, true}, 
			{true, false}, 
			{true, false}
	}; 
	
	private static final boolean[][] SHAPE_MAGIC_WAND = {
			{true, true, true}
	}; 
	
	private static final boolean[][] SHAPE_MANA_STONE = {
			{true}
	}; 
	
	public static final boolean[][] SHAPE_GOLD = {
			{true}
	}; 
	public static final Item[] ALL_ITEM = {
	  new Item("Wooden Sword", "MELEE", "COMMON", SHAPE_WOODEN_SWORD, 7, 0, 1, 0, 0, 0, 5, 0),
		new Item("Arrow", "ARROW", "COMMON", SHAPE_ARROW, 2, 0, 0, 0, 0, 0, 1, 0),
		new Item("Rough Buckler", "SHIELD", "COMMON", SHAPE_ROUGH_BUCKLER, 0, 7, 1, 0, 0, 0, 2, 0),
		new Item("Tunic", "ARMOR", "COMMON", SHAPE_TUNIC, 0, 5, 0, 0, 0, 0, 1, 0),
		new Item("Magic Wand", "MAGIC", "COMMON", SHAPE_MAGIC_WAND, 3, 0, 0, 0, 2, 0, 3, 0),
		new Item("Mana Stone", "MANA", "COMMON", SHAPE_MANA_STONE, 0, 0, 0, 0, 0, 2, 2, 0), 
	};
	
	@Override
	public String toString() {
		return this.name() + " | type : " + this.type() + " | rarity : " + this.rarity() + " | width : " + this.width() + " | height : " + this.height() + " | damage : " + this.damage() + " | defense : " + this.defense() + " | energy cost : " + this.energyCost() + " | energy gained " + this.energyGained() + " | mana cost : " + this.manaCost() + " | mana gained : "  + this.manaGained() + " | cost : " + this.goldCost() + " gold" + " | Amount : " + this.goldAmount() + " gold";  
	}
}
