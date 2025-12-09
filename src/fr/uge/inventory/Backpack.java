package fr.uge.inventory;
import java.util.ArrayList;

import fr.uge.application.Interactions; 

public class Backpack {
  private ArrayList<ArrayList<Item>> grid;  
  private final boolean[][] unlocked; 
  private final int maxLines = 5; 
  private final int maxColumns = 7; 
 
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
  public int maxColumns() {return maxColumns; }
  public boolean[][] unlocked() { return unlocked; }; 
  
  // Placement/Suppression
  public boolean canPlaceItem(Item item, int line, int column) { return placement.canPlaceItem(item, line, column); }
  public void add(Item item, int line, int column) { placement.add(item, line, column);}
  public void delete(Item item) { placement.delete(item);}
  public int lengthLine(int line, int column) {return placement.lengthLine(line, column);}
  public int lengthColumn(int line, int column) {return placement.lengthColumn(line, column);}
  
  
  
  // Recherche d'un item
  public Item choice(int line, int column) {return search.choice(line, column); }
  public Item findGoldItem() {return search.findGoldItem();}
  
  //Ajout des cases 
  public void unlockCase(int n) {
  	IO.println("You can unlock " + n + " cases !"); 
  	int cpt = 0; 
  	Interactions interaction = new Interactions(); 
  	while(cpt < n) {
  		IO.println(this.toString()); 
  		IO.println("Choose a case to unlock : (line/column)"); 
  		int line = interaction.requestInt(); 
  		int column = interaction.requestInt(); 
  		if(!inside(line, column)) {
  			IO.println("The case is not in the backpack"); 
  			continue; 
  		}
  		
  		if(unlocked[line][column]) {
  			IO.println("The case is already unlocked"); 
  			continue; 
  		}
  		
  		if(!isAdjacent(line, column)) {
  	  	IO.println("Cases must be adjacent");
  	  	continue; 
  		}
  		unlocked[line][column] = true; 
  		cpt++; 
  		IO.println("Case unlocked"); 
  	}
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
  
  @Override
  public String toString() {
    var builder = new StringBuilder(); 
    builder.append("Backpack : ").append("\n"); 
    for(int i = 0; i < maxLines; i++) {
      for(int j = 0; j < maxColumns; j++) {
      	if(!unlocked[i][j]) {
      		 builder.append(String.format("%-15s", " X ")); 
      	}
      	else {
      		if(grid.get(i).get(j) == null) {
      			builder.append(String.format("%-15s", "free")); 
      		}
      		else {
      			builder.append(String.format("%-15s", grid.get(i).get(j).name())); 
      		}
      	}
      }
      builder.append("\n"); 
    }
    return builder.toString(); 
  }
  
  
  
}
