package fr.uge.character;

import java.util.Objects;

import fr.uge.inventory.Item;

public class HeroEconomyManager {
	private final HeroInventoryManager inventory; 
	
	public HeroEconomyManager(HeroInventoryManager inventory, HeroFight fight) {
		Objects.requireNonNull(inventory); 
		Objects.requireNonNull(fight); 
		this.inventory = inventory; 
	}
	
	public boolean buy(int cost) {
		return inventory.buy(cost); 
	}
}
