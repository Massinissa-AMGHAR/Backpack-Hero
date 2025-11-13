package fr.uge.map;
import fr.uge.character.*;
import fr.uge.temp.*;
import java.util.ArrayList; 

public class Room {
  private final ArrayList<Ennemy> enemies; 
  private final Merchant merchant; 
  private final Healer healer; 
  private final Grid grid; 
  
  public Room(Merchant merchant, Healer healer, Grid grid, ArrayList<Ennemy> enemies) {
    
	this.enemies = enemies; 
    this.merchant = merchant; 
    this.healer = healer; 
    this.grid = grid; 
    
  }
  
  
  @Override
  public String toString() {
	var builder = new StringBuilder(); 
	builder.append("[Room : "); 
    if(enemies != null) builder.append("E "); 
    if(merchant != null) builder.append("M ");
    if(healer != null) builder.append("H ");
    if(grid != null) builder.append("G ");
    
    builder.append("]"); 
    return builder.toString();      
  }
 
}