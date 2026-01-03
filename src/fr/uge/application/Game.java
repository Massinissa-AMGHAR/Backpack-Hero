package fr.uge.application;

import fr.uge.character.Hero;

import fr.uge.inventory.Backpack;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemRegistry;
import fr.uge.inventory.Items;
import fr.uge.map.Dungeon;
import fr.uge.map.Floor;
import fr.uge.map.Room;

public class Game {
	
	private final Interactions interaction; 
	private final Dungeon dungeon; 
	private final Hero hero;
	
	public Game(Interactions interaction) {
		this.interaction = interaction; 
		dungeon = new Dungeon(2); 
		dungeon.createDungeon();
		
		Backpack backpack = new Backpack(); 
		var item = ItemRegistry.create(ItemRegistry.WOODEN_SWORD);
		item.setLeftPlacement(1, 2); 
		backpack.add(item, 1, 2);
		
		hero = new Hero("Mouse", 40, 40, 0, 3, 5, 5,  0, 0, backpack, interaction);
	}
	
	public void start() {
		boolean victory = false; 
		int startFloor = 0; 
		Floor floor = dungeon.getFloor(startFloor); 
		floor.addAllNeighbor();
		floor.initPlaceHero();
		
		while(!victory) {
			Room currentRoom= floor.getRoom(floor.heroX(), floor.heroY());
			sceneRoom(currentRoom); 
			
			if(currentRoom.hasDoor()){
				floor = dungeon.nextFloor(floor); 
				if(floor == null) {
					victory = true; 
				}
				else {
					IO.println("Welcome to the new floor " + hero.name() + " !"); 
					floor.initPlaceHero();;
				}
			}
			if (floor != null) {
				IO.println(floor.toString()); 
				floor.moveHero(interaction); 
			}
		}
		IO.println("##### End of game #####");
	}
	
	private void sceneRoom(Room room) {
		if(room.hasMerchant()) room.merchant().interaction(hero, interaction);
		if(room.hasAngel())room.angel().interaction(hero, interaction);
		if(room.hasEnemies()) enemiesRoom(room); 
		if(room.hasTreasure()) treasureRoom(room); 
	}
	
	private void enemiesRoom(Room room) {
		var enemies = room.enemies(); 
		IO.println("There are several enemies in this room :\n\n" + enemies.toString()); 
		for(var e : enemies.getEnemies()) {
			var fight = new Fight(hero, e, interaction); 
			if(!fight.game()) {
				IO.println("##### End of game #####");
				System.exit(0); 
			}
		}	
		room.noEnemies(); 
		Items reward = new Items(); 
		reward.randomItems(3);
		hero.manageBackpack(reward, false);
	}
	
	private void treasureRoom(Room room) {
		IO.println("Oh ! You found a treasure !"); 
		var treasure = room.treasure();  
	  hero.manageBackpack(treasure, false);
	  if(treasure.getItems().isEmpty()) room.noTreasure();
	}
		
}
