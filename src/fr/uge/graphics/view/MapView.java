package fr.uge.graphics.view;

import fr.uge.graphics.*;
import fr.uge.graphics.image.*;
import fr.uge.inventory.Item;

import com.github.forax.zen.ApplicationContext;

import com.github.forax.zen.ScreenInfo;

import fr.uge.map.*;
import fr.uge.character.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.time.Instant;
import java.util.Objects;

/**
 * Creates a view for the dungeon map.
 * It shows the hero, floor, dungeon, messages, and all images.
 */
public class MapView implements View {
	private final MessageView message;
	private final Floor floor;
	private final Dungeon dungeon;
	private final Hero hero; 
	private final ImageLoader images;
	private final ImageLoader imagesItems;
	private final ImageLoader imagesServices;
	private final ImageLoader imagesEnemies;
	private final ImageLoader imagesStatus;
	private final int cellSize = 75;
	private final int imageSize = 200;
	private final int cols = 11;
	private final int rows = 5;
	private boolean readyToReturnToMenu = false;
	
	/**
	 * Creates the map view with all needed data and images.
	 *
	 * @param hero the hero on the map
	 * @param floor the current floor
	 * @param dungeon the dungeon
	 * @param images the map images
	 * @param imagesItems the item images
	 * @param imagesServices the service images
	 * @param imagesEnemies the enemy images
	 * @param imagesStatus the hero status images
	 */
	public MapView(Hero hero, Floor floor, Dungeon dungeon, ImageLoader images, ImageLoader imagesItems, ImageLoader imagesServices, ImageLoader imagesEnemies, ImageLoader imagesStatus) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(dungeon);
		Objects.requireNonNull(images);
		Objects.requireNonNull(imagesServices);
		Objects.requireNonNull(imagesEnemies);
		Objects.requireNonNull(imagesStatus);
		Objects.requireNonNull(imagesItems);
		
