package fr.uge.character;
import java.util.Objects;
import fr.uge.inventory.*;


public class Hero implements Fighter {
  private final String name;
  private int HP;
  private int HPMAX;
  private int protection;
  private int energy;
  private int mana;
  private int manaMax; 
  private int gold;
  private int xp; 
  private int level;
  private final Backpack backpack;
  
  public Hero(String name, int HP, int HPMAX, int protection, int energy, int manaMax, int mana, int gold, int xp,  int level, Backpack backpack) {
    Objects.requireNonNull(name, "name can't be null");
    Objects.requireNonNull(backpack, "backpack can't be null");
    if (HP < 0 || HPMAX < 0 || protection < 0 || energy < 0 || mana < 0 || manaMax < 0 || xp < 0 || gold < 0 || level < 0) throw new IllegalArgumentException("Integer must be >= 0");
      this.name = name;
      this.HP = HP;
      this.HPMAX = HPMAX;
      this.protection = protection;
      this.energy = energy;
      this.mana = mana;
      this.manaMax = manaMax;  
      this.gold = gold;
      this.xp = xp; 
      this.level = level;
      this.backpack = backpack;
  }
	
  public String name() { return name; }
  public int HP() { return HP; } 
  public int HPMAX() { return HPMAX; }
  public int protection() { return protection; }
  public int energy() { return energy; }
  public int mana() { return mana; }
  public int manaMax() { return manaMax; }
  public int gold() { return gold; }
  public int level() { return level; }
  public int xp() {return xp; }
  public Backpack backpack() { return backpack; }
	
	
	public int attack(Fighter opponent, Item item) {
		if (!(opponent instanceof Ennemy)) throw new IllegalArgumentException("Hero can only attack Ennemy");
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println(name + " doesn't have enough resources to use " + item.name());
			return 0;
		}
		Ennemy ennemy = (Ennemy) opponent;
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

	
  public void receiveDamage(int damage) {
    HP = Math.max(HP - damage, 0);
  }
	

	public void receiveProtectionDamage(int damage) {
	    protection = Math.max(protection - damage, 0);
	}
	
	public void resetProtection() {
		protection = 0;
	}
	
	public int defense(Item item) {
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println(name + " doesn't have enough resources to use " + item.name());
			return 0;
		}
		protection += item.defense();
		
		energy -= item.energyCost(); 
		mana -= item.manaCost(); 
		return protection;
	}
	
	public int manaBonus(Item item) {
	  if (mana < item.manaCost() || energy < item.energyCost()) {
	    IO.println(name + " doesn't have enough resources to use " + item.name());
		return 0;
	  }

	  
	  mana += item.manaGained(); 
	  
	  energy -= item.energyCost(); 
	  mana -= item.manaCost();  
	  return mana;
	}
	
  public void winXp(Ennemy ennemy) {
    Objects.requireNonNull(ennemy, "Ennemy cannot be null");
    this.xp += ennemy.xp();
  }
	
  public void resetEnergy() {
  	energy = 3; 
  }
	
  public void resetMana() {
    mana = manaMax; 
  }
  public boolean buy(int cost) {
    if(gold < cost) {
    	IO.println("You don't have enough money !"); 
    	return false; 
    }
    gold -= cost; 
    return true; 
  }
  

	public void buyItem(Item item, int line, int column) {
	  Objects.requireNonNull(item, "Item cannot be null");
	  if (buy(item.goldCost())) {
	  	this.backpack.add(item, line, column);
	  }
	}

	public void sellItem(Merchant merchant, Item item) {
		Objects.requireNonNull(merchant, "Merchant cannot be null");
	  Objects.requireNonNull(item, "Item cannot be null");
	  this.backpack.delete(item);
	  gold += merchant.goldCost(item);
	}

	public void treat(Healer healer) {
		Objects.requireNonNull(healer, "Healer cannot be null");
	  if (buy(healer.price())) {
	  	HP += healer.hp();
	  }
	}

	@Override
	public String toString() {
		return this.name() + ": " + this.HP() + "/" + this.HPMAX() + " | Mana " + this.mana + "/" + this.manaMax() + "| Protection: " + this.protection() + " | Energy: " + this.energy();
	}

	
}
