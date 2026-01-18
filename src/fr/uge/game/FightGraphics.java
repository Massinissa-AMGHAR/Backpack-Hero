package fr.uge.game;


import java.util.Objects;

import fr.uge.character.*;
import fr.uge.graphics.view.*;
import fr.uge.inventory.*;
import fr.uge.map.Dungeon;

/**
 * Manages the graphical interface and state of a fight.
 */
public class FightGraphics {
	/**
	 * Visual representation of the battle
	 */
	private final BattleView battleView;
	/**
	 * The hero participating in the fight
	 */
	private final Hero hero;
	/**
	 * Enemies in the current fight
	 */
	private final Enemies enemies; 
	/**
	 * Dungeon of the game
	 */
	private final Dungeon dungeon;
	
	private boolean heroTurn = true;
	private boolean fightEnded = false;
	private boolean lootPhase = false; 
	private Items lootItems = new Items(); 
	/**
	 * Curse item expected from enemies
	 */
	private Item expectedCurse;
	
	private final boolean[] enemyDefeatedMessageShown;
	private boolean goldRewardConsumed = false; 
	private boolean manaRewardConsumed = false; 
	private boolean curseLaunched = false; 
	private boolean curseShown = false; 
	
	/**
	 * Initializes the fight graphics.
	 *
	 * @param hero the hero participating in the fight
	 * @param enemies the enemies in this fight
	 * @param dungeon the dungeon of the game 
	 * @param battleView the battle view interface
	 */
	public FightGraphics(Hero hero, Enemies enemies, Dungeon dungeon, BattleView battleView) {
		Objects.requireNonNull(hero); 
		Objects.requireNonNull(enemies); 
		Objects.requireNonNull(dungeon); 
		Objects.requireNonNull(battleView); 
		this.hero = hero;
		this.enemies = enemies;
		this.dungeon = dungeon;
		this.battleView = battleView;
		hero.resetEnergy();
		hero.resetArmor();
		hero.backpack().resetItemsBonusDamage();
		this.enemyDefeatedMessageShown = new boolean[enemies.getEnemies().size()];
		if (enemies.getEnemies().size() == 1) {
			battleView.messageView().showMessage("The fight starts between " + hero.name() + " and " + enemies.getEnemies().get(0).name() + "!", 1000);
		} else {
			battleView.messageView().showMessage("The fight starts between " + hero.name() + " and " + enemies.getEnemies().size() + " enemies !", 1000);
		}
	}
	
	/**
	 * Checks if the curse is shown.
	 *
	 * @return true if the curse is shown
	 */
	public boolean curseShown() { return curseShown; }
	
	/**
	 * Checks if the curse is launched.
	 *
	 * @return true if the curse is launched
	 */
	public boolean curseLaunched() { return curseLaunched; }
	
	/**
	 * Sets if the curse is launched.
	 *
	 * @param bool true if the curse is launched
	 */
	public void setCurseLaunced(boolean bool) { curseLaunched = bool; }
	
	/**
	 * Sets if the curse is shown.
	 *
	 * @param value true if the curse is shown
	 */
	public void setCurseShown(boolean value) { curseShown = value;}
	
	/**
	 * Returns the expected curse.
	 *
	 * @return the expected curse
	 */
	public Item expectedCurse() { return expectedCurse;}
	
	/**
	 * Clears the expected curse.
	 */
	public void clearExpectedCurse() { expectedCurse = null;}
	
	/**
	 * Checks if the gold reward was consumed.
	 *
	 * @return true if the gold reward was consumed
	 */
	public boolean goldRewardConsumed() { return goldRewardConsumed; }
	
	/**
	 * Marks the gold reward as consumed.
	 */
	public void consumeGoldReward() { goldRewardConsumed = true; }
	
	/**
	 * Checks if the mana reward was consumed.
	 *
	 * @return true if the mana reward was consumed
	 */
	public boolean manaRewardConsumed() { return manaRewardConsumed; }
	
	/**
	 * Marks the mana reward as consumed.
	 */
	public void consumeManaReward() { manaRewardConsumed = true; }
	
	/**
	 * Returns the battle view.
	 *
	 * @return the battle view
	 */
	public BattleView battleView() { return battleView; }
	
