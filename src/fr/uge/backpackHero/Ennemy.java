package fr.uge.backpackHero;
import java.util.Objects; 
public record Ennemy(int lifePoints, int protection, String type) implements Fighter{
  public Ennemy {
    Objects.requireNonNull(lifePoints); 
    Objects.requireNonNull(protection); 
    Objects.requireNonNull(type); 
	  
  }
  
  public void attack(Fighter opponent) {
	  
  }
  
  public void increaseProtection() {
	  
  }

}
