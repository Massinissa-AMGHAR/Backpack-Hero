package fr.uge.map;
import java.util.ArrayList;
import fr.uge.application.*;

public class Floor {
  private final Room[][] floor = new Room[5][11]; 
  private int heroX = -1; 
  private int heroY = -1;
  private boolean hasHero = false; 
  
  public void add(Room room, int line, int column) {
    if(floor[line][column] != null) {
      throw new IllegalArgumentException("A room is already here"); 
    }
    floor[line][column] = room; 
  }
  
  public boolean hasRoom(int x, int y) {
  	if(x < 0 || x > 4 || y < 0 || y > 10) return false; 
  	if (floor[x][y] == null) return false;
  	return true;
  }
  
  public Room getRoom(int x, int y) {
  	if (!hasRoom(x, y)) throw new IllegalArgumentException("No room here");
  	return floor[x][y];
  }
  public void placeHero(int x, int y) {
  	if(!hasRoom(x, y)) {
  		throw new IllegalArgumentException("No room here"); 
  	}
  	this.heroX = x; 
  	this.heroY = y; 
  	this.hasHero = true; 
  }
  
  public void moveHero(Interactions interaction) {
  	boolean goodMove = false; 
  	int x = -1; 
  	int y = -1; 
  	while(!goodMove) {
  		IO.println("Where do you want to go ? (line/column)"); 
    	x = interaction.requestInt(); 
    	y = interaction.requestInt(); 
    	if(!hasRoom(x, y)) IO.println("No room here"); 
    	else {
	    	Room current = floor[heroX][heroY]; 
	    	var neighbors = current.neighbors(); 
	    	if(neighbors.contains(floor[x][y])) {
		    	heroX = x; 
		    	heroY = y; 
		    	goodMove = true; 
	      }
	    	else IO.println("Step by step... Follow the way"); 
      }
  	}
  }
  public int heroX(){return heroX;}
  public int heroY() {return heroY;}
  
  
  public static Floor createFloor() {
  	Floor floor2 = new Floor(); 
  	Room room1 = new Room(false, true, false, false, false); 
		Room room2 = new Room(true, false, false, false, false); 
		Room room3 = new Room(true, false, false, false, false); 
		Room room4 = new Room(false, false, true, true, false); 
		Room room5 = new Room(true, false, false, true, false);
		Room room6 = new Room(false, true, false, false, false);
		Room room7 = new Room(false, false, false, false, true); 
		
		Room corridor = new Room(false, false, false, false, false); 
		
		floor2.add(room1, 1, 1);
		floor2.add(room2, 1, 3);
		floor2.add(room3, 2, 4);
		floor2.add(room4, 3, 3);
		floor2.add(room5, 3, 6);
		floor2.add(room6, 1, 6);
		floor2.add(room7, 1, 9);
		
		floor2.add(corridor, 1, 2);
		floor2.add(corridor, 2, 3);
		floor2.add(corridor, 3, 4);
		floor2.add(corridor, 3, 5);
		floor2.add(corridor, 2, 6);
		floor2.add(corridor, 1, 7);
		floor2.add(corridor, 1, 8);
		
		room1.addNeighbor(room2);
		room2.addNeighbor(room3);
		room2.addNeighbor(room4);
		room3.addNeighbor(room4);
		room4.addNeighbor(room5);
		room5.addNeighbor(room6);
		room6.addNeighbor(room7);
		
		return floor2; 
  }
  @Override
  public String toString() {
	var builder = new StringBuilder(); 
    for(int i = 0; i < 5; i++) {
      for(int j = 0; j < 11; j++) {
      	if(i == heroX && j == heroY) {
      		builder.append(String.format("%-5s", "H")); 
      	}
      	else if (floor[i][j] == null) builder.append(String.format("%-5s", "x"));
      	else builder.append(floor[i][j].toString());
      }
      builder.append("\n"); 
    }
    return builder.toString();     
  }
}
