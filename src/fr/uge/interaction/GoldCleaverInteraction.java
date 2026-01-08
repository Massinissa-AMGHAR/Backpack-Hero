package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;

/**
 * Gives extra damage to the enemy for each adjacent item with "Cleaver" in its name.
 */
public class GoldCleaverInteraction implements ItemInteraction {
  /**
   * Applies bonus damage from nearby cleaver items.
   *
   * @param hero the hero owning the item
   * @param enemy the enemy receiving damage
   * @param fight current fight graphics
   * @param self the item causing the bonus damage
   */
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
