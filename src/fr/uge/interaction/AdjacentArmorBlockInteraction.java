package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

public class AdjacentArmorBlockInteraction {
	private final int blockPerArmor;
	
	public AdjacentArmorBlockInteraction(int blockPerArmor) {
		this.blockPerArmor = blockPerArmor;
	}
	
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self, Item other) {
		if (other.type() == ItemType.ARMOR) {
			hero.addBlock(blockPerArmor);
		}
	}
}
