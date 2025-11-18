package fr.uge.character;
import java.util.Random; 
import java.util.ArrayList;

public class Enemies {
	
	private final ArrayList<Enemy> enemies; 
	private final Random rand; 
	
	public Enemies() {
		this.rand= new Random(); 
		enemies = new ArrayList<>();
    int enemiesNumber = rand.nextInt(2) + 1; 
    for(int i = 0; i < enemiesNumber; i++) {
    	int indexEnemies = rand.nextInt(Enemy.ALL_ENEMIES.length); 
    	enemies.add(Enemy.ALL_ENEMIES[indexEnemies]); 
    }
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies; 
	}
	
}
