package fr.uge.inventory;
import java.util.Random; 
public class Treasure {
	private final Random rand;
	private final int gold; 
	public Treasure() {
		this.rand = new Random(); 
		this.gold = rand.nextInt(10)+ 1; 
	}
	
	public int gold() {
		return gold; 
	}
	
	@Override
	public String toString() {
		return "Treasure contains " + gold + " gold"; 
	}
}
