package fr.uge.graphics.button;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

import fr.uge.inventory.Item;

/**
 * Represents a "ROTATE" button that rotates an item when clicked.
 */
public class RotateButton {
	private final Button button = new Button(0, 0, 200, 100, "ROTATE"); 
	
	private void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 450; 
		int y = context.getScreenInfo().height() / 10 - 20;
		button.setPosition(x, y);
	}
	
  /**
   * Draws the rotate button on the screen.
   *
   * @param graphics the graphics context to draw on
   * @param context the application context for screen information
   */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
  /**
   * Checks if the rotate button is clicked and rotates the item.
   *
   * @param mouseX the x-coordinate of the click
   * @param mouseY the y-coordinate of the click
   * @param item the item to rotate
   * @return true if the item was rotated
   */
	public boolean click(int mouseX, int mouseY, Item item) {
		if(item != null && button.click(mouseX, mouseY)) {
			item.applyRotation(); 
			return true; 
		}
		return false; 
	}
}
