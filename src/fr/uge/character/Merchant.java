package fr.uge.character;

import fr.uge.inventory.*;

/**
 * Represents a merchant selling items.
 */
public class Merchant {
	/**
	 * Items available for sale
	 */
  private final Items shop; 
  
  /**
   * Initializes the merchant with 3 random items.
   */
  public Merchant(){
    this.shop = new Items(); 
    shop.randomItems(3);
  }
 
  /**
   * @param item the item to check
   * @return gold cost of the item
   */
  public int goldCost(Item item) {
    int goldCost =  item.goldCost(); 
    return goldCost; 
  }
  
  /**
   * @return the merchant's shop items
   */
  public Items shop(){ return shop; }
  
}

