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
  private final Random rand; 
  private final boolean hasTreasure;
  private final boolean hasDoor;
  private final boolean isCorridor; 
  
  private final ArrayList<Enemy> enemies; 
  private final ArrayList<Item> treasure; 
  private final Merchant merchant; 
  private final Angel healer; 
  private final ArrayList<Room> neighbors = new ArrayList<>(); 
  
  public Room(boolean hasEnemies, boolean hasMerchant, boolean hasAngel, boolean hasTreasure, boolean hasDoor) {
    this.hasEnemies = hasEnemies; 
    this.hasMerchant = hasMerchant; 
    this.hasAngel = hasAngel; 
    this.rand = new Random(); 
    this.hasTreasure = hasTreasure; 
    this.hasDoor = hasDoor;  
    
    if(!hasEnemies && !hasMerchant && !hasAngel && !hasTreasure && !hasDoor) {
    	isCorridor = true; 
    }
    else {
    	isCorridor = false; 
    }
    enemies = new ArrayList<>();
    if(hasEnemies) { 
    	int enemiesNumber = rand.nextInt(2) + 1; 
    	for(int i = 0; i < enemiesNumber; i++) {
    		int indexEnemies = rand.nextInt(Enemy.ALL_ENEMIES.length); 
    		enemies.add(Enemy.ALL_ENEMIES[indexEnemies]); 
    	}
    }
    
    if(hasMerchant) {
    	merchant = new Merchant(); 
    }
    else {
    	merchant = null; 
    }
    
    if(hasAngel) {
    	healer = new Angel(10, 5); 
    }
    else {
    	healer = null; 
    }
    
    treasure = new ArrayList<>(); 
    if(hasTreasure) {
    	for(int i = 0; i < 3; i++) {
    		int indexItem = rand.nextInt(Item.ALL_ITEM.length); 
    		treasure.add(Item.ALL_ITEM[indexItem]); 
    	}
    }
    
  }
  public boolean hasEnemies() {return hasEnemies; }
  public boolean hasMerchant() {return hasMerchant;}
  public boolean hasAngel() {return hasAngel;}
  public boolean hasTreasure() {return hasTreasure;}
  public boolean hasDoor() {return hasDoor;}
  
  public Merchant merchant() { return merchant; }
  public Angel healer() { return healer; }
  public ArrayList<Enemy> enemies() { return enemies; }
  public ArrayList<Item> treasure(){return treasure; }
  public ArrayList<Room> neighbors(){return neighbors;}
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