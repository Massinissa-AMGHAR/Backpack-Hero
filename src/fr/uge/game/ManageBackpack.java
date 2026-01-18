package fr.uge.game;


import java.util.Objects;

import fr.uge.character.HeroInventoryManager;

import fr.uge.character.Merchant;
import fr.uge.graphics.view.ItemPlacementView;
import fr.uge.inventory.Backpack;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemRegistry;
import fr.uge.inventory.ItemType;
import fr.uge.inventory.Items;

/**
 * Manages interactions between the hero's backpack and the merchant.
 */
public class ManageBackpack {
	/**
	 * Merchant for buying items
	 */
	private final Merchant merchant; 
	/**
	 * Hero's backpack
	 */
	private final Backpack backpack; 
	/**
	 * Interface to arrange items in backpack
	 */
	private final ItemPlacementView placementView;
	/**
	 * Manages inventory actions
	 */
	private final HeroInventoryManager inventory; 
	
  /**
   * Initializes backpack management with a merchant and placement view.
   *
   * @param backpack the hero's backpack
   * @param placementView interface to arrange items in the backpack
   * @param merchant the merchant providing items
   */
	public ManageBackpack(Backpack backpack, ItemPlacementView placementView, Merchant merchant) {
		Objects.requireNonNull(backpack); 
		Objects.requireNonNull(placementView);  
		this.backpack = backpack; 
		this.placementView = placementView; 
		this.inventory = new HeroInventoryManager(backpack); 
		this.merchant = merchant; 
	
	}
	
	/**
	 * Moves an item from the backpack to the placement view if possible.
	 *
	 * @param item the item clicked in the backpack
	 * @return true if the item was successfully moved, false otherwise
	 */
	public boolean clickFromBackpack(Item item) {
		if(item == null) {
			return false; 
		}
		if (placementView.hasItem()) {
			return false; 
		}
		if(item.type().equals(ItemType.CURSE)) {
			return false; 
		}
		placementView.take(item);
		backpack.delete(item);
		return true; 
	}
	
	/**
	 * Buys an item from the shop and moves it to the placement view.
	 *
	 * @param item the item to buy
	 * @param items the shop's item list
	 * @return true if the item was successfully bought and moved, false otherwise
	 */
	public boolean clickFromShop(Item item, Items items) {
		Objects.requireNonNull(item); 
		Objects.requireNonNull(items); 
		if(!inventory.buy(item.goldCost())) {
			return false; 
		}
		items.deleteItem(items.index(item));
		placementView.take(item);
		return true; 
	}
	
	/**
	 * Moves a loot item to the placement view if it is empty.
	 *
	 * @param item the loot item to move
	 * @param items the list of loot items
	 */
	public void clickFromLoot(Item item, Items items) {
		Objects.requireNonNull(item); 
		Objects.requireNonNull(items); 
		if (placementView.hasItem()) {
			return;
		}
		items.deleteItem(items.index(item));
		placementView.take(item); 
	}
	
	/**
	 * Sells the item currently in the placement view and adds gold to the backpack.
	 *
	 * @return true if an item was sold, false otherwise
	 */
	public boolean sell() {
		if(!placementView.hasItem()) {
			return false; 
		}
		Item soldItem = placementView.item(); 
		int goldAmount = merchant.goldCost(soldItem); 
		placementView.clear();
		Item goldItem = backpack.findGoldItem(); 
		if(goldItem != null) {
			goldItem.addGold(goldAmount);
		}
		else {
			Item gold = ItemRegistry.create(ItemRegistry.GOLD, goldAmount); 
			placementView.take(gold);
		}
		return true; 
	}
	
	/**
	 * Places the item from the placement view into the backpack at the given position.
	 *
	 * @param line the row in the backpack
	 * @param column the column in the backpack
	 * @return true if the item was successfully placed, false otherwise
	 */
	public boolean placeInBackpack(int line, int column) {
		if(!placementView.hasItem()) {
			return false; 
		}
		Item item = placementView.item(); 
		if(backpack.canPlaceItem(item, line, column)) {
			backpack.add(item, line, column);
			placementView.clear();
			return true; 
		}
		return false; 
	}
	
	/**
	 * Places a curse item from the placement view into the backpack, removing any items in the same area.
	 *
	 * @param line the starting row in the backpack
	 * @param column the starting column in the backpack
	 * @return true if the curse was successfully placed, false otherwise
	 */
	public boolean placeCurseInBackpack(int line, int column) {
		if(!placementView.hasItem()) {
			return false; 
		}
		Item curse = placementView.item(); 
		if(!backpack.canPlaceCurse(curse, line, column)) {
			return false; 
		}
		int width = curse.width(); 
		int height = curse.height(); 
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int l = line + i; 
				int c = column + j; 
				Item existing = backpack.grid().get(l).get(c); 
				if(existing != null) backpack.delete(existing);
			}
		}
		backpack.add(curse, line, column);
		placementView.clear();
		return true; 
	}
}
