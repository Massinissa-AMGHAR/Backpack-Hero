package fr.uge.application;
import fr.uge.character.*;
import java.util.Scanner; 
import fr.uge.map.*;
import fr.uge.inventory.*;
public class Main {
	static void main(String[] args) {
		 
		boolean victory = false; 
		Scanner scanner = new Scanner(System.in); 
		var interaction = new Interactions(scanner); 
		Dungeon dungeon = new Dungeon(2); 
		dungeon.createDungeon();
		int startFloor = 0; 
		Floor floor = dungeon.getFloor(startFloor); 
		floor.addAllNeighbor();
		Backpack backpack = new Backpack(3, 5); 
		var item = Item.ALL_ITEM[0]; 
		item.leftPlacementX = 0;
		item.leftPlacementY = 0; 
		backpack.add(item, 0, 0);
		
		Hero hero = new Hero("Mouse", 40, 40, 0, 3, 5, 5,  0, 0, backpack);
		IO.println("###### Backpack Hero ######"); 
		floor.initPlaceHero();
		while(!victory) {
			Room currentRoom= floor.getRoom(floor.heroX(), floor.heroY()); 
			if(currentRoom.hasMerchant()) {
				currentRoom.merchant().interaction(hero, interaction); 
			}
			
			if(currentRoom.hasAngel()) {
				currentRoom.angel().interaction(hero, interaction); 
			}
			
			if(currentRoom.hasEnemies()) {
				var enemies = currentRoom.enemies(); 
				IO.println("There are several enemies in this room :\n\n" + enemies.toString()); 
				for(var e : enemies.getEnemies()) {
					var fight = new Fight(hero, e, interaction); 
					if(fight.game()) continue;
					else {
						IO.println("##### End of game #####");
						return; 
					}
				}	
				currentRoom.noEnemies(); 
				Items reward = new Items(); 
				reward.randomItems(3);
				hero.manageBackpack(reward, interaction, false);
			}
			if(currentRoom.hasTreasure()) { 
			 IO.println("Oh ! You found a treasure !"); 
			 var treasure = currentRoom.treasure();  
		   hero.manageBackpack(treasure, interaction, false);
		   if(treasure.getItems().isEmpty()) {
		  	 currentRoom.noTreasure();
		   }
			}
			
			
			if(currentRoom.hasDoor()) {
				floor = dungeon.nextFloor(floor); 
				if(floor == null) {
					victory = true; 
				}
				else {
					IO.println("Welcome to the new floor " + hero.name() + " !"); 
					floor.initPlaceHero();;
				}
			}
			IO.println(floor.toString()); 
			floor.moveHero(interaction); 
		}
		
		IO.println("##### End of game #####"); 
		
		
	}
}

