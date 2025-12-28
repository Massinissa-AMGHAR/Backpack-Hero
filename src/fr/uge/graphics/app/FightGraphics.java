package fr.uge.graphics.app;

import java.time.Instant;

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
	private Items lootItems = new Items(); 
	
	private final boolean[] enemyDefeatedMessageShown;
	
	public FightGraphics(Hero hero, Enemies enemies, BattleView battleView) {
		this.hero = hero;
		this.enemies = enemies;
		this.battleView = battleView;
		hero.resetEnergy();
		this.enemyDefeatedMessageShown = new boolean[enemies.getEnemies().size()];
		if (enemies.getEnemies().size() == 1) battleView.messageView().showMessage("The fight starts between " + hero.name() + " and " + enemies.getEnemies().get(0).name() + "!", 1000);
		else battleView.messageView().showMessage("The fight starts between " + hero.name() + " and " + enemies.getEnemies().size() + " enemies !", 1000);
	}
	
	public int getGoldReward() {
		return enemies.getEnemies().size();
	}
	
	public boolean isFightOver() {
    if (hero.HP() <= 0) return true;
    for (var enemy : enemies.getEnemies()) {
    	if (enemy.HP() > 0) return false;
    }
    return true;
	}
	
  public boolean isHeroTurn() {
    return heroTurn;
  }
  
  public void endHeroTurn() {
  	if (!heroTurn || isFightOver()) return;
  	heroTurn = false;
  }
  
	public void heroUseItem(Item item, Enemy enemy) {
    if (!heroTurn || isFightOver() || item == null || enemy == null || enemy.HP() <= 0) return;
    int value;
    switch(item.type()) {
	    case "MELEE", "ARROW", "MAGIC" -> {
        value = hero.attack(enemy, item);
        battleView.messageView().showMessage(hero.name() + " uses " + item.name() + " and deals " + value + " damage to "  + enemy.name() + "!", 1000);
	    }
	    case "SHIELD", "ARMOR" -> {
        value = hero.defense(item);
        battleView.messageView().showMessage(hero.name() + " blocks " + value + " damage!", 1000);
	    }
	    case "MANA" -> {
        value = hero.manaBonus(item);
        battleView.messageView().showMessage(hero.name() + " recovers " + value + " mana!", 1000);
	    }
    }
    if (hero.energy() <= 0) heroTurn = false;
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
  
  public void checkEndFight() {
    if (fightEnded) return;
    if (!isFightOver()) return;
    fightEnded = true;
    heroTurn = false;
    if (heroWon()) {
      battleView.messageView().showMessage(hero.name() + " wins the fight!", 2000);
      if (hero.progression().hasLeveledUpDuringFight()) {
        int cases = hero.backpack().casesToUnlock();
        hero.backpack().setUnlockCount(cases);
        hero.backpack().setUnlockMode(true);
        battleView.messageView().showMessage("You can unlock " + cases + " backpack cases!", 4000);
        hero.progression().resetLevelUpFlag(); 
      } else {
      	lootPhase = true;
        battleView.messageView().showMessage("Choose your loot !!", 4000);

      }
    } else {
        battleView.messageView().showMessage(hero.name() + " was defeated...", 2000);
        battleView.messageView().showMessage("Returning to the map...", 4000);
        battleView.setReadyToReturnToMap(true);
    }
  }

  public boolean isLootPhase() {return lootPhase;}
	public void setLootPhase(boolean bool) { this.lootPhase = bool; }
  public Items lootItems() {return lootItems;}
	
	public boolean heroWon() {
	    if (hero.HP() <= 0) return false;
	    for (var enemy : enemies.getEnemies()) {
	    	if (enemy.HP() > 0) return false;
	    }
	    return true;
	}
	
	public void enemyAction() {
    if (isFightOver() || heroTurn) return;
    var time = 0;
    for (var enemy : enemies.getEnemies()) {
    	if (enemy.HP() <= 0) continue;
    	int action = enemy.actionAnnounced();
    	if (action == 0) {
    		int damage = enemy.attack(hero, null);
    		battleView.messageView().showMessage(enemy.name() + " attacks and deals " + damage + " damage !", 1000 + time);
    	} else {
    		int blocked = enemy.defense(null);
    		battleView.messageView().showMessage(enemy.name() + " defends and gains " + blocked + " protection !", 1000 + time);
    	}
    	time += 1000; 
    }
    heroTurn = true;
    hero.resetEnergy();
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
