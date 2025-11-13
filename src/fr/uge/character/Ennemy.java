package fr.uge.character;
import fr.uge.inventory.*;
import java.util.Random; 

import java.util.Objects;

public class Ennemy implements Fighter {
	private final String name;
	private int HP;
	private int protection;
	private int attackDamage;
	private int baseDefense;
	private int xp; 
	
	public Ennemy(String name, int HP, int protection, int attackDamage, int baseDefense, int xp) {
        if (HP < 0 || protection < 0 || attackDamage < 0 || baseDefense < 0) throw new IllegalArgumentException("HP and protection must be >= 0");
	    Objects.requireNonNull(name, "name can't be null");
	    this.HP = HP;
	    this.protection = protection;
	    this.name = name;
	    this.attackDamage = attackDamage;
	    this.baseDefense = baseDefense;
	    this.xp = xp; 
	}
	
	public int HP() { return HP; }
	public int protection() { return protection; }	
	public String name() { return name; }
	public int attackDamage() { return attackDamage; }
	public int baseDefense() { return baseDefense; }
	public int xp() { return xp; }
	
	public void receiveDamage(int damage) {
		HP = Math.max(HP - damage, 0);
	}
	
	public void attack(Fighter oppenent, Item item) {
		if (!(oppenent instanceof Hero)) throw new IllegalArgumentException("Ennemy can only attack Hero");
		Hero hero = (Hero) oppenent;
		int damage = Math.max(attackDamage - hero.protection(), 0);
		hero.receiveDamage(damage);
	}
	
	public void defense(Item item) {
		protection += baseDefense;
	}
	
	public int actionAnnounced() {
	  Random rand = new Random(); 
	  int action = rand.nextInt(2); 
	  
	  if(action == 0) IO.println("The next action of " + this.name() + " will be a damage"); 
	  else IO.println("The next action will be a defense"); 
	  
	  return action; 
	}
	@Override
	public String toString() {
		return this.name() + ": " + this.HP() + " HP | Protection: " + this.protection();
	}
}
