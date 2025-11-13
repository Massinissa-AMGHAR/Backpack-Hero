package fr.uge.character;
import fr.uge.inventory.*; 
import java.util.Scanner;

public class Fight {
  private final Hero hero; 
  private final Ennemy ennemy; 
  
  private final Scanner scanner = new Scanner(System.in);
  
  public Fight(Hero hero, Ennemy ennemy) {
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
    
    if(hero.HP() <= 0)IO.println("You are dead bro..."); 
    else {
      IO.println(ennemy.name() + " was tchoufara by " + hero.name()); 
      hero.winXp(ennemy);
      IO.println("Hero win " + hero.xp() + " experience points"); 
    }
  }
  private void heroTurn() { 
    IO.println("Hero turn : \n"); 
    IO.println(ennemy.toString() + "\n"); 
    
    while(hero.energy() > 0) {
     
      IO.println(hero.toString() + "\n"); 
      IO.println(hero.backpack().toString()); 
      Item item = null; 
      while(item == null) {
    	  
        IO.println("Choose an item to use (enter line and column) :"); 
        int line = scanner.nextInt(); 
        int column = scanner.nextInt();
        scanner.nextLine(); 
      
        try {
          item = hero.backpack().choice(line, column); 
          IO.println("You choose " + item.name() + " and it costs " + item.energyCost());
          
        } catch(IllegalArgumentException e){
    	    IO.println(e.getMessage() + ", please choose again"); 
        }
      }
      
      useItem(item); 
      
    }
    
  }
  
  private void useItem(Item item) {
    switch(item.type()) {
      case "MELEE" -> hero.attack(ennemy, item);
      case "ARROW" -> hero.attack(ennemy, item);
      case "SHIELD" -> hero.defense(item);
      case "ARMOR" -> hero.defense(item);
      case "MAGIC" -> hero.attack(ennemy, item);
      case "MANA" -> hero.manaBonus(item);
      default -> IO.println("Unknow item type"); 
    }
  }
  
  private void ennemyTurn(int action) {
    if(action == 0) ennemy.attack(hero, null);
    else ennemy.defense(null);
  }
}
