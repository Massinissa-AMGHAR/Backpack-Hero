package fr.uge.interaction;

import java.util.Objects;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;

/**
 * Gives extra damage to the enemy for each adjacent item named "Key".
 */
public class KeySwordInteraction implements ItemInteraction {
  /**
   * Applies bonus damage from nearby key items.
   *
   * @param hero the hero owning the item
   * @param enemy the enemy receiving damage
   * @param fight current fight graphics
   * @param self the item causing the bonus damage
   */
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(fight);
		Objects.requireNonNull(self);
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
