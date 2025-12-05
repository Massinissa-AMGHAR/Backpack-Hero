package fr.uge.graphics;
import java.awt.Color;


import com.github.forax.zen.Application;

public class Game {
	public static void main(String[] args) {
		Application.run(Color .BLACK, new Controller()::game);
	}
}

