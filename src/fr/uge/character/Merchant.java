package fr.uge.character;

import fr.uge.inventory.*;


public class Merchant {
  private final Items shop; 
  
  public Merchant(){
    this.shop = new Items(); 
    shop.randomItems(3);
  }
 
  public int goldCost(Item item) {
    int goldCost =  item.goldCost() / 2; 
    return goldCost; 
  }
  
  public Items shop(){ return shop; }
  
}

