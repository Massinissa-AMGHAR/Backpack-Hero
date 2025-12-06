package fr.uge.graphics;

import com.github.forax.zen.ApplicationContext;

import com.github.forax.zen.ScreenInfo;

import fr.uge.character.*;
import fr.uge.inventory.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;


public class BattleView implements View {
	private final ImageLoader imagesHero;
	private final ImageLoader imagesEnemies; 
	private final ImageLoader imagesItems;
	private final int imageSize = 200;
	private final Hero hero;
	private final Enemies enemies;
	private final int cellSize = 100;
	private final int cols = 5;
	private final int rows = 3;
	
	public BattleView(Hero hero, Enemies enemies, ImageLoader imagesHero, ImageLoader imagesEnemies, ImageLoader imagesItems) {
		this.hero = hero;
		this.enemies = enemies;
		this.imagesHero = imagesHero;
		this.imagesEnemies = imagesEnemies;
		this.imagesItems = imagesItems;
	}
	
	private int[] calculateMapStart(int screenWidth, int screenHeight, int cols, int rows, int cellSize) {
		int mapWidth = cols * cellSize;
		int mapHeight = rows * cellSize;
		int x = (screenWidth - mapWidth) / 2;
		int y = (screenHeight - mapHeight) / 4;
		return new int[] {x, y};
	}
	
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
	}

	private void drawRec(Graphics2D graphics, int x, int y, int cellSize, Color fillColor) {
	    graphics.setColor(fillColor);
	    graphics.fillRect(x, y, cellSize, cellSize);
	    graphics.setColor(Color.WHITE);
	    graphics.drawRect(x, y, cellSize, cellSize);
	}
	
	private boolean isItemStartOrigin(Item item, int line, int column, ArrayList<ArrayList<Item>> grid) {
		if (item == null) return false;
    boolean topFree = (line == 0 || grid.get(line - 1).get(column) != item);
    boolean leftFree = (column == 0 || grid.get(line).get(column - 1) != item);
    return topFree && leftFree;
	}
	
	private void importImageItemOnBackPack(Graphics2D graphics, int startX, int startY) {
		var grid = hero.backpack().grid();
		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(i).size(); j++) {
				var item = grid.get(i).get(j);
				if (isItemStartOrigin(item, i, j, grid)) {
					int x = startX + j * cellSize;
					int y = startY + i * cellSize;
					int width = item.width() * cellSize;
					int height = item.height() * cellSize;
					imagesItems.drawImage(graphics, imagesItems.get(0), x, y, width, height);
				}
			}
		}
	}
	
	private void draw_backpack(Graphics2D graphics, ApplicationContext context) {
	  var start = calculateMapStart(context.getScreenInfo().width(), context.getScreenInfo().height(), cols, rows, cellSize);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				drawRec(graphics, start[0] + j * cellSize, start[1] + i * cellSize, cellSize, Color.GRAY);
			}
		}
		importImageItemOnBackPack(graphics, start[0], start[1]);
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		int statX = context.getScreenInfo().width() / 5 + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
    var stat = hero.HP() + " " + "/" + " " + hero.HPMAX() + " PV";
    var eX = context.getScreenInfo().width() / 8 - 175;
    var eY = context.getScreenInfo().height() / 8;
    var energy = "Energy : " + hero.energy();
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    graphics.drawString(stat, statX, statY);
    graphics.drawString(energy, eX, eY);
	}
	
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		int heroX = context.getScreenInfo().width() / 5;
		int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesHero.get(0), heroX, heroY, imageSize, imageSize); // Hero
	}
	
	private void enemyStat(Graphics2D graphics, ApplicationContext context, Enemy enemy, int index, int spacing) {
		int statX = context.getScreenInfo().width() - (imageSize * 3 + 150) + index * spacing + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    String stat = enemy.HP() + " / " + enemy.HPMax() + " PV ";
    graphics.drawString(stat, statX, statY);
	}
	
	private void importEnemy(Graphics2D graphics, ApplicationContext context, List<Enemy> enemies, int startX, int startY, int spacing) {
		for (int i = 0; i < enemies.size(); i++) {
			int enemyX = startX + i * spacing;
			int enemyY = startY;
			imagesEnemies.drawImage(graphics, imagesEnemies.get(0), enemyX, enemyY, imageSize, imageSize);
			enemyStat(graphics, context, enemies.get(i), i, spacing);
		}
	}
	
	private void importEnemiesImages(Graphics2D graphics, ApplicationContext context) {
		int enemyX = context.getScreenInfo().width() - imageSize * 3 - 150;
		int startY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		int spacing = imageSize + 30;
		importEnemy(graphics, context, enemies.getEnemies(), enemyX, startY, spacing);
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		background(graphics, context.getScreenInfo());
		draw_backpack(graphics, context);
		heroStat(graphics, context);
		importHeroImage(graphics, context);
		importEnemiesImages(graphics, context);
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
	}

}
