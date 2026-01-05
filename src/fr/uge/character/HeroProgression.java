package fr.uge.character;
import java.util.Objects;

public class HeroProgression { 
	private int xp; 
	private int level; 
	private boolean leveledUpDuringFight = false;
	
	public HeroProgression(Hero hero) {
		this.xp= 0;
		this.level = 1; 
	}
	
	public int xp() { return xp; }
	public int level() { return level;}
	public boolean hasLeveledUpDuringFight() { return leveledUpDuringFight; }
	public void resetLevelUpFlag() { leveledUpDuringFight = false; }
  
  public void WinXpGraphics(Enemy enemy) {
    Objects.requireNonNull(enemy);
    xp += enemy.xp();
    while (xp >= level * 10) {
      xp -= level * 10;
      level++;
      leveledUpDuringFight = true;
    }
  }
  
  public int levelXP() {
  	return level * 10; 
  }
}
