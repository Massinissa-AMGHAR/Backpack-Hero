package fr.uge.inventory;
import java.util.ArrayList; 

public class Backpack {
  private ArrayList<ArrayList<Item>> backpack; 
  private int lines; 
  private int columns; 
  
  public Backpack(int lignes, int colonnes) {
    this.lines = lignes; 
    this.columns = colonnes; 
    this.backpack = new ArrayList<>();
    for(int i = 0; i < lines; i++) {
      ArrayList<Item> line = new ArrayList<>(); 
      for(int j = 0; j < columns; j++) {
        line.add(null); 
      }
      backpack.add(line); 
    }
  }
  
  public boolean caseFree(int line, int column) {
    if(line < 0 || line >= lines || column < 0 || column >= columns) return false;
    if(backpack.get(line).get(column) != null) {
      return false; 
    }
    return true; 
  }
  
  public int lengthLine(int line, int column) {
	int length = 0; 
	for(int j = column; j < columns; j++) {
	  if(caseFree(line, j)) length++; 
	  else break;  
    }
	return length; 
  }
  
  public int lengthColumn(int line, int column) {
    int length = 0; 
    for(int i = line; i < lines; i++) {
	  if(caseFree(i, column)) length++; 
	  else break; 
	}
	return length; 
  }
  
  public void add(Item item, int line, int column) { 
  	boolean[][] shape = item.shape(); 
		
		for(int i = 0; i < item.height(); i++) {
			for(int j = 0; j < item.width(); j++) {
				if(shape[i][j]) {
					if(!caseFree(line + i, column + j)) {
						IO.println("The case is already occupied");
						return; 
					}
				}
			}
		}
		for(int i = 0; i < item.height(); i++) {
			for(int j = 0; j < item.width(); j++) {
				if(shape[i][j]) {
					backpack.get(line + i).set(column + j, item); 
				}
			}
		}
  }
  
  public void delete(Item item) {
    for(int i = 0; i < lines; i++) {
      for(int j = 0; j < columns; j++) {
      	Item current = backpack.get(i).get(j); 
        if(current != null && current.id() == item.id()) backpack.get(i).set(j, null); 
      }
    }
  }
  
  public Item choice(int line, int column) {
	  if(line < 0 || line >= lines || column < 0 || column >= columns) {
	    IO.println("The case is not in the backpack"); 
	    return null; 
	  }
	  
	  if(backpack.get(line).get(column) == null) {
	   IO.println("The case is null"); 
	   return null; 
	  }
	  
	  return backpack.get(line).get(column); 
  }
  
  public Item findGoldItem() {
  	for(int i = 0; i < lines; i++) {
  		for(int j = 0; j < columns; j++) {
  			Item item = backpack.get(i).get(j); 
  			if(item != null && item.isGold())return item; 
  		}
  	}
  	return null; 
  }

  @Override
  public String toString() {
    var builder = new StringBuilder(); 
    builder.append("Backpack : ").append("\n"); 
    for(int i = 0; i < lines; i++) {
      for(int j = 0; j < columns; j++) {
      	String value = backpack.get(i).get(j) == null ? " free " : backpack.get(i).get(j).name(); 
        builder.append(String.format("%-15s", value)); 
      }
      builder.append("\n"); 
    }
    return builder.toString(); 
  }
  
  
  
}
