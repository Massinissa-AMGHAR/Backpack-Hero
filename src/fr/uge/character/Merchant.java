package fr.uge.character;
import java.util.ArrayList;
import java.util.Scanner; 
import java.util.Random;

import fr.uge.inventory.*;


public class Merchant {
  private final ArrayList<Item> shop; 
  private final Random rand; 
  
  public Merchant(){
    this.shop = new ArrayList<Item>();
    this.rand = new Random(); 
    for(int i = 0; i < 3; i++) {
      int position = rand.nextInt(Item.ALL_ITEM.length); 
      shop.add(Item.ALL_ITEM[position]); 
    }
  }
 
  public int goldCost(Item item) {
    return item.goldCost()/2; 
  }
  public ArrayList<Item> shop(){return shop;}
  
  public void interactionBuy(Hero hero, Scanner scanner) {
  	IO.println(this.toString()); 
  	IO.println(hero.name() + " : " + hero.gold() + " gold"); 
  	IO.println("Do you want to buy an item ? (y/no)"); 
  	String choice = scanner.nextLine(); 
  	
  	if(!choice.equals("y")) return; 
  	IO.println("Choose an item index : "); 
  	if(!scanner.hasNextInt()) {
  		IO.println("Invalid input"); 
  		scanner.nextLine(); 
  		return; 
  	}
  	
  	int index = scanner.nextInt();
  	scanner.nextLine(); 
  	
  	if(index < 0 || index >= shop.size()) {
  		IO.println("Invalid index"); 
  		return; 
  	}
  	Item item = shop.get(index); 
  	IO.println("You bought : " + item.name()); 
  	IO.println(hero.backpack().toString()); 
  	
  	IO.println("Choose a place for your item : (line and column)"); 
  	int line = scanner.nextInt(); 
  	int column = scanner.nextInt(); 
  	scanner.nextLine(); 
  	hero.buyItem(item, line, column); 
  	IO.println(hero.backpack().toString()); 
 
  }
  
  public void interactionSell(Hero hero, Scanner scanner) {
  	IO.println("Do you wan to sell an item ? (y/no)"); 
  	String choice = scanner.nextLine(); 
  	
  	if(!choice.equals("y")) {
  		return; 
  	}
  	IO.println(hero.backpack().toString()); 
  	IO.println("Choose an item what you want to sell (line and column)"); 
  	if(!scanner.hasNextInt()) {
  		IO.println("Invalid input"); 
  		scanner.nextLine(); 
  		return; 
  	}
  	int line = scanner.nextInt();
  	
  	if(!scanner.hasNextInt()) {
  		IO.println("Invalid input"); 
  		scanner.nextLine(); 
  		return; 
  	}
  	
  	int column = scanner.nextInt(); 
  	scanner.nextLine(); 
  	
  	Item item = hero.backpack().choice(line, column); 
  	hero.sellItem(this, item); 
  	IO.println(hero.backpack()); 
  	
  }
  @Override
  public String toString(){
    var builder = new StringBuilder(); 
    builder.append("Hello, I am the Merchant ! What do you want to buy in my shop ?\n\n");
    for(var i : shop) {
      builder.append(i.name() + " : ").append(i.goldCost() + "\n"); 
    }
    return builder.toString(); 
  }
}
