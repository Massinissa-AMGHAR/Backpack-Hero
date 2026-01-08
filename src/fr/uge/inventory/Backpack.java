package fr.uge.inventory;

import java.util.List;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Represents the hero's backpack.
*/
public class Backpack {
	/** Grid containing all items */
  private ArrayList<ArrayList<Item>> grid;  
  /** Unlocked slots in the backpack */
  private final boolean[][] unlocked; 
  /** Maximum number of lines */
  private final int maxLines = 5;
  /** Maximum number of columns */
  private final int maxColumns = 7; 
  /** Indicates if unlock mode is active */
  public boolean unlockMode = false;
  /** Number of unlocked slots */
  private int unlockCount = 0;
  /** Manages item placement logic */
  private final BackpackPlacement placement; 
  /** Manages item search logic */
  private final BackpackSearch search; 
  
  /**
   * Creates a new backpack with a grid and default unlocked area.
   */
  public Backpack() {
    this.grid = new ArrayList<>();
    this.unlocked = new boolean[maxLines][maxColumns]; 
    for(int i = 0; i < maxLines; i++) {
      ArrayList<Item> line = new ArrayList<>(); 
      for(int j = 0; j < maxColumns; j++) {
        line.add(null); 
        
      }
      grid.add(line); 
    }
    for(int i = 1; i < 4; i++) {
    	for(int j = 2; j < 5; j++) {
    		unlocked[i][j] = true; 
    	}
    }
    this.placement = new BackpackPlacement(this);
    this.search = new BackpackSearch(this); 
  }
  
  /**
   * Returns the backpack item grid.
   *
   * @return the grid containing all items
   */
  public ArrayList<ArrayList<Item>> grid(){return grid;}
  /**
   * Returns the maximum number of lines.
   *
   * @return the number of lines
   */
  public int maxLines() { return maxLines; }
  /**
   * Returns the maximum number of columns.
   *
   * @return the number of columns
   */
  public int maxColumns() { return maxColumns; }
  /**
   * Returns the unlocked slots grid.
   *
   * @return a 2D array of unlocked slots
   */
  public boolean[][] unlocked() { return unlocked; }; 

  /**
   * Checks if an item can be placed at a specific position.
   * @param item the item to place
   * @param line the row in the backpack
   * @param column the column in the backpack
   * @return true if the item can be placed, false otherwise
   */
  public boolean canPlaceItem(Item item, int line, int column) { return placement.canPlaceItem(item, line, column); }
  /**
   * Checks if a curse item can be placed at a specific position.
   * @param item the curse item
   * @param line the row in the backpack
   * @param column the column in the backpack
   * @return true if the curse can be placed, false otherwise
   */
  public boolean canPlaceCurse(Item item, int line, int column) { return placement.canPlaceCurse(item, line, column); }
  /**
   * Checks if there is at least one free slot in the backpack.
   * @return true if a free slot exists, false otherwise
   */
  public boolean caseFree() { return placement.caseFree(maxLines, maxColumns);}
  /**
   * Adds an item to the backpack at a specific position.
   * @param item the item to add
   * @param line the row where the item will be placed
   * @param column the column where the item will be placed
   */
  public void add(Item item, int line, int column) { placement.add(item, line, column);}
  /**
   * Deletes an item from the backpack.
   * @param item the item to delete
   */
  public void delete(Item item) { placement.delete(item);}
  /**
   * Consumes a number of mana stones from the backpack.
   * @param amount the number of mana stones to consume
   * @return true if the stones were successfully consumed, false otherwise
   */
  public boolean consumeManaStones(int amount) { return placement.consumeManaStones(amount); }
  /**
   * Returns the horizontal length occupied by an item at a position.
   * @param line the row of the item
   * @param column the column of the item
   * @return the length in columns
   */
  public int lengthLine(int line, int column) { return placement.lengthLine(line, column);}
  /**
   * Returns the vertical length occupied by an item at a position.
   * @param line the row of the item
   * @param column the column of the item
   * @return the length in rows
   */
  public int lengthColumn(int line, int column) { return placement.lengthColumn(line, column);}
  
  /**
   * Returns the item at the given position in the backpack.
   * @param line the row to check
   * @param column the column to check
   * @return the item at the position, or null if empty
   */
  public Item choice(int line, int column) {return search.choice(line, column); }
  /**
   * Finds the gold item in the backpack.
   * @return the gold item, or null if not found
   */
  public Item findGoldItem() { return search.findGoldItem();}
  /**
   * Finds the mana item in the backpack.
   * @return the mana item, or null if not found
   */
  public Item findManaItem() { return search.findManaItem(); }
  /**
   * Finds the key item in the backpack.
   * @return the key item, or null if not found
   */
  public Item findKeyItem() { return search.findKeyItem(); }
  /**
   * Returns the total number of mana stones in the backpack.
   * @return the number of mana stones
   */
  public int findManaCount() { return search.findManaCount(); }
  /**
   * Returns how many backpack cases can currently be unlocked.
   * @return the number of cases left to unlock
   */
  public int unlockCount() { return unlockCount; }
  /**
   * Checks if the backpack is in unlock mode.
   * @return true if unlock mode is active, false otherwise
   */
  public boolean isUnlockMode() { return unlockMode; }
  /**
   * Sets whether the backpack is in unlock mode.
   * @param bool true to enable unlock mode, false to disable
   */
  public void setUnlockMode(boolean bool) { unlockMode = bool; }
  /**
   * Sets the number of backpack cases that can be unlocked.
   * @param count the number of cases to unlock
   */
  public void setUnlockCount(int count) { unlockCount = count; }
  
