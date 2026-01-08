package fr.uge.effect;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;

/**
 * Applies a fixed poison effect to an enemy.
 */
public class PoisonEffect implements ItemEffect {
	/**
	 * Amount of poison to apply
	 */
	private final int poisonAmount;
	
  /**
   * @param poisonAmount number of poison turns to apply
   */
	public PoisonEffect(int poisonAmount) {
		this.poisonAmount = poisonAmount;
	}
	
  /**
   * Applies poison to the enemy.
   *
   * @param hero the hero using the item
   * @param enemy the enemy affected by poison
   * @param fight the fight
   * @param item the item giving the effect
   */
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item item) {
		if (enemy == null) {
			return;
		}
		var applied = enemy.addPoison(poisonAmount);
		if (applied) {
			fight.battleView().messageView().showMessage(enemy.name() + "is poisoned for " + poisonAmount + "damage by " + item.name() + "!", 1500);
		}
	}
}
