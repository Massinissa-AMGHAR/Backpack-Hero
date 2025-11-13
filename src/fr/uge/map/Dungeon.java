package fr.uge.map;
import java.util.ArrayList; 
import java.util.Objects; 

public class Dungeon {
  private final ArrayList<Floor> dungeon; 
 
  public Dungeon(){
    this.dungeon = new ArrayList<Floor>(); 
  }
  
  public void add(Floor floor) {
    Objects.requireNonNull(floor, "floor cannot be null");
    dungeon.add(floor); 
  }
  
}