		this.hero = hero;
		this.floor = floor;
		this.dungeon = dungeon;
		this.images = images;
		this.imagesItems = imagesItems;
		this.imagesServices = imagesServices;
		this.imagesEnemies = imagesEnemies;
		this.imagesStatus = imagesStatus;
		this.message = new MessageView(); 
	}
	
	public MessageView messageView() { return message; }
	
	public void setReadyToReturnToMenu(boolean ready) {
    readyToReturnToMenu = ready;
	}
	
	public boolean isReadyToReturnToMenu() { 
		return readyToReturnToMenu; 
	}
	
	private void background(Graphics2D graphics, ApplicationContext context) {
		images.drawImage(graphics, images.get(8 + dungeon.currentFloor()), 0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());
	}
	
	private int[] calculateMapStart(int screenWidth, int screenHeight, int cols, int rows, int cellSize) {
		int mapWidth = cols * cellSize;
		int mapHeight = rows * cellSize;
		int x = (screenWidth - mapWidth) / 2;
		int y = (screenHeight - mapHeight) / 4;
		return new int[] {x, y};
	}
	
	private void importDoorImageOnMap(int i, int j, int[] start, Graphics2D graphics) {
		int doorX = start[0] + j * cellSize;
		int doorY = start[1] + i * cellSize;
		images.drawImage(graphics, images.get(2), doorX, doorY, cellSize, cellSize);
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		int statX = context.getScreenInfo().width() / 5 + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
    var stat = hero.HP() + " " + "/" + " " + hero.HPMAX() + " PV";
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    graphics.drawString(stat, statX, statY);
	}
	
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		int heroX = context.getScreenInfo().width() / 5;
		int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		images.drawImage(graphics, images.get(0), heroX, heroY, imageSize, imageSize); // Hero
	}
	
	private void importHeroImageOnMap(Graphics2D graphics, ApplicationContext context) {
		var start = calculateMapStart(context.getScreenInfo().width(), context.getScreenInfo().height(), cols, rows, cellSize);
		var heroPos = floor.getPlaceHero();
		int heroPosX = start[0] + heroPos[1] * cellSize;
		int heroPosY = start[1] + heroPos[0] * cellSize; 
		images.drawImage(graphics, images.get(1), heroPosX, heroPosY, cellSize, cellSize); 
	}
	
	private void importTreasureOnMap(int i, int j, int[] start, Graphics2D graphics) {
    int TreasuretX = start[0] + j * cellSize;
    int TreasureY = start[1] + i * cellSize;
    images.drawImage(graphics, images.get(6), TreasuretX, TreasureY, cellSize, cellSize); 
	}	
	
	private void importGridOnMap(int i, int j, int[] start, Graphics2D graphics) {
    int GridX = start[0] + j * cellSize;
    int GridY = start[1] + i * cellSize;
    images.drawImage(graphics, images.get(7), GridX, GridY, cellSize, cellSize); 
	}	
	
	private void importMerchantOnMap(int i, int j, int[] start, Graphics2D graphics) {
    int MerchantX = start[0] + j * cellSize;
    int MerchantY = start[1] + i * cellSize;
    images.drawImage(graphics, images.get(4), MerchantX, MerchantY, cellSize, cellSize); 
	}

	private void importHealerOnMap(int i, int j, int[] start, Graphics2D graphics) {
    int MerchantX = start[0] + j * cellSize;
    int MerchantY = start[1] + i * cellSize;
    images.drawImage(graphics, images.get(5), MerchantX, MerchantY, cellSize, cellSize); 
	}
	
	private void importEnemyOnMap(int i, int j, int[] start, Graphics2D graphics) {
    int EnemyX = start[0] + j * cellSize;
    int EnemyY = start[1] + i * cellSize;
    images.drawImage(graphics, images.get(3), EnemyX, EnemyY, cellSize, cellSize); 
	}
	
	private void drawRec(Graphics2D graphics, int x, int y, int cellSize, Color fillColor) {
    graphics.setColor(fillColor);
    graphics.fillRect(x, y, cellSize, cellSize);
    graphics.setColor(Color.WHITE);
    graphics.drawRect(x, y, cellSize, cellSize);
	}
	
	private boolean isHeroOnCell(int i, int j) {
		var heroPos = floor.getPlaceHero();
		return heroPos[0] == i && heroPos[1] == j;
	}
	
	private void drawRoomElements(Room room, int i, int j, int[] start, Graphics2D graphics) {
		if (room.hasDoor()) {
			importDoorImageOnMap(i, j, start, graphics);
		}
		if (room.hasMerchant() && !isHeroOnCell(i, j)) {
			importMerchantOnMap(i, j, start, graphics);
		}
		if (room.hasHealer() && !isHeroOnCell(i, j)) {
			importHealerOnMap(i, j, start, graphics);
		}
		if (room.hasEnemies() && !isHeroOnCell(i, j)) {
			importEnemyOnMap(i, j, start, graphics);
		}
		if (room.hasTreasure() && !isHeroOnCell(i, j)) {
			importTreasureOnMap(i, j, start, graphics);
		}
		if (room.hasGrid() && !isHeroOnCell(i, j)) {
			importGridOnMap(i, j, start, graphics);
		}
	}
	
	private void draw_map(Graphics2D graphics, ApplicationContext context, Floor floor) {
    var start = calculateMapStart(context.getScreenInfo().width(), context.getScreenInfo().height(), cols, rows, cellSize);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (floor.hasRoom(i, j)) {
					Room room = floor.getRoom(i, j);
					Color color;
					if (floor.isAccessible(i, j)) {
						color = Color.GREEN;
					} else {
						color = room.isCorridor() ? Color.LIGHT_GRAY : Color.ORANGE;
					}
					drawRec(graphics, start[0] + j * cellSize, start[1] + i * cellSize, cellSize, color);
					drawRoomElements(room, i, j, start, graphics);
 				} else {
 					drawRec(graphics, start[0] + j * cellSize, start[1] + i * cellSize, cellSize, Color.GRAY);
 				}
			}
		}
	}
	
	/**
	 * Draws the map view on the screen.
	 * It shows the background, map, hero, messages, and hero stats.
	 *
	 * @param graphics the graphics used to draw
	 * @param context the application context with screen info
	 */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(graphics); 
		Objects.requireNonNull(context); 
		message.update(); 
		background(graphics, context);
		draw_map(graphics, context, floor);
		importHeroImage(graphics, context);
		importHeroImageOnMap(graphics, context);
		message.drawMessage(graphics, context);
		heroStat(graphics, context);

	}
	 
	/**
	 * Checks if a room on the map is clicked.
	 *
	 * @param x the mouse X position
	 * @param y the mouse Y position
	 * @param i the row of the room
	 * @param j the column of the room
	 * @param start the starting position of the map [x, y]
	 * @return true if the room is clicked, false otherwise
	 */
	public boolean isRoomClicked(int x, int y, int i, int j, int[] start) {
		int roomX = start[0] + j * cellSize;
		int roomY = start[1] + i * cellSize;
		return x >= roomX && x <= roomX + cellSize && y >= roomY && y <= roomY + cellSize;
	}
	
	private boolean gridClick(Room room, int i, int j, Controller controller) {
		if (!room.hasGrid()) {
			return false;
		}
		if (!room.hasVisited()) {
			var unlocked = floor.tryUnlockGrid(room, hero);
			if (!unlocked) {
				message.showMessage("You need a key to enter this grid !", 1500);
				return true;
			}
			message.showMessage("You use a key. The grid opens...", 2000);
			return true;
		}
		controller.enterGridRoom(room, i, j);
		return true;
	}
	
	private void roomServices(Room clickedRoom, Controller controller) {
		if (clickedRoom.hasHealer()) {
			controller.startHealer(clickedRoom);
			clickedRoom.setVisited(true);
		}
		if (clickedRoom.hasMerchant()) {
			controller.startMerchant(clickedRoom);
			clickedRoom.setVisited(true);
		}
		if (clickedRoom.hasTreasure()) {
			controller.startTreasure(clickedRoom);
			clickedRoom.setVisited(true);   
		}
		if (clickedRoom.hasEnemies()) {
	    controller.startFight(clickedRoom.enemies(), clickedRoom);
	    clickedRoom.setVisited(true);
		}
		if (clickedRoom.hasDoor()) {
			controller.goToNextFloor();
			return;
		}
	}
	
	/**
	 * Processes a click on the map.
	 * It checks which room is clicked and moves the hero or shows messages.
	 *
	 * @param x the mouse X position
	 * @param y the mouse Y position
	 * @param controller the game controller
	 * @param context the application context with screen info
	 */
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		Objects.requireNonNull(controller); 
		Objects.requireNonNull(context);
		var start = calculateMapStart(context.getScreenInfo().width(), context.getScreenInfo().height(), cols, rows, cellSize);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Room clickedRoom = floor.getRoom(i, j);
			 	if (floor.hasRoom(i, j) && isRoomClicked(x, y, i, j, start)) {
          if (clickedRoom.isCorridor()) {
            message.showMessage("You can only move to rooms!", 2000);
            return;
          }
					if (!floor.isAccessible(i, j)) {
						message.showMessage("Step by step ... Follow the path", 2000);
						return;
					} 
					if (gridClick(clickedRoom, i, j, controller)) {
						return;
					}
					floor.placeHero(i, j);
					roomServices(clickedRoom, controller);
				}
			}
		}
	}
}
