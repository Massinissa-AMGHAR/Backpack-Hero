package fr.uge.inventory;

import java.util.List;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class Backpack {
  private ArrayList<ArrayList<Item>> grid;  
  private final boolean[][] unlocked; 
  private final int maxLines = 5; 
  private final int maxColumns = 7; 
  public boolean unlockMode = false;
  private int unlockCount = 0;
  private final BackpackPlacement placement; 
  private final BackpackSearch search; 
  
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
  
  public ArrayList<ArrayList<Item>> grid(){return grid;}
  public int maxLines() { return maxLines; }
  public int maxColumns() { return maxColumns; }
  public boolean[][] unlocked() { return unlocked; }; 

  
  public boolean canPlaceItem(Item item, int line, int column) { return placement.canPlaceItem(item, line, column); }
  public boolean canPlaceCurse(Item item, int line, int column) { return placement.canPlaceCurse(item, line, column); }
  public boolean caseFree() { return placement.caseFree(maxLines, maxColumns);}
  public void add(Item item, int line, int column) { placement.add(item, line, column);}
  public void delete(Item item) { placement.delete(item);}
  public boolean consumeManaStones(int amount) { return placement.consumeManaStones(amount); }
  public int lengthLine(int line, int column) { return placement.lengthLine(line, column);}
  public int lengthColumn(int line, int column) { return placement.lengthColumn(line, column);}
  
  
  public Item choice(int line, int column) {return search.choice(line, column); }
  public Item findGoldItem() { return search.findGoldItem();}
  public Item findManaItem() { return search.findManaItem(); }
  public Item findKeyItem() { return search.findKeyItem(); }
  
  public int findManaCount() { return search.findManaCount(); }
  public int unlockCount() { return unlockCount; }
  public boolean isUnlockMode() { return unlockMode; }
  public void setUnlockMode(boolean bool) { unlockMode = bool; }
  public void setUnlockCount(int count) { unlockCount = count; }
  
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
  
  public void resetItemsBonusDamage() {
  	for (var item : allItems()) {
  		item.resetBonusDamage();
  	}
  }
  
  public int casesToUnlock() {
  	var r = new Random().nextInt(2);
  	if (r == 0) return 3;
  	return 4;
  }
  
  public void useUnlockCase() {
  	if (unlockMode && unlockCount > 0) {
  		unlockCount--;
  		if (unlockCount <= 0) {
  	    unlockMode = false;
  		}
  	}
  }
  
  
  public String tryUnlockCase(int line, int column) {
    if (!inside(line, column)) return "The case is not in the backpack";
    if (unlocked[line][column]) return "The case is already unlocked";
    if (!isAdjacent(line, column)) return "Cases must be adjacent";
    unlocked[line][column] = true;
    return null;
  }
  
  private boolean inside(int line, int column) {
  	if(line >= 0 && line < maxLines && column >= 0 && column < maxColumns)return true; 
  	return false; 
  }
  
  private boolean isAdjacent(int line, int column) {
  	if(inside(line - 1, column) && unlocked[line - 1][column]) return true; 
  	if(inside(line + 1, column) && unlocked[line + 1][column])return true; 
  	if(inside(line, column - 1)&& unlocked[line][column - 1]) return true; 
  	if(inside(line, column + 1) && unlocked[line][column + 1]) return true; 
  	return false; 
  }
  
  public int curseCount() {
  	return (int) grid.stream().
  			flatMap(List::stream)
  			.filter(Objects::nonNull)
  			.filter(item -> item.type() == ItemType.CURSE)
  			.distinct().count();
  }
  
  private boolean isHorizontal(int i, int j, int startX, int startY, int width, int height) {
  	return (i >= startY && i < startY + height) && (j == startX - 1 || j == startX + width);
  }
  
  private boolean isVertical(int i, int j, int startX, int startY, int width, int height) {
  	return (j >= startX && j < startX + width) && (i == startY - 1 || i == startY + height);
  }
  
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
