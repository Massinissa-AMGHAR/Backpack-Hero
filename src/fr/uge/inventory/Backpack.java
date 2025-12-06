package fr.uge.inventory;
import java.util.ArrayList; 

public class Backpack {
  private ArrayList<ArrayList<Item>> backpack; 
  private int lines; 
  private int columns; 
  
  private final BackpackPlacement placement; 
  private final BackpackSearch search; 
  
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
    
    this.placement = new BackpackPlacement(this);
    this.search = new BackpackSearch(this); 
    
  }
  public ArrayList<ArrayList<Item>> grid() {return backpack;}
  public int lines() {return lines; }
  public int colums() { return columns;}
  
  // Placement/Suppression
  public boolean canPlaceItem(Item item, int line, int column) { return placement.canPlaceItem(item, line, column); }
  public void add(Item item, int line, int column) { placement.add(item, line, column);}
  public void delete(Item item) { placement.delete(item);}
  public int lengthLine(int line, int column) {return placement.lengthLine(line, column);}
  public int lengthColumn(int line, int column) {return placement.lengthColumn(line, column);}
  
  
  
  // Recherche d'un item
  public Item choice(int line, int column) {return search.choice(line, column); }
  public Item findGoldItem() {return search.findGoldItem();}
  
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
