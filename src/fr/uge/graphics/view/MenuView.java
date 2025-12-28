package fr.uge.graphics.view;

import fr.uge.graphics.app.*;


import com.github.forax.zen.ApplicationContext;

import com.github.forax.zen.ScreenInfo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

public class MenuView implements View {
	private int playX, playY, playWidth, playHeight;
	private boolean nextFloor = false;
	
	public int playX() { return playX; }
	public int playY() { return playY; }
	public int playWidth() { return playWidth; }
	public int playHeight() { return playHeight; }
	
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
	}
	
	private void title(Graphics2D graphics, ApplicationContext context, List<String> l, ScreenInfo screenInfo) {
    int titleX = screenInfo.width() / 2 - 250;
    int titleY = screenInfo.height() / 5 - 60;
    int titleWidth = 500;
    int titleHeight = 80;
    graphics.setColor(Color.LIGHT_GRAY);
    graphics.fillRect(titleX, titleY, titleWidth, titleHeight); 
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 50));
    var fontmetric1 = graphics.getFontMetrics();
    graphics.drawString(l.get(0), (screenInfo.width() - fontmetric1.stringWidth(l.get(0))) / 2, screenInfo.height()/5);
	}
	
	
	private void play(Graphics2D graphics, List<String> l, ScreenInfo screenInfo) {
		playX = screenInfo.width() / 2 - 250;
    playY = screenInfo.height() / 2 - 60;
    playWidth = 500;
    playHeight = 80;
    graphics.setColor(Color.LIGHT_GRAY);
    graphics.fillRect(playX, playY, playWidth, playHeight); 
    graphics.setFont(new Font("Arial", Font.BOLD, 50));
    graphics.setColor(Color.WHITE);
    var fontmetric2 = graphics.getFontMetrics();
    graphics.drawString(l.get(1), (screenInfo.width() - fontmetric2.stringWidth(l.get(1))) / 2, screenInfo.height()/2);
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		var screenInfo = context.getScreenInfo();
		 var l = List.of("BACKPACK HERO", "START GAME");
		// BackGround
		background(graphics, screenInfo);
    // Title
    title(graphics, context, l, screenInfo);
    //Button
    play(graphics, l, screenInfo);
	}
	
	private boolean isPlayButton(int x, int y) {
    return x >= playX && x <= playX + playWidth && y >= playY && y <= playY + playHeight;
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if (isPlayButton(x, y)) {
			controller.changeView(controller.mapView());
			controller.mapView().messageView().showMessage("Welcome to the dungeon !", 2000);
		}
	}
	
}
