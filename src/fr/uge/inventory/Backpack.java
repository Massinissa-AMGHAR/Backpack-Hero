package fr.uge.inventory;
import java.util.Objects;
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
	  
	if(line < 0 || line >= lines || column < 0 || column >= columns) {
	  throw new IllegalArgumentException("The case is not in the backpack"); 
	}
    int lengthLine = lengthLine(line, column); 
    int lengthColumn = lengthColumn(line, column); 
    
    if(item.height() > lengthColumn || item.width() > lengthLine) {
      throw new IllegalArgumentException("Item can't be stored :" + item.name()); 
    }
    for(int i = line; i < line + item.height(); i++) {
      for(int j = column; j < column + item.width(); j++) {
        backpack.get(i).set(j, item); 
      }
    }
  }
  public void delete(Item item) {
    for(int i =0; i < lines; i++) {
      for(int j = 0; j < columns; j++) {
        if(backpack.get(i).get(j).equals(item)) backpack.get(i).set(j, null); 
        
      }
    }
  }
  
  public Item choice(int line, int column) {
	  if(line < 0 || line >= lines || column < 0 || column >= columns) {
	    throw new IllegalArgumentException("The case is not in the backpack"); 
	  }
	  
	  if(backpack.get(line).get(column) == null) {
	    throw new IllegalArgumentException("The case is vacant"); 
	  }
	  
	  return backpack.get(line).get(column); 
  }
  
  
  @Override
  public String toString() {
    var builder = new StringBuilder(); 
    builder.append("Backpack : ").append("\n"); 
    for(int i = 0; i < lines; i++) {
      for(int j = 0; j < columns; j++) {
        builder.append(backpack.get(i).get(j) == null ? "free |" : backpack.get(i).get(j).name()+ " | "); 
      }
      builder.append("\n"); 
  
    }
    
    return builder.toString(); 
  }
  
  
  
}
