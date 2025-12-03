package fr.uge.graphics;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.ScreenInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Objects;
import java.util.List;


public class BattleView implements View {
	
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
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		background(graphics, context.getScreenInfo());
		title(graphics, context, context.getScreenInfo());
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
	}

}
