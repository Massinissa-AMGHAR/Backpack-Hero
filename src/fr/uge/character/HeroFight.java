package fr.uge.character;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;

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
  
  public HeroFight(int HP, int HPMAX, int protection, int energy, int manaMax, int mana) {
  	this.HP = HP; 
  	this.HPMAX = HPMAX; 
  	this.protection = protection; 
  	this.energy = energy;
    this.mana = mana;
    this.manaMax = manaMax;
  }
  
  public int HP() { return HP; } 
  public int HPMAX() { return HPMAX; }
  public int protection() { return protection; }
  public int energy() { return energy; }
  public int mana() { return mana; }
  public int manaMax() { return manaMax; }
  public int cursePenalty() {return cursePenalty;}
  public int cursePenaltyStacks() {return cursePenaltyStacks;}
  
  
  public void receiveDamage(int damage) {
  	int reduceDamage = Math.max(damage - armorValue, 0);
  	HP = Math.max(HP - reduceDamage, 0);
  }
	public void receiveProtectionDamage(int damage) { protection = Math.max(protection - damage, 0);}
  public void resetProtection() {protection = 0;}
  public void resetArmor() { armorValue = 0; }
  public void resetMana() { mana = manaMax; }
  public void addEnergy(int amount) { energy += amount; }
  public void resetEnergy() { energy = 3; }
  
  public void hpmaxAugmentation() {
  	HPMAX += 2; 
  }
  
  public void addCursePenalty() {
  	cursePenaltyStacks++; 
  	cursePenalty = 2; 
  }
  
  public boolean hasCursePenalty() { return cursePenalty > 0; }
  
  public boolean applyCursePenalty() {
  	if(hasCursePenalty() && cursePenaltyStacks > 0) {
  		receiveDamage(3 * cursePenaltyStacks); 
  		return true; 
  	}
  	return false; 
  }
  
  public void endFightPenalty() {
  	if(cursePenalty > 0) {
  		cursePenalty--;
  		if(cursePenalty == 0) {
  			cursePenaltyStacks = 0; 
  		}
  	}
  }
  public void heal(int hpBonus) {
		HP += hpBonus; 
		if(HP > HPMAX) {
			HP = HPMAX; 
		}
	}
  
  public void equipArmor(Item item) {
  	if (item.type() != ItemType.ARMOR) {
  		return;
  	}
  	armorValue = item.defense();
  }
  
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
