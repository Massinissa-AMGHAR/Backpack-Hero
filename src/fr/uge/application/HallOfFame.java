package fr.uge.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the hall of fame of the game.
 * Saves and reads the best scores in a text file.
 */

public class HallOfFame {
	/**
	 * File used to save the scores. 
	*/
	private static final Path FILE = Path.of("data", "hof.txt");
	
	/**
	 * Saves a new score in the file.
	 * @param score the score to save.
	*/
	public void addScore(int score) {
		try(var writer = Files.newBufferedWriter(FILE, StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
			writer.write(Integer.toString(score)); 
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error, unable to write the score ");
		}
	}
	
	/**
	 * Reads the best scores from the file.
	 * @return a list of the 3 best scores.
	*/
	public List<Integer> bestScores(){
		try {
			if(!Files.exists(FILE))return new ArrayList<>();
			return Files.readAllLines(FILE).stream()
									.filter(line -> !line.isBlank())
									.map(Integer::parseInt)
									.sorted((a, b) -> b - a)
									.limit(3)
									.toList(); 
		}catch(IOException e) {
			System.err.println("Error, unable to read hall_of_fame.txt ");
			return new ArrayList<>(); 
		}
	}
}