	/**
	 * Returns the gold reward.
	 *
	 * @return the amount of gold reward
	 */
	public int getGoldReward() {
		return 2 * enemies.getEnemies().size();
	}
	
	/**
	 * Checks if the fight has ended.
	 *
	 * @return true if the hero is dead or all enemies are defeated, false otherwise
	 */
	public boolean isFightOver() {
    if (hero.HP() <= 0) {
    	return true;
    }
    for (var enemy : enemies.getEnemies()) {
    	if (enemy.HP() > 0) {
    		return false;
    	}
    }
    return true;
	}
	
	/**
	 * @return true if it is currently the hero's turn
	 */
  public boolean isHeroTurn() {
    return heroTurn;
  }
  
  /**
   * Ends the hero's turn if it is active and the fight is not over.
   */
  public void endHeroTurn() {
  	if (!heroTurn || isFightOver()) {
  		return;
  	}
  	heroTurn = false;

  }
  
  private void applyPoisonToEnemy(Enemy enemy) {
  	if (enemy == null || enemy.HP() <= 0) {
  		return;
  	}
  	var damage = enemy.applyPoison();
  	if (damage > 0) {
  		battleView.messageView().showMessage(enemy.name() + " takes " + damage + " poison damage !", 2000);
  	}
  }
  
  /**
   * Applies all effects of an item to an enemy.
   *
   * @param item the item whose effects are applied
   * @param enemy the enemy affected by the effects
   * @return number of effects applied
   */
  private int applyItemEffects(Item item, Enemy enemy) { 
  	var effects = 0;
  	for (var effect : item.effects()) {
  		effect.apply(hero, enemy, this, item);
  		effects++;
  	}
  	return effects;
  }
  
  /**
   * Applies all interactions of an item to an enemy.
   *
   * @param item the item whose interactions are applied
   * @param enemy the enemy affected by the interactions
   * @return number of interactions applied
   */
  private int applyItemInteractions(Item item, Enemy enemy) {
    int interactions = 0;
    for (var interaction : item.interactions()) {
        interaction.apply(hero, enemy, this, item);
        interactions++;
    }
    return interactions;
  }

  /**
   * Checks if the hero has enough energy and mana to use an item.
   *
   * @param item the item to check
   * @return true if the hero has enough resources, false otherwise
   */
  private boolean hasEnoughResources(Item item) {
    if (hero.energy() < item.energyCost() || hero.mana() < item.manaCost()) {
    	battleView.messageView().showMessage("Not enough energy/mana to use " + item.name(), 1000);
    	return false;
    }
    return true;
  }
  
  /**
   * Consumes mana stones from the hero's backpack to pay for an item's mana cost.
   *
   * @param item the item to use
   * @return true if the mana cost was successfully paid, false otherwise
   */
  private boolean consumeManaStones(Item item) {
  	if (item.manaCost() <= 0) {
  		return true;
  	}
  	var ok = hero.backpack().consumeManaStones(item.manaCost());
  	if (!ok) {
  		return false;
  	}
  	return true;
  }
  
  /**
   * Hero uses a weapon or magic item to attack an enemy.
   *
   * @param item the weapon or magic item used
   * @param enemy the enemy being attacked
   */
  private void useWeaponOrMagic(Item item, Enemy enemy) {
    int value = hero.attack(enemy, item);
    if (value > 0 ) {
      battleView.messageView().showMessage(hero.name() + " uses " + item.name() + " and deals " + value + " damage to "  + enemy.name() + "!", 1000);
    }
  }
  
  /**
   * Hero uses a shield item to block damage.
   *
   * @param item the shield item
   * @param enemy the enemy (not used but required by interface)
   */
  private void useShield(Item item, Enemy enemy) {
    int value = hero.defense(item);
    battleView.messageView().showMessage(hero.name() + " blocks " + value + " damage!", 1000);	
  }
  
  /**
   * Hero equips an armor item for passive damage reduction.
   *
   * @param item the armor item
   * @param enemy the enemy (not used but required by interface)
   */
  private void useArmor(Item item, Enemy enemy) {
  	hero.equipArmor(item);
  	battleView.messageView().showMessage(hero.name() + " uses " + item.name() + " for passive damage reduction of " + item.defense() + "!", 1000);
  }
  
