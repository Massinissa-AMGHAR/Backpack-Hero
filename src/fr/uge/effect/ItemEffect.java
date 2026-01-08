package fr.uge.effect;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;

/**
 * Interface for item effects applied to the enemy during a fight.
 */
public interface ItemEffect {
  /**
   * Applies the effect to the enemy.
   *
   * @param hero the hero involved
   * @param enemy the enemy affected by the effect
   * @param fight the fight
   * @param item the item giving the effect
   */
	void apply(Hero hero, Enemy enemy, FightGraphics fight, Item item);
}
