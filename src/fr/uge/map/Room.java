package fr.uge.map;
import fr.uge.character.*;
import fr.uge.inventory.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Room {
	private Items loot;
  private boolean lootGenerated = false;
  private boolean hasEnemies; 
  private final boolean hasMerchant; 
  private final boolean hasAngel; 
  private boolean hasTreasure;
  private boolean hasDoor;
  private boolean hasGrid;
  private boolean visited;
  private final Enemies enemies; 
  private Items treasure; 
  private final Merchant merchant; 
  private final Healer healer; 
  private final ArrayList<Room> neighbors = new ArrayList<>(); 
  private Room generatedRoom;
  private boolean generatedVisited = false;

  public Room(boolean hasEnemies, boolean hasMerchant, boolean hasAngel, boolean hasTreasure, boolean hasDoor, boolean hasGrid) {
    this.hasEnemies = hasEnemies;
    this.hasMerchant = hasMerchant; 
    this.hasAngel = hasAngel; 
    this.hasTreasure = hasTreasure; 
    this.hasDoor = hasDoor;  
    this.hasGrid = hasGrid;
    this.visited = false;
   
    if(hasEnemies) enemies = new Enemies(); 
    else enemies = null; 
    
    if(hasMerchant) merchant = new Merchant(); 
    else merchant = null; 
    
    if(hasAngel) healer = new Healer(10, 5); 
    else healer = null;
    
  }
  
  public Items getLoot() {
  	if (!lootGenerated) {
  		generateLoot();
  		lootGenerated = true;
  	}
  	return loot;
  }
  
  private void generateLoot() {
  	loot = new Items();
  	loot.randomItems(3);
  }
  
  public boolean generatedVisited() {
  	return generatedVisited;
  }
  
  public void setGeneratedVisited(boolean bool) {
  	generatedVisited = bool;
  }
  
  public Room getGeneratedRoom() {
  	return generatedRoom;
  }
  
  public void setGeneratedRoom(Room room) {
  	this.generatedRoom = room;
  }
  
  public void resetGeneratedRoom() {
  	generatedRoom = null;
  }
  
  public boolean hasEnemies() {return hasEnemies; }
  public boolean hasMerchant() {return hasMerchant;}
  public boolean hasAngel() {return hasAngel;}
  public boolean hasTreasure() {return hasTreasure;}
  public boolean hasDoor() {return hasDoor;}
  public boolean hasGrid() { return hasGrid; }
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
    if(!hasEnemies && !hasMerchant && !hasAngel && !hasTreasure && !hasDoor && !hasGrid) {
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