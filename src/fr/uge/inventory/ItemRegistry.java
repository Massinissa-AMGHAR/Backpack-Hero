package fr.uge.inventory;

public class ItemRegistry {
	private ItemRegistry() {}
	
	// RIEN
	public static final ItemData WOODEN_SWORD = new ItemData("Wooden Sword", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_3x1, 4, 0, 1, 0, 0, 0, 5);
	// RIEN
	public static final ItemData CLEAVER = new ItemData("Cleaver", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 2, 0, 1, 0, 0, 0, 3);
	// 4 DEGATS POUR CHAQUE CLEAVER ADJACENT
	public static final ItemData GOLD_CLEAVER = new ItemData("Gold Cleaver", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_1x1, 4, 0, 1, 0, 0, 0, 5);
	// 2 DEGATS + 2 POISON A L'ENNEMI
	public static final ItemData DAGGER = new ItemData("Dagger", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 2, 0, 1, 0, 0, 0, 3);
	// 7 DEGATS + 1 CHAQUE TOUR
	public static final ItemData TILTED_SWORD = new ItemData("Tilted Sword", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_2x2, 7, 0, 2, 0, 0, 0, 7);
	// RIEN
	public static final ItemData HEAVY_BLADE = new ItemData("Heavy Blade", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_3x1, 5, 0, 1, 0, 0, 0, 6);
	// 7 DEGATS + 2 POUR CHAQUE CLE ADJACTENTE
	public static final ItemData KEY_SWORD = new ItemData("Key Sword", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_2x1, 7, 0, 1, 0, 0, 0, 7);
	// 8 + 2 POUR CHAQUE MALEDICTION ADJACENTE
	public static final ItemData CURSED_BLADE = new ItemData("Cursed Blade", ItemType.WEAPON, ItemRarity.LEGENDARY, ItemShapes.SIZE_3x1, 8, 0, 2, 0, 0, 0, 9);
	// 10 + 3 POISON
	public static final ItemData VAMPIRIC_AXE = new ItemData("Vampiric Axe", ItemType.WEAPON, ItemRarity.RARE, ItemShapes.SIZE_3x1, 10, 0, 2, 0, 0, 0, 8);
	// RIEN
	public static final ItemData ARROW = new ItemData("Arrow", ItemType.WEAPON, ItemRarity.COMMON, ItemShapes.SIZE_1x3, 2, 0, 1, 0, 0, 0, 2);
	// 2 POISON + 2 POUR CHAQUE ESPACE VIDE A DROITE
	public static final ItemData POISON_ARROW = new ItemData("Poison Arrow", ItemType.WEAPON, ItemRarity.UNCOMMON, ItemShapes.SIZE_1x2, 2, 0, 0, 0, 1, 0, 0);
	// RIEN
	public static final ItemData ROUGH_BUCKLER = new ItemData("Rough Buckler", ItemType.SHIELD, ItemRarity.COMMON, ItemShapes.SIZE_2x2, 0, 7, 1, 0, 0, 0, 2); 
	// 7 + 1 POUR CHAQUE ARMOR ADJACENT
	public static final ItemData KNIGHT_SHIELD = new ItemData("Knight Shield", ItemType.SHIELD, ItemRarity.COMMON, ItemShapes.SIZE_1x2, 0, 7, 1, 0, 0, 0, 3);
	// 6 + 1 POUR CHAQUE WEAPON SUR LA LIGNE
	public static final ItemData AMETHYST_BUCKLER = new ItemData("Amethyst Buckler", ItemType.SHIELD, ItemRarity.RARE, ItemShapes.SIZE_1x1, 0, 6, 1, 0, 0, 0, 5);
	// 2 + 1 POUR CHAQUE ARMOR ADJACENT
	public static final ItemData IRON_HELMET = new ItemData("Iron Helmet", ItemType.ARMOR, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 2, 1, 0, 0, 0, 3);
	// 5 + 1 POUR CHAQUE ARMOR ADJACENT
	public static final ItemData TUNIC = new ItemData("Tunic", ItemType.ARMOR, ItemRarity.COMMON, ItemShapes.SIZE_3x2, 0, 5, 1, 0, 0, 0, 2); 
	// 4 + 2 POUR CHAQUE ARMOR ADJACENT
	public static final ItemData KNIGHT_ARMOR = new ItemData("Knight Armor", ItemType.ARMOR, ItemRarity.UNCOMMON, ItemShapes.SIZE_3x2, 0, 4, 1, 0, 0, 0, 4);
	// RIEN
	public static final ItemData MAGIC_WAND = new ItemData("Magic Wand", ItemType.MAGIC, ItemRarity.COMMON, ItemShapes.SIZE_2x2, 3, 0, 1, 0, 2, 0, 3); 
	// RIEN
	public static final ItemData MANASTONE_BOW = new ItemData("Manastone Bow", ItemType.MAGIC, ItemRarity.UNCOMMON, ItemShapes.SIZE_3x1, 5, 0, 1, 0, 2, 0, 5);
	// RIEN
	public static final ItemData ELECTRIC_WAND = new ItemData("Electric Wand", ItemType.MAGIC, ItemRarity.COMMON, ItemShapes.SIZE_2x1, 5, 0, 0, 0, 1, 0, 4);
	// RIEN
	public static final ItemData DARK_WAND = new ItemData("Dark Wand", ItemType.MAGIC, ItemRarity.RARE, ItemShapes.SIZE_2x2, 14, 0, 0, 0, 3, 0, 8);
	// RIEN
	public static final ItemData ROBIN_WAND = new ItemData("Robin Wand", ItemType.MAGIC, ItemRarity.RARE, ItemShapes.SIZE_2x2, 3, 0, 0, 0, 1, 0, 6);
	// RIEN
	public static final ItemData MANA_STONE = new ItemData("Mana Stone", ItemType.MANA, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 1, 2);  
	// RIEN
	public static final ItemData GOLD = new ItemData("Gold", ItemType.GOLD, ItemRarity.COMMON, ItemShapes.SIZE_1x1, 0, 0, 0, 0, 0, 0, 0); 
	
	
	public static final ItemData[] ALL_ITEMS = {
			WOODEN_SWORD, 
			ARROW, 
			ROUGH_BUCKLER, 
			TUNIC, 
			MAGIC_WAND, 
			MANA_STONE, 
	}; 
	
	public static Item create(ItemData data) {
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
				data.goldCost(), 
				0
		); 		
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
				data.goldCost(), 
				goldAmount
		); 		
	}
}
