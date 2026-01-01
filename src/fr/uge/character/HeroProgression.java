package fr.uge.character;
import java.util.Objects;
import fr.uge.inventory.Backpack;

public class HeroProgression { 
	private int xp; 
	private int level; 
	private boolean leveledUpDuringFight = false;
	
	public HeroProgression(Hero hero) {
		this.xp= 0;
		this.level = 1; 
	}
	
	public int xp() {return xp; }
	public int level() {return level;}
	public boolean hasLeveledUpDuringFight() { return leveledUpDuringFight; }
	public void resetLevelUpFlag() { leveledUpDuringFight = false; }
	
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
  		backpack.unlockCase(backpack.casesToUnlock()); 
  	}
  }
  
  public void WinXpGraphics(Enemy enemy) {
    Objects.requireNonNull(enemy);
    xp += enemy.xp();
    IO.println("Hero wins " + enemy.xp() + " XP. Total: " + xp);

    while (xp >= level * 10) {
        xp -= level * 10;
        level++;
        IO.println("You have reached level " + level + "!");
        leveledUpDuringFight = true;
    }
    
  }
  
  public int xpThreshold() {
  	return level * 10; 
  }
}
