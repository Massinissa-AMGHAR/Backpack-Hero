package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public interface ItemInteraction {
	void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self, Item other);
}
