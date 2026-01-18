package fr.uge.character;
import fr.uge.inventory.*;


import java.util.Random; 

import java.util.Objects;

/**
 * Represents an enemy in the game. Implements Fighter interface.
 */
public class Enemy implements Fighter {
	private final EnemyData data; 
	private int HP; 
	private int protection;
	private final Random rand = new Random();
	private int poisonCounter = 0;
	private Integer action = null;
	
  /**
   * Creates an enemy from EnemyData.
   *
   * @param data the template data for the enemy
   */
	public Enemy(EnemyData data) {
		Objects.requireNonNull(data, "Data cannot be null"); 
    this.data = data;  
    this.HP = data.HPMax(); 
    this.protection = 0; 
	}
	
  /**
   * Creates a copy of another enemy.
   *
   * @param other the enemy to copy
   */
	public Enemy(Enemy other) {
		Objects.requireNonNull(other, "Enemy cannot be null"); 
		this.data = other.data; 
		this.HP = other.HP; 
		this.protection = other.protection; 
	}

	/**
	 * @return current HP
	 */
	public int HP() { return HP; }
	
	/**
	 * @return maximum HP
	 */
	public int HPMax() { return data.HPMax(); } 
	
	/**
	 * @return current protection
	 */
	public int protection() { return protection; } 
	
	/**
	 * @return enemy name
	 */
	public String name() { return data.name(); } 
	
	/**
	 * @return minimum attack value
	 */
	public int minAttack() { return data.maxAttack(); }
	
	/**
	 * @return maximum attack value
	 */
	public int maxAttack() { return data.maxAttack(); }
	
	/**
	 * @return minimum defense value
	 */
	public int minDefense() { return data.maxDefense(); }
	
	/**
	 * @return maximum defense value
	 */
	public int maxDefense() { return data.maxDefense(); }
	
	/**
	 * @return XP given when defeated
	 */
	public int xp() { return data.xp(); }
	
	/**
	 * Computes a random attack damage value based on enemy stats.
	 *
	 * @return the attack damage dealt
	 */
	public int attackDamage() {
		return rand.nextInt(data.maxAttack() - data.minAttack()+ 1) + data.minAttack();
	}
	
	/**
	 * Computes a random defense value based on enemy stats.
	 *
	 * @return the defense value gained
	 */
	public int baseDefense() { 
		return rand.nextInt(data.maxDefense() - data.minDefense() + 1) + data.minDefense(); 
	}
	
	/**
	 * Reduces enemy HP by the given amount.
	 *
	 * @param damage the damage taken
	 */
	public void receiveDamage(int damage) {
		
		HP = Math.max(HP - damage, 0);
	}
	
	/**
	 * Reduces enemy protection by the given amount.
	 *
	 * @param damage the protection damage taken
	 */
	public void receiveProtectionDamage(int damage) {
	    protection = Math.max(protection - damage, 0);
	}
	//Reset protection
	public void resetProtection() {
		protection = 0;
	}
	
	/**
	 * Resets enemy protection to zero.
	 */
	public boolean addPoison(int amount) {
		if (poisonCounter > 0) return false;
		poisonCounter += amount;
		return true;
	}
	
	/**
	 * Applies poison damage for this turn if the enemy is poisoned.
	 *
	 * @return the poison damage applied
	 */
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
	
	/**
	 * Indicates whether the enemy is poisoned.
	 *
	 * @return true if poison is active, false otherwise
	 */
	public boolean isPoisoned() { return poisonCounter > 0; }
	
	/**
	 * Enemy attacks a hero.
	 *
	 * @param opponent the fighter to attack (must be Hero)
	 * @param item currently unused, kept for interface
	 * @return total damage dealt to hero (including protection)
	 */
	public int attack(Fighter opponent, Item item) {
		Objects.requireNonNull(opponent); 
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
	
	/**
	 * Enemy raises its protection for this turn.
	 *
	 * @param item currently unused, kept for interface
	 * @return new protection value after defense
	 */
	public int defense(Item item) {
		protection += baseDefense();
		return protection;
	}
		

	/**
	 * Returns the enemy action for this turn.
	 * The action is chosen once and kept until it is reset.
	 *
	 * @param curseLaunched true if a curse was already launched this turn
	 * @return the chosen action index
	 */
	public int getAction(boolean curseLaunched) {
		if (action == null) {
			action = actionAnnounced(curseLaunched);
		}
		return action;
	}
	
	public void resetAction() { action = null; }
	
	/**
	 * Decides the enemy's next action.
	 *
	 * @param curseLaunched true if a curse was launched this turn
	 * @return action index (0, 1, or 2)
	 */
	public int actionAnnounced(boolean curseLaunched) {
	  if (curseLaunched) {
	  	return rand.nextInt(2);
	  } else {
	  	return rand.nextInt(3); 
	  }
	}
	
	/**
	 * Generates a curse item for the enemy.
	 *
	 * @param floor current floor number
	 * @return a curse item
	 */
	public Item curse(int floor) {
		var shape = rand.nextInt(2);
		if (shape == 1) {
			if (floor > 0) {
				return ItemRegistry.create(ItemRegistry.CURSE); 
			}
		}
		return ItemRegistry.create(ItemRegistry.CURSE_SAP); 
	}
	
	/**
	 * All predefined enemy.
	 */
	public static final EnemyData[] ALL_ENEMIES ={
		new EnemyData("Small Rat-Wolf", 10, 3, 5, 4, 6, 4),
		new EnemyData("Rat-Wolf", 15, 5, 7, 6, 8, 8),
		new EnemyData("Frog Wizard", 20, 6, 9, 6, 10, 12),
		new EnemyData("Living Shadow", 30, 8, 11, 6, 10, 16),
		new EnemyData("Queen Bee", 40, 9, 13, 7, 11, 20)
	};
	
}
