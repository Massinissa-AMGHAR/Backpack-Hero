package fr.uge.map;
import fr.uge.character.*;
import fr.uge.inventory.*;
import java.util.ArrayList;
import java.util.Objects;

public class Room {
  private boolean hasEnemies; 
  private final boolean hasMerchant; 
  private final boolean hasAngel; 
  private boolean hasTreasure;
  private boolean hasDoor;
  private boolean visited;
  
  private final Enemies enemies; 
  private Items treasure; 
  private final Merchant merchant; 
  private final Healer healer; 
  private final ArrayList<Room> neighbors = new ArrayList<>(); 
  
  public Room(boolean hasEnemies, boolean hasMerchant, boolean hasAngel, boolean hasTreasure, boolean hasDoor) {
    this.hasEnemies = hasEnemies;
    this.hasMerchant = hasMerchant; 
    this.hasAngel = hasAngel; 
    this.hasTreasure = hasTreasure; 
    this.hasDoor = hasDoor;  
    this.visited = false;
   
    if(hasEnemies) enemies = new Enemies(); 
    else enemies = null; 
    
    if(hasMerchant) merchant = new Merchant(); 
    else merchant = null; 
    
    if(hasAngel) healer = new Healer(10, 5); 
    else healer = null;
    
    if(hasTreasure) {
    	treasure = new Items(); 
    	treasure.randomItems(2);
    	var gold = new Item("Gold", "GOLD", "COMMON", Item.SHAPE_GOLD, 0, 0, 0, 0, 0, 0, 0, 10); 
    	treasure.addItem(gold);
    }
    else treasure = null; 
  }
  
  public boolean hasEnemies() {return hasEnemies; }
  public boolean hasMerchant() {return hasMerchant;}
  public boolean hasAngel() {return hasAngel;}
  public boolean hasTreasure() {return hasTreasure;}
  public boolean hasDoor() {return hasDoor;}
  public boolean hasVisited() { return visited; }
  
  public Merchant merchant() { return merchant; }
  public Healer angel() { return healer; }
  public Enemies enemies() { return enemies; }
  public Items treasure(){return treasure; }
  public ArrayList<Room> neighbors(){return neighbors;}
  
  public void setVisited(boolean bool) { this.visited = bool; }
  public void noEnemies() { hasEnemies = false;}
  public void noTreasure() { hasTreasure = false;}
  public void addNeighbor(Room room) {
  	Objects.requireNonNull(room, "Room cannot be null"); 
  	if(!neighbors.contains(room))neighbors.add(room); 
  	
  	if(!room.neighbors.contains(this))room.neighbors.add(this); 
  }
  
  public boolean isCorridor() {
    if(!hasEnemies && !hasMerchant && !hasAngel && !hasTreasure && !hasDoor) {
    	return true; 
    }
    return false; 
  }
  @Override
  public String toString() {
	  var builder = new StringBuilder(); 
    if(hasEnemies) builder.append("E"); 
    if(hasMerchant) builder.append("M");
    if(hasAngel) builder.append("A");
    if(hasTreasure) builder.append("T"); 
    if(hasDoor) builder.append("D"); 
    if(isCorridor())builder.append("."); 
    
    return String.format("%-5s", builder.toString());     
  }
 
}