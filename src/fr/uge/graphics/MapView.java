package fr.uge.graphics;

import com.github.forax.zen.ApplicationContext;

import com.github.forax.zen.ScreenInfo;

import fr.uge.map.*;
import fr.uge.character.*;
import fr.uge.inventory.Backpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.time.Instant;

public class MapView implements View {
	private String currentMessageError = null;
	private Instant messageEndTime;
	private final Floor floor;
	private Backpack backpack = new Backpack();
	private final Hero hero = new Hero("Mouse", 40, 40, 0, 3, 5, 5,  0, 0, backpack, null);
	private final ImageLoader images;
	private final int cellSize = 75;
	private final int imageSize = 200;
	private final int cols = 11;
	private final int rows = 5;
	
	public MapView(Floor floor, ImageLoader images) {
		this.floor = floor;
		this.images = images;
	}
	
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
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
		images.drawImage(graphics, images.get(3), doorX, doorY, cellSize, cellSize);
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		int statX = context.getScreenInfo().width() / 5;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 75;
        var stat = hero.HP() + " " + "/" + " " + hero.HPMAX() + " PV";
		graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 40));
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
		images.drawImage(graphics, images.get(1), heroPosX, heroPosY, cellSize, cellSize); //Hero sur la map
	}
	
	private void importMerchantOnMap(int i, int j, int[] start, Graphics2D graphics) {
		int MerchantX = start[0] + j * cellSize + cellSize / 4 + 4;
		int MerchantY = start[1] + i * cellSize + 3 * cellSize / 4;
    graphics.setColor(Color.WHITE); 
    graphics.setFont(new Font("Arial", Font.BOLD, cellSize / 2));
    graphics.drawString("M", MerchantX, MerchantY);
	}
	
	private void importHealerOnMap(int i, int j, int[] start, Graphics2D graphics) {
		int MerchantX = start[0] + j * cellSize + cellSize / 4 + 4;
		int MerchantY = start[1] + i * cellSize + 3 * cellSize / 4;
    graphics.setColor(Color.WHITE); 
    graphics.setFont(new Font("Arial", Font.BOLD, cellSize / 2));
    graphics.drawString("H", MerchantX, MerchantY);
	}
	
	private void importEnemyOnMap(int i, int j, int[] start, Graphics2D graphics) {
		int MerchantX = start[0] + j * cellSize + cellSize / 4 + 4;
		int MerchantY = start[1] + i * cellSize + 3 * cellSize / 4;
    graphics.setColor(Color.WHITE); 
    graphics.setFont(new Font("Arial", Font.BOLD, cellSize / 2));
    graphics.drawString("E", MerchantX, MerchantY);
	}
	
	private void drawRec(Graphics2D graphics, int x, int y, int cellSize, Color fillColor) {
    graphics.setColor(fillColor);
    graphics.fillRect(x, y, cellSize, cellSize);
    graphics.setColor(Color.WHITE);
    graphics.drawRect(x, y, cellSize, cellSize);
	}
	
	private void draw_map(Graphics2D graphics, ApplicationContext context, Floor floor) {
    var start = calculateMapStart(context.getScreenInfo().width(), context.getScreenInfo().height(), cols, rows, cellSize);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (floor.hasRoom(i, j)) {
					Room room = floor.getRoom(i, j);
					Color color;
					if (floor.isAccessible(i, j)) color = Color.GREEN;
					else color = room.isCorridor() ? Color.LIGHT_GRAY : Color.ORANGE;
					drawRec(graphics, start[0] + j * cellSize, start[1] + i * cellSize, cellSize, color);
					if (room.hasDoor()) importDoorImageOnMap(i, j, start, graphics);
					if (room.hasMerchant()) importMerchantOnMap(i, j, start, graphics);
					if (room.hasAngel()) importHealerOnMap(i, j, start, graphics);
					if (room.hasEnemies()) importEnemyOnMap(i, j, start, graphics);
 				} else drawRec(graphics, start[0] + j * cellSize, start[1] + i * cellSize, cellSize, Color.GRAY);
			}
		}
	}
	
	private void drawMessage(Graphics2D graphics, ScreenInfo screenInfo) {
    if (currentMessageError != null && Instant.now().isBefore(messageEndTime)) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Arial", Font.BOLD, 40));
        var fontmetrics = graphics.getFontMetrics();
        graphics.drawString(currentMessageError, (screenInfo.width() - fontmetrics.stringWidth(currentMessageError)) / 2, screenInfo.height() / 10);
    } else currentMessageError = null; 
	}
	
	private void showMessage(String msg, int duration) {
    currentMessageError = msg;
    messageEndTime = Instant.now().plusMillis(duration);
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		background(graphics, context.getScreenInfo());
		draw_map(graphics, context, floor);
		importHeroImage(graphics, context);
		importHeroImageOnMap(graphics, context);
		drawMessage(graphics, context.getScreenInfo());
		heroStat(graphics, context);
	}
	 
	public boolean isRoomClicked(int x, int y, int i, int j, int[] start) {
		int roomX = start[0] + j * cellSize;
		int roomY = start[1] + i * cellSize;
		return x >= roomX && x <= roomX + cellSize && y >= roomY && y <= roomY + cellSize;
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		var start = calculateMapStart(context.getScreenInfo().width(), context.getScreenInfo().height(), cols, rows, cellSize);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (floor.hasRoom(i, j) && isRoomClicked(x, y, i, j, start)) {
					if (!floor.isAccessible(i, j)) {
						showMessage("Step by step ... Follow the path", 2000);
						return;
					}
					Room clickedRoom = floor.getRoom(i, j);
					if (clickedRoom.hasEnemies()) controller.changeView(controller.battleView());
					if (clickedRoom.hasAngel()) controller.changeView(controller.healerView());
					if (clickedRoom.hasMerchant()) controller.changeView(controller.merchantView());
				}
			}
		}
	}
}
