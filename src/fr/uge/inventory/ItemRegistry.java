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

	// RIEN
	public static final ItemData WOODEN_SWORD = new ItemData("Wooden Sword", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_3x1, 7, 0, 1, 0, 0, 0, 5, 0);
	// RIEN
	public static final ItemData CLEAVER = new ItemData("Cleaver", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 2, 0, 1, 0, 0, 0, 3, 0);
	// 4 DEGATS POUR CHAQUE CLEAVER ADJACENT
	public static final ItemData GOLD_CLEAVER = new ItemData("Gold Cleaver", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_1x1, 2, 0, 1, 0, 0, 0, 5, 0);
	// 2 DEGATS + 2 POISON A L'ENNEMI
	public static final ItemData DAGGER = new ItemData("Dagger", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 2, 0, 1, 0, 0, 0, 3, 0);
	// 7 DEGATS + 1 CHAQUE TOUR
	public static final ItemData TILTED_SWORD = new ItemData("Tilted Sword", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_2x2, 7, 0, 2, 0, 0, 0, 7, 0);
	// RIEN
	public static final ItemData HEAVY_BLADE = new ItemData("Heavy Blade", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_3x1, 5, 0, 1, 0, 0, 0, 6, 0);
	// RIEN
	public static final ItemData KEY = new ItemData("Key", ItemType.KEY, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 0, 0, 0);

	// 7 DEGATS + 2 POUR CHAQUE CLE ADJACTENTE
	public static final ItemData KEY_SWORD = new ItemData("Key Sword", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_2x1, 7, 0, 1, 0, 0, 0, 7, 0);
	// 8 + 2 POUR CHAQUE MALEDICTION ADJACENTE
	public static final ItemData CURSED_BLADE = new ItemData("Cursed Blade", ItemType.WEAPON, ItemRarity.LEGENDARY, ItemShapes.SIZE_3x1, 8, 0, 2, 0, 0, 0, 9, 0);
	// 8 + 3 POISON
	public static final ItemData VAMPIRIC_AXE = new ItemData("Vampiric Axe", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_3x1, 8, 0, 2, 0, 0, 0, 8, 0);
	// RIEN
	public static final ItemData ARROW = new ItemData("Arrow", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_1x3, 2, 0, 1, 0, 0, 0, 2, 0);
	// 2 POISON + 2 POUR CHAQUE ESPACE VIDE A DROITE
	public static final ItemData POISON_ARROW = new ItemData("Poison Arrow", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_1x2, 0, 0, 1, 0, 1, 0, 2, 0);
	// RIEN
	public static final ItemData ROUGH_BUCKLER = new ItemData("Rough Buckler", ItemType.SHIELD, ItemRarity.COMMON, ItemShapes.SIZE_2x2, 0, 7, 1, 0, 0, 0, 2, 0); 
	// 7 + 1 POUR CHAQUE ARMOR ADJACENT
	public static final ItemData KNIGHT_SHIELD = new ItemData("Knight Shield", ItemType.SHIELD, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 0, 7, 1, 0, 0, 0, 3, 0);
	// 6 + 1 POUR CHAQUE WEAPON SUR LA LIGNE
	public static final ItemData ROBO_BUCKLER = new ItemData("Robo Buckler", ItemType.SHIELD, ItemRarity.RARE, ItemShapes.SIZE_1x1, 0, 6, 1, 0, 0, 0, 5, 0);
	// 2 + 1 POUR CHAQUE ARMOR ADJACENT
	public static final ItemData IRON_HELMET = new ItemData("Iron Helmet", ItemType.ARMOR, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 2, 1, 0, 0, 0, 3, 0);
	// 5 + 1 POUR CHAQUE ARMOR ADJACENT
	public static final ItemData TUNIC = new ItemData("Tunic", ItemType.ARMOR, ItemRarity.COMMON, ItemShapes.SIZE_3x2, 0, 5, 1, 0, 0, 0, 2, 0); 
	// 4 + 2 POUR CHAQUE ARMOR ADJACENT
	public static final ItemData KNIGHT_ARMOR = new ItemData("Knight Armor", ItemType.ARMOR, ItemRarity.UNCOMMON, ItemShapes.SIZE_2x2, 0, 4, 1, 0, 0, 0, 4, 0);
	// RIEN
	public static final ItemData MAGIC_WAND = new ItemData("Magic Wand", ItemType.MAGIC, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 3, 0, 1, 0, 1, 0, 3, 0); 
	// RIEN
	public static final ItemData MANASTONE_BOW = new ItemData("Manastone Bow", ItemType.MAGIC, ItemRarity.UNCOMMON, ItemShapes.SIZE_3x1, 5, 0, 1, 0, 1, 0, 5, 0);
	// RIEN
	public static final ItemData ELECTRIC_WAND = new ItemData("Electric Wand", ItemType.MAGIC, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 5, 0, 0, 0, 1, 0, 4, 0);
	// RIEN
	public static final ItemData DARK_WAND = new ItemData("Dark Wand", ItemType.MAGIC, ItemRarity.RARE, ItemShapes.SIZE_2x2, 14, 0, 0, 0, 1, 0, 8, 0);
	// RIEN
	public static final ItemData ROBIN_WAND = new ItemData("Robin Wand", ItemType.MAGIC, ItemRarity.RARE, ItemShapes.SIZE_2x2, 3, 0, 0, 0, 1, 0, 6, 0);
	// RIEN
	public static final ItemData MANA_STONE = new ItemData("Mana Stone", ItemType.MANA, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 1, 2, 0);  
	// RIEN
	public static final ItemData GOLD = new ItemData("Gold", ItemType.GOLD, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 0, 0, 0); 
	
	public static final ItemData CURSE = new ItemData("Curse", ItemType.CURSE, ItemRarity.RARE, ItemShapes.CURSE, 0, 0, 0, 0, 0, 0, 0, 0); 

	public static final ItemData CURSE_SAP = new ItemData("Curse Sap", ItemType.CURSE, ItemRarity.RARE, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 0, 0, 0); 
	
	public static final ItemData MEAL = new ItemData("Meal", ItemType.CONSUMABLE, ItemRarity.COMMON, ItemShapes.SIZE_1x2, 0, 0, 0, 2, 0, 0, 0, 0);
	
	public static final ItemData COOL_DRINK = new ItemData("Cool Drink", ItemType.CONSUMABLE, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 1, 0, 0, 0, 0);
	
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
