package fr.uge.effect;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public class PoisonArrowEffect implements ItemEffect {
private final int basePoison = 2;	
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item item) {
		if (enemy == null) {
			return;
		}
		var totalPoison = basePoison;
		var backpack = hero.backpack();
		var x = item.leftPlacementX();
		var y = item.leftPlacementY();
		var width = item.width();
		for (var col = x + width; col < backpack.maxColumns(); col++) {
			if (backpack.grid().get(y).get(col) == null && backpack.unlocked()[y][col]) {
				totalPoison += 2;
			}
		}
		var applied = enemy.addPoison(totalPoison);
		if (applied) {
			fight.battleView().messageView().showMessage(enemy.name() + " is poisoned for " + totalPoison + " turns by " + item.name(), 2500);
		}
	}
}
