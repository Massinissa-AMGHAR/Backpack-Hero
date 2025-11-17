package fr.uge.character;
import fr.uge.inventory.*; 
import java.util.Scanner;

public class Fight {
  private final Hero hero; 
  private final Enemy ennemy; 

  private final Scanner scanner = new Scanner(System.in);

  public Fight(Hero hero, Enemy ennemy) {
    this.hero = hero;
    this.ennemy = ennemy; 
  }

  public void game() {
    hero.resetMana(); 
    IO.println("The fight start between " + hero.name() + " and "+ ennemy.name() + " !\n");
    while(hero.HP() > 0 && ennemy.HP() > 0) {
      int action = ennemy.actionAnnounced();
      hero.resetEnergy();
      heroTurn(); 
      if(ennemy.HP() <= 0) break; 
      ennemyTurn(action); 
    }

    if(hero.HP() <= 0) IO.println("You are dead bro..."); 
    else {
      IO.println(ennemy.name() + " was tchoufara by " + hero.name()); 
      hero.winXp(ennemy);
      IO.println("Hero win " + hero.xp() + " experience points"); 
    }
  }

  private void heroTurn() { 
    IO.println("Hero turn : \n"); 
    IO.println(ennemy + "\n"); 
    IO.println(hero + "\n"); 
    IO.println(hero.backpack()); 

    while(hero.energy() > 0) {
      if (ennemy.HP() <= 0) break;
      Item item = null; 
      while(item == null) {
        IO.println("Choose an item to use (enter line and column) :"); 
        int line = -1;
        int column = -1;
        if (scanner.hasNextInt()) line = scanner.nextInt(); 
        else {
          IO.println("You have to enter a Integer for line !");
          scanner.nextLine();
          continue;
        }
        if (scanner.hasNextInt()) column = scanner.nextInt();
        else {
          IO.println("You have to enter a Integer for column !");
          scanner.nextLine();
          continue;
        }

        try {
          item = hero.backpack().choice(line, column); 
          IO.println(hero.name() + " chooses " + item.name() + " and it costs " + item.energyCost());
        } catch(IllegalArgumentException e){
          IO.println(e.getMessage() + ", please choose again"); 
        }
      }
      useItem(item); 
      IO.println("\nCurrent stats:");
      IO.println(hero);
      IO.println(ennemy);
    }
  }

  private void useItem(Item item) {
    int value; 
    switch(item.type()) {
      case "MELEE", "ARROW", "MAGIC" -> {
        value = hero.attack(ennemy, item); 
        IO.println(hero.name() + " uses " + item.name() + " and deals " + value + " damage to " + ennemy.name() + "!");
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
      int damage = ennemy.attack(hero, null);
      IO.println(ennemy.name() + " attacks and deals " + damage + " damage to " + hero.name() + "!");
    } else {
      ennemy.resetProtection();
      int blocked = ennemy.defense(null);
      IO.println(ennemy.name() + " defends and gains " + blocked + " protection!");
    }
    IO.println("\nCurrent stats:");
    IO.println(hero);
    IO.println(ennemy);
  }
}

