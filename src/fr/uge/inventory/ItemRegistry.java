package fr.uge.inventory;

public class ItemRegistry {
	private ItemRegistry() {}
	
	public static final ItemData WOODEN_SWORD = new ItemData("Wooden Sword", ItemType.MELEE, ItemRarity.COMMON, ItemShapes.WOODEN_SWORD, 4, 0, 1, 0, 0, 0, 5);
	public static final ItemData ARROW = new ItemData("Arrow", ItemType.MELEE, ItemRarity.COMMON, ItemShapes.ARROW, 2, 0, 1, 0, 0, 0, 2); 
	public static final ItemData ROUGH_BUCKLER = new ItemData("Rough Buckler", ItemType.SHIELD, ItemRarity.COMMON, ItemShapes.ROUGH_BUCKLER, 0, 7, 1, 0, 0, 0, 2); 
	public static final ItemData TUNIC = new ItemData("Tunic", ItemType.ARMOR, ItemRarity.COMMON, ItemShapes.TUNIC, 0, 5, 1, 0, 0, 0, 2); 
	public static final ItemData MAGIC_WAND = new ItemData("Magic Wand", ItemType.MAGIC, ItemRarity.COMMON, ItemShapes.MAGIC_WAND, 3, 0, 1, 0, 2, 0, 3); 
	public static final ItemData MANA_STONE = new ItemData("Mana Stone", ItemType.MANA, ItemRarity.COMMON, ItemShapes.MANA_STONE, 0, 0, 0, 0, 0, 1, 2);  
	public static final ItemData GOLD = new ItemData("Gold", ItemType.GOLD, ItemRarity.COMMON, ItemShapes.GOLD, 0, 0, 0, 0, 0, 0, 0); 
	
	
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
