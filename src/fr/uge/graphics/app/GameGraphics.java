package fr.uge.graphics.app;


import fr.uge.application.*;
import fr.uge.character.*;
import fr.uge.inventory.*;
import fr.uge.map.*;

public class GameGraphics {
  private final Interactions interaction;
  private final Dungeon dungeon;
  private final Floor floor;
  private final Hero hero;
  private final Enemies enemies;
  
  public GameGraphics() {
    this.interaction = new Interactions();
    dungeon = new Dungeon(2);
    dungeon.createDungeon();

    Backpack backpack = new Backpack();
    var item = Item.ALL_ITEM[0];
    backpack.add(item, 1, 2);

    hero = new Hero("Mouse", 40, 40, 0, 3, 5, 5, 0, 0, backpack, interaction);
    enemies = new Enemies();

    floor = Floor.createFloors().get(0);
    floor.initPlaceHero();
  }

  public Hero hero() { return hero; }
  public Enemies enemies() { return enemies; }
  public Interactions interaction() { return interaction; }
  public Floor floor() { return floor; }
  public Dungeon dungeon() { return dungeon; }
}