  /**
   * Hero uses a consumable item to heal or regain energy.
   *
   * @param item the consumable item
   */
  private void useConsumable(Item item) {
  	var healedHP = item.hpGained();
  	var energyGained = item.energyGained();
  	if (healedHP > 0) {
  		hero.heal(healedHP);
  		battleView.messageView().showMessage(hero.name() + " uses " + item.name() + " and recovers " + healedHP + " HP !", 1000);
  	}
  	if (energyGained > 0) {
  		hero.addEnergy(energyGained);
  		battleView.messageView().showMessage(hero.name() + " uses " + item.name() + " and gains " + energyGained + " energy !", 1000);
  	}
  }
  
  /**
   * Uses an item based on its type.
   *
   * @param item the item to use
   * @param enemy the enemy affected (if applicable)
   */
  private void useItemByType(Item item, Enemy enemy) {
    switch(item.type()) {
	    case WEAPON, MAGIC -> {
	    	useWeaponOrMagic(item, enemy);
	    }
	    case SHIELD -> {
    		useShield(item, enemy);
	    }
	    case ARMOR -> {
	    	useArmor(item, enemy);
	    }
	    case CONSUMABLE -> {
	    	useConsumable(item);
	    }
	    case GOLD, MANA, KEY, CURSE -> {}
    }
  }
  
  /**
   * Hero uses an item on an enemy during their turn.
   *
   * @param item the item to use
   * @param enemy the enemy affected by the item
   */
	public void heroUseItem(Item item, Enemy enemy) {
    if (!heroTurn || isFightOver() || item == null || enemy == null || enemy.HP() <= 0) return;
    if (!hasEnoughResources(item)) {
    	return;
    }
    if (!consumeManaStones(item)) {
    	return;
    }
    useItemByType(item, enemy);
    applyItemEffects(item, enemy);
    applyItemInteractions(item, enemy);
    applyPoisonToEnemy(enemy);
    if (hero.energy() <= 0) {
    	heroTurn = false;
    }
	}
  
	/**
	 * Checks all enemies and shows a message if any is defeated, granting XP to the hero.
	 */
  public void checkEnemyDefeated() {
    for (var i = 0; i < enemies.getEnemies().size(); i++) {
    	var enemy = enemies.getEnemies().get(i);
    	if (enemy.HP() <= 0 && !enemyDefeatedMessageShown[i]) {
    		battleView.messageView().showMessage(enemy.name() + " was defeated !", 1000);
    		hero.progression().WinXpGraphics(enemy);
    		enemyDefeatedMessageShown[i] = true;
    	}
    }
  }
  
  /**
   * Handles victory actions after a fight ends.
   * If the hero leveled up, unlocks backpack cases and increases HP max.
   * Otherwise, starts the loot phase.
   */
  private void victory() {
    if (hero.progression().hasLeveledUpDuringFight()) {
    	hero.hpmaxAugmentation();
      int cases = hero.backpack().casesToUnlock();
      hero.backpack().setUnlockCount(cases);
      hero.backpack().setUnlockMode(true);
      battleView.messageView().showMessage("You can unlock " + cases + " backpack cases!", 4000);
      hero.progression().resetLevelUpFlag(); 
    } else {
    	lootPhase = true;
      battleView.messageView().showMessage("Choose your loot !!", 4000);
    }
  }
  
  /**
   * Handles defeat actions when the hero loses a fight.
   */
  private void defeat() { 
    battleView.messageView().showMessage(hero.name() + " was defeated...", 2000);
    battleView.setReadyToReturnToMap(true);
  }
  
  /**
   * Checks if the fight has ended and triggers victory or defeat actions.
   */
  public void checkEndFight() {
    if (fightEnded) {
    	return;
    }
    if (!isFightOver()) {
    	return;
    }
    hero.endFightPenalty();
    fightEnded = true;
    heroTurn = false;
    if (heroWon()) {
      if(hero.applyCursePenalty()) {
      	int damage = 3 * hero.cursePenaltyStacks();
      	battleView.messageView().showMessage(damage + " damage from curse penalties !", 2000);
      }
    	victory();
    } else {
    	defeat();
    }
  }

