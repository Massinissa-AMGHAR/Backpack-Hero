package fr.uge.application; 
import fr.uge.inventory.*; 
import fr.uge.character.*;
import java.util.Scanner;

public class Fight {
  private final Hero hero; 
  private final Enemy enemy; 
  private final Interactions interaction; 

  public Fight(Hero hero, Enemy ennemy, Interactions inter) {
    this.hero = hero;
    this.enemy = ennemy; 
    this.interaction = inter; 
  }

  public boolean game() {
    hero.resetMana(); 
    IO.println("The fight start between " + hero.name() + " and "+ enemy.name() + " !\n");
    while(hero.HP() > 0 && enemy.HP() > 0) {
      int action = enemy.actionAnnounced();
      hero.resetEnergy();
      heroTurn(); 
      if(enemy.HP() <= 0) break; 
      ennemyTurn(action); 
    }

    if(hero.HP() <= 0) {
    	IO.println("You are dead bro..."); 
    	return false; 
    }
    else {
      IO.println(enemy.name() + " was tchoufara by " + hero.name()); 
      hero.winXp(enemy);
      hero.resetMana(); 
      hero.resetEnergy();
      IO.println("Hero wins " + enemy.xp() + " experience points"); 
      IO.println("Hero has " + hero.xp() + " experience points"); 
      return true; 
    }
  }

  private void heroTurn() { 
    IO.println("Hero turn : \n"); 
    IO.println(enemy + "\n"); 
    IO.println(hero + "\n"); 

    while(hero.energy() > 0) {
      if (enemy.HP() <= 0) break;
      Item item = null;
      boolean valid = false; 
      while(!valid) {
      	IO.println(hero.backpack()); 
        IO.println("Choose an item to use (enter line and column) :"); 
        int line = interaction.requestInt(); 
        int column = interaction.requestInt(); 

        item = hero.backpack().choice(line, column); 
        if(item == null) {
          IO.println("Please choose again"); 
        } 
        else {
        	IO.println(hero.name() + " chooses " + item.name() + " and it costs " + item.energyCost());
        	valid = true; 
        }
      }
      useItem(item); 
      IO.println("\nCurrent stats:");
      IO.println(hero);
      IO.println(enemy);
    }
  }

  private void useItem(Item item) {
    int value; 
    switch(item.type()) {
      case "MELEE", "ARROW", "MAGIC" -> {
        value = hero.attack(enemy, item); 
        IO.println(hero.name() + " uses " + item.name() + " and deals " + value + " damage to " + enemy.name() + "!");
      }
      case "SHIELD", "ARMOR" -> {
        value = hero.defense(item);
        IO.println(hero.name() + " uses " + item.name() + " and blocks " + value + " damage!");
      }
      case "MANA" -> {
        value = hero.manaBonus(item);
        IO.println(hero.name() + " uses " + item.name() + " and recovers " + value + " mana!");
      }
      default -> IO.println("Unknown item type");
    }
  }

  private void ennemyTurn(int action) {
    IO.println("Enemy turn : \n"); 
    if(action == 0) {
      int damage = enemy.attack(hero, null);
      IO.println(enemy.name() + " attacks and deals " + damage + " damages to " + hero.name() + "!"); 
    } else {
      enemy.resetProtection();
      int blocked = enemy.defense(null);
      IO.println(enemy.name() + " defends and gains " + blocked + " protection!");
      IO.println(enemy.toString()); 
    }
    IO.println("\nCurrent stats:");
    IO.println(hero);
    IO.println(enemy);
  }
}