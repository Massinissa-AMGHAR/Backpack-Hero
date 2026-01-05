package fr.uge.graphics.view;

import fr.uge.graphics.*;
import fr.uge.graphics.image.ImageLoader;

import com.github.forax.zen.ApplicationContext;

import com.github.forax.zen.ScreenInfo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

public class MenuView implements View {
	private final ImageLoader images;
	private int playX, playY, playWidth, playHeight;
	private int hofX, hofY, hofWidth, hofHeight; 
	
	public MenuView(ImageLoader images) {
		this.images = images;
	}
	
	public int playX() { return playX; }
	public int playY() { return playY; }
	public int playWidth() { return playWidth; }
	public int playHeight() { return playHeight; }
	
	private void background(Graphics2D graphics, ApplicationContext context) {
		images.drawImage(graphics, images.get(11), 0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());
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
	
	private void hallOfFame(Graphics2D graphics, List<String> l, ScreenInfo screenInfo) {
		hofX = playX;
    hofY = playY + playHeight + 20;
    hofWidth = playWidth;
    hofHeight = playHeight;
    
    graphics.setColor(Color.LIGHT_GRAY);
    graphics.fillRect(hofX, hofY, hofWidth, hofHeight); 
    graphics.setFont(new Font("Arial", Font.BOLD, 50));
    graphics.setColor(Color.WHITE);
    var fontMetrics = graphics.getFontMetrics();
    
    String text = l.get(2); 
    graphics.drawString(text, (screenInfo.width() - fontMetrics.stringWidth(text)) /2, hofY + hofHeight/2 + fontMetrics.getAscent() /2 - 5);
   
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		var screenInfo = context.getScreenInfo();
		var l = List.of("BACKPACK HERO", "START GAME", "HALL OF FAME");
		background(graphics, context);
    title(graphics, context, l, screenInfo);
    play(graphics, l, screenInfo);    
    hallOfFame(graphics, l, screenInfo); 
	}
	
	private boolean isPlayButton(int x, int y) {
    return x >= playX && x <= playX + playWidth && y >= playY && y <= playY + playHeight;
	}
	private boolean isHofButton(int x, int y) {
    return x >= hofX && x <= hofX + hofWidth && y >= hofY && y <= hofY + hofHeight;
	}
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if (isPlayButton(x, y)) {
			controller.startNewGame(false);
		}
		if (isHofButton(x, y)) {
			controller.changeView(controller.hofView());
		}
	}
}