  /**
   * Checks if the loot phase is active.
   *
   * @return true if the loot phase is active
   */
  public boolean isLootPhase() { return lootPhase;}
  
  /**
   * Sets if the loot phase is active.
   *
   * @param bool true if the loot phase is active
   */
	public void setLootPhase( boolean bool) { this.lootPhase = bool; }
	
	/**
	 * Returns the loot items.
	 *
	 * @return the loot items
	 */
  public Items lootItems() { return lootItems;}
	
  /**
   * Checks if the hero won the fight.
   *
   * @return true if all enemies are defeated and the hero is alive
   */
	public boolean heroWon() {
	    if (hero.HP() <= 0) {
	    	return false;
	    }
	    for (var enemy : enemies.getEnemies()) {
	    	if (enemy.HP() > 0) {
	    		return false;
	    	}
	    }
	    return true;
	}
	
	/**
	 * Assigns a curse item for the fight if it exists.
	 *
	 * @param curse the curse item to assign
	 */
	private void giveCurse(Item curse) {
		if(curse == null) {
			return; 
		}
		expectedCurse = curse; 
	}
	
	/**
	 * Applies damage from all active curses on the hero.
	 *
	 * @param time additional delay for the message display
	 */
	private void cursesAttack(int time) {
	 int curseCount = hero.backpack().curseCount(); 
    if(curseCount > 0) {
    	int damage = 2 * curseCount; 
      hero.receiveDamage(damage);
      battleView.messageView().showMessage(damage + " damages from curse!", 1000 + time);
    }
	}
	
	/**
	 * Makes an enemy perform an action based on its choice.
	 *
	 * @param action the action type (0 = attack, 1 = defend, 2 = curse)
	 * @param enemy the enemy performing the action
	 * @param time additional delay for message display
	 */
	private void choiceEnemyAction(int action, Enemy enemy, int time) {
  	switch (action) {
		case 0 -> {
			int damage = enemy.attack(hero, null);
			battleView.messageView().showMessage(enemy.name() + " attacks and deals " + damage + " damage !", 1000 + time);
		}
		case 1 -> {
  		int blocked = enemy.defense(null);
  		battleView.messageView().showMessage(enemy.name() + " defends and gains " + blocked + " protection !", 1000 + time);
		}
		case 2 -> {
			Item curse = enemy.curse(dungeon.currentFloor()); 
			battleView.messageView().showMessage(enemy.name() + " inflicts a curse ", 1000 + time);
			giveCurse(curse); 
		}
   }
	} 
	
	/**
	 * Executes actions for all enemies during their turn.
	 *
	 * @param time initial delay for message display
	 */
	private void playEnemies(int time) {
    for (var enemy : enemies.getEnemies()) {
    	if (enemy.HP() <= 0) {
    		continue;
    	}
    	int action = enemy.getAction(curseLaunched);
    	choiceEnemyAction(action, enemy, time);
    	time += 1000; 
    	enemy.resetAction();
    }
	}
	
	/**
	 * Executes all enemy actions during their turn and updates fight state.
	 */
	public void enemyAction() {
    if (isFightOver() || heroTurn) {
    	return;
    }
    var time = 0;
    for (var enemy : enemies.getEnemies()) {
    	enemy.resetProtection();
    }
    playEnemies(time);
    heroTurn = true;
  	curseShown = false; 
    cursesAttack(time); 
    hero.resetEnergy();
    hero.resetProtection();
	}

	/**
	 * Updates the fight state each frame.
	 * Executes enemy actions if it's their turn and checks for fight end.
	 */
	public void updateFight() {
    if (battleView.messageView().isMessageActive()) {
        return;
    }
    if (!heroTurn && !isFightOver()) {
        enemyAction();
        return;
    }
    checkEnemyDefeated();
    checkEndFight();
	}

	/**
	 * @return the hero participating in the fight
	 */
	public Hero Hero() {
	    return hero;
	}
	
	/**
	 * @return the enemies in the fight
	 */
	public Enemies Enemies() {
	    return enemies;
	}
}
