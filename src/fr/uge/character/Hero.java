package fr.uge.character;
import java.util.Objects;

import fr.uge.inventory.*;


public class Hero implements Fighter{
  private final String name;
  
  private final HeroProgression progression; 
  private final HeroFight heroFight; 
  private final HeroInventoryManager inventoryManager; 

  
  public Hero(String name, int HP, int HPMAX, int protection, int energy, int manaMax, int mana, int xp,  int level, Backpack backpack) {
    Objects.requireNonNull(name, "name can't be null");
    Objects.requireNonNull(backpack, "backpack can't be null");
    if (HP < 0 || HPMAX < 0 || protection < 0 || energy < 0 || mana < 0 || manaMax < 0 || xp < 0 || level < 0) throw new IllegalArgumentException("Integer must be >= 0");
      this.name = name;  
      
      this.progression = new HeroProgression(this); 
      this.heroFight = new HeroFight(HP, HPMAX, protection, energy, manaMax, mana); 
      this.inventoryManager = new HeroInventoryManager(backpack); 
  }
  
  public String name() { return name; }
  public int HP() { return heroFight.HP();}
  public boolean isDead() { return heroFight.HP() == 0;}
  public int HPMAX() { return heroFight.HPMAX();}
  public int protection(){ return heroFight.protection(); }
  public void equipArmor(Item item) { heroFight.equipArmor(item); } 
  public int energy() { return heroFight.energy(); }
  public int mana() { return heroFight.mana(); }
  public int manaMax() { return heroFight.manaMax(); }
  public void addCursePenalty() { heroFight.addCursePenalty(); }
  public boolean hasCursePenalty() { return heroFight.hasCursePenalty(); }
  public boolean applyCursePenalty() { return heroFight.applyCursePenalty(); } 
  public void endFightPenalty() { heroFight.endFightPenalty();}
  public int xp() { return progression.xp(); }
  public int nextLevelXp() { return progression.levelXP(); }
  public int level() { return progression.level(); }
  public void heal(int heal) { heroFight.heal(heal); }
  public Backpack backpack() { return inventoryManager.backpack(); }
  public HeroProgression progression() { return progression; }
  public int cursePenalty() { return heroFight.cursePenalty();}
  public int cursePenaltyStacks() { return heroFight.cursePenaltyStacks();}
  public void hpmaxAugmentation() { heroFight.hpmaxAugmentation();}
  
  public int attack(Fighter enemy, Item item) { return heroFight.attack(enemy, item);}
  public int defense(Item item) { return heroFight.defense(item);}
  public void addBlock(int amount) { heroFight.addBlock(amount); }
  public void receiveDamage(int damage) { heroFight.receiveDamage(damage);}
  public void receiveProtectionDamage(int damage) { heroFight.receiveProtectionDamage(damage);}
  public void resetProtection() { heroFight.resetProtection();}
  public void resetArmor() { heroFight.resetArmor(); }
  public void resetEnergy() { heroFight.resetEnergy();}
  public void addEnergy(int amount) { heroFight.addEnergy(amount); }
  public void resetMana() { heroFight.resetMana();}
  public void addMana(int amount) { heroFight.addMana(amount); }
  public void reduceMana(int amount) { heroFight.reduceMana(amount); }  
  public int score() {return heroFight.HPMAX() * 10 + inventoryManager.backpack().totalPrice(); }
}
