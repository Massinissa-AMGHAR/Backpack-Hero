package fr.uge.application;
import fr.uge.game.GameGraphics;
import fr.uge.graphics.*;
import fr.uge.graphics.image.*;

import java.awt.Color;

import com.github.forax.zen.Application;


public class Main {
	static void main(String[] args) {
    GameGraphics gameGraphics = new GameGraphics(); 
    ImageLoader imagesMap = new ImageLoader("data", "hero.png", "minihero.png", "door.png", "enemymap.png", "minimerchant.png", "minihealer.png", "treasure.png", "grid.png", "floor1.png", "floor2.png", "floor3.png", "background_menu.png", "victory_background.png", "hero_victory.png");
    ImageLoader  imagesEnemies = new ImageLoader("data", "enemy.png", "minienemy.png");
    ImageLoader imagesItems = new ImageLoader("data/", "Wooden_sword.png", "Arrow.png", "Rough_Buckler.png", "Tunic.png", "magic_wand.png", "mana_stone.png", "Gold.png",
																							"Cleaver.png", "Cursed_Blade.png", "Dagger.png", "Gold_Cleaver.png", "Heavy_Blade.png", "Key_Sword.png", "Poison_Arrow.png", "Tilted_Sword.png", "Vampiric_Axe.png",
																							"Robo_Buckler.png", "Iron_Helmet.png", "Knight_Armor.png", "Knight_Shield.png", "Manastone_Bow.png", "Electric_Wand.png", "Dark_Wand.png", "Robin's_Wand.png", "Keys.png", "curse.png",
																							"Cool_Drink.png", "Meal.png", "Steak.png", "Cursed_Sap.png");
    ImageLoader imagesServices = new ImageLoader("data", "merchant.png", "healer.png");
    ImageLoader imagesStatus = new ImageLoader("data", "Status_Poison.png", "fight.png", "defense.png", "curse_action.png", "Block.png");
    Controller controller = new Controller(gameGraphics.hero(), gameGraphics.dungeon(), gameGraphics.floor(), imagesMap, imagesEnemies, imagesItems, imagesServices, imagesStatus);
    Application.run(Color.BLACK, controller::game);
	}
}


