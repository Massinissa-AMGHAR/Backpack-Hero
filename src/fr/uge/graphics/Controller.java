package fr.uge.graphics;

import java.awt.Color;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.KeyboardEvent.Key;
import com.github.forax.zen.PointerEvent;

import fr.uge.application.*;
import fr.uge.character.*;
import fr.uge.inventory.*;
import fr.uge.map.Floor;

public class Controller {	
  private final Hero hero;
  private final Enemies enemies;
  private final Interactions interactions;
  private final ImageLoader imagesMap;
  private final ImageLoader imagesEnemies;
  private final ImageLoader imagesItems;
  private final MenuView menuView;
  private final MapView mapView;
  private final BattleView battleView;
  private final HealerView healerView;
  private final MerchantView merchantView;
  private final Floor floor;
  private View view;
	
  public Controller(Hero hero, Enemies enemies, Interactions interactions, ImageLoader imagesMap, ImageLoader imagesEnemies, ImageLoader imagesItems) {
		this.hero = hero;
		this.enemies = enemies;
		this.interactions = interactions;
		this.imagesMap = imagesMap;
		this.imagesEnemies = imagesEnemies;
		this.imagesItems = imagesItems;		
		menuView = new MenuView();
		floor = Floor.createFloors().get(0);
		floor.initPlaceHero();
		mapView = new MapView(hero, floor, imagesMap);
		battleView = new BattleView(hero, enemies, imagesMap, imagesEnemies, imagesItems);
		healerView = new HealerView();
		merchantView = new MerchantView();
		view = menuView;
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
			context.renderFrame(graphics -> view.draw(graphics, context));
			running = gameLoop(context);
		}
		context.dispose();
	}
}
