package fr.uge.graphics.app;


import fr.uge.application.*;

import fr.uge.character.*;
import fr.uge.graphics.view.*;
import fr.uge.inventory.*;

public class FightGraphics {
	private final BattleView battleView;
	private final Hero hero;
  private final Enemies enemies; 
	private boolean heroTurn = true;
	private boolean fightEnded = false;
	private boolean lootPhase = false; 
	private HallOfFame hof = new HallOfFame(); 
	private Items lootItems = new Items(); 
	private Item expectedCurse; 
	
	private final boolean[] enemyDefeatedMessageShown;
	private boolean goldRewardConsumed = false; 
	private boolean manaRewardConsumed = false; 
	private boolean curseLaunched = false; 
	private boolean curseShown = false; 
	
	public FightGraphics(Hero hero, Enemies enemies, BattleView battleView) {
		this.hero = hero;
		this.enemies = enemies;
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
	
	public boolean curseShown() { return curseShown; }
	public boolean curseLaunched() { return curseLaunched; }
	public void setCurseLaunced(boolean bool) { curseLaunched = bool; }
	public void setCurseShown(boolean value) { curseShown = value;}
	public Item expectedCurse() { return expectedCurse;}
	public void clearExpectedCurse() { expectedCurse = null;}
	public boolean goldRewardConsumed() { return goldRewardConsumed; }
	public void consumeGoldReward() { goldRewardConsumed = true; }
	public boolean manaRewardConsumed() { return manaRewardConsumed; }
	public void consumeManaReward() { manaRewardConsumed = true; }
	public BattleView battleView() { return battleView; }
	
	public int getGoldReward() {
		return enemies.getEnemies().size();
	}
	
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
	
  public boolean isHeroTurn() {
    return heroTurn;
  }
  
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
  
  private int applyItemEffects(Item item, Enemy enemy) {
  	var effects = 0;
  	for (var effect : item.effects()) {
  		effect.apply(hero, enemy, this, item);
  		effects++;
  	}
  	return effects;
  }
  
  private int applyItemInteractions(Item item, Enemy enemy) {
    int interactions = 0;
    for (var interaction : item.interactions()) {
        interaction.apply(hero, enemy, this, item);
        interactions++;
    }
    return interactions;
  }

  private boolean hasEnoughResources(Item item) {
    if (hero.energy() < item.energyCost() || hero.mana() < item.manaCost()) {
    	battleView.messageView().showMessage("Not enough energy/mana to use " + item.name(), 1000);
    	return false;
    }
    return true;
  }
  
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
  
  private void useWeaponOrMagic(Item item, Enemy enemy) {
    int value = hero.attack(enemy, item);
    if (value > 0 ) {
      battleView.messageView().showMessage(hero.name() + " uses " + item.name() + " and deals " + value + " damage to "  + enemy.name() + "!", 1000);
    }
  }
  
  private void useShield(Item item, Enemy enemy) {
    int value = hero.defense(item);
    battleView.messageView().showMessage(hero.name() + " blocks " + value + " damage!", 1000);	
  }
  
  private void useArmor(Item item, Enemy enemy) {
  	hero.equipArmor(item);
  	battleView.messageView().showMessage(hero.name() + " uses " + item.name() + " for passive damage reduction of " + item.defense() + "!", 1000);
  }
  
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
  
  private void defeat() {
		int score = hero.score(); 
		hof.addScore(score);
    battleView.messageView().showMessage(hero.name() + " was defeated...", 2000);
    battleView.setReadyToReturnToMap(true);
  }
  
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

  public boolean isLootPhase() { return lootPhase;}
	public void setLootPhase( boolean bool) { this.lootPhase = bool; }
  public Items lootItems() { return lootItems;}
	
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
	
	private void giveCurse(Item curse) {
		if(curse == null) {
			return; 
		}
		expectedCurse = curse; 
	}
	
	private void cursesAttack(int time) {
	 int curseCount = hero.backpack().curseCount(); 
    if(curseCount > 0) {
    	int damage = 2 * curseCount; 
      hero.receiveDamage(damage);
      battleView.messageView().showMessage(damage + " damages from curse!", 1000 + time);
    }
	}
	
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
			Item curse = enemy.curse(); 
			battleView.messageView().showMessage(enemy.name() + " inflicts a curse ", 1000 + time);
			giveCurse(curse); 
		}
   }
	} 
	
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
	
	public void enemyAction() {
    if (isFightOver() || heroTurn) {
    	return;
    }
    var time = 0;
    playEnemies(time);
    heroTurn = true;
  	curseShown = false; 
    cursesAttack(time); 
    hero.resetEnergy();
    hero.resetProtection();
	}

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


	public Hero Hero() {
	    return hero;
	}
	
	public Enemies Enemies() {
	    return enemies;
	}
}
