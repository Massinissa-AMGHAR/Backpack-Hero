package fr.uge.character;

import java.util.Objects;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemRegistry;
import fr.uge.inventory.Items;

public class HeroEconomyManager {
	private final HeroInventoryManager inventory; 
	private final HeroFight fight; 
	
	public HeroEconomyManager(HeroInventoryManager inventory, HeroFight fight) {
		Objects.requireNonNull(inventory); 
		Objects.requireNonNull(fight); 
		this.inventory = inventory; 
		this.fight = fight; 
	}
	
	public boolean buy(int cost) {
		return inventory.buy(cost); 
	}
	
	 public boolean winGold(int amount) {
	  	Item goldItem = inventory.backpack().findGoldItem(); 
	  	if(goldItem != null) {
	  		goldItem.addGold(amount);
	  		return true; 
	  	}
	  	else {
	  		Items list = new Items(); 
	  		Item newGold = ItemRegistry.create(ItemRegistry.GOLD, amount); 
	  		list.addItem(newGold);
	  		if(!inventory.choicePlaceItem(newGold, list, 0)) return false; 
	  		return true; 
	  	}
	  }

  
  public boolean sellItem(Merchant merchant, Item item) {
		Objects.requireNonNull(merchant, "Merchant cannot be null");
	  Objects.requireNonNull(item, "Item cannot be null");
	  if(winGold(merchant.goldCost(item))) {
	  	inventory.backpack().delete(item);
	  	return true; 
	  }
	  return false; 
	}

	public void treat(Healer healer) {
		Objects.requireNonNull(healer, "Angel cannot be null");
	  if (buy(healer.price())) {
	  	fight.heal(healer.hp()); 
	  	IO.println("Your life was restored"); 
	  }
	}
}
