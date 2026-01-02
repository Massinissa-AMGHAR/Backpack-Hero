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
  private final Merchant merchant; 
  private final Healer healer;
  
  public GameGraphics() {
    this.interaction = new Interactions();
    dungeon = new Dungeon(2);
    dungeon.createDungeon();

    Backpack backpack = new Backpack();
    var item = ItemRegistry.create(ItemRegistry.WOODEN_SWORD);
    var item2 = ItemRegistry.create(ItemRegistry.KEY);
    backpack.add(item, 1, 2);
    backpack.add(item2, 1, 3);

    hero = new Hero("Mouse", 40, 40, 0, 3, 5, 0, 0, 0, backpack, interaction);
    enemies = new Enemies();
    merchant = new Merchant(); 
    healer = new Healer(1, 50);

    floor = dungeon.getFloor(0);
    floor.initPlaceHero();
    floor.placeHero(3, 6);
  }

  public Hero hero() { return hero; }
  public Merchant merchant() {return merchant; }
  public Healer healer() { return healer; }
  public Enemies enemies() { return enemies; }
  public Interactions interaction() { return interaction; }
  public Floor floor() { return floor; }
  public Dungeon dungeon() { return dungeon; }
}

