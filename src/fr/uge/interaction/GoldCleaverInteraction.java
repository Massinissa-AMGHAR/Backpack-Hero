package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public class GoldCleaverInteraction implements ItemInteraction {
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self, Item other) {
		if (other.name().contains("Cleaver")) {
			enemy.receiveDamage(4);
		}
	}
}
