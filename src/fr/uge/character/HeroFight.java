package fr.uge.character;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

/**
 * Saves and manages the hero's combat-related stats.
 */
public class HeroFight {
	private int HP;
  private int HPMAX;
  private int protection;
  private int energy;
  private int mana;
  private final int manaMax;
  private int cursePenalty = 0; 
  private int cursePenaltyStacks = 0; 
  private int armorValue;
  
  /**
   * Initializes the hero's combat stats.
   *
   * @param HP current health points
   * @param HPMAX maximum health points
   * @param protection current protection
   * @param armor armor value
   * @param energy current energy
   * @param manaMax maximum mana
   * @param mana current mana
   */
  public HeroFight(int HP, int HPMAX, int protection, int armor, int energy, int manaMax, int mana) {
  	this.HP = HP; 
  	this.HPMAX = HPMAX; 
  	this.protection = protection;
  	this.armorValue = armor;
  	this.energy = energy;
    this.mana = mana;
    this.manaMax = manaMax;
  }
  
  public int HP() { return HP; } 
  public int HPMAX() { return HPMAX; }
  public int protection() { return protection; }
  public int armor() { return armorValue; }
  public int energy() { return energy; }
  public int mana() { return mana; }
  public int manaMax() { return manaMax; }
  public int cursePenalty() {return cursePenalty;}
  public int cursePenaltyStacks() {return cursePenaltyStacks;}
  
  /**
   * Reduces hero's HP by incoming damage, considering protection and armor.
   *
   * @param damage amount of damage to receive
   */
  public void receiveDamage(int damage) {
  	int damageLeft = damage;
  	if (protection > 0) {
  		int absorbed = Math.min(protection, damageLeft);
  		protection -= absorbed;
  		damageLeft -= absorbed;
  	}
  	int reduceDamage = Math.max(damageLeft - armorValue, 0);
  	HP = Math.max(HP - reduceDamage, 0);
  }
  
	public void receiveProtectionDamage(int damage) { protection = Math.max(protection - damage, 0);}
  public void resetProtection() {protection = 0;}
  public void resetArmor() { armorValue = 0; }
  public void resetMana() { mana = manaMax; }
  public void addEnergy(int amount) { energy += amount; }
  public void resetEnergy() { energy = 3; }
  
  public void hpmaxAugmentation() {
  	HPMAX += 4; 
  }
  
  public void addCursePenalty() {
  	cursePenaltyStacks++; 
  	cursePenalty = 2; 
  }
  
  public boolean hasCursePenalty() { return cursePenalty > 0; }
  /**
   * Applies curse damage if the hero has active curse stacks.
   *
   * @return true if curse damage was applied, false otherwise
   */
  public boolean applyCursePenalty() {
  	if(hasCursePenalty() && cursePenaltyStacks > 0) {
  		receiveDamage(3 * cursePenaltyStacks); 
  		return true; 
  	}
  	return false; 
  }
  
  /**
   * Reduces curse penalty at the end of a fight.
   */
  public void endFightPenalty() {
  	if(cursePenalty > 0) {
  		cursePenalty--;
  		if(cursePenalty == 0) {
  			cursePenaltyStacks = 0; 
  		}
  	}
  }
  
  /**
   * Heals the hero by a given amount, without exceeding max HP.
   *
   * @param hpBonus amount of HP to restore
   */
  public void heal(int hpBonus) {
		HP += hpBonus; 
		if(HP > HPMAX) {
			HP = HPMAX; 
		}
	}
  
  /**
   * Equips an armor item, updating armor, energy, and mana.
   *
   * @param item the armor item to equip
   */
  public void equipArmor(Item item) {
  	if (item.type() != ItemType.ARMOR) {
  		return;
  	}
  	armorValue = item.defense();
  	energy -= item.energyCost();
  	mana -= item.manaCost();
  }
  
  /**
   * Uses an item to increase protection if resources allow.
   *
   * @param item the item used for defense
   * @return new protection value after applying the item
   */
	public int defense(Item item) {
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println("You don't have enough resources to use " + item.name());
			return 0;
		}
		protection += item.defense();
		energy -= item.energyCost(); 
		mana -= item.manaCost(); 
		return protection;
	}
	
	public void addBlock(int amount) {
		protection += amount;
	}
	
	public void addMana(int amount) {
		mana += amount;
		if (mana > manaMax) {
			mana = manaMax;
		}
	}
	
	public void reduceMana(int amount) {
		mana -= amount;
		if (mana < 0) {
			mana = 0;
		}
	}
	
	private int dealDamage(Enemy enemy, Item item) {
		int totalDamage = 0;
		int attackDamage = item.damage();
		
		int protection = enemy.protection();
		int damageToProtection = Math.min(attackDamage, protection);
	    
	  enemy.receiveProtectionDamage(damageToProtection); 
	    
		attackDamage -= damageToProtection;
		totalDamage += damageToProtection;
		
		if (attackDamage > 0) {
			enemy.receiveDamage(attackDamage);
			totalDamage += attackDamage;
		}
		return totalDamage;
	}
	
	/**
	 * Hero attacks an enemy using an item.
	 *
	 * @param opponent the fighter to attack (must be Enemy)
	 * @param item the item used for the attack
	 * @return total damage dealt to the enemy
	 */
	public int attack(Fighter opponent, Item item) {
		if (!(opponent instanceof Enemy)) {
			throw new IllegalArgumentException("Hero can only attack Ennemy");
		}
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println("You don't have enough resources to use " + item.name());
			return 0;
		}
		Enemy enemy = (Enemy) opponent;
		var totalDamage = dealDamage(enemy, item);
		energy -= item.energyCost(); 
		mana -= item.manaCost();
		return totalDamage;
	}
}
