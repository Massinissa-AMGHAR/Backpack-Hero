package fr.uge.inventory;

import java.util.Objects;

import fr.uge.effect.ItemEffect;
import fr.uge.interaction.ItemInteraction;

import java.util.ArrayList;
import java.util.List;

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
  private final List<ItemEffect> effects = new ArrayList<>();
  private final List<ItemInteraction> interactions = new ArrayList<>();
	
	public Item(String name, String type, String rarity, boolean[][] shape, int damage, int defense, int energyNeeded, int energyGained, int manaNeeded, int manaGained, int goldCost, int goldAmount) {
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
				goldCost
		); 
		this.shape = shape; 
	  this.id = counter++;
		this.goldAmount = goldAmount; 
		this.rotation = new ItemRotation(this); 
	}
	
	public void addEffect(ItemEffect effect) {
		Objects.requireNonNull(effect);
		effects.add(effect);
	}
	
	public void addInteraction(ItemInteraction interaction) {
		Objects.requireNonNull(interaction);
		interactions.add(interaction);
	}
	
	public List<ItemEffect> effects() { return effects; }
	public List<ItemInteraction> interacions() { return interactions; }
	  
	public String name() { return data.name(); }
	public ItemType type() { return data.type(); }
	public ItemRarity rarity() { return data.rarity(); }
	public boolean[][] shape() {return shape; }
	public int width() { return shape[0].length; }
	public int height() { return shape.length; }
	public int damage() { return data.damage(); }
	public int defense() { return data.defense(); }
	public int energyCost() { return data.energyCost(); }
	public int energyGained() { return data.energyGained(); }
	public int manaCost() { return data.manaCost(); }
	public int manaGained() { return data.manaGained(); }
	public int goldCost() { return data.goldCost(); }
	public int id() { return id; }
	public int goldAmount() {return goldAmount;}
	public int leftPlacementX() {return leftPlacementX; }
	public int leftPlacementY() {return leftPlacementY; }
	
	public void VisualRotation() {
		graphicRotation += 90; 
		graphicRotation %= 360; 
	}
	
	public int graphicRotation() {
		return graphicRotation; 
	}
	public void setLeftPlacement(int x, int y) {
		this.leftPlacementX = x; 
		this.leftPlacementY = y; 
	}
	
	public boolean isGold() {return data.type() == ItemType.GOLD; }
	public boolean isMana() { return data.type() == ItemType.MANA; }
	
	public void addGold(int amount) {
		if(!isGold()) {
			return; 
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
	
	public void applyRotation() {
		this.shape = rotation.rotate(); 
	}

	public Item copy() {
		boolean[][] newShape = new boolean[shape.length][]; 
		for(int i = 0; i < shape.length; i++) {
			newShape[i] = new boolean[shape[i].length]; 
			for(int j = 0; j < shape[i].length; j++) {
				newShape[i][j] = shape[i][j]; 
			}
		}
		return new Item(data.name(), data.type().name(), data.rarity().name(), newShape, data.damage(), data.defense(), data.energyCost(), data.energyGained(), data.manaCost(), data.manaGained(), data.goldCost(), goldAmount);
	}
	
	public int imageIndex() {
		return switch(this.name()) {
		 case "Wooden Sword" -> 0; 
		 case "Arrow" -> 1; 
		 case "Rough Buckler" ->2; 
		 case "Tunic" -> 3; 
		 case "Magic Wand" -> 4; 
		 case "Mana Stone" -> 5;
		 case "Gold" -> 6; 
		 default -> 0; 
		};
	}
	
	@Override
	public String toString() {
		return this.name() + " | type : " + this.type() + " | rarity : " + this.rarity() + " | width : " + this.width() + " | height : " + this.height() + " | damage : " + this.damage() + " | defense : " + this.defense() + " | energy cost : " + this.energyCost() + " | energy gained " + this.energyGained() + " | mana cost : " + this.manaCost() + " | mana gained : "  + this.manaGained() + " | cost : " + this.goldCost() + " gold" + " | Amount : " + this.goldAmount() + " gold";  
	}
}
