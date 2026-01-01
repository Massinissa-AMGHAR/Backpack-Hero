package fr.uge.character;
import java.util.Objects;

import fr.uge.application.*;
import fr.uge.inventory.*;


public class Hero implements Fighter{
  private final String name;
  
  private final HeroProgression progression; 
  private final HeroFight heroFight; 
  private final HeroInventoryManager inventoryManager; 
  private final HeroEconomyManager economyManager; 

  
  public Hero(String name, int HP, int HPMAX, int protection, int energy, int manaMax, int mana, int xp,  int level, Backpack backpack, Interactions interaction) {
    Objects.requireNonNull(name, "name can't be null");
    Objects.requireNonNull(backpack, "backpack can't be null");
    if (HP < 0 || HPMAX < 0 || protection < 0 || energy < 0 || mana < 0 || manaMax < 0 || xp < 0 || level < 0) throw new IllegalArgumentException("Integer must be >= 0");
      this.name = name;  
      
      this.progression = new HeroProgression(); 
      this.heroFight = new HeroFight(HP, HPMAX, protection, energy, manaMax, mana); 
      this.inventoryManager = new HeroInventoryManager(backpack); 
      this.economyManager = new HeroEconomyManager(inventoryManager, heroFight); 
  }
  
  public String name() { return name; }
  public int HP() {return heroFight.HP();}
  public int HPMAX() {return heroFight.HPMAX();}
  public int protection(){return heroFight.protection(); }
  public void equipArmor(Item item) { heroFight.equipArmor(item); } 
  public int energy() {return heroFight.energy(); }
  public int mana() {return heroFight.mana(); }
  public int manaMax() {return heroFight.manaMax(); }
  public int xp() {return progression.xp(); }
  public int nextLevelXp() { return progression.xpThreshold(); }
  public int level() { return progression.level(); }
  public void heal(int heal) { heroFight.heal(heal); }
  public Backpack backpack() {return inventoryManager.backpack(); }
  public HeroProgression progression() { return progression; }
 
  //Progression du héros
  public void winXp(Enemy enemy, Backpack backpack) { progression.winXp(enemy, backpack);}
  
  public int attack(Fighter enemy, Item item) { return heroFight.attack(enemy, item);}
  public int defense(Item item) { return heroFight.defense(item);}
  public void addBlock(int amount) { heroFight.addBlock(amount); }
  public int manaBonus(Item item) { return heroFight.manaBonus(item);}
  public void receiveDamage(int damage) {heroFight.receiveDamage(damage);}
  public void receiveProtectionDamage(int damage) {heroFight.receiveProtectionDamage(damage);}
  public void resetProtection() {heroFight.resetProtection();}
  public void resetArmor() { heroFight.resetArmor(); }
  public void resetEnergy() {heroFight.resetEnergy();}
  public void resetMana() {heroFight.resetMana();}
  public void addMana(int amount) { heroFight.addMana(amount); }
  public void reduceMana(int amount) { heroFight.reduceMana(amount); }
  public boolean buy(int cost) {return economyManager.buy(cost); }
  public boolean winGold(int amount) { return economyManager.winGold(amount);}
  public boolean sellItem(Merchant merchant, Item item) {return economyManager.sellItem(merchant, item);}
  public void treat(Healer healer) {economyManager.treat(healer);}
  
  public void manageBackpack(Items items, boolean isShop) {inventoryManager.manageBackpack(items,isShop);}
  
	@Override
	public String toString() {
		return this.name() + ": " + this.HP() + "/" + this.HPMAX() + " | Mana " + this.mana() + "/" + this.manaMax() + " | Protection: " + this.protection() + " | Energy: " + this.energy() + " | Level: " + this.level(); 
	}

	
}
