package fr.uge.application;
import fr.uge.graphics.app.*;
import fr.uge.graphics.image.*;
import fr.uge.graphics.view.*;

import java.awt.Color;
import java.util.Scanner;

import com.github.forax.zen.Application;

import fr.uge.graphics.*;
import fr.uge.character.*;
import fr.uge.inventory.*;

public class Main {
	static void main(String[] args) {
		var graphic = true  ;
		if (graphic) {
	    GameGraphics gameGraphics = new GameGraphics(); 
	    ImageLoader imagesMap = new ImageLoader("data", "hero.png", "minihero.png", "door.png", "enemymap.png", "minimerchant.png", "minihealer.png");
	    ImageLoader  imagesEnemies = new ImageLoader("data", "enemy.png", "minienemy.png");
	    ImageLoader imagesItems = new ImageLoader("data", "Wooden_Sword.png");
	    ImageLoader imagesServices = new ImageLoader("data", "merchant.png", "healer.png");
	    Controller controller = new Controller(gameGraphics.hero(), gameGraphics.enemies(), gameGraphics.interaction(), imagesMap, imagesEnemies, imagesItems, imagesServices);
	    Application.run(Color.BLACK, controller::game);
		} else {
	    Scanner scanner = new Scanner(System.in); 
	    Interactions interaction = new Interactions(scanner); 
	    Game game = new Game(interaction); 
	    game.start();
		}
	}
}


 