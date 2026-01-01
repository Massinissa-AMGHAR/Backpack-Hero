package fr.uge.application;
import fr.uge.graphics.app.*;
import fr.uge.graphics.image.*;

import java.awt.Color;

import com.github.forax.zen.Application;


public class Main {
	static void main(String[] args) {
		var graphic = true;
		if (graphic) {
	    GameGraphics gameGraphics = new GameGraphics(); 
	    ImageLoader imagesMap = new ImageLoader("data", "hero.png", "minihero.png", "door.png", "enemymap.png", "minimerchant.png", "minihealer.png");
	    ImageLoader  imagesEnemies = new ImageLoader("data", "enemy.png", "minienemy.png");
	    ImageLoader imagesItems = new ImageLoader("data/", "Wooden_sword.png", "Arrow.png", "Rough_Buckler.png", "Tunic.png", "magic_wand.png", "mana_stone.png", "Gold.png",
																								"Cleaver.png", "Cursed_Blade.png", "Dagger.png", "Gold_Cleaver.png", "Heavy_Blade.png", "Key_Sword.png", "Poison_Arrow.png", "Tilted_Sword.png", "Vampiric_Axe.png",
																								"Robo_Buckler.png", "Iron_Helmet.png", "Knight_Armor.png", "Knight_Shield.png", "Manastone_Bow.png", "Electric_Wand.png", "Dark_Wand.png", "Robin's_Wand.png", "Keys.png", "curse.png");
	    ImageLoader imagesServices = new ImageLoader("data", "merchant.png", "healer.png");
	    ImageLoader imagesStatus = new ImageLoader("data", "Status_Poison.png", "fight.png", "defense.png", "curse.png");
	    Controller controller = new Controller(gameGraphics.hero(), gameGraphics.merchant(), gameGraphics.healer(), gameGraphics.dungeon(), gameGraphics.floor(), gameGraphics.enemies(), gameGraphics.interaction(), imagesMap, imagesEnemies, imagesItems, imagesServices, imagesStatus);
	    Application.run(Color.DARK_GRAY, controller::game);
		} else { 
	    Interactions interaction = new Interactions(); 
	    Game game = new Game(interaction); 
	    game.start();
		}
	}
}


