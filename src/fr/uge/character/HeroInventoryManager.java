package fr.uge.character;

import java.util.Objects;

import fr.uge.application.Interactions;
import fr.uge.inventory.Backpack;
import fr.uge.inventory.Item;
import fr.uge.inventory.Items;

public class HeroInventoryManager {
  private Backpack backpack;
	private final Interactions interaction; 
	
	public HeroInventoryManager(Backpack backpack, Interactions interaction) {
		Objects.requireNonNull(backpack); 
		Objects.requireNonNull(interaction); 
		
		this.backpack = backpack; 
		this.interaction = interaction; 
	}
	
	public Backpack backpack() {return backpack; }
	
	public void manageBackpack(Items items, boolean isShop) {
		while(! items.getItems().isEmpty()) {
			var listItems = items.getItems(); 
			IO.println(items.toString()); 
			IO.println("\nDo you want to add an item in your backpack ? (y/n) "); 
			if(!interaction.requestYesNo())return; 
			IO.println("Choose an item : (line)"); 
			int index = interaction.requestInt(); 
			if(index < 0 || index >= listItems.size()) {
				IO.println("Invalid index"); 
				continue; 
			}
			Item item = listItems.get(index); 
			if(item.isGold()) {
				var firstItem = backpack.findGoldItem(); 
				if(firstItem != null) {
					firstItem.addGold(item.goldAmount()); 
					listItems.remove(index); 
					continue; 
				}
			}
			if(manageItemPlacement(item,items, index, isShop)) listItems.remove(item); 
		}
	}
	
	private boolean manageItemPlacement(Item item, Items items, int index, boolean isShop) {
		while(true) {
			IO.println("#### Backpack Management ####"); 
			IO.println(backpack.toString()); 
			IO.println("What do you want to do ? "); 
			IO.println("1) Moove an item :");
			IO.println("2) Delete an item :");
			IO.println("3) Rotate an item :");
			IO.println("4)Place your new item :"); 
			IO.println("5) Go back :");
			
			int choice = interaction.requestInt(); 
			switch(choice) {
				case 1 -> choiceTranslation(interaction); 
				case 2 -> choiceDelItem(interaction); 
				case 3 -> choiceRotation(interaction); 
				case 4 -> {
					if(isShop && !buy(item.goldCost())) { 
						return false; 
					}
					if(choicePlaceItem(item, items, index)) {
						return true; 
					}
					else IO.println("Item not placed"); return false; 
				}
				case 5 -> {
					IO.println("Go back !"); 
					return false;  
				}
				default -> IO.println("This choice doesn't exist"); 
			}
			
		}
	}
	
	 public boolean buy(int cost) {
	    Item gold = backpack.findGoldItem(); 
	    if(gold == null || gold.goldAmount() < cost) {
	    	IO.println("You don't have enough money!"); 
	    	return false; 
	    }
	    gold.spendGold(cost);
	    IO.println("You spend "+ cost + " golds"); 
	  	IO.println("You have "+ gold.goldAmount() + " golds"); 
	    return true; 
	  }
	 
		private Item chooseItemInBackpack() {
			IO.println("Choose an item : (line/column)"); 
			int line = interaction.requestInt(); 
			int column = interaction.requestInt(); 
			return backpack.choice(line, column); 
		}
		
		private void choiceRotation(Interactions interaction) {
			IO.println("You decided to make a rotation");
			var item = chooseItemInBackpack(); 
			if(item == null) return; 
			var rotated = item.copy(); 
			rotated.applyRotation(); 
			backpack.delete(item);
			if(backpack.canPlaceItem(rotated, item.leftPlacementX(), item.leftPlacementY())) {
				backpack.add(rotated, item.leftPlacementX(), item.leftPlacementY());
				rotated.setLeftPlacement(item.leftPlacementX(), item.leftPlacementY()); 
				IO.println("The item was placed"); 
				return; 
			}
			backpack.add(item, item.leftPlacementX(), item.leftPlacementY());
			IO.println("Item cannot be placed");
			return; 
		}
		
		private void choiceTranslation(Interactions interaction) {
			IO.println("You decided to make a translation"); 
			var item = chooseItemInBackpack(); 
			if(item == null) return; 
			int line = item.leftPlacementX(); 
			int column = item.leftPlacementY(); 
			backpack.delete(item); 
			Items list = new Items(); 
			list.addItem(item); 
			if(!choicePlaceItem(item, list, 0)) {
				backpack.add(item, line, column);
				backpack.toString(); 
				IO.println("Item cannot be moved and was restored"); 
			}
		}
		
		private void choiceDelItem(Interactions interaction) {
			IO.println("You decided to delete an item");
		  var item = chooseItemInBackpack(); 
			if(item == null)return; 
			backpack.delete(item); 
			IO.println("You delete " + item.name()); 	
		}
		
		public boolean choicePlaceItem(Item item, Items listItems, int index) {
			IO.println(backpack.toString()); 
			if(item.type().equals("GOLD"))IO.println("Choose a place for your gold : (line/column)"); 
			else IO.println("Choose a place for your item : (line/column)"); 
			int line = interaction.requestInt(); 
			int column = interaction.requestInt(); 
			int availableWidth = backpack.lengthLine(line, column);
			int availableHeight = backpack.lengthColumn(line, column);
			if (item.width() <= availableWidth && item.height() <= availableHeight) {
				backpack.add(item, line, column);
				item.setLeftPlacement(line, column); 
				listItems.deleteItem(index); 
				IO.println(backpack.toString()); 
				return true; 
			} 
			else {
				IO.println("Item doesn't fit at this position.");
				return false;  
			}

	 }
		
}


