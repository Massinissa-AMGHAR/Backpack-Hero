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
  
  public void receiveDamage(int damage) {
  	int reduceDamage = Math.max(damage - armorValue, 0);
  	HP = Math.max(HP - reduceDamage, 0);
  }
	public void receiveProtectionDamage(int damage) { protection = Math.max(protection - damage, 0);}
  public void resetProtection() {protection = 0;}
  public void resetArmor() { armorValue = 0; }
  public void resetMana() { mana = manaMax; }
  public void resetEnergy() {energy = 3; }
  
  public void heal(int hpBonus) {
		HP += hpBonus; 
		if(HP > HPMAX) HP = HPMAX; 
	}
  
  public void equipArmor(Item item) {
  	if (item.type() != ItemType.ARMOR) return;
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
		if (mana > manaMax) mana = manaMax;
	}
	
	public void reduceMana(int amount) {
		mana -= amount;
		if (mana < 0) mana = 0;
	}
	
	public int manaBonus(Item item) {
	  if (mana < item.manaCost() || energy < item.energyCost()) {
	    IO.println("You don't have enough resources to use " + item.name());
		return 0;
	  }
	  mana -= item.manaCost(); 
	  energy -= item.energyCost(); 
	  if(mana < 0) mana = 0; 
	  mana += item.manaGained(); 
	  if(mana > manaMax) mana = manaMax; 
	  
	  return mana;
	}
	
	public int attack(Fighter opponent, Item item) {
		if (!(opponent instanceof Enemy)) throw new IllegalArgumentException("Hero can only attack Ennemy");
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println("You don't have enough resources to use " + item.name());
			return 0;
		}
		Enemy ennemy = (Enemy) opponent;
		int totalDamage = 0;
		int attackDamage = item.damage();
		
		int protection = ennemy.protection();
		int damageToProtection = Math.min(attackDamage, protection);
	    
	  ennemy.receiveProtectionDamage(damageToProtection); 
	    
		attackDamage -= damageToProtection;
		totalDamage += damageToProtection;
		
		if (attackDamage > 0) {
			ennemy.receiveDamage(attackDamage);
			totalDamage += attackDamage;
		}
		energy -= item.energyCost(); 
		mana -= item.manaCost();
		
		return totalDamage;
	}
	
}
