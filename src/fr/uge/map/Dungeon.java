package fr.uge.map;
import java.util.ArrayList; 
import java.util.Objects; 

/**
 * Represents a dungeon with multiple floors.
 */
public class Dungeon {
  private final ArrayList<Floor> dungeon = new ArrayList<>(); 
  private final int floorCount;
  private int currentFloor = 0;
  
  /**
   * Creates a dungeon with the given number of floors.
   *
   * @param floorCount the number of floors (must be >= 0)
   */
  public Dungeon(int floorCount) {
  	if (floorCount < 0) throw new IllegalArgumentException("Integer must be >= 0");
  	this.floorCount = floorCount;
  }
  
  /**
   * Returns the total number of floors in the dungeon.
   *
   * @return the total floor count
   */
  public int floorCount() { return floorCount; }
  
  /**
   * Returns the index of the current floor.
   *
   * @return the current floor index
   */
  public int currentFloor() { return currentFloor; }

  /**
   * Adds a floor to the dungeon.
   *
   * @param floor the Floor to add (cannot be null)
   */
  public void add(Floor floor) {
    Objects.requireNonNull(floor, "floor cannot be null");
    dungeon.add(floor); 
  }
  
  /**
   * Returns the floor at the specified index.
   *
   * @param i the index of the floor
   * @return the Floor at that index
   * @throws IllegalArgumentException if the index is invalid
   */
  public Floor getFloor(int i) {
  	if(i < 0 || i >= dungeon.size()) {
  		throw new IllegalArgumentException("Invalid floor index"); 
  	}
  	return dungeon.get(i); 
  }
  
  /**
   * Creates the dungeon by adding the first 3 floors from a generated list.
   */
  public void createDungeon() {
  	var floors = Floor.createFloors(); 
		for (var i = 0; i < 3; i++) {
			dungeon.add(floors.get(i));
		}
  }
  
  /**
   * Returns the next floor after the given floor.
   *
   * @param current the current floor
   * @return the next floor, or null if this is the last floor
   * @throws IllegalArgumentException if the current floor is not in the dungeon
   */
  public Floor nextFloor(Floor current) {
  	int i = dungeon.indexOf(current); 
  	if(i == -1) {
  		throw new IllegalArgumentException("Floor not in dungeon"); 
  	}
  	if(i == dungeon.size() - 1) {
  		return null; 
  	}
  	currentFloor++;
  	return dungeon.get(i + 1); 
  }
}
