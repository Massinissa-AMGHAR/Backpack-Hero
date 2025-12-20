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
  private final Enemies enemies;
  private final Interactions interactions;
  private final ImageLoader imagesMap;
  private final ImageLoader imagesEnemies;
  
  private final ImageLoader imagesItems;
  private final Items merchantItems; 
  private final ImageLoader imagesServices;
  private final MenuView menuView;
  private final MapView mapView;
  
  private final Items lootItems; 
  private final BattleView battleView;
  private final HealerView healerView;
  private final ImageLoader imagesItemsLoot; 
  private final MerchantView merchantView;
  private final Floor floor;
  private View view;
  private FightGraphics fightGraphics;
  
	
  public Controller(Hero hero, Merchant merchant, Enemies enemies, Interactions interactions, ImageLoader imagesMap, ImageLoader imagesEnemies, ImageLoader imagesItems, ImageLoader imagesServices) {
		this.hero = hero;
		this.merchant = merchant; 
		this.enemies = enemies;
		this.interactions = interactions;
		this.imagesMap = imagesMap;
		this.imagesEnemies = imagesEnemies;
		this.imagesItems = imagesItems;		
		this.imagesServices = imagesServices;
		menuView = new MenuView();
		floor = Floor.createFloors().get(0);
		floor.initPlaceHero();
		mapView = new MapView(hero, floor, imagesMap); 
		view = menuView;
		healerView = new HealerView(hero, imagesMap, imagesServices);
		
		imagesItemsLoot = new ImageLoader(
				"data/", 
				"Wooden_sword.png", 
				"Arrow.png", 
				"Rough_Buckler.png", 
				"Tunic.png", 
				"magic_wand.png", 
				"mana_stone.png", 
				"Gold.png"
	  ); 
		merchantItems = new Items(); 
		merchantItems.randomItems(3);
		merchantView = new MerchantView(hero, merchant, merchantItems, imagesMap, imagesServices, imagesItemsLoot);

		lootItems = new Items(); 
		lootItems.randomItems(3);
		battleView = new BattleView(hero, lootItems, enemies, imagesMap, imagesEnemies, imagesItems, imagesItemsLoot);
		fightGraphics = new FightGraphics(hero, enemies, battleView);
		battleView.setFight(fightGraphics);
  }

	public MenuView menuView() { return menuView; }
	public MapView mapView() { return mapView; }
	public BattleView battleView() { return battleView; }
	public HealerView healerView() { return healerView; }
	public MerchantView merchantView() { return merchantView; }
	public View view() { return view; }
  
	public void changeView(View newView) {
		view = newView;
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
        view.draw(graphics, context);
        if(view == battleView) {
        	fightGraphics.updateFight();
          battleView.messageView().update();            
          if (!battleView.messageView().isMessageActive() && battleView.isReadyToReturnToMap()) {
            changeView(mapView());
            battleView.setReadyToReturnToMap(false);
          }
        }
    	});
    	running = gameLoop(context);
    }
    context.dispose();
	}
}
