package fr.uge.graphics.button;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

public class RefuseButton {
	private final Button button = new Button(300, 300, 200, 100, "REFUSE"); 
	public void updateLayout(ApplicationContext context) {
		int x = 300;  
		int y = context.getScreenInfo().height() / 2 - 50; 
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
