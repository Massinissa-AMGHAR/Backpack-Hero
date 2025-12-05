package fr.uge.character;
import java.util.Objects;

import fr.uge.application.*;
import fr.uge.inventory.*;


public class Hero implements Fighter{
  private final String name;
  private int xp; 
  private int level;
  
  public final HeroFight heroFight; 
  public final HeroInventoryManager inventoryManager; 
  public final HeroEconomyManager economyManager; 

  
  public Hero(String name, int HP, int HPMAX, int protection, int energy, int manaMax, int mana, int xp,  int level, Backpack backpack, Interactions interaction) {
    Objects.requireNonNull(name, "name can't be null");
    Objects.requireNonNull(backpack, "backpack can't be null");
    if (HP < 0 || HPMAX < 0 || protection < 0 || energy < 0 || mana < 0 || manaMax < 0 || xp < 0 || level < 0) throw new IllegalArgumentException("Integer must be >= 0");
      this.name = name;  
      this.xp = xp; 
      this.level = level;
     
      this.heroFight = new HeroFight(HP, HPMAX, protection, energy, manaMax, mana); 
      this.inventoryManager = new HeroInventoryManager(backpack, interaction); 
      this.economyManager = new HeroEconomyManager(inventoryManager, heroFight); 
  }
  
	// Infos du héros
  public String name() { return name; }
  public int HP() {return heroFight.HP();}
  public int HPMAX() {return heroFight.HPMAX();}
  public int protection(){return heroFight.protection(); }
  public int energy() {return heroFight.energy(); }
  public int mana() {return heroFight.mana(); }
  public int manaMax() {return heroFight.manaMax(); }
  public int xp() {return xp; }
  public int level() { return level; }
  public Backpack backpack() {return inventoryManager.backpack(); }
 
  public void winXp(Enemy ennemy) {
    Objects.requireNonNull(ennemy, "Ennemy cannot be null");
    this.xp += ennemy.xp();
  }
  
  //Combat du héros
  public int attack(Fighter enemy, Item item) { return heroFight.attack(enemy, item);}
  public int defense(Item item) { return heroFight.defense(item);}
  public int manaBonus(Item item) { return heroFight.manaBonus(item);}
  public void receiveDamage(int damage) {heroFight.receiveDamage(damage);}
  public void receiveProtectionDamage(int damage) {heroFight.receiveProtectionDamage(damage);}
  public void resetProtection() {heroFight.resetProtection();}
  public void resetEnergy() {heroFight.resetEnergy();}
  public void resetMana() {heroFight.resetMana();}
  
  //Gestion argent du héros
  public boolean buy(int cost) {return economyManager.buy(cost); }
  public boolean winGold(int amount) { return economyManager.winGold(amount);}
  public boolean sellItem(Merchant merchant, Item item) {return economyManager.sellItem(merchant, item);}
  public void treat(Healer healer) {economyManager.treat(healer);}
  
  //Gestion backpack
  public void manageBackpack(Items items, boolean isShop) {inventoryManager.manageBackpack(items,isShop);}
  
	@Override
	public String toString() {
		return this.name() + ": " + this.HP() + "/" + this.HPMAX() + " | Mana " + this.mana() + "/" + this.manaMax() + " | Protection: " + this.protection() + " | Energy: " + this.energy(); 
	}

	
}
