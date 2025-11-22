package fr.uge.character;
import java.util.Scanner; 
import fr.uge.application.*;

public class Healer implements interactionCharacters {
  private final int price; 
  private final int hp; 
  public Healer(int price, int hp){
    this.price = price; 
    this.hp = hp; 
  }
  
  public int price() {
    return price; 
  }
  
  public int hp() {
    return hp; 
  }
  
  @Override
  public void interaction(Hero hero, Interactions interaction) {
  	IO.println("Angel : Hello, I am the Angel. I can restore your energy ... if you have some money\n"); 
  	IO.println(hero.toString());
  	IO.println("Do you want to restore your energy ? (y/no)");
  	if(!interaction.requestYesNo())return; 
  	hero.treat(this); 
  	IO.println(hero.toString()); 
  }
}

