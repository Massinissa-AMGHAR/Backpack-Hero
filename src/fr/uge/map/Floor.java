package fr.uge.map;
import java.util.ArrayList;
import fr.uge.character.Hero;

import java.util.List;
import java.util.Random;

/**
 * Represents a floor in the dungeon with a grid of rooms.
 */
public class Floor {
  private final Room[][] floor = new Room[5][11]; 
  private int heroX = -1; 
  private int heroY = -1;
  private final Random rand = new Random();
  
  /**
   * Adds a room at the specified position.
   *
   * @param room   the Room to add
   * @param line   the row index (0-4)
   * @param column the column index (0-10)
   * @throws IllegalArgumentException if a room is already at that position
   */
  public void add(Room room, int line, int column) {
    if(floor[line][column] != null) {
      throw new IllegalArgumentException("A room is already here"); 
    }
    floor[line][column] = room; 
  }
  
  /**
   * Checks if there is a room at the given coordinates.
   *
   * @param x the row index (0-4)
   * @param y the column index (0-10)
   * @return true if a room exists at (x, y), false otherwise
   */
  public boolean hasRoom(int x, int y) {
  	if(x < 0 || x > 4 || y < 0 || y > 10) {
  		return false; 
  	}
  	if (floor[x][y] == null) {
  		return false;
  	}
  	return true;
  }
  
  /**
   * Returns the room at the given coordinates.
   *
   * @param x the row index (0-4)
   * @param y the column index (0-10)
   * @return the Room at (x, y), or null if no room exists
   */
  public Room getRoom(int x, int y) {
  	if (!hasRoom(x, y)) {
  		return null;
  	}
  	return floor[x][y];
  }
  
  /**
   * Places the hero at the specified coordinates on the floor.
   *
   * @param x the row index (0-4)
   * @param y the column index (0-10)
   */
  public void placeHero(int x, int y) {
  	if(!hasRoom(x, y)) {
  		return;  	
  	}
  	this.heroX = x; 
  	this.heroY = y; 
  }
  
  /**
   * Places the hero in the first corridor room found on the floor.
   */
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
  
  /**
   * Returns the current coordinates of the hero on this floor.
   *
   * @return an array with [heroX, heroY]
   */
  public int[] getPlaceHero() {
  	return new int[]{heroX, heroY};
  }
  
  /**
   * Checks if there is a valid path from the current room to the target room.
   * Only corridor rooms or the target room itself can be used in the path.
   *
   * @param current the starting room
   * @param target  the destination room
   * @param visited the list of already visited rooms
   * @return true if a path exists, false otherwise
   */
  public boolean goodPath(Room current, Room target, List<Room> visited) {
  	if(current.equals(target)) {
  		return true; 
  	}
  	visited.add(current); 
  	for(var n : current.neighbors()) {
  		if(!visited.contains(n) && (n.isCorridor() || n.equals(target))) {
  			if(goodPath(n, target, visited)) {
  				return true; 
  			}
  		}
  	}
  	return false; 
  }
  
  /**
   * Checks if the room at (x, y) is accessible from the hero's current position.
   *
   * @param x the row index (0-4)
   * @param y the column index (0-10)
   * @return true if the room exists, is not the hero's current room, and is reachable via corridors
   */
  public boolean isAccessible(int x, int y) {
  	if (!hasRoom(x, y)) {
  		return false;
  	}
  	if (x == heroX && y == heroY) {
  		return false;
  	}
  	return goodPath(floor[heroX][heroY], floor[x][y], new ArrayList<>());
  }
 
  /** Returns the hero's current row index on the floor. */
  public int heroX() { return heroX; }
  
  /** Returns the hero's current column index on the floor. */
  public int heroY() { return heroY; }
  
  /**
   * Generates a random room based on the type probabilities.
   * If the room was already generated, returns the existing generated room.
   *
   * @param gridRoom the base room to generate from
   * @return the generated Room
   */
	public Room generateRandomRoom(Room gridRoom) {
		if (gridRoom.getGeneratedRoom() != null) {
			return gridRoom.getGeneratedRoom();
		}
		var random = rand.nextInt(5);
		Room randomRoom;
		switch(random) {
			case 0 -> randomRoom = new Room(gridRoom.floor(), true, false, false, false, false, false);
			case 1 -> randomRoom = new Room(gridRoom.floor(), false, true, false, false, false, false);
			case 2 -> randomRoom = new Room(gridRoom.floor(), false, false, true, false, false, false);
			case 3 -> randomRoom = new Room(gridRoom.floor(), false, false, false, true, false, false);
			case 4 -> randomRoom = new Room(gridRoom.floor(), false, false, false, false, true, false);
			default -> randomRoom = new Room(gridRoom.floor(), false, false, false, false, false, false);
		}
		randomRoom.getLoot();
		gridRoom.setGeneratedRoom(randomRoom);
		return randomRoom;
	}
	
