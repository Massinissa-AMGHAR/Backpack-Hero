package fr.uge.inventory;

import java.util.Objects;

import fr.uge.effect.ItemEffect;
import fr.uge.interaction.ItemInteraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an item with stats, shape, effects, and interactions.
 */
public class Item {
	private final ItemData data; 
	private boolean[][] shape; 
	private final int id;
	private static int counter = 0;
	private int goldAmount = 0; 
	private int leftPlacementX = 0; 
	private int leftPlacementY = 0; 
	private int graphicRotation = 0; 
	private final ItemRotation rotation; 
	private int bonusDamage = 0;
  private final List<ItemEffect> effects = new ArrayList<>();
  private final List<ItemInteraction> interactions = new ArrayList<>();
	
  /**
   * Creates a new item with the given properties.
   *
   * @param name the item name
   * @param type the item type as string
   * @param rarity the item rarity as string
   * @param shape the item's shape grid
   * @param damage damage value
   * @param defense defense value
   * @param energyNeeded energy required
   * @param energyGained energy gained
   * @param manaNeeded mana required
   * @param manaGained mana gained
   * @param hpGained HP gained
   * @param goldCost cost in gold
   * @param goldAmount amount of gold
   */
	public Item(String name, String type, String rarity, boolean[][] shape, int damage, int defense, int energyNeeded, int energyGained, int manaNeeded, int manaGained, int hpGained, int goldCost, int goldAmount) {
		Objects.requireNonNull(name, "name can't be null.");
		Objects.requireNonNull(type, "type can't be null.");
		Objects.requireNonNull(rarity, "rarity can't be null.");
	  if (damage < 0 || defense < 0 || energyNeeded < 0 || energyGained < 0|| manaNeeded < 0 || manaGained < 0 ) throw new IllegalArgumentException("Invalid values for the item");
		
	  this.data = new ItemData(
				name, 
				ItemType.valueOf(type), 
				ItemRarity.valueOf(rarity), 
				shape, 
				damage, 
				defense, 
				energyNeeded, 
				energyGained, 
				manaNeeded, 
				manaGained,
				goldCost,
				hpGained
		); 
		this.shape = shape; 
	  this.id = counter++;
		this.goldAmount = goldAmount; 
		this.rotation = new ItemRotation(this); 
	}
	
	/**
	 * Adds an effect to this item.
	 *
	 * @param effect the effect to add
	 */
	public void addEffect(ItemEffect effect) {
		Objects.requireNonNull(effect);
		effects.add(effect);
	}
	
	/**
	 * Adds an interaction to this item.
	 *
	 * @param interaction the interaction to add
	 */
	public void addInteraction(ItemInteraction interaction) {
		Objects.requireNonNull(interaction);
		interactions.add(interaction);
	}
	
	public List<ItemEffect> effects() { return effects; }
	public List<ItemInteraction> interactions() { return interactions; }
	  
	public String name() { return data.name(); }
	public ItemType type() { return data.type(); }
	public ItemRarity rarity() { return data.rarity(); }
	public boolean[][] shape() {return shape; }
	public int width() { return shape[0].length; }
	public int height() { return shape.length; }
	public int damage() { return data.damage() + bonusDamage; }
	public int defense() { return data.defense(); }
	public int energyCost() { return data.energyCost(); }
	public int energyGained() { return data.energyGained(); }
	public int manaCost() { return data.manaCost(); }
	public int manaGained() { return data.manaGained(); }
	public int hpGained() { return data.hpGained(); }
	public int goldCost() { return data.goldCost(); }
	public int id() { return id; }
	public int goldAmount() {return goldAmount;}
	public int leftPlacementX() {return leftPlacementX; }
	public int leftPlacementY() {return leftPlacementY; }
	
	/**
	 * Adds bonus damage to the item.
	 *
	 * @param amount the bonus damage to add
	 */
	public void addBonusDamage(int amount) {
		bonusDamage += amount;
	}
	

/**
 * Resets the bonus damage to zero.
 */
	public void resetBonusDamage() {
		bonusDamage = 0;
	}
	
