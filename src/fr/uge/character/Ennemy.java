package fr.uge.character;
import fr.uge.inventory.*;

import java.util.Objects;

public class Ennemy implements Fighter {
	private final String name;
	private int HP;
	private int protection;
	private int attackDamage;
	private int baseDefense;
	
	public Ennemy(String name, int HP, int protection, int attackDamage, int baseDefense) {
        if (HP < 0 || protection < 0 || attackDamage < 0 || baseDefense < 0) throw new IllegalArgumentException("HP and protection must be >= 0");
	    Objects.requireNonNull(name, "name can't be null");
	    this.HP = HP;
	    this.protection = protection;
	    this.name = name;
	    this.attackDamage = attackDamage;
	    this.baseDefense = baseDefense;
	}
	
	public int HP() { return HP; }
	public int protection() { return protection; }	
	public String name() { return name; }
	public int attackDamage() { return attackDamage; }
	public int baseDefense() { return baseDefense; }
	
	public void receiveDamage(int damage) {
		HP = Math.max(HP - damage, 0);
	}
	
	public void attack(Fighter oppenent, Item item) {
		if (!(oppenent instanceof Ennemy)) throw new IllegalArgumentException("Ennemy can only attack Hero");
		Hero hero = (Hero) oppenent;
		int damage = Math.max(attackDamage - hero.protection(), 0);
		hero.receiveDamage(damage);
	}
	
	public void defense(Item item) {
		protection += baseDefense;
	}
}
