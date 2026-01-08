package fr.uge.effect;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;

/**
 * Applies poison to an enemy from a poison arrow item.
 */
public class PoisonArrowEffect implements ItemEffect {
	/**
	 * Base poison applied
	 */
	private final int basePoison = 2;	
	
  /**
   * Applies poison effect to the enemy.
   *
   * @param hero the hero using the item
   * @param enemy the enemy affected by the poison
   * @param fight the fight
   * @param item the item giving the effect
   */
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
