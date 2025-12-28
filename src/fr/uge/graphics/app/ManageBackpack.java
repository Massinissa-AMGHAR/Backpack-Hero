package fr.uge.graphics.app;

import fr.uge.character.Hero;
import fr.uge.character.HeroInventoryManager;
import fr.uge.character.Merchant;
import fr.uge.graphics.view.ItemPlacementView;
import fr.uge.graphics.view.MessageView;
import fr.uge.inventory.Backpack;
import fr.uge.inventory.Item;
import fr.uge.inventory.Items;

public class ManageBackpack {
	private final Hero hero; 
	private final Merchant merchant; 
	private final Backpack backpack; 
	private final ItemPlacementView placementView; 
	private final HeroInventoryManager inventory;
	
	public ManageBackpack(Backpack backpack, ItemPlacementView placementView, Hero hero, Merchant merchant) {
		this.backpack = backpack; 
		this.placementView = placementView; 
		this.inventory = new HeroInventoryManager(backpack); 
		this.hero = hero; 
		this.merchant = merchant; 
	
	}
	
	public boolean clickFromBackpack(Item item) {
		if(item == null) return false; 
		if (placementView.hasItem()) return false; 
		backpack.delete(item);
		placementView.take(item); 
		return true; 
	}
	
	public boolean clickFromShop(Item item, Items items) {
		if(!inventory.buy(item.goldCost())) return false; 
		items.deleteItem(items.index(item));
		placementView.take(item);
		return true; 
	}
	
	public void clickFromLoot(Item item, Items items) {
		if (placementView.hasItem()) return;
		items.deleteItem(items.index(item));
		placementView.take(item); 
	}
	
	public boolean sell() {
		if(!placementView.hasItem()) return false; 
		Item soldItem = placementView.item(); 
		int goldAmount = merchant.goldCost(soldItem); 
		placementView.clear();
		Item goldItem = backpack.findGoldItem(); 
		if(goldItem != null) {
			goldItem.addGold(goldAmount);
		}
		else {
			Item gold = new Item("Gold", "GOLD", "COMMON", Item.SHAPE_GOLD, 0, 0, 0, 0, 0, 0, 0, goldAmount); 
			placementView.take(gold);
		}
	
		return true; 
	}
	
	public boolean placeInBackpack(int line, int column) {
		if(!placementView.hasItem()) return false; 
		Item item = placementView.item(); 
		if(backpack.canPlaceItem(item, line, column)) {
			backpack.add(item, line, column);
			placementView.clear();
			return true; 
		}
		return false; 
	}
}
