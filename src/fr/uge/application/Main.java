package fr.uge.application;
import fr.uge.character.*;
import fr.uge.map.*;
import fr.uge.inventory.*;
import java.util.ArrayList; 

public class Main {
	static void main(String[] args) {
		
    Ennemy ratWolf = new Ennemy("Rat Wolf", 20, 0, 6, 12, 2, 7, 6); 
    //Ennemy ratWolf1 = new Ennemy("Rat Wolf", 20, 0, 12, 7, 6); 
    
    var enemies = new ArrayList<Ennemy>();
    enemies.add(ratWolf);
   // enemies.add(ratWolf1); 
        
		Floor floor1 = new Floor(3, 2); 
		Room room1 = new Room(null, null, null, null, false, false); 
		Room room2 = new Room(null, null, null, enemies, false, false); 
		floor1.add(room1, 0, 1); 
		floor1.add(room2, 0, 0); 
		IO.println(floor1); 
		Backpack backpack = new Backpack(3, 5);
    Item woodenSword = Item.ALL_ITEM[0];  
    //Item roughBuckler = Item.ALL_ITEM[2]; 
    Item manaStone = Item.ALL_ITEM[5];    
    Item magicWand = Item.ALL_ITEM[4];  

    backpack.add(woodenSword, 0, 0);
    //backpack.add(roughBuckler, 1, 2);
    //backpack.add(manaStone, 1, 1);

 
    Hero hero = new Hero("Mouse", 40, 40, 0, 3, 5, 0, 1, 0, 1, backpack);
    var merchant = new Merchant();
    IO.println(merchant);
    
    IO.println("=== GAME START ===");
    IO.println("Hero stats: " + hero);
    IO.println("Enemy stats: " + ratWolf + "\n");

    // Lancement du combat
    Fight fight = new Fight(hero, ratWolf);
    fight.game();


    // Affichage après combat
    IO.println("\n=== GAME END ===");
    IO.println("Hero stats: " + hero);
    IO.println("Enemy stats: " + ratWolf);
        
		
	}
}

