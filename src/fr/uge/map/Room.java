package fr.uge.map;
import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.graphics.image.ImageLoader;
import fr.uge.graphics.view.*;
import fr.uge.inventory.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a room in a dungeon floor.
 * A room can contain enemies, a merchant, a healer, treasure, or a door.
 * It can also be part of a grid and keep track of its neighbors.
 */
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
  private final boolean hasHealer; 
  private boolean hasTreasure;
  private boolean hasDoor;
  private boolean hasGrid;
  private boolean visited;
  private final Enemies enemies; 
  private Items treasure; 
  private final int floor;
  private final Merchant merchant; 
  private final Healer healer; 
  private final ArrayList<Room> neighbors = new ArrayList<>(); 
  private Room generatedRoom;

  /**
   * Creates a new Room.
   * 
   * @param floor The floor number where the room is located
   * @param hasEnemies True if the room contains enemies
   * @param hasMerchant True if the room contains a merchant
   * @param hasHealer True if the room contains a healer
   * @param hasTreasure True if the room contains treasure
   * @param hasDoor True if the room has a door
   * @param hasGrid True if the room has a grid
   */
  public Room(int floor, boolean hasEnemies, boolean hasMerchant, boolean hasHealer, boolean hasTreasure, boolean hasDoor, boolean hasGrid) {
    this.floor = floor;
  	this.hasEnemies = hasEnemies;
    this.hasMerchant = hasMerchant; 
    this.hasHealer = hasHealer; 
    this.hasTreasure = hasTreasure; 
    this.hasDoor = hasDoor;  
    this.hasGrid = hasGrid;
    this.visited = false;
   
    if(hasEnemies) enemies = new Enemies(floor); 
    else enemies = null; 
    
    if(hasMerchant) merchant = new Merchant(); 
    else merchant = null; 
    
    if(hasHealer) healer = new Healer(3, 6); 
    else healer = null;
    
  }
  
  /**
   * Returns the BattleView for this room.
   * If the room has no enemies, returns null.
   * If the BattleView or FightGraphics is not yet created, they are initialized here.
   * If the room has already been visited, shows a message indicating that enemies were defeated.
   *
   * @param hero The hero who will fight in this room
   * @param dungeon The dungeon containing this room
   * @param imagesHero Images for the hero
   * @param imagesEnemies Images for the enemies
   * @param imagesItems Images for items
   * @param imagesStatus Images for status indicators
   * @return The BattleView of this room, or null if no enemies are present
   */
  public BattleView getBattleView(Hero hero, Dungeon dungeon, ImageLoader imagesHero, ImageLoader imagesEnemies, ImageLoader imagesItems, ImageLoader imagesStatus) {
    if (!hasEnemies) return null;
    if (battleView == null) {
        battleView = new BattleView(hero, dungeon, getLoot(), enemies, imagesHero, imagesEnemies, imagesItems, imagesStatus);
    }
    if (fightGraphics == null) {
      fightGraphics = new FightGraphics(hero, enemies, dungeon, battleView);
      battleView.setFight(fightGraphics);
    }
    if (visited) {
      battleView.messageView().showMessage("Enemies defeated here already!", 2000);
    }
    return battleView;
  } 
  
  /**
   * Returns the TreasureView for this room.
   * If it hasn't been created yet, it will be initialized here.
   *
   * @param hero The hero who will collect the loot
   * @param dungeon The dungeon containing this room
   * @param images Images for the room map
   * @param imagesItems Images for the items
   * @return The TreasureView for this room
   */
  public TreasureView getTreasureView(Hero hero, Dungeon dungeon, ImageLoader images, ImageLoader imagesItems) {
  	if (treasureView == null) {
  		treasureView = new TreasureView(hero, dungeon, getLoot(), images, imagesItems);
  	}
  	return treasureView;
  }
  
  /**
   * Returns the HealerView for this room.
   * If the room does not have a healer, returns null.
   * If it hasn't been created yet, it will be initialized here.
   *
   * @param hero The hero to heal
   * @param dungeon The dungeon containing this room
   * @param imagesMap Images for the room map
   * @param imagesServices Images for service icons
   * @param imagesItems Images for items
   * @return The HealerView for this room, or null if no healer is present
   */
  public HealerView getHealerView(Hero hero, Dungeon dungeon, ImageLoader imagesMap, ImageLoader imagesServices, ImageLoader imagesItems) {
    if (!hasHealer) return null; 
    if (healerView == null) {
        healerView = new HealerView(hero, dungeon, angel(), imagesMap, imagesServices, imagesItems);
    }
    return healerView;
  }
  
  /**
   * Returns the MerchantView for this room.
   * If the room does not have a merchant, returns null.
   * If it hasn't been created yet, it will be initialized here.
   *
   * @param hero The hero who can buy or sell items
   * @param dungeon The dungeon containing this room
   * @param imagesHero Images for the hero
   * @param imagesServices Images for service icons
   * @param imagesItems Images for items
   * @return The MerchantView for this room, or null if no merchant is present
   */
  public MerchantView getMerchantView(Hero hero, Dungeon dungeon, ImageLoader imagesHero, ImageLoader imagesServices, ImageLoader imagesItems) {
    if (!hasMerchant) return null;
    if (merchantView == null) {
        merchantView = new MerchantView(hero, dungeon, merchant, getLoot(), imagesHero, imagesServices, imagesItems);
    }
    return merchantView;
  }

  /**
   * Returns the loot items available in this room.
   * If the loot hasn't been generated yet, it will be created now.
   *
   * @return The Items object containing loot for this room
   */
  public Items getLoot() {
  	if (!lootGenerated) {
  		generateLoot();
  		lootGenerated = true;
  	}
  	return loot;
  }
  
  /**
   * Generates the loot for this room.
   * Currently, it creates 3 random items.
   * This method is called automatically by getLoot() if loot hasn't been generated yet.
   */
  private void generateLoot() {
  	loot = new Items();
  	loot.randomItems(3);
  }
  
  /**
   * Returns the room that was generated from this room.
   *
   * @return The generated Room, or null if none has been set
   */
  public Room getGeneratedRoom() {
  	return generatedRoom;
  }
  
  /**
   * Sets the generated room for this room.
   *
   * @param room The Room to set as the generated room
   */
  public void setGeneratedRoom(Room room) {
  	this.generatedRoom = room;
  }
  
  /** Returns the floor number this room belongs to. */
  public int floor() { return floor; }
  /** Returns true if this room contains enemies. */
  public boolean hasEnemies() {return hasEnemies; }
  /** Returns true if this room contains a merchant. */
  public boolean hasMerchant() {return hasMerchant;}
  /** Returns true if this room contains a healer. */
  public boolean hasHealer() {return hasHealer;}
  /** Returns true if this room contains treasure. */
  public boolean hasTreasure() {return hasTreasure;}
  /** Returns true if this room has a door to another floor. */
  public boolean hasDoor() {return hasDoor;}
  /** Returns true if this room has a grid. */
  public boolean hasGrid() { return hasGrid; }
  /** Returns true if the room has already been visited. */
  public boolean hasVisited() { return visited; }  
  /** Returns the merchant in this room, or null if none. */
  public Merchant merchant() { return merchant; }
  /** Returns the healer (angel) in this room, or null if none. */
  public Healer angel() { return healer; }
  /** Returns the enemies in this room, or null if none. */
  public Enemies enemies() { return enemies; }
  /** Returns the treasure items in this room. */
  public Items treasure(){return treasure; }
  /** Returns the list of neighboring rooms. */
  public ArrayList<Room> neighbors(){return neighbors;}
  
  /**
   * Marks this room as visited or not.
   *
   * @param bool true to mark as visited, false otherwise
   */
  public void setVisited(boolean bool) { this.visited = bool; }
  
  /**
   * Adds a neighbor room to this room. Both rooms become neighbors.
   *
   * @param room the room to add; cannot be null
   * @throws NullPointerException if room is null
   */
  public void addNeighbor(Room room) {
  	Objects.requireNonNull(room, "Room cannot be null"); 
  	if(!neighbors.contains(room)) {
  		neighbors.add(room); 
  	}
  	
  	if(!room.neighbors.contains(this)) {
  		room.neighbors.add(this); 
  	}
  }
  
  /**
   * Checks if this room is an empty corridor.
   * A corridor has no enemies, merchant, healer, treasure, door, or grid.
   *
   * @return true if the room is empty, false otherwise
   */
  public boolean isCorridor() {
    if(!hasEnemies && !hasMerchant && !hasHealer && !hasTreasure && !hasDoor && !hasGrid) {
    	return true; 
    }
    return false; 
  }
}