	/**
	 * Rotates the item's graphic by 90 degrees.
	 */
	public void VisualRotation() {
		graphicRotation += 90; 
		graphicRotation %= 360; 
	}
	
	/**
	 * Returns the current graphic rotation of the item.
	 *
	 * @return rotation in degrees
	 */
	public int graphicRotation() {
		return graphicRotation; 
	}
	
	/**
	 * Sets the top-left placement of the item in the backpack.
	 *
	 * @param x column index
	 * @param y line index
	 */
	public void setLeftPlacement(int x, int y) {
		this.leftPlacementX = x; 
		this.leftPlacementY = y; 
	}
	
	/**
	 * Checks if the item is a gold item.
	 *
	 * @return true if type is GOLD, false otherwise
	 */
	public boolean isGold() {return data.type() == ItemType.GOLD; }
	
	/**
	 * Checks if the item is a mana item.
	 *
	 * @return true if type is MANA, false otherwise
	 */
	public boolean isMana() { return data.type() == ItemType.MANA; }
	
	public void addGold(int amount) {
		if(!isGold()) {
			return; 
		}
		goldAmount += amount; 
		IO.println(amount + " gold added !");
		IO.println("You have "+ goldAmount + " gold"); 
	}
	
	/**
	 * Adds gold to the item if it is a gold type.
	 *
	 * @param amount the amount of gold to add
	 */
	public void spendGold(int amount) {
		if(!isGold()) {
			throw new IllegalArgumentException("Trying to spend gold on a no gold item"); 
		}
		goldAmount -= amount; 
		if(goldAmount < 0) goldAmount = 0; 
	}
	
	/**
	 * Applies the item's rotation to its shape.
	 */
	public void applyRotation() {
		this.shape = rotation.rotate(); 
	}

	/**
	 * Creates a copy of this item with the same properties.
	 *
	 * @return a new Item object identical to this one
	 */
	public Item copy() {
		boolean[][] newShape = new boolean[shape.length][]; 
		for(int i = 0; i < shape.length; i++) {
			newShape[i] = new boolean[shape[i].length]; 
			for(int j = 0; j < shape[i].length; j++) {
				newShape[i][j] = shape[i][j]; 
			}
		}
		return new Item(data.name(), data.type().name(), data.rarity().name(), newShape, data.damage(), data.defense(), data.energyCost(), data.energyGained(), data.manaCost(), data.manaGained(), data.hpGained(), data.goldCost(), goldAmount);
	}
	
	/**
	 * Returns the image index for this item based on its name.
	 *
	 * @return the index of the item's image
	 */
	public int imageIndex() {
		return switch(this.name()) {
		 case "Wooden Sword" -> 0; 
		 case "Arrow" -> 1; 
		 case "Rough Buckler" ->2; 
		 case "Tunic" -> 3; 
		 case "Magic Wand" -> 4; 
		 case "Mana Stone" -> 5;
		 case "Gold" -> 6; 
		 case "Cleaver" -> 7;
		 case "Cursed Blade" -> 8;
		 case "Dagger" -> 9;
		 case "Gold Cleaver" -> 10;
		 case "Heavy Blade" -> 11;
		 case "Key Sword" -> 12;
		 case "Poison Arrow" -> 13;
		 case "Tilted Sword" -> 14;
		 case "Vampiric Axe" -> 15;
		 case "Robo Buckler" -> 16;
		 case "Iron Helmet" -> 17;
		 case "Knight Armor" -> 18;
		 case "Knight Shield" -> 19;
		 case "Manastone Bow" -> 20;
		 case "Electric Wand" -> 21;
		 case "Dark Wand" -> 22;
		 case "Robin Wand" -> 23;
		 case "Key" -> 24;
		 case "Curse" -> 25;
		 case "Cool Drink" -> 26;
		 case "Meal" -> 27;
		 case "Steak" -> 28;
		 case "Curse Sap" -> 29;
		 default -> 0; 
		};
	}
}
