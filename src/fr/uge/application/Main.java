package fr.uge.application;
import fr.uge.character.*;
import fr.uge.inventory.*;

public class Main {
	static void main(String[] args) {
		Backpack backpack = new Backpack();
		Hero hero = new Hero("Mouse", 40, 40, 0, 0, 0, 0, 1, backpack);
		Ennemy ennemy = new Ennemy("Rat Wolf", 15, 0, 8, 5);
		Item woodenSword = Item.ALL_ITEM[0];
		
		IO.println("Before attack : ");
		IO.println("Enemy HP : " + ennemy.HP());
		
		hero.attack(ennemy, woodenSword);
		
		IO.println("\nAfter attack : ");
		IO.println("Enemy HP : " + ennemy.HP());
		
	}
}
