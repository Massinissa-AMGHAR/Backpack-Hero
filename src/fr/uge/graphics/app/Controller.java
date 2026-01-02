package fr.uge.graphics.app;
import fr.uge.graphics.image.*;
import fr.uge.graphics.view.*;
import java.awt.Color;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.KeyboardEvent.Key;
import com.github.forax.zen.PointerEvent;

import fr.uge.application.*;
import fr.uge.character.*;
import fr.uge.inventory.*;
import fr.uge.map.*;

public class Controller {	
  private final Hero hero;
  private final Merchant merchant; 
  private final Healer healer;
  private final HallOfFame hof; 
  private final Enemies enemies;
  private final Dungeon dungeon;
  private Floor floor;
  private final Interactions interactions;
  private final ImageLoader imagesMap;
  private final ImageLoader imagesEnemies;
  private final ImageLoader imagesStatus;
  
  private final ImageLoader imagesItems;
  private Items merchantItems; 
  private final ImageLoader imagesServices;
  private final MenuView menuView;
  private final HallOfFameView hofView; 
  private MapView mapView;
  
  private final HealerView healerView;
  private MerchantView merchantView;
  private TreasureView treasureView;
  private View view;
  private BattleView battleView;
  private FightGraphics fightGraphics;
  private boolean dungeonFinished = false; 
  
	
  public Controller(Hero hero, Merchant merchant, Healer healer, Dungeon dungeon, Floor floor, Enemies enemies, Interactions interactions, ImageLoader imagesMap, ImageLoader imagesEnemies, ImageLoader imagesItems, ImageLoader imagesServices, ImageLoader imagesStatus) {
		this.hero = hero;
		this.merchant = merchant; 
		this.healer = healer;
		this.enemies = enemies;
		this.dungeon = dungeon;
		this.floor = floor;
		this.interactions = interactions;
		this.imagesMap = imagesMap;
		this.imagesEnemies = imagesEnemies;
		this.imagesItems = imagesItems;		
		this.imagesServices = imagesServices;
		this.imagesStatus = imagesStatus;
		menuView = new MenuView();
		hofView = new HallOfFameView(); 
		mapView = new MapView(hero, floor, imagesMap); 
		view = menuView;
		hof = new HallOfFame(); 
		healerView = new HealerView(hero, healer, imagesMap, imagesServices, imagesItems);
		merchantItems = new Items(); 
		merchantItems.randomItems(3);
		merchantView = new MerchantView(hero, merchant, merchantItems, imagesMap, imagesServices, imagesItems);
  }

	public MenuView menuView() { return menuView; }
	public HallOfFameView hofView() {return hofView; }
	public MapView mapView() { return mapView; }
	public BattleView battleView() { return battleView; }
	public HealerView healerView() { return healerView; }
	public MerchantView merchantView() { return merchantView; }
	public TreasureView treasureView() { return treasureView; }
	public View view() { return view; }
  
	public void changeView(View newView) {
		view = newView;
	}
	
	public void goToNextFloor() {
		var next = dungeon.nextFloor(floor);
		if (next == null) {
			mapView.messageView().showMessage("You have cleared the dungeon !", 1000);
			dungeonFinished = true; 
			return;
		}
		floor = next;
		floor.initPlaceHero();
		mapView = new MapView(hero, floor, imagesMap);
		merchantItems = new Items();
		merchantItems.randomItems(3);
		merchantView = new MerchantView(hero, merchant, merchantItems, imagesMap, imagesServices, imagesItems);
		changeView(mapView);
		mapView.messageView().showMessage("You enter a new floor...", 2000);
	}
	
	public void startFight(Enemies roomEnemies) {
		var lootItems = new Items(); 
		lootItems.randomItems(3);
		
		battleView = new BattleView(hero, lootItems, roomEnemies, imagesMap, imagesEnemies, imagesItems, imagesStatus);
		fightGraphics = new FightGraphics(hero, roomEnemies, battleView);
		battleView.setFight(fightGraphics);
	}
	
	public void startTreasure() {
		var lootItems = new Items(); 
		lootItems.randomItems(3);
		treasureView = new TreasureView(hero, imagesMap, imagesItems);
	}
	
	public FightGraphics fightGraphics() { return fightGraphics; }
	
	private boolean gameLoop(ApplicationContext context) {
		var event = context.pollOrWaitEvent(10);
		if (event == null) return true;
		switch(event) {
		case KeyboardEvent key -> {
			if (key.key() == KeyboardEvent.Key.Q) return false;
		}
		case PointerEvent pe -> {
			if (pe.action() == PointerEvent.Action.POINTER_DOWN) {
				view.Click(pe.location().x(), pe.location().y(), this, context);
			}
		}
		default -> {}
		}
		return true;
	}
	
	public void game(ApplicationContext context) {
    boolean running = true;
    while (running) {
    	context.renderFrame(graphics -> {
    		if(dungeonFinished) {
    			int score = hero.score(); 
    			hof.addScore(score);
    			dungeonFinished = false; 
    		}
        view.draw(graphics, context);
        if (view == battleView) {
        	fightGraphics.updateFight();
        	battleView.messageView().update();
        	if (!battleView.messageView().isMessageActive() && battleView.isReadyToReturnToMap()) {
        		changeView(mapView);
        		battleView.setReadyToReturnToMap(false);
        	}
        }
        if (view == merchantView) {
        	merchantView.messageView().update();
        	if (!merchantView.messageView().isMessageActive() && merchantView.isReadyToReturnToMap()) {
        		changeView(mapView);
        		merchantView.setReadyToReturnToMap(false);
        	}
        }
        if (view == healerView) {
        	healerView.messageView().update();
        	if (!healerView.messageView().isMessageActive() && healerView.isReadyToReturnToMap()) {
        		changeView(mapView);
        		healerView.setReadyToReturnToMap(false);
        	}
        }
        if (view == treasureView) {
        	treasureView.messageView().update();
        	if (!treasureView.messageView().isMessageActive() && treasureView.isReadyToReturnToMap()) {
        		changeView(mapView);
        		treasureView.setReadyToReturnToMap(false);
        	}
        }
    	});
    	running = gameLoop(context);
    } 
    context.dispose();
	}
}
