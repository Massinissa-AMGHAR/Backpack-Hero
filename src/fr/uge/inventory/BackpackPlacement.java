package fr.uge.inventory;

public class BackpackPlacement {
	private Backpack backpack; 
	
	public BackpackPlacement(Backpack backpack) {
		this.backpack = backpack; 
	}
	
	public int lengthLine(int line, int column) {
		int length = 0; 
		for(int j = column; j < backpack.maxColumns(); j++) {
		  if(caseFree(line, j)) length++; 
		  else break;  
	  }
		return length; 
	}
	  
	public int lengthColumn(int line, int column) {
		int length = 0; 
	  for(int i = line; i < backpack.maxLines(); i++) {
	  	if(caseFree(i, column)) length++; 
	    else break; 
	  }
	  return length; 
	}
	  
  public boolean caseFree(int line, int column) {
    if(line < 0 || line >= backpack.maxLines() || column < 0 || column >= backpack.maxColumns()) return false;
    if(backpack.grid().get(line).get(column) != null) return false; 
    if(!backpack.unlocked()[line][column])return false; 
    return true; 
  }
	
  public boolean canPlaceItem(Item item, int line, int column) {
		boolean[][] shape = item.shape(); 
				
				for(int i = 0; i < item.height(); i++) {
					for(int j = 0; j < item.width(); j++) {
						if(shape[i][j]) {
							if(!caseFree(line + i, column + j)) {
								IO.println("The case is already occupied");
								return false; 
							}
						}
					}
				}
		return true; 
  }
  public boolean canPlaceCurse(Item curse, int line, int column) {
  	if(line < 0 || column < 0 || line + curse.height() > backpack.maxLines() || column + curse.width() > backpack.maxColumns()) return false;
  	
  	for(int i = 0; i < curse.height(); i++) {
  		for(int j = 0; j < curse.width(); j++) {
  			if(curse.shape()[i][j] && !backpack.unlocked()[line + i][column + j])return false; 
  		}
  	}
  	return true; 
  }
  
  public void add(Item item, int line, int column) { 
  	boolean[][] shape = item.shape(); 
		for(int i = 0; i < item.height(); i++) {
			for(int j = 0; j < item.width(); j++) {
				if(shape[i][j]) {
					backpack.grid().get(line + i).set(column + j, item); 
				}
			}
		}
  }
  
  public void delete(Item item) {
    for(int i = 0; i < backpack.maxLines(); i++) {
      for(int j = 0; j < backpack.maxColumns(); j++) {
      	Item current = backpack.grid().get(i).get(j); 
        if(current != null && current.id() == item.id()) backpack.grid().get(i).set(j, null); 
      }
    }
  }
}
