package fr.uge.effect;

import java.util.Objects;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.inventory.Item;

/**
 * Applies extra damage per turn from an item.
 */
public class DamagePerTurnEffect implements ItemEffect {
	/**
	 * Bonus damage applied each turn
	 */
	private final int damagePerTurn;
	
  /**
   * @param damagePerTurn amount of bonus damage per turn
   */
	public DamagePerTurnEffect(int damagePerTurn) {
		this.damagePerTurn = damagePerTurn;
	}
	
  /**
   * Applies the effect to the hero during a fight.
   *
   * @param hero the hero involved
   * @param enemy the enemy involved
   * @param fight the fight 
   * @param @param item the item giving the effect
   */
	public void apply(Hero hero, Enemy enemy, FightGraphics fight, Item item) {
		Objects.requireNonNull(hero); 
		Objects.requireNonNull(enemy); 
		Objects.requireNonNull(fight); 
		Objects.requireNonNull(item); 
		item.addBonusDamage(damagePerTurn);
		fight.battleView().messageView().showMessage(item.name() + " gains " + damagePerTurn + " bonus this turn !", 1500);
	}
}
