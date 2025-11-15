package fr.uge.character;
import fr.uge.inventory.*;

public interface Fighter {
	public int attack(Fighter opponent, Item item);
	public int defense(Item item);
	void receiveDamage(int damage);
	void receiveProtectionDamage(int damage);
	void resetProtection();
}
