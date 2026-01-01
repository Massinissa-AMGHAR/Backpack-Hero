package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public class GoldCleaverInteraction implements ItemInteraction {
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self) {
		var adjacentItems = hero.backpack().getAdjacentItems(self);
		var bonusDamage = 0;
		for (var adj : adjacentItems) {
			if (adj.name().contains("Cleaver")) {
				bonusDamage += 4;
			}		
		}
		if (bonusDamage > 0) {
			enemy.receiveDamage(bonusDamage);
			fight.battleView().messageView().showMessage(self.name() + " deals " + bonusDamage + " extra damage to " + enemy.name() +" thanks to adjacent Cleavers", 2000);
		}
	}
}
