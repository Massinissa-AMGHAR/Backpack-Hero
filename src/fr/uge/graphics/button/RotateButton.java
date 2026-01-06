package fr.uge.graphics.button;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

import fr.uge.inventory.Item;

public class RotateButton {
	private final Button button = new Button(0, 0, 200, 100, "ROTATE"); 
	
	public void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 450; 
		int y = context.getScreenInfo().height() / 10 - 20;
		button.setPosition(x, y);
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
	public boolean click(int mouseX, int mouseY, Item item) {
		if(item != null && button.click(mouseX, mouseY)) {
			item.applyRotation(); 
			return true; 
		}
		return false; 
	}
}
