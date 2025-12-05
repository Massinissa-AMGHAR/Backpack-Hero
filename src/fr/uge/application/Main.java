package fr.uge.application;

import java.util.Scanner; 
public class Main {
	static void main(String[] args) {
		 
		Scanner scanner = new Scanner(System.in); 
		Interactions interaction = new Interactions(scanner); 
		Game game = new Game(interaction); 
		game.start();
	}
}