	/**
	 * Tries to unlock a room/grid using a key from the hero's backpack.
	 *
	 * @param grid the room to unlock
	 * @param hero the hero attempting to unlock the room
	 * @return true if the room was unlocked or already visited, false if no key is available
	 */
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
      new Room(0, false, true, false, false, false, false),
      new Room(0, true, false, false, false, false, false),
      new Room(0, true, false, false, false, false, false),
      new Room(0, false, false, true, false, false, false),
      new Room(0, false, false, false, true, false, false),
      new Room(0, true, false, false, false, false, false),
      new Room(0, false, false, false, true, false, false),
      new Room(0, false, false, false, false, true, false),
      new Room(0, false, false, false, false, false, true)
    );
  }

  private static List<Room> listRoomOfFloor2() {
    return List.of(
      new Room(1, false, false, false, true, false, false),
      new Room(1, false, true, false, false, false, false),
      new Room(1, false, false, false, false, false, false),
      new Room(1, true, false, false, false, false, false),
      new Room(1, false, false, true, false, false, false),
      new Room(1, true, false, false, false, false, false), 
      new Room(1, false, false, false, false, true, false), 
      new Room(1, true, false, false, false, false, false), 
      new Room(1, false, false, false, true, false, false),
      new Room(1, false, false, false, false, false, true)

    );
  }

	private static List<Room> listRoomOfFloor3() {
	    return List.of(
	      new Room(2, false, false, true, false, false, false),
	      new Room(2, true, false, false, false, false, false),
	      new Room(2, true, false, false, false, false, false),
	      new Room(2, false, true, false, false, false, false),
	      new Room(2, true, false, false, false, false, false),
	      new Room(2, false, false, false, false, true, false),
	      new Room(2, false, false, false, true, false, false), 
	      new Room(2, false, false, false, true, false, false),
	      new Room(2, false, false, false, false, false, true)
	    );

	}
	  
  private static void addRoomsToFloor(Floor floor, List<Room> rooms, List<int[]> positions) {
    for (int i = 0; i < rooms.size(); i++) {
      int[] pos = positions.get(i);
      floor.add(rooms.get(i), pos[0], pos[1]);
    }
  }
  
  /**
   * Adds all adjacent rooms as neighbors for each room in the floor grid.
   * Checks up, down, left, and right for existing rooms.
   */
  public void addAllNeighbor() {
  	for (int i = 0; i < 5; i++) {
  		for (int j = 0; j < 11; j++) {
  			if (!hasRoom(i, j)) {
  				continue;
  			}
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
      new int[]{0, 4}, new int[]{3, 1}, new int[]{2, 3},
      new int[]{2, 6}, new int[]{2, 4}, new int[]{0, 8}, new int[] {2, 10}, 
      new int[] {2, 8}, new int[] {4, 8}, new int[]{0, 10}
  		);
  }

  private static List<int[]> positionsRoomsFloor3() { 
  	return List.of(
      new int[]{2, 7}, new int[]{2, 5}, new int[]{4, 2}, 
      new int[]{2, 2}, new int[]{4, 8}, new int[]{4, 5}, 
      new int[]{0, 5}, new int[] {2, 0}, new int[]{0, 8}
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
        new int[]{0, 1}, new int[]{0, 2}, new int[]{0, 3}, 
        new int[]{0, 5}, new int[]{0, 6}, new int[]{0, 7},
        new int[]{1, 1}, new int[]{2, 1}, new int[]{2, 7},
	      new int[]{1, 4}, new int[]{2, 2}, new int[]{2, 5}, 
	      new int[]{1, 6}, new int[]{3, 8}, new int[]{2, 9},
	      new int[]{0, 9}, new int[]{1, 8}
	    );
	}
	
	private static List<int[]> corridorPositionsFloor3() {
	    return List.of(
        new int[]{0, 2}, new int[]{1, 2}, new int[]{0, 3}, new int[]{0, 4},
        new int[]{2, 1}, new int[]{3, 2}, new int[]{4, 3}, new int[]{4, 4},
        new int[]{2, 3}, new int[]{2, 4}, new int[]{3, 5},
        new int[]{0, 6}, new int[]{0, 7}, new int[]{1, 8}, new int[]{2, 8},
        new int[]{4, 6}, new int[]{4, 7}, new int[]{3, 8}, new int[]{2, 6}
	    );
	}

	/**
	 * Creates 3 floors with rooms and corridors.
	 * Adds neighbors for all rooms.
	 *
	 * @return list of the 3 floors
	 */
  public static List<Floor> createFloors() {
    Floor floor1 = new Floor();
    Floor floor2 = new Floor();
    Floor floor3 = new Floor();
    addRoomsToFloor(floor1, listRoomOfFloor1(), positionsRoomsFloor1());
    addRoomsToFloor(floor2, listRoomOfFloor2(), positionsRoomsFloor2());
    addRoomsToFloor(floor3, listRoomOfFloor3(), positionsRoomsFloor3());
    for(var pos : corridorPositionsFloor1()) {
    	floor1.add(new Room(0, false, false, false, false, false, false), pos[0], pos[1]);
    }
    for(var pos : corridorPositionsFloor2()) {
    	floor2.add(new Room(1, false, false, false, false, false, false), pos[0], pos[1]);
    }
    for(var pos : corridorPositionsFloor3()) {
    	floor3.add(new Room(2, false, false, false, false, false, false), pos[0], pos[1]);
    }
    floor1.addAllNeighbor();
    floor2.addAllNeighbor();
    floor3.addAllNeighbor();
		return List.of(floor1, floor2, floor3); 
  }
}
