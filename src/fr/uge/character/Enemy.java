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
	
	public int defense(Item item) {
		protection += baseDefense();
		return protection;
	}
		
	public int getAction(boolean curseLaunched) {
		if (action == null) {
			action = actionAnnounced(curseLaunched);
		}
		return action;
	}
	
	public void resetAction() { action = null; }
	
	public int actionAnnounced(boolean curseLaunched) {
	  if (curseLaunched) {
	  	return rand.nextInt(2);
	  } else {
	  	return rand.nextInt(3); 
	  }
	}
	
	public Item curse(int floor) {
		var shape = rand.nextInt(2);
		if (shape == 1) {
			if (floor > 0) {
				return ItemRegistry.create(ItemRegistry.CURSE); 
			}
		}
		return ItemRegistry.create(ItemRegistry.CURSE_SAP); 
	}
	
	public static final EnemyData[] ALL_ENEMIES ={
		new EnemyData("Small Rat-Wolf", 10, 3, 5, 4, 6, 4),
		new EnemyData("Rat-Wolf", 15, 5, 7, 6, 8, 8),
		new EnemyData("Frog Wizard", 20, 6, 9, 6, 10, 12),
		new EnemyData("Living Shadow", 30, 8, 11, 6, 10, 16),
		new EnemyData("Queen Bee", 40, 9, 13, 7, 11, 20)
	};
	
}
