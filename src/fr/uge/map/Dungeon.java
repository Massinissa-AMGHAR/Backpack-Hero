package fr.uge.map;
import java.util.ArrayList; 
import java.util.Objects; 

public class Dungeon {
  private final ArrayList<Floor> dungeon = new ArrayList<>(); 
  private final int floorCount;
  
  public Dungeon(int floorCount) {
  	if (floorCount <= 0) throw new IllegalArgumentException("Integer must be >= 0");
  	this.floorCount = floorCount;
  }
  
  public int floorCount() { return floorCount; }

  public void add(Floor floor) {
    Objects.requireNonNull(floor, "floor cannot be null");
    dungeon.add(floor); 
  }
  
}
