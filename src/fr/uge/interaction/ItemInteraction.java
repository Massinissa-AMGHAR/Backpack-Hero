package fr.uge.interaction;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;

public interface ItemInteraction {
	void apply(Hero hero, Enemy enemy, FightGraphics fight, Item self);
}
