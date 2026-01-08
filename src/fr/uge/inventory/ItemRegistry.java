package fr.uge.inventory;

import fr.uge.effect.DamagePerTurnEffect;
import fr.uge.effect.PoisonArrowEffect;
import fr.uge.effect.PoisonEffect;
import fr.uge.interaction.AdjacentArmorBlockInteraction;
import fr.uge.interaction.CurseBladeInteraction;
import fr.uge.interaction.RoboBucklerInteraction;
import fr.uge.interaction.GoldCleaverInteraction;
import fr.uge.interaction.KeySwordInteraction;

public class ItemRegistry {	

	/** Wooden Sword: simple weapon, common, 3x1, 4 damage. */
	public static final ItemData WOODEN_SWORD = new ItemData("Wooden Sword", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_3x1, 4, 0, 1, 0, 0, 0, 3, 0);
	
	/** Cleaver: basic weapon, common, 1x1, 3 damage. */
	public static final ItemData CLEAVER = new ItemData("Cleaver", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 3, 0, 1, 0, 0, 0, 3, 0);
	
	/** Gold Cleaver: uncommon weapon, 1x1, 4 damage, +4 bonus damage per adjacent Cleaver. */
	public static final ItemData GOLD_CLEAVER = new ItemData("Gold Cleaver", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_1x1, 4, 0, 1, 0, 0, 0, 6, 0);
	
	/** Dagger: common weapon, 2x1, 3 damage, +2 poison to enemy. */
	public static final ItemData DAGGER = new ItemData("Dagger", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 3, 0, 1, 0, 0, 0, 4, 0);
	
	/** Tilted Sword: rare weapon, 2x2, 8 damage, +1 damage each turn. */
	public static final ItemData TILTED_SWORD = new ItemData("Tilted Sword", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_2x2, 8, 0, 2, 0, 0, 0, 10, 0);
	
	/** Heavy Blade: uncommon weapon, 3x1, 6 damage. */
	public static final ItemData HEAVY_BLADE = new ItemData("Heavy Blade", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_3x1, 6, 0, 1, 0, 0, 0, 7, 0);
	
	/** Key: common key, 1x1, no combat effect. */
	public static final ItemData KEY = new ItemData("Key", ItemType.KEY, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 0, 0, 0);

	/** Key Sword: rare weapon, 2x1, 7 base damage, +2 damage per adjacent Key. */
	public static final ItemData KEY_SWORD = new ItemData("Key Sword", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_2x1, 7, 0, 1, 0, 0, 0, 9, 0);
	
	/** Cursed Blade: legendary weapon, 3x1, 12 base damage, +2 per adjacent curse. */
	public static final ItemData CURSED_BLADE = new ItemData("Cursed Blade", ItemType.WEAPON, ItemRarity.LEGENDARY, ItemShapes.SIZE_3x1, 12, 0, 2, 0, 0, 0, 15, 0);
	
	/** Vampiric Axe: rare weapon, 3x1, 9 base damage, +3 poison. */
	public static final ItemData VAMPIRIC_AXE = new ItemData("Vampiric Axe", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_3x1, 9, 0, 2, 0, 0, 0, 12, 0);
	
	/** Arrow: common weapon, 1x3, 3 base damage, no special effect. */
	public static final ItemData ARROW = new ItemData("Arrow", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_1x3, 3, 0, 1, 0, 0, 0, 2, 0);
	
	/** Poison Arrow: uncommon weapon, 1x2, 2 base damage, +2 poison and +2 for each empty space to the right. */
	public static final ItemData POISON_ARROW = new ItemData("Poison Arrow", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_1x2, 2, 0, 1, 0, 1, 0, 5, 0);
	
	/** Rough Buckler: common shield, 2x2, 6 base block, no special effect. */
	public static final ItemData ROUGH_BUCKLER = new ItemData("Rough Buckler", ItemType.SHIELD, ItemRarity.COMMON, ItemShapes.SIZE_2x2, 0, 6, 1, 0, 0, 0, 4, 0); 
	
