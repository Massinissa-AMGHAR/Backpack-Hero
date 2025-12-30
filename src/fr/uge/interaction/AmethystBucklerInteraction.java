package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

public class AmethystBucklerInteraction implements ItemInteraction {
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self, Item other) {
		var weaponsOnLine = 0;
		var line = self.leftPlacementX();
		for (var col = 0; col < hero.backpack().maxColumns(); col++) {
			var item = hero.backpack().grid().get(line).get(col);
			if (item != null && item.type() == ItemType.WEAPON) {
				weaponsOnLine++;
			}
		}
		hero.addBlock(weaponsOnLine);
	}
}
