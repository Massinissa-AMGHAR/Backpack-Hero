package fr.uge.character;
import java.util.ArrayList;
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
  
  @Override
  public String toString(){
    var builder = new StringBuilder(); 
    builder.append("Hello, I am the Merchant ! What do you want to buy in my shop !\n");
    for(var i : shop) {
      builder.append(i.name() + " : ").append(i.goldCost() + "\n"); 
    }
    return builder.toString(); 
  }
}
