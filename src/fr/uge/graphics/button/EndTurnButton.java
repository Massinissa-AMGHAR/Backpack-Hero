package fr.uge.graphics.button;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;


public class EndTurnButton {
	private final Button button = new Button(0, 0, 200, 100, "END TURN"); 
	public void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 450; 
		int y = context.getScreenInfo().height() / 10 + 300;
		button.setPosition(x, y);
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
	public boolean click(int mouseX, int mouseY) {
		return button.click(mouseX, mouseY);
	}
}
