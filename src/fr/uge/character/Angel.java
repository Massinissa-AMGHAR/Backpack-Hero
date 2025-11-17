package fr.uge.character;
import java.util.Scanner; 

public class Angel {
  private final int price; 
  private final int hp; 
  public Angel(int price, int hp){
    this.price = price; 
    this.hp = hp; 
  }
  
  public int price() {
    return price; 
  }
  
  public int hp() {
    return hp; 
  }
  
  public void interaction(Hero hero, Scanner scanner) {
  	IO.println("Angel : Hello, I am the Angel. I can restore your energy ... if you have some money\n"); 
  	IO.println(hero.toString());
  	IO.println("Do you want to restore your energy ? (y/no)");
  	String choice = scanner.nextLine(); 
  	
  	if(!choice.equals("y")) return; 
  	hero.treat(this); 
  }
}

