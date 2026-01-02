package fr.uge.inventory;

import java.util.Objects;

public record ItemData(
		String name,
		ItemType type, 
		ItemRarity rarity, 
		boolean[][] shape,
		int damage, 
		int defense, 
		int energyCost, 
		int energyGained, 
		int manaCost, 
		int manaGained, 
		int goldCost,
		int hpGained) {
	
	public ItemData{
		Objects.requireNonNull(name); 
		Objects.requireNonNull(type); 
		Objects.requireNonNull(rarity); 
		Objects.requireNonNull(shape); 
	}

}
