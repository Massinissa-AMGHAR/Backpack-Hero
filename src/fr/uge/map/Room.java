package fr.uge.map;
import fr.uge.character.*;

import fr.uge.graphics.app.FightGraphics;
import fr.uge.graphics.image.ImageLoader;
import fr.uge.graphics.view.*;
import fr.uge.inventory.*;
import java.util.ArrayList;
import java.util.Objects;

public class Room {
	private TreasureView treasureView;
	private MerchantView merchantView;
	private BattleView battleView;
	private FightGraphics fightGraphics;
	private HealerView healerView;
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
    
    if(hasAngel) healer = new Healer(1, 10); 
    else healer = null;
    
  }
  
  public BattleView getBattleView(Hero hero, ImageLoader imagesHero, ImageLoader imagesEnemies, ImageLoader imagesItems, ImageLoader imagesStatus) {
    if (!hasEnemies) return null;
    if (battleView == null) {
        battleView = new BattleView(hero, getLoot(), enemies, imagesHero, imagesEnemies, imagesItems, imagesStatus);
    }
    if (fightGraphics == null) {
      fightGraphics = new FightGraphics(hero, enemies, battleView);
      battleView.setFight(fightGraphics);
    }
    if (visited) {
      battleView.messageView().showMessage("Enemies defeated here already!", 2000);
    }
    return battleView;
  } 
  
  public TreasureView getTreasureView(Hero hero, ImageLoader images, ImageLoader imagesItems) {
  	if (treasureView == null) {
  		treasureView = new TreasureView(hero, getLoot(), images, imagesItems);
  	}
  	return treasureView;
  }
  
  public HealerView getHealerView(Hero hero, ImageLoader imagesMap, ImageLoader imagesServices, ImageLoader imagesItems) {
    if (!hasAngel) return null; 
    if (healerView == null) {
        healerView = new HealerView(hero, angel(), imagesMap, imagesServices, imagesItems);
    }
    return healerView;
  }
  
  public MerchantView getMerchantView(Hero hero, ImageLoader imagesHero, ImageLoader imagesServices, ImageLoader imagesItems) {
    if (!hasMerchant) return null;
    if (merchantView == null) {
        merchantView = new MerchantView(hero, merchant, getLoot(), imagesHero, imagesServices, imagesItems);
    }
    return merchantView;
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
  
  public Room getGeneratedRoom() {
  	return generatedRoom;
  }
  
  public void setGeneratedRoom(Room room) {
  	this.generatedRoom = room;
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
}