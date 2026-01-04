package fr.uge.graphics.view;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.ScreenInfo;

import fr.uge.application.HallOfFame;
import fr.uge.graphics.app.Controller;

public class HallOfFameView implements View{
	private final HallOfFame hallOfFame; 
	private final Button backButton; 
	
	public HallOfFameView(){
		hallOfFame = new HallOfFame(); 
		
		int width = 200; 
		int height = 60; 
		int x = 20; 
		int y = 20; 
		backButton = new Button(x, y, width, height, "BACK"); 
	}
	
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
	}
	
	private void title(Graphics2D graphics, ApplicationContext context, String text, ScreenInfo screenInfo) {
    int titleX = screenInfo.width() / 2 - 250;
    int titleY = screenInfo.height() / 5 - 60;
    int titleWidth = 500;
    int titleHeight = 80;
    graphics.setColor(Color.YELLOW);
    graphics.fillRect(titleX, titleY, titleWidth, titleHeight); 
		graphics.setColor(Color.RED);
    graphics.setFont(new Font("Arial", Font.BOLD, 50));
    var fontmetric1 = graphics.getFontMetrics();
    graphics.drawString(text, (screenInfo.width() - fontmetric1.stringWidth(text)) / 2, screenInfo.height()/5);
	}
	
	private void drawCenteredText(Graphics2D graphics, String text, int centerX, int y, FontMetrics fontMetrics) {
    int textWidth = fontMetrics.stringWidth(text);
    int x = centerX - textWidth / 2;
    graphics.drawString(text, x, y);
	}

	
	private void ranking(Graphics2D graphics, ApplicationContext context, List<Integer> ranking, ScreenInfo screenInfo) {
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    var fontMetrics = graphics.getFontMetrics(); 
    
    int centerX = screenInfo.width()/2; 
    int startY = screenInfo.height()/2; 
    int lineSpacing = 50; 
    if(ranking.isEmpty()) {
      drawCenteredText(graphics, "No scores yet !", centerX, startY, fontMetrics);
    	return; 
    }
    for(int i = 0; i < ranking.size(); i++) {
    	String text = i +1  + ") Score : " + ranking.get(i); 
      drawCenteredText(graphics, text, centerX, startY + i * lineSpacing, fontMetrics);
    }
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		var screenInfo = context.getScreenInfo();
		background(graphics, screenInfo);
    title(graphics, context, "HALL OF FAME", screenInfo);
    var ranking = hallOfFame.bestScores(); 
    ranking(graphics, context, ranking, screenInfo); 
    backButton.draw(graphics);
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if(backButton.click(x, y)) {
			controller.changeView(controller.menuView());
		}
	}
}
