package fr.uge.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.KeyboardEvent.Key;
import com.github.forax.zen.PointerEvent;

import fr.uge.character.Hero;
import fr.uge.inventory.Backpack;
import fr.uge.map.Floor;

public class Controller {	
	private final MenuView menuView = new MenuView();
	private final Floor floor;
	private final MapView mapView;
	private final BattleView battleView;
	private final HealerView healerView;
	private final MerchantView merchantView;
	private Backpack backpack = new Backpack(3, 5);
	private Hero hero = new Hero("Mouse", 40, 40, 0, 3, 5, 5,  0, 0, backpack);
	//private final Enemies enemies = new Enemies();
	private View view;
	
	public Controller() {
		floor = Floor.createFloors().get(0);
		floor.initPlaceHero();
		ImageLoader imagesMap = new ImageLoader("data", "hero.png", "minihero.png", "ratwolf.png", "door.png");
		mapView = new MapView(floor, imagesMap);
		battleView = new BattleView(hero, imagesMap);
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
