package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public class KeySwordInteraction implements ItemInteraction {
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self, Item other) {
		if (enemy == null) return ;
		var totalDamage = self.damage();
		var adjacentItems = hero.backpack().getAdjacentItems(self);
		for (var item : adjacentItems) {
			if (item.name().equals("Key")) {
				totalDamage += 2;
			}
		}
		enemy.receiveDamage(totalDamage);
	}
}
