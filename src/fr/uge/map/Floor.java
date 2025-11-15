package fr.uge.map;
import java.util.ArrayList; 

public class Floor {
  private final Room[][] floor = new Room[5][11]; 
  private int heroX; //position du héros en x
  private int heroY; //position du héros en y

  public Floor(int heroX, int heroY) {
  	if (heroX < 0 || heroY < 0) throw new IllegalArgumentException("Integer must be >= 0");
    this.heroX = heroX;
    this.heroY = heroY;
}

  public int x() { return heroX; }
  public int y() { return heroY; };
  
  public void add(Room room, int line, int column) {
    if(floor[line][column] != null) {
      throw new IllegalArgumentException("A room is already here"); 
    }
    floor[line][column] = room; 
  }
  
  public boolean hasRoom(int x, int y) {
  	if (floor[x][y] == null) return false;
  	return true;
  }
  
  public Room getRoom(int x, int y) {
  	if (!hasRoom(x, y)) throw new IllegalArgumentException("No room here");
  	return floor[x][y];
  }
  
  
  
  @Override
  public String toString() {
	var builder = new StringBuilder(); 
    for(int i = 0; i < 5; i++) {
      for(int j = 0; j < 11; j++) {
      	if (i == heroX && j == heroY) builder.append("H ");
      	else if (floor[i][j] == null) builder.append("x ");
      	else builder.append(floor[i][j] + " ");
      }
      builder.append("\n"); 
    }
    return builder.toString();     
  }
}
