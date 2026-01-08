package fr.uge.inventory;

/**
 * Represents the different types of items in the game.
 */
public enum ItemType {
  /** Weapons like swords, axes, or daggers. */
	WEAPON, 
	/** Defensive items such as shields or bucklers. */
	SHIELD, 
	/** Protective gear that increases armor or block. */
	ARMOR,
	/** Magic items like wands or magical bows. */
	MAGIC, 
	/** Keys used for unlocking doors or special interactions. */
	KEY, 
	/** Consumable items like food, drinks, or healing items. */
	CONSUMABLE, 
	/** Mana stones used for restores mana. */
	MANA,
	/** Gold items used as currency. */
	GOLD, 
	/** Cursed items that have negative effects or special interactions. */
	CURSE
}
