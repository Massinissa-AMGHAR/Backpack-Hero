package fr.uge.character;
import java.util.Objects;
import fr.uge.inventory.Backpack;

public class HeroProgression {
	private int xp; 
	private int level; 
	public HeroProgression() {
		this.xp= 0;
		this.level = 1; 
	}
	
	public int xp() {return xp; }
	public int level() {return level;}
	
  public void winXp(Enemy enemy, Backpack backpack) {
    Objects.requireNonNull(enemy, "Ennemy cannot be null");
    xp += enemy.xp();
    IO.println("Hero wins " + enemy.xp() + " experience points"); 
    IO.println("Hero has " + xp + " experience points");
    levelUp(backpack); 
  }
  
  private void levelUp(Backpack backpack) {
  	while(xp >= xpThreshold()) {
  		xp -= xpThreshold(); 
  		level++; 
  		IO.println("You have reached level "+ level + " !"); 
  		IO.println("Hero has " + xp + " experience points");
  		backpack.unlockCase(3); 
  	}
  }
  
  private int xpThreshold() {
  	return level * 15; 
  }
  
}
