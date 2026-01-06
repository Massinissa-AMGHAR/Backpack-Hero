package fr.uge.game;

import fr.uge.character.*;
import fr.uge.inventory.*;
import fr.uge.map.*;

public class GameGraphics {
  private final Dungeon dungeon;
  private final Floor floor;
  private final Hero hero;
  
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

  public Hero hero() { return hero; }
  public Floor floor() { return floor; }
  public Dungeon dungeon() { return dungeon; }
}

