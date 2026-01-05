package fr.uge.map;
import java.util.ArrayList; 
import java.util.Objects; 

public class Dungeon {
  private final ArrayList<Floor> dungeon = new ArrayList<>(); 
  private final int floorCount;
  private int currentFloor = 0;
  
  public Dungeon(int floorCount) {
  	if (floorCount < 0) throw new IllegalArgumentException("Integer must be >= 0");
  	this.floorCount = floorCount;
  }
  
  public int floorCount() { return floorCount; }
  public int currentFloor() { return currentFloor; }

  public void add(Floor floor) {
    Objects.requireNonNull(floor, "floor cannot be null");
    dungeon.add(floor); 
  }
  
  public Floor getFloor(int i) {
  	if(i < 0 || i >= dungeon.size()) {
  		throw new IllegalArgumentException("Invalid floor index"); 
  	}
  	return dungeon.get(i); 
  }
  
  public void createDungeon() {
  	var floors = Floor.createFloors(); 
		for (var i = 0; i < 3; i++) {
			dungeon.add(floors.get(i));
		}
  }
  
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
