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
		backpack.add(Item.ALL_ITEM[0], 0, 0);
		Hero hero = new Hero("Mouse", 40, 40, 0, 3, 5, 5, 30, 0, 0, backpack);
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
				Items reward = new Items(); 
				hero.manageBackpack(reward, interaction, false);
			}
			if(currentRoom.hasTreasure()) { 
				IO.println(currentRoom.treasure().toString()); 
				hero.winGold(currentRoom.treasure().gold());
			}
			
			if(currentRoom.hasDoor()) {
				floor = dungeon.nextFloor(floor); 
				if(floor == null) {
					victory = true; 
				}
				else {
					IO.println("Welcome to the new floor ! " + hero.name()); 
					floor.placeHero(1, 1);
				}
			}
			IO.println(floor.toString()); 
			floor.moveHero(interaction); 
		}
		
		IO.println("##### End of game #####"); 
		
		
	}
}

