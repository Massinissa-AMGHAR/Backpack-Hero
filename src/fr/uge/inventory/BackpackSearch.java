package fr.uge.inventory;

public record BackpackSearch(Backpack backpack) {
	
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
