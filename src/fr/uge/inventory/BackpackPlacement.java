package fr.uge.inventory;

import java.util.Objects;

/**
 * Manages item placement logic in the backpack.
 */
public class BackpackPlacement {
	/** The backpack to manage */
	private Backpack backpack; 
	
  /**
   * Creates a new placement handler for a backpack.
   *
   * @param backpack the backpack to manage
   */
	public BackpackPlacement(Backpack backpack) {
		Objects.requireNonNull(backpack); 
		this.backpack = backpack; 
	}
	
	/**
	 * Returns the number of consecutive free cases to the right from the given position.
	 *
	 * @param line the starting line
	 * @param column the starting column
	 * @return the length of free spaces in the line
	 */
	public int lengthLine(int line, int column) {
		int length = 0; 
		for(int j = column; j < backpack.maxColumns(); j++) {
		  if(caseFree(line, j)) {
		  	length++; 
		  }
		  else break;  
	  }
		return length; 
	}
	  
	/**
	 * Returns the number of consecutive free cases downward from the given position.
	 *
	 * @param line the starting line
	 * @param column the starting column
	 * @return the length of free spaces in the column
	 */
	public int lengthColumn(int line, int column) {
		int length = 0; 
	  for(int i = line; i < backpack.maxLines(); i++) {
	  	if(caseFree(i, column)) {
	  		length++; 
	  	}
	    else break; 
	  }
	  return length; 
	}
	 
	/**
	 * Checks if a case in the backpack is free (inside, unlocked, and empty).
	 *
	 * @param line the line index
	 * @param column the column index
	 * @return true if the case is free, false otherwise
	 */
  public boolean caseFree(int line, int column) {
    if(line < 0 || line >= backpack.maxLines() || column < 0 || column >= backpack.maxColumns()) return false;
    if(backpack.grid().get(line).get(column) != null) return false; 
    if(!backpack.unlocked()[line][column])return false; 
    return true; 
  }
	
  /**
   * Checks if an item can be placed at the given position.
   *
   * @param item the item to place
   * @param line the starting line
   * @param column the starting column
   * @return true if all required cases are free, false otherwise
   */
  public boolean canPlaceItem(Item item, int line, int column) {
  	Objects.requireNonNull(item); 
		boolean[][] shape = item.shape(); 
		for(int i = 0; i < item.height(); i++) {
			for(int j = 0; j < item.width(); j++) {
				if(shape[i][j]) {
					if(!caseFree(line + i, column + j)) {
						IO.println("The case is already occupied");
						return false; 
					}
				}
			}
		}
		return true; 
  }
  
  /**
   * Checks if a curse item can be placed at the given position.
   *
   * @param curse the curse item to place
   * @param line the starting line
   * @param column the starting column
   * @return true if the curse can be placed, false otherwise
   */
  public boolean canPlaceCurse(Item curse, int line, int column) {
  	Objects.requireNonNull(curse); 
  	if(line < 0 || column < 0 || line + curse.height() > backpack.maxLines() || column + curse.width() > backpack.maxColumns()) return false;
  	
  	for(int i = 0; i < curse.height(); i++) {
  		for(int j = 0; j < curse.width(); j++) {
  			if (curse.shape()[i][j]) {
  				if (!backpack.unlocked()[line + i][column + j]) {
  					return false;
  				}
  				var current = backpack.grid().get(line + i).get(column + j);
  				if (current != null && current.type() == ItemType.CURSE) {
  					return false;
  				}
  			}
  		}
  	}
  	return true; 
  }
  
  /**
   * Adds an item to the backpack at the given position.
   *
   * @param item the item to add
   * @param line the starting line
   * @param column the starting column
   */
  public void add(Item item, int line, int column) { 
  	Objects.requireNonNull(item); 
  	boolean[][] shape = item.shape(); 
		for(int i = 0; i < item.height(); i++) {
			for(int j = 0; j < item.width(); j++) {
				if(shape[i][j]) {
					backpack.grid().get(line + i).set(column + j, item); 
				}
			}
		}
		item.setLeftPlacement(column, line);
  }
  
  /**
   * Removes an item from the backpack.
   *
   * @param item the item to delete
   */
  public void delete(Item item) {
  	Objects.requireNonNull(item); 
    for(int i = 0; i < backpack.maxLines(); i++) {
      for(int j = 0; j < backpack.maxColumns(); j++) {
      	Item current = backpack.grid().get(i).get(j); 
        if(current != null && current.id() == item.id()) {
        	backpack.grid().get(i).set(j, null); 
        }
      }
    }
  }
  
  /**
   * Consumes a given number of mana stones from the backpack.
   *
   * @param amount the number of mana stones to consume
   * @return true if enough mana stones were consumed, false otherwise
   */
  public boolean consumeManaStones(int amount) {
  	var temp = amount;
  	for (var i = 0; i < backpack.maxLines(); i++) {
  		for (var j = 0; j < backpack.maxColumns(); j++) {
  			var item = backpack.grid().get(i).get(j);
  			if (item != null && item.type() == ItemType.MANA) {
  				delete(item);
  				temp--;
  				if (temp == 0) {
  					return true;
  				}
  			}
  		}
  	}
  	return false;
  }
}
