package fr.uge.character;
import fr.uge.inventory.*;
import java.util.Random; 

import java.util.Objects;

public class Enemy implements Fighter {
	private final String name;
	private int HP;
	private int HPMax; 
	private int protection;
	private final int minAttack;
	private final int maxAttack;
	private final int minDefense;
	private final int maxDefense;
	private final Random rand = new Random();
	private int xp; 
	
	
	public Enemy(String name, int HP, int HPMax, int protection, int minAttack, int maxAttack, int minDefense, int maxDefense, int xp) {
      if (HP < 0 || HPMax < 0 || protection < 0 || minAttack < 0 || maxAttack < 0 || minDefense < 0 || maxDefense < 0 || xp < 0) throw new IllegalArgumentException("HP and protection must be >= 0");
	    Objects.requireNonNull(name, "name can't be null");
	    this.HP = HP;
	    this.HPMax = HPMax; 
	    this.protection = protection;
	    this.name = name;
	    this.minAttack = minAttack;
	    this.maxAttack = maxAttack;
	    this.minDefense = minDefense;
	    this.maxDefense = maxDefense;
	    this.xp = xp; 
	}
	
	public int HP() { return HP; }
	public int HPMax() { return HPMax; }
	public int protection() { return protection; }	
	public String name() { return name; }
	public int attackDamage() { return rand.nextInt(maxAttack - minAttack + 1) + minAttack; }
	public int baseDefense() { return rand.nextInt(maxDefense - minDefense + 1) + minDefense; }
	public int minAttack() { return minAttack; }
	public int maxAttack() { return maxAttack; }
	public int minDefense() { return minDefense; }
	public int maxDefense() { return maxDefense; }
	public int xp() { return xp; }
	
	public void receiveDamage(int damage) {
		HP = Math.max(HP - damage, 0);
	}
	
	public void receiveProtectionDamage(int damage) {
	    protection = Math.max(protection - damage, 0);
	}
	
	public void resetProtection() {
		protection = 0;
	}
	
	public int attack(Fighter oppenent, Item item) {
		if (!(oppenent instanceof Hero)) throw new IllegalArgumentException("Ennemy can only attack Hero");
		Hero hero = (Hero) oppenent;
		int totalDamage = 0;
		int attackDamage = attackDamage();
		
		int protection = hero.protection();
		int damageToProtection = Math.min(attackDamage, protection);
		hero.receiveProtectionDamage(damageToProtection);
		attackDamage -= damageToProtection;
		totalDamage += damageToProtection;
		
		if (attackDamage > 0) {
			hero.receiveDamage(attackDamage);
			totalDamage += attackDamage;
		}
		return totalDamage;
	}
	
	public int defense(Item item) {
		protection += baseDefense();
		return protection;
	}
		
	public int actionAnnounced() {
	  Random rand = new Random(); 
	  int action = rand.nextInt(2); 
	  
	  if(action == 0) IO.println("\nThe next action of " + this.name() + " will be a damage\n"); 
	  else IO.println("\nThe next action of " + this.name() + " will be a defense\n"); 
	  
	  return action; 
	}
	
	public static final Enemy[] ALL_ENEMIES ={
		new Enemy("Little Rat-Wolf", 3, 3, 5, 1, 4, 1, 6, 3 ),
		new Enemy("Rat-Wolf", 6, 6, 5, 3, 6, 3, 6, 6)
	};
	
	@Override
	public String toString() {
		return this.name() + ": " + this.HP() + "/" + HPMax() + " HP | Protection: " + this.protection();
	}
}
