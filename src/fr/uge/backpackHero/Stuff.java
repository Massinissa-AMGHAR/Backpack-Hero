package fr.uge.backpackHero;

public class Stuff {
  private final String name; 
  private final String type; 
  private int price; 
  
  public Stuff(String name, String type, int price){
    this.name = name; 
    this.type = type; 
    this.price = price; 
  }
  
  public String name() {
    return name; 
  }
  
  public String type() {
    return type; 
  }
  
  public int price() {
    return price; 
  }
}
