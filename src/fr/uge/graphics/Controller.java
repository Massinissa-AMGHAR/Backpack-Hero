package fr.uge.graphics;
import fr.uge.graphics.image.*;

import fr.uge.graphics.view.*;


import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import fr.uge.application.*;
import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.game.GameGraphics;
import fr.uge.map.*;

public class Controller {	
  private Hero hero;
  private HallOfFame hof; 
  private Dungeon dungeon;
  private Floor floor;
  private final ImageLoader imagesMap;
  private final ImageLoader imagesEnemies;
  private final ImageLoader imagesStatus;
  private final ImageLoader imagesItems;
  private final ImageLoader imagesServices;
  private final MenuView menuView;
  private final HallOfFameView hofView; 
  private MapView mapView;
  
  private HealerView healerView;
  private MerchantView merchantView;
  private TreasureView treasureView;
  private View view;
  private BattleView battleView;
  private VictoryView victoryView;
  private boolean dungeonFinished = false; 
  private boolean running;
  
	
  public Controller(Hero hero, Dungeon dungeon, Floor floor, ImageLoader imagesMap, ImageLoader imagesEnemies, ImageLoader imagesItems, ImageLoader imagesServices, ImageLoader imagesStatus) {
		this.hero = hero;
		this.dungeon = dungeon;
		this.floor = floor;
		this.imagesMap = imagesMap;
		this.imagesEnemies = imagesEnemies;
		this.imagesItems = imagesItems;		
		this.imagesServices = imagesServices;
		this.imagesStatus = imagesStatus;
		menuView = new MenuView(imagesMap);

		hofView = new HallOfFameView(); 
		hof = new HallOfFame(); 

		mapView = new MapView(hero, floor, dungeon, imagesMap, imagesItems, imagesServices, imagesEnemies, imagesStatus); 
		view = menuView;
  }
  
  public void startNewGame(boolean startFromMenu) {
  	GameGraphics newGame = new GameGraphics(); 
  	hero = newGame.hero(); 
  	dungeon = newGame.dungeon(); 
  	floor = newGame.floor(); 
  	mapView = new MapView(hero, floor, dungeon, imagesMap, imagesItems, imagesServices, imagesEnemies, imagesStatus); 
  	if (!startFromMenu) {
    	changeView(mapView); 
  		mapView.messageView().showMessage("Welcome to the dungeon !", 2000);

  	}
  	dungeonFinished = false; 
  }
  
  public void startPlayAgain() {
  	changeView(menuView);
  	startNewGame(true);
  }
  
	public MenuView menuView() { return menuView; }
	public HallOfFameView hofView() { return hofView; }
	public MapView mapView() { return mapView; }
	public BattleView battleView() { return battleView; }
	public HealerView healerView() { return healerView; }
	public MerchantView merchantView() { return merchantView; }
	public TreasureView treasureView() { return treasureView; }
	public View view() { return view; }
  
	public void changeView(View newView) {
		view = newView;
	}
	
	private void enterRoom(Room randomRoom) {
    if (randomRoom.hasEnemies()) {
      startFight(randomRoom.enemies(), randomRoom);
      randomRoom.setVisited(true);
      return;
    }
    if (randomRoom.hasMerchant()) {
      startMerchant(randomRoom);
      randomRoom.setVisited(true);
      return;
    }
    if (randomRoom.hasAngel()) {
      startHealer(randomRoom);
      randomRoom.setVisited(true);
    	return;
    }
    if (randomRoom.hasTreasure()) {
      startTreasure(randomRoom);
      randomRoom.setVisited(true);
      return;
    }
    if (randomRoom.hasDoor()) {
      goToNextFloor();
      return;
    }
	}
	
	public void enterGridRoom(Room gridRoom, int i, int j) {
		floor.placeHero(i, j);
		var randomRoom = floor.generateRandomRoom(gridRoom);
		enterRoom(randomRoom);
	}
	
	public void goToNextFloor() {
		var next = dungeon.nextFloor(floor);
		if (next == null) {
			startVictory();
			dungeonFinished = true; 
			return;
		}
		floor = next;
		floor.initPlaceHero();
		mapView = new MapView(hero, floor, dungeon, imagesMap, imagesItems, imagesServices, imagesEnemies, imagesStatus);
		changeView(mapView);
		mapView.messageView().showMessage("You enter a new floor...", 2000);
	}
	
	public void startFight(Enemies roomEnemies, Room room) {
		battleView = room.getBattleView(hero, dungeon, imagesMap, imagesEnemies, imagesItems, imagesStatus);
		if (battleView.fight() == null) {
			var fg = new FightGraphics(hero, roomEnemies, battleView);
			battleView.setFight(fg);
		}
		changeView(battleView);
	}
	
	public void startMerchant(Room room) {
		merchantView = room.getMerchantView(hero, dungeon, imagesMap, imagesServices, imagesItems);
		changeView(merchantView);
	}
	
	public void startHealer(Room room) {
		healerView = room.getHealerView(hero, dungeon, imagesMap, imagesServices, imagesItems);
		changeView(healerView);
	}
	
	public void startTreasure(Room room) {
		treasureView = room.getTreasureView(hero, dungeon, imagesMap, imagesItems);
		changeView(treasureView);
	}
	
	public void startVictory() {
		victoryView = new VictoryView(hero, imagesMap);
		changeView(victoryView);
	}
	
	public void startDefeat() {
		changeView(menuView);
		startNewGame(true);
	}
	
	
	private boolean gameLoop(ApplicationContext context) {
		var event = context.pollOrWaitEvent(10);
		if (event == null) {
			return true;
		}
		switch(event) {
		case KeyboardEvent key -> {
			if (key.key() == KeyboardEvent.Key.Q) {
				return false;
			}
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
	
	private void endGame() {
		int score = hero.score(); 
		hof.addScore(score);
		startDefeat();
	}
	
	private void updateView() {
		if (view == battleView) {
			updateBattle();
			return;
		}
		updateRoomView(view);
	}
	
	private void updateBattle() {
  	battleView.fight().updateFight();
  	battleView.messageView().update();
  	if(hero.isDead() && !battleView.messageView().isMessageActive() && battleView.isReadyToReturnToMap()) {
  		endGame();
			return; 
  	}
  	if (!battleView.messageView().isMessageActive() && battleView.isReadyToReturnToMap()) {
  		changeView(mapView);
  		battleView.setReadyToReturnToMap(false);
  	}
	}
	
	private void updateRoomView(View view) {
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
	}
	
	private boolean checkEndGameButtons() {
		if (victoryView == null) {
			return false;
		}
		if (view == victoryView) {
			if (victoryView.isQuit()) {
				return true;
			}
			if (victoryView.isPlayAgain()) {
				startPlayAgain();
				victoryView.setPlayAgain(false);
				return false;
			}
		}
		return false;
	}
	
	public void game(ApplicationContext context) {
    running = true;
    while (running) {
    	context.renderFrame(graphics -> {
    		if(dungeonFinished) {
    			dungeonFinished = false;
    			endGame();
    			return; 
    		}
        view.draw(graphics, context);
        updateView();

    	});
    	running = gameLoop(context);
      if (checkEndGameButtons()) {
      	running = false;
      }
    } 
    context.dispose();
	}
}
