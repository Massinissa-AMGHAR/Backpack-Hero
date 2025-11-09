package fr.uge.backpackHero;
import java.util.Objects; 
public record Hero(int lifePoints, int protection, int energyPoints, int manaPoints, int or, Backpack backpack) implements Fighter{
  public Hero {
    Objects.requireNonNull(lifePoints); 
    Objects.requireNonNull(protection); 
    Objects.requireNonNull(energyPoints);
    Objects.requireNonNull(manaPoints);
    Objects.requireNonNull(or); 
    Objects.requireNonNull(backpack); 
    assert(lifePoints >= 0); 
    assert(protection >= 0); 
    assert(energyPoints >=0); 
    assert(manaPoints >= 0); 
    assert(or >= 0); 
	  
  }
  
  public void attack(Fighter opponent) {
	  
  }
  
  public void increaseProtection() {
	  
  }

}
