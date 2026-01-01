package fr.uge.effect;

import fr.uge.character.*;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.inventory.Item;

public class DamagePerTurnEffect implements ItemEffect {
	private final int damagePerTurn;
	
	public DamagePerTurnEffect(int damagePerTurn) {
		this.damagePerTurn = damagePerTurn;
	}
	
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item item) {
		item.addBonusDamage(damagePerTurn);
		fight.battleView().messageView().showMessage(item.name() + " gains " + damagePerTurn + " bonus this turn !", 1500);
	}
}