	/** Knight Shield: common shield, 2x1, 7 base block, +1 block per adjacent armor. */
	public static final ItemData KNIGHT_SHIELD = new ItemData("Knight Shield", ItemType.SHIELD, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 0, 7, 1, 0, 0, 0, 5, 0);
	
	/** Robo Buckler: rare shield, 1x1, 6 base block, +1 block for each weapon on the same line. */
	public static final ItemData ROBO_BUCKLER = new ItemData("Robo Buckler", ItemType.SHIELD, ItemRarity.RARE, ItemShapes.SIZE_1x1, 0, 6, 1, 0, 0, 0, 8, 0);
	
	/** Iron Helmet: common armor, 1x1, 3 base protection, +1 per adjacent armor. */
	public static final ItemData IRON_HELMET = new ItemData("Iron Helmet", ItemType.ARMOR, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 3, 1, 0, 0, 0, 3, 0);
	
	/** Knight Armor: uncommon armor, 2x2, 6 base protection, +2 per adjacent armor. */
	public static final ItemData TUNIC = new ItemData("Tunic", ItemType.ARMOR, ItemRarity.COMMON, ItemShapes.SIZE_3x2, 0, 5, 1, 0, 0, 0, 4, 0); 
	
	/** Knight Armor: uncommon armor, 2x2, 6 base protection, +2 per adjacent armor. */
	public static final ItemData KNIGHT_ARMOR = new ItemData("Knight Armor", ItemType.ARMOR, ItemRarity.UNCOMMON, ItemShapes.SIZE_2x2, 0, 6, 1, 0, 0, 0, 6, 0);
	
	/** Magic Wand: common magic item, 2x1, 5 base damage, no special effect. */
	public static final ItemData MAGIC_WAND = new ItemData("Magic Wand", ItemType.MAGIC, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 5, 0, 1, 0, 1, 0, 4, 0); 
	
	/** Manastone Bow: uncommon magic item, 3x1, 7 base damage, no special effect. */
	public static final ItemData MANASTONE_BOW = new ItemData("Manastone Bow", ItemType.MAGIC, ItemRarity.UNCOMMON, ItemShapes.SIZE_3x1, 7, 0, 1, 0, 1, 0, 7, 0);
	
	/** Electric Wand: common magic item, 2x1, 6 base damage, no special effect. */
	public static final ItemData ELECTRIC_WAND = new ItemData("Electric Wand", ItemType.MAGIC, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 6, 0, 0, 0, 1, 0, 5, 0);
	
	/** Dark Wand: rare magic item, 2x2, 11 base damage, no special effect. */
	public static final ItemData DARK_WAND = new ItemData("Dark Wand", ItemType.MAGIC, ItemRarity.RARE, ItemShapes.SIZE_2x2, 11, 0, 0, 0, 1, 0, 9, 0);
	
	/** Robin Wand: rare magic item, 2x2, 9 base damage, no special effect. */
	public static final ItemData ROBIN_WAND = new ItemData("Robin Wand", ItemType.MAGIC, ItemRarity.RARE, ItemShapes.SIZE_2x2, 9, 0, 0, 0, 1, 0, 6, 0);
	
	/** Mana Stone: common mana item, 1x1, restores 1 mana, 2 value, no other effect. */
	public static final ItemData MANA_STONE = new ItemData("Mana Stone", ItemType.MANA, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 1, 2, 0);  
	
	/** Gold: common item, 1x1, used as currency, no effect. */
	public static final ItemData GOLD = new ItemData("Gold", ItemType.GOLD, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 0, 0, 0); 
	
	/** Curse: rare curse item, special shape, no effect by itself. */
	public static final ItemData CURSE = new ItemData("Curse", ItemType.CURSE, ItemRarity.RARE, ItemShapes.CURSE, 0, 0, 0, 0, 0, 0, 0, 0); 
	
	/** Curse Sap: rare curse item, 1x1, no effect by itself. */
	public static final ItemData CURSE_SAP = new ItemData("Curse Sap", ItemType.CURSE, ItemRarity.RARE, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 0, 0, 0); 
	
