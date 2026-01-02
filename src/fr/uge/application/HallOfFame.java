package fr.uge.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class HallOfFame {
	private static final Path FILE = Path.of("hof.txt");
	
	public void addScore(int score) {
		try(var writer = Files.newBufferedWriter(FILE, StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
			writer.write(Integer.toString(score)); 
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error, unable to write the score ");
		}
	}
	
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