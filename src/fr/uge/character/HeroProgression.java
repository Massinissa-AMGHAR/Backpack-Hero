package fr.uge.character;
import java.util.Objects;

/**
 * Tracks the hero's experience and level progression.
 */
public class HeroProgression {
	/**
	 * Current experience points
	 */
	private int xp; 
	/**
	 * Current hero leve
	 */
	private int level; 
	/**
	 *  True if leveled up during a fight
	 */
	private boolean leveledUpDuringFight = false;
	
  /**
   * Initializes progression for a hero.
   *
   * @param hero the hero whose progression is tracked
   */
	public HeroProgression(Hero hero) {
		this.xp= 0;
		this.level = 1; 
	}
	
	public int xp() { return xp; }
	public int level() { return level;}
	public boolean hasLeveledUpDuringFight() { return leveledUpDuringFight; }
	public void resetLevelUpFlag() { leveledUpDuringFight = false; }
  
	/**
	 * Adds experience from a defeated enemy and handles leveling up.
	 *
	 * @param enemy the defeated enemy
	 */
  public void WinXpGraphics(Enemy enemy) {
    Objects.requireNonNull(enemy);
    xp += enemy.xp();
    while (xp >= level * 10) {
      xp -= level * 10;
      level++;
      leveledUpDuringFight = true;
    }
  }
  
  /**
   * @return experience required to reach the next level
   */
  public int levelXP() {
  	return level * 10; 
  }
}
