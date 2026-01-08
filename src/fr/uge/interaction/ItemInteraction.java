package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;

/**
 * Represents a special effect that an item can give during a fight.
 */
public interface ItemInteraction {
  /**
   * Applies the effect to the enemy using the hero's item.
   *
   * @param hero the hero using the item
   * @param enemy the enemy affected by the item
   * @param fight the current fight graphics
   * @param self the item causing the effect
   */
	void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self);
}
