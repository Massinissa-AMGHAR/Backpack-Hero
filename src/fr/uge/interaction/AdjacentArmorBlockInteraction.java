package fr.uge.interaction;

import java.util.Objects;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

/**
 * Gives extra protection if this item is next to armor items in the backpack.
 */
public class AdjacentArmorBlockInteraction implements ItemInteraction {
  private final int blockPerArmor;

  /**
   * @param blockPerArmor extra protection per adjacent armor
   */
  public AdjacentArmorBlockInteraction(int blockPerArmor) {
      this.blockPerArmor = blockPerArmor;
  }

  /**
   * Applies bonus protection for each adjacent armor item.
   *
   * @param hero the hero receiving the bonus
   * @param enemy the enemy (not used)
   * @param fight current fight graphics
   * @param self the item giving the bonus
   */
  public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self) {
  	Objects.requireNonNull(hero);
  	Objects.requireNonNull(fight);
  	Objects.requireNonNull(self);
  	var adjacentItems = hero.backpack().getAdjacentItems(self);
  	var bonusBlock = 0;
  	var time = 2500;
  	for (var adj : adjacentItems) {
  		if (adj.type() == ItemType.ARMOR) {
  			bonusBlock += blockPerArmor;
  			fight.battleView().messageView().showMessage(self.name() + " gives " + blockPerArmor + " extra block thanks to " + adj.name(), time);
  			time += 1000;
  		}
  	}
  	if (bonusBlock > 0) {
  		hero.addBlock(bonusBlock);
  	}
  }
}

