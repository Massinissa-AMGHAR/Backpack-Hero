package fr.uge.graphics;
import fr.uge.graphics.image.*;

import fr.uge.graphics.view.*;

import java.util.Objects;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import fr.uge.application.*;
import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.game.GameGraphics;
import fr.uge.map.*;

/**
 * Main game controller.
 * Manages the hero, dungeon, floors, and all game views.
 * Controls the progression between exploration, battles, shops, healing, and victory screens.
 */
public class Controller {	
  private Hero hero;
  private HallOfFame hof; 
  private Dungeon dungeon;
  private Floor floor;
  /**
   * Images for the map
   */
  private final ImageLoader imagesMap; 
  /**
   * Images for enemies
   */
  private final ImageLoader imagesEnemies; 
  /**
   * Images for status effects
   */
  private final ImageLoader imagesStatus; 
  /**
   * Images for items
   */
  private final ImageLoader imagesItems; 
  /**
   * Images for merchants, healers
   */
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
		Objects.requireNonNull(hero); 
		Objects.requireNonNull(dungeon);
		Objects.requireNonNull(floor);
		Objects.requireNonNull(imagesMap);
		Objects.requireNonNull(imagesEnemies);
		Objects.requireNonNull(imagesServices);
		Objects.requireNonNull(imagesStatus);
		Objects.requireNonNull(imagesItems);
	
  
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
  
  /**
   * Starts a new game by creating the dungeon, hero, and floor.
   * Initializes the map view and optionally shows a welcome message.
   *
   * @param startFromMenu true if the game starts from the menu, false to show map immediately
   */
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
  
  /**
   * Restarts the game from the menu by resetting everything and creating a new game.
   */
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
  
	/**
	 * Changes the current active view in the game.
	 *
	 * @param newView the view to switch to
	 */
	public void changeView(View newView) {
		Objects.requireNonNull(newView); 
		view = newView;
	}
	
	/**
	 * Makes the hero enter a room and starts the right event.
	 *
	 * @param randomRoom the room being entered
	 */
	private void enterRoom(Room randomRoom) {
		Objects.requireNonNull(randomRoom); 
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
    if (randomRoom.hasHealer()) {
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
	
	/**
	 * Places the hero on the grid and makes them enter the corresponding room.
	 *
	 * @param gridRoom the room on the grid
	 * @param i the row position
	 * @param j the column position
	 */
	public void enterGridRoom(Room gridRoom, int i, int j) {
		Objects.requireNonNull(gridRoom); 
		floor.placeHero(i, j);
		var randomRoom = floor.generateRandomRoom(gridRoom);
		enterRoom(randomRoom);
	}
	
	/**
	 * Moves the hero to the next floor or ends the dungeon if there are no more floors.
	 */
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
	
	/**
	 * Starts a fight in the given room with its enemies.
	 *
	 * @param roomEnemies the enemies in the room
	 * @param room the room where the fight happens
	 */
	public void startFight(Enemies roomEnemies, Room room) {
		Objects.requireNonNull(roomEnemies); 
		Objects.requireNonNull(room); 
		battleView = room.getBattleView(hero, dungeon, imagesMap, imagesEnemies, imagesItems, imagesStatus);
		if (battleView.fight() == null) {
			var fg = new FightGraphics(hero, roomEnemies, dungeon, battleView);
			battleView.setFight(fg);
		}
		changeView(battleView);
	}
	
	/**
	 * Starts the merchant interaction in the given room.
	 *
	 * @param room the room with the merchant
	 */
	public void startMerchant(Room room) {
		Objects.requireNonNull(room); 
		merchantView = room.getMerchantView(hero, dungeon, imagesMap, imagesServices, imagesItems);
		changeView(merchantView);
	}
	
	/**
	 * Starts the healer interaction in the given room.
	 *
	 * @param room the room with the healer
	 */
	public void startHealer(Room room) {
		Objects.requireNonNull(room);
		healerView = room.getHealerView(hero, dungeon, imagesMap, imagesServices, imagesItems);
		changeView(healerView);
	}
	
	/**
	 * Starts the treasure event in the given room.
	 *
	 * @param room the room containing the treasure
	 */
	public void startTreasure(Room room) {
		Objects.requireNonNull(room);
		treasureView = room.getTreasureView(hero, dungeon, imagesMap, imagesItems);
		changeView(treasureView);
	}
	
	/**
	 * Shows the victory screen when the dungeon is completed.
	 */
	public void startVictory() {
		victoryView = new VictoryView(hero, imagesMap);
		changeView(victoryView);
	}
	
	/**
	 * Ends the game on defeat and returns to the menu, starting a new game.
	 */
	public void startDefeat() {
		changeView(menuView);
		startNewGame(true);
	}
	
	/**
	 * Main game loop that processes input events and updates the current view.
	 *
	 * @param context the application context providing events
	 * @return false if the game should stop, true otherwise
	 */
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
	
	/**
	 * Ends the game by saving the hero's score and showing defeat or victory screen.
	 */
	private void endGame() {
		int score = hero.score(); 
		hof.addScore(score);
		if (hero.isDead()) {
			startDefeat();
		} else {
			startVictory();
		}
	}
	
	/**
	 * Updates the view, either battle or map/room.
	 */
	private void updateView() {
		if (view == battleView) {
			updateBattle();
			return;
		}
		updateRoomView(view);
	}
	
	/**
	 * Updates the battle view, processes fight logic, and switches back to the map if the fight ends.
	 */
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
	
	/**
	 * Updates a non-battle view (merchant, healer, or treasure) and returns to the map when done.
	 *
	 * @param view the current view to update
	 */
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
	
	/**
	 * Checks if the player clicked a button on the victory screen (quit or play again).
	 *
	 * @return true if the player chose to quit, false otherwise
	 */
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
	
	/**
	 * Main game method that runs the game loop, updates views, and ends the game when finished.
	 *
	 * @param context the application context for rendering and events
	 */
	public void game(ApplicationContext context) {
		Objects.requireNonNull(context);
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
