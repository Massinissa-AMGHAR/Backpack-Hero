package fr.uge.application;
import fr.uge.character.*;
import fr.uge.map.*;
import fr.uge.inventory.*;
import java.util.ArrayList; 
import java.util.Scanner; 

public class Main {
	static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); 
		boolean victory = false; 
		
		Dungeon dungeon = new Dungeon(2); 
		dungeon.createDungeon();
		Backpack backpack = new Backpack(3, 5); 
		backpack.add(Item.ALL_ITEM[0], 0, 0);
		Hero hero = new Hero("Mouse", 40, 40, 0, 3, 5, 5, 30, 0, 0, backpack);
		int startFloor = 0; 
		Floor floor = dungeon.getFloor(startFloor); 
		floor.placeHero(1, 1);
		IO.println("###### Backpack Hero ######"); 
		while(!victory) {
			Room currentRoom= floor.getRoom(floor.heroX(), floor.heroY()); 
			if(currentRoom.hasMerchant()) {
				currentRoom.merchant().interactionBuy(hero, scanner); 
				currentRoom.merchant().interactionSell(hero, scanner);
			
			}
			victory = true; 
		}
		
		
	}
}

