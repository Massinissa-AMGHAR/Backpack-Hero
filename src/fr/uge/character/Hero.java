package fr.uge.character;
import java.util.Objects;

import fr.uge.inventory.*;

/**
 * Represents the player character and implements Fighter.
 */
public class Hero implements Fighter{
	
	/** Hero name. */
  private final String name; 
  
  /** Manages hero level and experience. */
  private final HeroProgression progression;
  
  /** Manages hero combat stats and fight logic. */
  private final HeroFight heroFight; 
  
  /** Manages hero inventory and items. */
  private final HeroInventoryManager inventoryManager; 

  /**
   * Creates a new hero with all stats and inventory.
   *
   * @param name hero's name
   * @param HP current health points
   * @param HPMAX maximum health points
   * @param protection current protection
   * @param armor armor value
   * @param energy current energy
   * @param manaMax maximum mana
   * @param mana current mana
   * @param xp experience points
   * @param level hero level
   * @param backpack hero's backpack
   */
  public Hero(String name, int HP, int HPMAX, int protection, int armor, int energy, int manaMax, int mana, int xp,  int level, Backpack backpack) {
    Objects.requireNonNull(name, "name can't be null");
    Objects.requireNonNull(backpack, "backpack can't be null");
    if (HP < 0 || HPMAX < 0 || protection < 0 || energy < 0 || mana < 0 || manaMax < 0 || xp < 0 || level < 0) throw new IllegalArgumentException("Integer must be >= 0");
      this.name = name;  
      
      this.progression = new HeroProgression(this); 
      this.heroFight = new HeroFight(HP, HPMAX, protection, armor, energy, manaMax, mana); 
      this.inventoryManager = new HeroInventoryManager(backpack); 
  }
  
  /**
   * @return the hero's name
   */
  public String name() { return name; }
  /**
   * @return current HP
   */
  public int HP() { return heroFight.HP();}
  /**
   * @return true if HP is 0, false otherwise
   */
  public boolean isDead() { return heroFight.HP() == 0;}
  /**
   * @return maximum HP
   */
  public int HPMAX() { return heroFight.HPMAX();}
  /**
   * @return current protection
   */
  public int protection(){ return heroFight.protection(); }
  /**
   * @return armor value
   */
  public int armor() { return heroFight.armor(); }
  /**
   * @param item the armor item to equip
   */
  public void equipArmor(Item item) { heroFight.equipArmor(item); } 
  /**
   * @return current energy
   */
  public int energy() { return heroFight.energy(); }
  /**
   * @return current mana
   */
  public int mana() { return heroFight.mana(); }
  /**
   * @return maximum mana
   */
  public int manaMax() { return heroFight.manaMax(); }
  /**
   * Adds a curse penalty to the hero.
   */
  public void addCursePenalty() { heroFight.addCursePenalty(); }
  /**
   * @return true if hero has a curse penalty, false otherwise
   */
  public boolean hasCursePenalty() { return heroFight.hasCursePenalty(); }
  /**
   * @return true if penalty was applied, false otherwise
   */
  public boolean applyCursePenalty() { return heroFight.applyCursePenalty(); } 
  /**
   * Reduces fight-related curse penalties at the end of a fight.
   */
  public void endFightPenalty() { heroFight.endFightPenalty();}
  /**
   * @return current XP
   */
  public int xp() { return progression.xp(); }
  /**
   * @return XP required to reach next level
   */
  public int nextLevelXp() { return progression.levelXP(); }
  /**
   * @return hero's level
   */
  public int level() { return progression.level(); }
  /**
   * @param heal amount of HP to restore
   */
  public void heal(int heal) { heroFight.heal(heal); }
  /**
   * @return the backpack
   */
  public Backpack backpack() { return inventoryManager.backpack(); }
  /**
   * @return hero progression
   */
  public HeroProgression progression() { return progression; }
  /**
   * @return curse penalty value
   */
  public int cursePenalty() { return heroFight.cursePenalty();}
  /**
   * @return number of curse stacks
   */
  public int cursePenaltyStacks() { return heroFight.cursePenaltyStacks();}
  /**
   * Increases the hero's maximum HP.
   */
  public void hpmaxAugmentation() { heroFight.hpmaxAugmentation();}
  
  /**
   * Performs an attack on an enemy using an item.
   * @param enemy the target enemy
   * @param item the item used for the attack
   * @return total damage dealt
   */
  public int attack(Fighter enemy, Item item) { return heroFight.attack(enemy, item);}
  /**
   * Uses an item to defend, increasing protection.
   * @param item the item used to defend
   * @return total protection after applying the item
   */
  public int defense(Item item) { return heroFight.defense(item);}
  /**
   * Adds block points to the hero.
   * @param amount number of block points to add
   */
  public void addBlock(int amount) { heroFight.addBlock(amount); }
  /**
   * Reduces hero's HP.
   * @param damage amount of damage received
   */
  public void receiveDamage(int damage) { heroFight.receiveDamage(damage);}
  /**
   * Reduces hero's protection.
   * @param damage amount of protection damage received
   */
  public void receiveProtectionDamage(int damage) { heroFight.receiveProtectionDamage(damage);}
  /**
   * Resets hero's protection to 0.
   */
  public void resetProtection() { heroFight.resetProtection();}
  /**
   * Resets hero's armor value to 0.
   */
  public void resetArmor() { heroFight.resetArmor(); }
  /**
   * Resets hero's energy to default.
   */
  public void resetEnergy() { heroFight.resetEnergy();}
  /**
   * Adds energy points to the hero.
   * @param amount amount of energy to add
   */

  public void addEnergy(int amount) { heroFight.addEnergy(amount); }
  /**
   * Resets hero's mana to 0.
   */
  public void resetMana() { heroFight.resetMana();}
  /**
   * Adds mana points to the hero.
   * @param amount amount of mana to add
   */
  public void addMana(int amount) { heroFight.addMana(amount); }
  /**
   * Reduces hero's mana by a specified amount.
   * @param amount amount of mana to reduce
   */
  public void reduceMana(int amount) { heroFight.reduceMana(amount); }  
  /**
   * Calculates the hero's score based on maximum HP and backpack total price.
   * @return the hero's score
   */
  public int score() {return heroFight.HPMAX() * 5 + inventoryManager.backpack().totalPrice(); }
}
