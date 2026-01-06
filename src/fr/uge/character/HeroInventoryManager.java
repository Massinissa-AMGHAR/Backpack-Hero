package fr.uge.character;

import java.util.Objects;

import fr.uge.inventory.Backpack;
import fr.uge.inventory.Item;

public class HeroInventoryManager {
  private Backpack backpack;
	
	public HeroInventoryManager(Backpack backpack) {
		Objects.requireNonNull(backpack);  
		
		this.backpack = backpack; 
	}
	
	public Backpack backpack() { return backpack; }
	
	
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

