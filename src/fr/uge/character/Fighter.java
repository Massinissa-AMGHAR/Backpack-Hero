package fr.uge.character;
import fr.uge.inventory.*;

/**
 * Interface for any combat participant.
 */
public interface Fighter {
  /**
   * @param opponent the fighter to attack
   * @param item optional item used in attack
   * @return total damage dealt
   */
	public int attack(Fighter opponent, Item item);
  /**
   * @param item optional item used for defense
   * @return new protection value
   */
	public int defense(Item item);
  /**
   * @param damage amount of HP to reduce
   */
	void receiveDamage(int damage);
  /**
   * @param damage amount of protection to reduce
   */
	void receiveProtectionDamage(int damage);
  /**
   * Resets protection to zero.
   */
	void resetProtection();
}
