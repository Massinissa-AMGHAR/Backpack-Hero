package fr.uge.inventory;

import java.util.Objects;

/**
 * Saves immutable data for an item.
 *
 * @param name the item's name
 * @param type the item's type
 * @param rarity the item's rarity
 * @param shape the item's shape grid
 * @param damage damage value
 * @param defense defense value
 * @param energyCost energy required
 * @param energyGained energy gained
 * @param manaCost mana required
 * @param manaGained mana gained
 * @param goldCost cost in gold
 * @param hpGained HP gained
 */
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
