package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public class KeySwordInteraction implements ItemInteraction {
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self) {
		if (enemy == null) {
			return;
		}
		var totalDamage = 0;
		var adjacentItems = hero.backpack().getAdjacentItems(self);
		for (var item : adjacentItems) {
			if (item.name().equals("Key")) {
				totalDamage += 2;
			}
		}
		if (totalDamage > 0) {
			enemy.receiveDamage(totalDamage);
			fight.battleView().messageView().showMessage(self.name() + " deals " + totalDamage + " bonus damage to " + enemy.name() + " from adjacent keys !", 2500);;
		}
	}
}
