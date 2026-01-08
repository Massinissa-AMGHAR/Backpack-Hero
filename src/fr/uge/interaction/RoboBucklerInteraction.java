package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

/**
 * Gives protection to the hero for each weapon on the same line.
 */
public class RoboBucklerInteraction implements ItemInteraction {  
  /**
   * Applies protection bonus based on weapons on the item's line.
   *
   * @param hero the hero owning the item
   * @param enemy the enemy (not used)
   * @param fight current fight graphics
   * @param self the item giving the block
   */
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self) {
    int weaponsOnLine = 0;
    int line = self.leftPlacementY();
    for (int col = 0; col < hero.backpack().maxColumns(); col++) {
      if (!hero.backpack().unlocked()[line][col]) continue;
      Item item = hero.backpack().grid().get(line).get(col);
      if (item != null && item.type() == ItemType.WEAPON) {
      	weaponsOnLine++;
      }
    }
    if (weaponsOnLine > 0) {
      hero.addBlock(weaponsOnLine);
      fight.battleView().messageView().showMessage(self.name() + " gains " + weaponsOnLine + " block from weapons on its line !", 2500);
    }
  }
}
