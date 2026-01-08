package fr.uge.game;

import fr.uge.character.*;
import fr.uge.inventory.*;
import fr.uge.map.*;

/**
 * Initializes the game graphics, dungeon, hero, and floor.
 */
public class GameGraphics {
	/**
	 * The game dungeon
	 */
  private final Dungeon dungeon;
  /**
   * Current floor in the dungeon
   */
  private final Floor floor; 
  /**
   * The hero controlled by the player
   */
  private final Hero hero;  
  
  /**
   * Sets up the dungeon, creates the hero with initial items, and places them on the floor.
   */
  public GameGraphics() {
    dungeon = new Dungeon(2);
    dungeon.createDungeon();

    Backpack backpack = new Backpack();
    var sword = ItemRegistry.create(ItemRegistry.WOODEN_SWORD);
    var shield = ItemRegistry.create(ItemRegistry.ROUGH_BUCKLER);
    var heal = ItemRegistry.create(ItemRegistry.MEAL);
    backpack.add(sword, 1, 2);
    backpack.add(heal, 1, 3);
    backpack.add(shield, 2, 3);
    hero = new Hero("Mouse", 40, 40, 0, 0, 3, 5, 0, 0, 0, backpack);
    floor = dungeon.getFloor(0);
    floor.initPlaceHero();
  }

  /**
   * @return the hero
   */
  public Hero hero() { return hero; }
  /**
   * @return the current floor
   */
  public Floor floor() { return floor; }
  /**
   * @return the dungeon
   */
  public Dungeon dungeon() { return dungeon; }
}

