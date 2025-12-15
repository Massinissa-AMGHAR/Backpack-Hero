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
	    ImageLoader imagesItems = new ImageLoader("data", "Wooden_sword.png");
	    ImageLoader imagesServices = new ImageLoader("data", "merchant.png", "healer.png");
	    Controller controller = new Controller(gameGraphics.hero(), gameGraphics.enemies(), gameGraphics.interaction(), imagesMap, imagesEnemies, imagesItems, imagesServices);
	    Application.run(Color.DARK_GRAY, controller::game);
		} else { 
	    Interactions interaction = new Interactions(); 
	    Game game = new Game(interaction); 
	    game.start();
		}
	}
}
