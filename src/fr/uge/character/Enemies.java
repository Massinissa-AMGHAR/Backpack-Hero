package fr.uge.character;
import java.util.Random; 
import java.util.ArrayList;

/**
 * Manages the enemies on a floor.
 */
public class Enemies {
	
	private final ArrayList<Enemy> enemies; 
	private final Random rand; 
	
  /**
   * Creates a random number of enemies depending on the floor.
   *
   * @param floor the current floor of the game
   */
	public Enemies(int floor) {
		this.rand= new Random(); 
		enemies = new ArrayList<>();
    int enemiesNumber = rand.nextInt(3) + 1; 
    int maxIndex;
    switch(floor) {
    	case 0 -> maxIndex = 2;
    	case 1 -> maxIndex = 4;
    	case 2 -> maxIndex = 5;
    	default -> maxIndex = Enemy.ALL_ENEMIES.length;
    }
    for(int i = 0; i < enemiesNumber; i++) {
    	int indexEnemies = rand.nextInt(maxIndex); 
    	var clone = Enemy.ALL_ENEMIES[indexEnemies]; 
    	enemies.add(new Enemy(clone)); 
    }
	}
	
  /**
   * Returns the list of enemies.
   *
   * @return list of enemies
   */
	public ArrayList<Enemy> getEnemies() {
		return enemies; 
	}
}
