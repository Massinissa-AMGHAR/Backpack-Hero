package fr.uge.map;
import java.util.ArrayList;
import fr.uge.application.*;
import fr.uge.character.Hero;

import java.util.List;
import java.util.Random;

public class Floor {
  private final Room[][] floor = new Room[5][11]; 
  private int heroX = -1; 
  private int heroY = -1;
  private final Random rand = new Random();
  
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
  	if (!hasRoom(x, y)) return null;
  	return floor[x][y];
  }
  
  public void placeHero(int x, int y) {
  	if(!hasRoom(x, y)) return;  	
  	this.heroX = x; 
  	this.heroY = y; 
  }
  
  public void initPlaceHero() {
  	for (int i = 0; i < 5; i++) {
  		for (int j = 0; j < 11; j++) {
  			if (floor[i][j] != null && floor[i][j].isCorridor()) {
  				placeHero(i, j);
  				return;
  			}
  		}
  	}
  }
  
  public int[]getPlaceHero() {
  	return new int[]{heroX, heroY};
  }
  
  public boolean goodPath(Room current, Room target, List<Room> visited) {
  	if(current.equals(target)) return true; 
  	visited.add(current); 
  	
  	for(var n : current.neighbors()) {
  		if(!visited.contains(n) && (n.isCorridor() || n.equals(target))) {
  			if(goodPath(n, target, visited)) return true; 
  		}
  	}
  	return false; 
  }
  
  public boolean isAccessible(int x, int y) {
  	if (!hasRoom(x, y)) return false;
  	if (x == heroX && y == heroY) return false;
  	return goodPath(floor[heroX][heroY], floor[x][y], new ArrayList<>());
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
	    	var target = floor[x][y];  
	    	if(!goodPath(current, target, new ArrayList<>())) {
	    		IO.println("Step by step ... Follow the path"); 
	    		continue; 
	    	}
	    	else {
	    	 	heroX = x; 
		    	heroY = y;
		    	floor[x][y].setVisited(true);
		    	goodMove = true; 
	    	}
		   
     }
  	}
  }
 
  public int heroX() { return heroX; }
  public int heroY() { return heroY; }
  
	public Room generateRandomRoom(Room gridRoom) {
		if (gridRoom.getGeneratedRoom() != null) {
			return gridRoom.getGeneratedRoom();
		}
		var random = rand.nextInt(5);
		Room randomRoom;
		switch(random) {
			case 0 -> randomRoom = new Room(true, false, false, false, false, false);
			case 1 -> randomRoom = new Room(false, true, false, false, false, false);
			case 2 -> randomRoom = new Room(false, false, true, false, false, false);
			case 3 -> randomRoom = new Room(false, false, false, true, false, false);
			case 4 -> randomRoom = new Room(false, false, false, false, true, false);
			default -> randomRoom = new Room(false, false, false, false, false, false);
		}
		gridRoom.setGeneratedRoom(randomRoom);
		return randomRoom;
	}
	
	public boolean tryUnlockGrid(Room grid, Hero hero) {
		if (!grid.hasVisited()) {
			var key = hero.backpack().findKeyItem();
			if (key == null) {
				return false;
			}
			hero.backpack().delete(key);
			grid.setVisited(true);
		}
		return true;
	}
	
  private static List<Room> listRoomOfFloor1() {
    return List.of(
      new Room(false, true, false, false, false, false),
      new Room(true, false, false, false, false, false),
      new Room(true, false, false, false, false, false),
      new Room(false, false, true, false, false, false),
      new Room(false, false, false, true, false, false),
      new Room(true, false, false, false, false, false),
      new Room(false, false, false, true, false, false),
      new Room(false, false, false, false, true, false),
      new Room(false, false, false, false, false, true)
    );
  }

  private static List<Room> listRoomOfFloor2() {
    return List.of(
      new Room(false, false, false, true, false, false),
      new Room(false, true, false, false, false, false),
      new Room(false, false, false, false, false, false),
      new Room(true, false, false, false, false, false),
      new Room(false, false, true, false, false, false),
      new Room(true, false, false, false, false, false), 
      new Room(false, false, false, false, true, false), 
      new Room(true, false, false, false, false, false), 
      new Room(false, false, false, true, false, false),
      new Room(false, false, false, false, false, true)

    );
  }

	private static List<Room> listRoomOfFloor3() {
	    return List.of(
	      new Room(false, false, true, false, false, false),
	      new Room(true, false, false, false, false, false),
	      new Room(true, false, false, false, false, false),
	      new Room(false, true, false, false, false, false),
	      new Room(true, false, false, false, false, false),
	      new Room(false, false, false, false, true, false),
	      new Room(false, false, false, true, false, false), 
	      new Room(false, false, false, true, false, false),
	      new Room(false, false, false, false, false, true)
	    );

	}
	  
  private static void addRoomsToFloor(Floor floor, List<Room> rooms, List<int[]> positions) {
    for (int i = 0; i < rooms.size(); i++) {
      int[] pos = positions.get(i);
      floor.add(rooms.get(i), pos[0], pos[1]);
    }
  }
  
  public void addAllNeighbor() {
  	for (int i = 0; i < 5; i++) {
  		for (int j = 0; j < 11; j++) {
  			if (!hasRoom(i, j)) continue;
  			var room = floor[i][j];
  			
        if (hasRoom(i - 1, j)) room.addNeighbor(floor[i - 1][j]);
        if (hasRoom(i + 1, j)) room.addNeighbor(floor[i + 1][j]);
        if (hasRoom(i, j - 1)) room.addNeighbor(floor[i][j - 1]); 
        if (hasRoom(i, j + 1)) room.addNeighbor(floor[i][j + 1]);
  		}
  	}
  }
  
  private static List<int[]> positionsRoomsFloor1() {
  	return List.of(
      new int[]{1, 1}, new int[]{1, 3}, new int[]{2, 4}, new int[]{3, 3}, new int[] {4, 4},
      new int[]{3, 6}, new int[]{1, 6}, new int[]{1, 9}, new int[]{4, 8}
  		);
  }

  private static List<int[]> positionsRoomsFloor2() {
  	return List.of(
      new int[]{0, 2}, new int[]{1, 1}, new int[]{2, 3},
      new int[]{2, 5}, new int[]{1, 5}, new int[]{0, 6}, new int[] {2, 6}, 
      new int[] {0, 5}, new int[] {0, 7}, new int[]{1, 7}
  		);
  }

  private static List<int[]> positionsRoomsFloor3() { 
  	return List.of(
      new int[]{1, 0}, new int[]{1, 2}, new int[]{2, 1}, new int[]{2, 3},
      new int[]{3, 2}, new int[]{3, 4}, new int[]{3, 5}, new int[] {1, 4}, new int[]{2, 5}
  	);
  }
  
  private static List<int[]> corridorPositionsFloor1() {
    return List.of(
        new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4},
        new int[]{3, 5}, new int[]{2, 6}, new int[]{1, 7}, new int[]{1, 8}, new int[]{2, 8},
        new int[] {4, 7}, new int[] {4, 6}, new int[] {3, 8}
    );
  }

	private static List<int[]> corridorPositionsFloor2() {
	    return List.of(
	        new int[]{0, 1}, new int[]{0, 3}, new int[]{1, 2},
	        new int[]{1, 4}, new int[]{2, 2}, new int[]{2, 4}, new int[]{3, 3}, new int[]{1, 6}
	    );
	}
	
	private static List<int[]> corridorPositionsFloor3() {
	    return List.of(
	        new int[]{0, 2}, new int[]{1, 1}, new int[]{1, 3},
	        new int[]{2, 2}, new int[]{2, 4}, new int[]{3, 1}, new int[]{3, 3}
	    );
	}

  
  public static List<Floor> createFloors() {
    Floor floor1 = new Floor();
    Floor floor2 = new Floor();
    Floor floor3 = new Floor();
    
    addRoomsToFloor(floor1, listRoomOfFloor1(), positionsRoomsFloor1());
    addRoomsToFloor(floor2, listRoomOfFloor2(), positionsRoomsFloor2());
    addRoomsToFloor(floor3, listRoomOfFloor3(), positionsRoomsFloor3());
   	 
    for(var pos : corridorPositionsFloor1()) {
    	floor1.add(new Room(false, false, false, false, false, false), pos[0], pos[1]);
    }
    
    for(var pos : corridorPositionsFloor2()) {
    	floor2.add(new Room(false, false, false, false, false, false), pos[0], pos[1]);
    }
    
    for(var pos : corridorPositionsFloor3()) {
    	floor3.add(new Room(false, false, false, false, false, false), pos[0], pos[1]);
    }
    
    floor1.addAllNeighbor();
    floor2.addAllNeighbor();
    floor3.addAllNeighbor();

		return List.of(floor1, floor2, floor3); 
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
