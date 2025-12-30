package fr.uge.effect;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public class PoisonEffect implements ItemEffect {
	private final int poisonAmount;
	
	public PoisonEffect(int poisonAmount) {
		this.poisonAmount = poisonAmount;
	}
	
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item item) {
		if (enemy != null) {
			enemy.addPoison(poisonAmount);
		}
	}
}
