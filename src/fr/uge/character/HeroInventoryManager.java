package fr.uge.character;

import java.util.Objects;

import fr.uge.inventory.Backpack;
import fr.uge.inventory.Item;

/**
 * Manages the hero's inventory and transactions.
 */
public class HeroInventoryManager {
	/**
	 * Hero's backpack containing items
	 */
  private Backpack backpack;
	
  /**
   * Initializes the inventory manager with a backpack.
   *
   * @param backpack the hero's backpack
   */
	public HeroInventoryManager(Backpack backpack) {
		Objects.requireNonNull(backpack);  
		this.backpack = backpack; 
	}
	
  /**
   * @return the hero's backpack
   */
	public Backpack backpack() { return backpack; }
	
  /**
   * Try to spend gold to buy an item.
   *
   * @param cost amount of gold required
   * @return true if purchase succeeded, false otherwise
   */
	public boolean buy(int cost) {
		Item gold = backpack.findGoldItem(); 
		if(gold == null || gold.goldAmount() < cost) {
			IO.println("You don't have enough money!"); 
			return false; 
		}
		gold.spendGold(cost);
		IO.println("You spend "+ cost + " golds"); 
		IO.println("You have "+ gold.goldAmount() + " golds"); 
		return true; 
	}
		
}

