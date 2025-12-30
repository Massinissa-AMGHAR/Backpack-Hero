package fr.uge.effect;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public class PoisonArrowEffect {
	private final ItemEffect poison = new PoisonEffect(2);
	
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item item) {
		poison.apply(hero, enemy, fight, item);
		var backpack = hero.backpack();
		var x = item.leftPlacementX();
		var y = item.leftPlacementY();
		var width = item.width();
		var bonus = 0;
		for (var col = x + width; col < backpack.maxColumns(); col++) {
			if (backpack.grid().get(y).get(col) == null && backpack.unlocked()[y][col]) {
				bonus += 2;
			}
		}
		enemy.receiveDamage(bonus);
	}
}
