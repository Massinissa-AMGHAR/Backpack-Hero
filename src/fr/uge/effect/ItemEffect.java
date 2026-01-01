package fr.uge.effect;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public interface ItemEffect {
	void apply(Hero hero, Enemy enemy, FightGraphics fight, Item item);
}
