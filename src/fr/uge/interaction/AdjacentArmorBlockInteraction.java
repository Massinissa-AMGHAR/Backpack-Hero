package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

public class AdjacentArmorBlockInteraction implements ItemInteraction {
  private final int blockPerArmor;

  public AdjacentArmorBlockInteraction(int blockPerArmor) {
      this.blockPerArmor = blockPerArmor;
  }

  public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self) {
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