	/** Meal: common consumable, 1x2, restores 2 energy. */
	public static final ItemData MEAL = new ItemData("Meal", ItemType.CONSUMABLE, ItemRarity.COMMON, ItemShapes.SIZE_1x2, 0, 0, 0, 2, 0, 0, 0, 0);
	
	/** Cool Drink: common consumable, 1x1, restores 1 energy. */
	public static final ItemData COOL_DRINK = new ItemData("Cool Drink", ItemType.CONSUMABLE, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 1, 0, 0, 0, 0);
	
	/** Steak: common consumable, 1x2, restores 5 HP. */
	public static final ItemData STEAK = new ItemData("Steak", ItemType.CONSUMABLE, ItemRarity.COMMON, ItemShapes.SIZE_1x2, 0, 0, 0, 0, 0, 0, 0, 5);
		
	public static final ItemData[] ALL_ITEMS = {
			WOODEN_SWORD,
			CLEAVER,
			GOLD_CLEAVER,
			DAGGER,
			TILTED_SWORD,
			HEAVY_BLADE,
			KEY_SWORD,
			CURSED_BLADE,
			VAMPIRIC_AXE,
			ARROW, 
			POISON_ARROW,
			ROUGH_BUCKLER,
			KNIGHT_SHIELD,
			ROBO_BUCKLER,
			IRON_HELMET,
			TUNIC, 
			KNIGHT_ARMOR,
			MAGIC_WAND,
			MANASTONE_BOW,
			ELECTRIC_WAND,
			DARK_WAND,
			ROBIN_WAND,
			MANA_STONE,
			KEY,
			MEAL,
			COOL_DRINK,
			STEAK
	}; 
	
	private static void addEffects(Item item) {
		switch(item.name()) {
		case "Dagger" -> item.addEffect(new PoisonEffect(2));
		case "Tilted Sword" -> item.addEffect(new DamagePerTurnEffect(1));
		case "Poison Arrow" -> item.addEffect(new PoisonArrowEffect());
		case "Vampiric Axe" -> item.addEffect(new PoisonEffect(3));
		}
	}
	
	private static void addInteractions(Item item) {
		switch(item.name()) {
		case "Gold Cleaver" -> item.addInteraction(new GoldCleaverInteraction());
		case "Key Sword" -> item.addInteraction(new KeySwordInteraction());
		case "Knight Shield", "Iron Helmet", "Tunic" -> item.addInteraction(new AdjacentArmorBlockInteraction(1));
		case "Knight Armor" -> item.addInteraction(new AdjacentArmorBlockInteraction(2));
		case "Robo Buckler" -> item.addInteraction(new RoboBucklerInteraction());
		case "Cursed Blade" -> item.addInteraction(new CurseBladeInteraction());
		}
	}
	
	/**
	 * Creates a new Item from the given ItemData.
	 * Automatically adds any effects and interactions defined for the item.
	 *
	 * @param data the item data defining type, stats, and shape
	 * @return a new Item instance
	 */
	public static Item create(ItemData data) {
		var item = new Item(
				data.name(), 
				data.type().name(), 
				data.rarity().name(), 
				data.shape(), 
				data.damage(), 
				data.defense(), 
				data.energyCost(), 
				data.energyGained(), 
				data.manaCost(), 
				data.manaGained(), 
				data.hpGained(),
				data.goldCost(), 
				0
		);
		addEffects(item);
		addInteractions(item);
		return item;
	}
	
	/**
	 * Creates a new Item from the given ItemData with a specific amount of gold.
	 *
	 * @param data the item data defining type, stats, and shape
	 * @param goldAmount the amount of gold this item holds
	 * @return a new Item instance
	 */
	public static Item create(ItemData data, int goldAmount) {
		return new Item(
				data.name(), 
				data.type().name(), 
				data.rarity().name(), 
				data.shape(), 
				data.damage(), 
				data.defense(), 
				data.energyCost(), 
				data.energyGained(), 
				data.manaCost(), 
				data.manaGained(), 
				data.hpGained(),
				data.goldCost(), 
				goldAmount
		); 		
	}
}
