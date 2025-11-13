package fr.uge.map;
import java.util.ArrayList; 

public class Floor {
  private final Room[][] floor; 
  private int heroX; //position du héros en x
  private int heroY; //position du héros en y
  
  public Floor() {
    this.floor = new Room[5][11]; 
  }
  
  public void add(Room room, int line, int column) {
    if(floor[line][column] != null) {
      throw new IllegalArgumentException("A room is already here"); 
    }
    floor[line][column] = room; 
  }
  
  @Override
  public String toString() {
	var builder = new StringBuilder(); 
    for(int i = 0; i < 5; i++) {
      for(int j = 0; j < 11; j++) {
        builder.append(floor[i][j] + " "); 
      }
      builder.append("\n"); 
    }
    return builder.toString();     
  }
}
