package fr.uge.interaction;

import fr.uge.character.Enemy;
import fr.uge.character.Hero;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

public class CurseBladeInteraction implements ItemInteraction {
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self) {
		if (enemy == null) {
			return;
		}
		var totalDamage = 0;
		var adjacentItems = hero.backpack().getAdjacentItems(self);
		for (var item : adjacentItems) {
			if (item.type() == ItemType.CURSE) {
				totalDamage += 2;
			}
		}
		if (totalDamage > 0) {
			enemy.receiveDamage(totalDamage);
			fight.battleView().messageView().showMessage(self.name() + " deals " + totalDamage + " bonus damage to " + enemy.name() + " from adjacent curses !", 2500);;
		}
	}

}
