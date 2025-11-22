package fr.uge.character;
import java.util.ArrayList;
import java.util.Scanner; 
import java.util.Random;

import fr.uge.application.Interactions;
import fr.uge.inventory.*;


public class Merchant implements interactionCharacters {
  private final Items shop; 
  private final Random rand; 
  
  public Merchant(){
    this.shop = new Items(); 
    this.rand = new Random(); 
   
  }
 
  public int goldCost(Item item) {
    int goldCost =  item.goldCost() / 2; 
    IO.println("Merchant : I buy this item for : " + goldCost + " gold"); 
    return goldCost; 
  }
  
  public Items shop(){ return shop; }
  
  public void interactionBuy(Hero hero, Interactions interaction) {
  	IO.println(this.toString()); 
  	IO.println(hero.toString()); 
  	
  	hero.manageBackpack(shop, interaction, true);  
 
  }
  
  public void interactionSell(Hero hero, Interactions interaction) {
  	IO.println("Merchant : I can recup an item at half the price...\n"); 
  	IO.println("Do you wan to sell an item ? (y/n)"); 
  	if(!interaction.requestYesNo())return; 
  	IO.println(hero.backpack().toString()); 
  	
  	IO.println("Choose an item what you want to sell (line and column)"); 
  	
  	int line = interaction.requestInt();
  	int column = interaction.requestInt(); 
  	
  	Item item = hero.backpack().choice(line, column); 
  	hero.sellItem(this, item); 
  	IO.println("You sold " + item.name());  
  	IO.println(hero.backpack()); 
  	
  }
  
  @Override
  public void interaction(Hero hero, Interactions interaction) {
  	interactionBuy(hero, interaction); 
  	interactionSell(hero, interaction); 
  }
  public String toString(){
    return "Merchant : Hello, do you want to buy something in my shop ?\n "; 
  }
}
