package fr.uge.interaction;

import fr.uge.character.Enemy;
import fr.uge.character.Hero;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

/**
 * Deals extra damage to the enemy for each curse item next to this item in the backpack.
 */
public class CurseBladeInteraction implements ItemInteraction {
  /**
   * Applies bonus damage from adjacent curse items.
   *
   * @param hero the hero owning the item
   * @param enemy the enemy receiving damage
   * @param fight current fight graphics
   * @param self the item causing the bonus damage
   */
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
