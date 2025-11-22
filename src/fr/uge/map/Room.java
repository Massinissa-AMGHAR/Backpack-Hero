package fr.uge.map;
import fr.uge.character.*;
import fr.uge.inventory.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random; 

public class Room {
  private final boolean hasEnemies; 
  private final boolean hasMerchant; 
  private final boolean hasAngel; 
  private final boolean hasTreasure;
  private final boolean hasDoor;
  private final boolean isCorridor; 
  private boolean visited;
  
  private final Enemies enemies; 
  private final Treasure treasure; 
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
    
    if(!hasEnemies && !hasMerchant && !hasAngel && !hasTreasure && !hasDoor) {
    	isCorridor = true; 
    }
    else {
    	isCorridor = false; 
    }
    if(hasEnemies) enemies = new Enemies(); 
    else enemies = null; 
    
    if(hasMerchant) merchant = new Merchant(); 
    else merchant = null; 
    
    if(hasAngel) healer = new Healer(10, 5); 
    else healer = null;
    
    if(hasTreasure) treasure = new Treasure(); 
    else treasure = null; 
  }
  
  public boolean hasEnemies() {return hasEnemies; }
  public boolean hasMerchant() {return hasMerchant;}
  public boolean hasAngel() {return hasAngel;}
  public boolean hasTreasure() {return hasTreasure;}
  public boolean hasDoor() {return hasDoor;}
  public boolean isCorridor() { return isCorridor; }
  public boolean hasVisited() { return visited; }
  
  public Merchant merchant() { return merchant; }
  public Healer angel() { return healer; }
  public Enemies enemies() { return enemies; }
  public Treasure treasure(){return treasure; }
  public ArrayList<Room> neighbors(){return neighbors;}
  
  public void setVisited(boolean bool) { this.visited = bool; }
  
  public void addNeighbor(Room room) {
  	Objects.requireNonNull(room, "Room cannot be null"); 
  	neighbors.add(room); 
  	room.neighbors.add(this); 
  }
  
  @Override
  public String toString() {
	  var builder = new StringBuilder(); 
    if(hasEnemies) builder.append("E"); 
    if(hasMerchant) builder.append("M");
    if(hasAngel) builder.append("A");
    if(hasTreasure) builder.append("T"); 
    if(hasDoor) builder.append("D"); 
    if(isCorridor)builder.append("."); 
    
    return String.format("%-5s", builder.toString());     
  }
 
}