package fr.uge.application; 
public class Main {
	static void main(String[] args) { 
		Interactions interaction = new Interactions(); 
		Game game = new Game(interaction); 
		game.start();
	}
}

