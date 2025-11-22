package fr.uge.application;
import java.util.Scanner; 

public class Interactions {
	private Scanner scanner; 
	
	public Interactions(Scanner scanner){
		this.scanner = scanner; 
	}
	
	public int requestInt() {
		while(!scanner.hasNextInt()) {
			IO.println("Invalid input, enter a number please:");
			scanner.nextLine();
		}
		
		int i = scanner.nextInt(); 
		scanner.nextLine();
		return i; 
  }
	
	public boolean requestYesNo() {
		while(true) {
			String input = scanner.nextLine(); 
			if(input.equals("y")) return true; 
			if(input.equals("n")) return false; 
			IO.println("Please answer with y or n"); 
		}
	}
}
