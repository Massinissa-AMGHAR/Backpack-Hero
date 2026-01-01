package fr.uge.character;
import fr.uge.inventory.*;

import java.util.Random; 

import java.util.Objects;

public class Enemy implements Fighter {
	private final EnemyData data; 
	private int HP; 
	private int protection;
	private final Random rand = new Random();
	private int poisonCounter = 0;
	private Integer action = null;
	
	public Enemy(EnemyData data) {
		Objects.requireNonNull(data, "Data cannot be null"); 
    this.data = data;  
    this.HP = data.HPMax(); 
    this.protection = 0; 
	}
	
	public Enemy(Enemy other) {
		Objects.requireNonNull(other, "Enemy cannot be null"); 
		this.data = other.data; 
		this.HP = other.HP; 
		this.protection = other.protection; 
	}
	
	public int HP() { return HP; }
	public int HPMax() { return data.HPMax(); }
	public int protection() { return protection; }	
	public String name() { return data.name(); }
	public int minAttack() { return data.maxAttack(); }
	public int maxAttack() { return data.maxAttack(); }
	public int minDefense() { return data.maxDefense(); }
	public int maxDefense() { return data.maxDefense(); }
	public int xp() { return data.xp(); }
	
	public int attackDamage() {
		return rand.nextInt(data.maxAttack() - data.minAttack()+ 1) + data.minAttack();
	}
	
	public int baseDefense() { 
		return rand.nextInt(data.maxDefense() - data.minDefense() + 1) + data.minDefense(); 
	}
	
	public void receiveDamage(int damage) {
		HP = Math.max(HP - damage, 0);
	}
	
	public void receiveProtectionDamage(int damage) {
	    protection = Math.max(protection - damage, 0);
	}
	
	public void resetProtection() {
		protection = 0;
	}
	
	public boolean addPoison(int amount) {
		if (poisonCounter > 0) return false;
		poisonCounter += amount;
		return true;
	}
	
	public int applyPoison() {
		if (poisonCounter > 0) {
			if (protection > 0) {
				receiveProtectionDamage(1);
			} else {
				receiveDamage(1);
			}
			poisonCounter--;
			return 1;
		}
		return 0;
	}
	
	public boolean isPoisoned() { return poisonCounter > 0; }
	
	@Override
	public int attack(Fighter opponent, Item item) {
		if (!(opponent instanceof Hero)) throw new IllegalArgumentException("Ennemy can only attack Hero");
		Hero hero = (Hero) opponent;
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
	
	@Override
	public int defense(Item item) {
		protection += baseDefense();
		return protection;
	}
		
	public int getAction() {
		if (action == null) {
			action = actionAnnounced();
		}
		return action;
	}
	
	public void resetAction() { action = null; }
	
	public int actionAnnounced() {
	  Random rand = new Random(); 
	  int action = rand.nextInt(3); 
	  
	 switch(action) {
	 	case 0 -> IO.println(name() + "will attack"); 
	 	case 1 -> IO.println(name() + "will defend"); 
	 	case 2 -> IO.println(name() + "will curse you"); 
	 }
	  return action; 
	}
	public Item curse() {
		return ItemRegistry.create(ItemRegistry.CURSE); 
	}
	
	public static final EnemyData[] ALL_ENEMIES ={
		new EnemyData("Little Rat-Wolf", 15, 1, 4, 1, 6, 3 ),
		new EnemyData("Rat-Wolf", 20, 3, 6, 3, 6, 6)
	};
	
	@Override
	public String toString() {
		return this.name() + ": " + this.HP() + "/" + HPMax() + " HP | Protection: " + this.protection();
	}
}
