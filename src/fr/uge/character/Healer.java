package fr.uge.character;
 
public class Healer {
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
}

