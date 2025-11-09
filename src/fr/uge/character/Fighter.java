package fr.uge.character;
import fr.uge.inventory.*;

public interface Fighter {
	public void attack(Fighter oppenent, Item item);
	public void defense(Item item);
	void receiveDamage(int damage);
}