  /**
   * Returns all items currently in the backpack.
   *
   * @return a list of all non-null items
   */
  public List<Item> allItems() {
  	List<Item> items = new ArrayList<>();
  	for (var line:  grid) {
  		for (var item : line) {
  			if (item != null) {
  				items.add(item);
  			}
  		}
  	}
  	return items;
  }
  
  /**
   * Resets bonus damage for all items in the backpack.
   */
  public void resetItemsBonusDamage() {
  	for (var item : allItems()) {
  		item.resetBonusDamage();
  	}
  }
  
  /**
   * Returns the number of cases to unlock.
   *
   * @return 3 or 4 randomly
   */
  public int casesToUnlock() {
  	var r = new Random().nextInt(2);
  	if (r == 0) return 3;
  	return 4;
  }
  
  /**
   * Uses one unlock case if unlock mode is active.
   */
  public void useUnlockCase() {
  	if (unlockMode && unlockCount > 0) {
  		unlockCount--;
  		if (unlockCount <= 0) {
  	    unlockMode = false;
  		}
  	}
  }
  
  /**
   * Tries to unlock a case at the given position.
   *
   * @param line the line of the case
   * @param column the column of the case
   * @return an error message or null if successful
   */
  public String tryUnlockCase(int line, int column) {
    if (!inside(line, column)) return "The case is not in the backpack";
    if (unlocked[line][column]) return "The case is already unlocked";
    if (!isAdjacent(line, column)) return "Cases must be adjacent";
    unlocked[line][column] = true;
    return null;
  }
  
  /**
   * Checks if the given position is inside the backpack bounds.
   *
   * @param line the line index
   * @param column the column index
   * @return true if inside bounds, false otherwise
   */
  private boolean inside(int line, int column) {
  	if(line >= 0 && line < maxLines && column >= 0 && column < maxColumns)return true; 
  	return false; 
  }
  
  /**
   * Checks if a case is adjacent to an already unlocked case.
   *
   * @param line the line index of the case
   * @param column the column index of the case
   * @return true if at least one adjacent case is unlocked
   */
  private boolean isAdjacent(int line, int column) {
  	if(inside(line - 1, column) && unlocked[line - 1][column]) return true; 
  	if(inside(line + 1, column) && unlocked[line + 1][column])return true; 
  	if(inside(line, column - 1)&& unlocked[line][column - 1]) return true; 
  	if(inside(line, column + 1) && unlocked[line][column + 1]) return true; 
  	return false; 
  }
  
  /**
   * Counts the number of distinct curse items in the backpack.
   *
   * @return the number of unique curses
   */
  public int curseCount() {
  	return (int) grid.stream().
			flatMap(List::stream)
			.filter(Objects::nonNull)
			.filter(item -> item.type() == ItemType.CURSE)
      .map(Item::id)                  
      .distinct()                     
      .count();      
  }
  
  private boolean isHorizontal(int i, int j, int startX, int startY, int width, int height) {
  	return (i >= startY && i < startY + height) && (j == startX - 1 || j == startX + width);
  }
  
  private boolean isVertical(int i, int j, int startX, int startY, int width, int height) {
  	return (j >= startX && j < startX + width) && (i == startY - 1 || i == startY + height);
  }
  
  /**
   * Returns all items adjacent to the given item.
   *
   * @param item the item to check around
   * @return a list of adjacent items
   */
  public List<Item> getAdjacentItems(Item item) {
  	List<Item> adjacents = new ArrayList<>();
  	var startX = item.leftPlacementX();
  	var startY = item.leftPlacementY();
  	var width = item.width();
  	var height = item.height();
    for (int i = startY - 1; i <= startY + height; i++) {
      for (int j = startX - 1; j <= startX + width; j++) {
        if (i < 0 || i >= maxLines || j < 0 || j >= maxColumns) continue;
        if (i >= startY && i < startY + height && j >= startX && j < startX + width) continue;
        if (!isHorizontal(i, j, startX, startY, width, height) && !isVertical(i, j, startX, startY, width, height)) continue; 
        Item adj = grid.get(i).get(j);
        if (adj != null) {
          if (!adjacents.contains(adj)) {
              adjacents.add(adj);
          }
        }
      }
   } 
   return adjacents;
  }

  /**
   * Calculates the total gold cost of all items in the backpack.
   *
   * @return the sum of all item gold costs
   */
  public int totalPrice() {
  	int sum = 0; 
  	for(var line : grid) {
  		for(var item : line) {
  			if(item != null) {
  				sum+= item.goldCost(); 
  			}
  		}
  	}
  	return sum; 
  }  
}
