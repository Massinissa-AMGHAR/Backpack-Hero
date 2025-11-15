package fr.uge.map;
import fr.uge.character.*;
import fr.uge.temp.*;
import java.util.ArrayList; 

public class Room {
  private final ArrayList<Ennemy> enemies = new ArrayList<>(); 
  private final Merchant merchant; 
  private final Healer healer; 
  private final Grid grid; 
  private final boolean exit;
  private final boolean treasure;
  
  public Room(Merchant merchant, Healer healer, Grid grid, ArrayList<Ennemy> enemies, boolean exit, boolean treasure) {
    this.merchant = merchant; 
    this.healer = healer; 
    this.grid = grid; 
    if (enemies != null) {
    	for (Ennemy e : enemies) this.enemies.add(e);
    } 
    this.exit = exit;
    this.treasure = treasure;
  }
  
  public Merchant merchant() { return merchant; }
  public Healer healer() { return healer; }
  public Grid grid() { return grid; }
  public ArrayList<Ennemy> enemies() { return enemies; }
  public boolean isExit() { return exit; }
  public boolean hasTreasure() { return treasure; }

  public boolean hasEnemies() {
  	if (enemies.isEmpty()) return false;
  	return true;
  }
  
  public boolean hasMerchant() {
  	if (merchant == null) return false;
  	return true;
  }
  
  public boolean hasHealer() {
  	if (healer == null) return false;
  	return true;
  }
  
  @Override
  public String toString() {
	var builder = new StringBuilder(); 
	builder.append("[Room : "); 
    if(!(enemies.isEmpty())) builder.append("E "); 
    if(merchant != null) builder.append("M ");
    if(healer != null) builder.append("H ");
    if(grid != null) builder.append("G ");
    
    builder.append("]"); 
    return builder.toString();      
  }
 
}