package fr.uge.inventory;

/**
 * Manages item search logic in the backpack.
 *
 * @param backpack the backpack to search in
 */
public record BackpackSearch(Backpack backpack) {
	
  /**
   * Returns the item at a specific position in the backpack.
   *
   * @param line the line index
   * @param column the column index
   * @return the item at the position or null if invalid/locked/empty
   */

	public Item choice(int line, int column) {
	  if(line < 0 || line >= backpack.maxLines() || column < 0 || column >= backpack.maxColumns()) {
	    IO.println("The case is not in the backpack"); 
	    return null; 
	  }
	  
	  if(!backpack.unlocked()[line][column]) {
	  	IO.println("The case is locked"); 
	  	return null; 
	  }
	  
	  if(backpack.grid().get(line).get(column) == null) {
	   IO.println("The case is null"); 
	   return null; 
	  }
	  
	  return backpack.grid().get(line).get(column); 
  }
  
	/**
	 * Finds the first gold item in the backpack.
	 *
	 * @return a gold item if found, otherwise null
	 */
  public Item findGoldItem() {
  	for(int i = 0; i < backpack.maxLines(); i++) {
  		for(int j = 0; j < backpack.maxColumns(); j++) {
  			Item item = backpack.grid().get(i).get(j); 
  			if(item != null && item.isGold()) {
  				return item; 
  			}
  		}
  	}
  	return null; 
  }
  
  /**
   * Finds the first key item in the backpack.
   *
   * @return a key item if found, otherwise null
   */
  public Item findKeyItem() {
  	for (int i = 0; i < backpack.maxLines(); i++) {
  		for (int j = 0; j < backpack.maxColumns(); j++) {
  			var item = backpack.grid().get(i).get(j);
  			if (item != null && item.type() == ItemType.KEY) {
  				return item;
  			}
  		}
  	}
  	return null;
  }
  
  /**
   * Finds the first mana item in the backpack.
   *
   * @return a mana item if found, otherwise null
   */

  public Item findManaItem() {
  	for(int i = 0; i < backpack.maxLines(); i++) {
  		for(int j = 0; j < backpack.maxColumns(); j++) {
  			Item item = backpack.grid().get(i).get(j); 
  			if(item != null && item.isMana()) {
  				return item; 
  			}
  		}
  	}
  	return null; 
  }
  
  /**
   * Counts the total mana gained from all mana items in the backpack.
   *
   * @return the total mana amount
   */
  public int findManaCount() {
		var manaStones = 0;
		for (var item : backpack().allItems()) {
			if (item.isMana()) {
				manaStones += item.manaGained();
			}
		}
		return manaStones;
  }
}
