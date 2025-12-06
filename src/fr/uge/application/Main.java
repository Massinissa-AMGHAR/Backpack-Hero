package fr.uge.application;

import java.awt.Color;
import java.util.Scanner;

import com.github.forax.zen.Application;

import fr.uge.graphics.*;
import fr.uge.character.*;
import fr.uge.inventory.*;

public class Main {
	static void main(String[] args) {
		var graphic = true;
		if (graphic) {
	    GameGraphics gameGraphics = new GameGraphics(); 
	    ImageLoader imagesMap = new ImageLoader("data", "hero.png", "minihero.png", "ratwolf.png", "door.png");
	    ImageLoader  imagesEnemies = new ImageLoader("data", "enemy.png");
	    ImageLoader imagesItems = new ImageLoader("data", "Wooden_Sword.png");
	    Controller controller = new Controller(gameGraphics.hero(), gameGraphics.enemies(), gameGraphics.interaction(), imagesMap, imagesEnemies, imagesItems);
	    Application.run(Color.BLACK, controller::game);
		} else {
	    Scanner scanner = new Scanner(System.in); 
	    Interactions interaction = new Interactions(scanner); 
	    Game game = new Game(interaction); 
	    game.start();
		}
	}
}

