package fr.uge.graphics.button;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

/**
 * Represents a "REFUSE" button that can be clicked by the player.
 */
public class RefuseButton {
	private final Button button = new Button(300, 300, 200, 100, "REFUSE"); 
	
	private void updateLayout(ApplicationContext context) {
		int x = 300;  
		int y = context.getScreenInfo().height() / 2 - 50; 
		button.setPosition(x, y);
	}
	
  /**
   * Draws the refuse button on the screen.
   *
   * @param graphics the graphics context to draw on
   * @param context the application context for screen information
   */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
  /**
   * Checks if the refuse button is clicked.
   *
   * @param mouseX the x-coordinate of the click
   * @param mouseY the y-coordinate of the click
   * @return true if clicked
   */
	public boolean click(int mouseX, int mouseY) {
		return button.click(mouseX, mouseY);
	}
}
