package fr.uge.graphics.button;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

/**
 * Represents an "END TURN" button that updates its position based on screen size.
 */
public class EndTurnButton {
	private final Button button = new Button(0, 0, 200, 100, "END TURN"); 
	
	private void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 450; 
		int y = context.getScreenInfo().height() / 10 + 300;
		button.setPosition(x, y);
	}
	
  /**
   * Draws the end turn button on the screen.
   *
   * @param graphics the graphics context to draw on
   * @param context the application context for screen information
   */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
  /**
   * Checks if the end turn button is clicked.
   *
   * @param mouseX the x-coordinate of the click
   * @param mouseY the y-coordinate of the click
   * @return true if clicked
   */
	public boolean click(int mouseX, int mouseY) {
		return button.click(mouseX, mouseY);
	}
}
