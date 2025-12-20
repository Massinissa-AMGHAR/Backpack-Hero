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
  private int currentEnemyIndex = 0;	
	private boolean heroTurn = true;
	private boolean fightEnded = false;
	private boolean nextEnemyAnnounced = false;
	private final int levelBeforeFight;
	
	private boolean lootPhase = false; 
	private Items lootItems = new Items(); 
	

	public FightGraphics(Hero hero, Enemies enemies, BattleView battleView) {
		this.hero = hero;
		this.enemies = enemies;
		this.battleView = battleView;
		this.levelBeforeFight = hero.progression().level();
    battleView.messageView().showMessage("The fight starts between " + hero.name() + " and " + currentEnemy().name() + " !", 1000);
	}
	
  public Enemy currentEnemy() {
    if (currentEnemyIndex < enemies.getEnemies().size()) {
        return enemies.getEnemies().get(currentEnemyIndex);
    }
    return null;
  }
  
  public void checkEnemyDefeated() {
    Enemy enemy = currentEnemy();
    if (enemy != null && enemy.HP() <= 0) {
    	battleView.messageView().showMessage(enemy.name() + " was defeated!", 1000);
    	hero.progression().WinXpGraphics(enemy);
      currentEnemyIndex++;
      nextEnemyAnnounced = false;
      return;
    }
    Enemy next = currentEnemy();
    if (next != null && !nextEnemyAnnounced && !battleView.messageView().isMessageActive()) { 
      battleView.messageView().showMessage("A new enemy appears: " + next.name(), 1000);
      nextEnemyAnnounced = true;    
    }
  }
  
  public void checkEndFight() {
    if (fightEnded) return;
    if (!isFightOver()) return;
    fightEnded = true;
    heroTurn = false;
    if (heroWon()) {
    	lootPhase = true; 
      battleView.messageView().showMessage(hero.name() + " wins the fight!", 2000);
      battleView.messageView().showMessage("Choose your loot !", 2000);
      if (hero.progression().hasLeveledUpDuringFight()) {
        int cases = hero.backpack().casesToUnlock();
        hero.backpack().setUnlockCount(cases);
        hero.backpack().setUnlockMode(true);
        battleView.messageView().showMessage("You can unlock " + cases + " backpack cases!", 4000);
        hero.progression().resetLevelUpFlag(); 
      } 
    } else {
        battleView.messageView().showMessage(hero.name() + " was defeated...", 2000);
        battleView.messageView().showMessage("Returning to the map...", 4000);
        battleView.setReadyToReturnToMap(true);
    }
  }

  public boolean isLootPhase() {return lootPhase;}
  
  public Items lootItems() {return lootItems;}
  public boolean isHeroTurn() {
    return heroTurn;
  }
  
	public boolean isFightOver() {
	    return hero.HP() <= 0 || currentEnemy() == null;
	}
	
	public boolean heroWon() {
	    return hero.HP() > 0 && currentEnemy() == null;
	}
	
	public void heroUseItem(Item item) {
    Enemy enemy = currentEnemy();
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

	
	public boolean heroTurnFinished() { return hero.energy() <= 0; }

	public void enemyAction() {
    if (isFightOver() || heroTurn) return;
    int action = currentEnemy().actionAnnounced();
    if (action == 0) {
      int damage = currentEnemy().attack(hero, null);
      battleView.messageView().showMessage(currentEnemy().name() + " attacks and deals " + damage + " damage!", 1000);
    } else {
	      int blocked = currentEnemy().defense(null);
	      battleView.messageView().showMessage(currentEnemy().name() + " defends and gains " + blocked + " protection!", 1000);
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
