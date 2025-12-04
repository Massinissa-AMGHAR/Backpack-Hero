package fr.uge.graphics;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.ScreenInfo;

import fr.uge.character.Enemies;
import fr.uge.character.Enemy;
import fr.uge.character.Hero;
import fr.uge.inventory.Backpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Objects;
import java.util.List;


public class BattleView implements View {
	private final ImageLoader images;
	private final int imageSize = 200;
	private final Hero hero;
	//private final Enemies enemies;
	private final int cellSize = 100;
	private final int cols = 5;
	private final int rows = 3;
	
	public BattleView(Hero hero, ImageLoader images) {
		this.hero = hero;
		//this.enemies = enemies;
		this.images = images;
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
	
	private void title(Graphics2D graphics, ApplicationContext context, ScreenInfo screenInfo) {
	    int titleX = screenInfo.width() / 2 - 250;
	    int titleY = screenInfo.height() / 5 - 60;
	    int titleWidth = 500;
	    int titleHeight = 80;
	    graphics.setColor(Color.LIGHT_GRAY);
	    graphics.fillRect(titleX, titleY, titleWidth, titleHeight); 
			graphics.setColor(Color.WHITE);
	    graphics.setFont(new Font("Arial", Font.BOLD, 50));
	    var fontmetric1 = graphics.getFontMetrics();
	    graphics.drawString("ENEMY VIEW", (screenInfo.width() - fontmetric1.stringWidth("ENEMY VIEW")) / 2, screenInfo.height()/5);
	}
	
	private void drawRec(Graphics2D graphics, int x, int y, int cellSize, Color fillColor) {
	    graphics.setColor(fillColor);
	    graphics.fillRect(x, y, cellSize, cellSize);
	    graphics.setColor(Color.WHITE);
	    graphics.drawRect(x, y, cellSize, cellSize);
	}
	
	private void draw_backpack(Graphics2D graphics, ApplicationContext context) {
	    var start = calculateMapStart(context.getScreenInfo().width(), context.getScreenInfo().height(), cols, rows, cellSize);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				drawRec(graphics, start[0] + j * cellSize, start[1] + i * cellSize, cellSize, Color.GRAY);
			}
		}
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
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		background(graphics, context.getScreenInfo());
		draw_backpack(graphics, context);
		heroStat(graphics, context);
		importHeroImage(graphics, context);
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
	}

}
