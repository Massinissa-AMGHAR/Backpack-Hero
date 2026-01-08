package fr.uge.graphics.button;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

import fr.uge.game.ManageBackpack;
import fr.uge.graphics.view.ItemPlacementView;
import fr.uge.graphics.view.MessageView;

/**
 * Represents a "SELL" button that allows the player to sell an item.
 */
public class SellButton {
	private final Button button = new Button(0, 0, 200, 100, "SELL"); 
	
	private void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 225;
		int y = context.getScreenInfo().height() / 10 - 20;
		button.setPosition(x, y);
	}
	
  /**
   * Draws the sell button on the screen.
   *
   * @param graphics the graphics context to draw on
   * @param context the application context for screen information
   */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
  /**
   * Checks if the sell button is clicked and sells the item.
   *
   * @param mouseX the x-coordinate of the click
   * @param mouseY the y-coordinate of the click
   * @param placementView the current item placement view
   * @param management the backpack management for selling items
   * @param message the message view to show
   * @return true if the button was clicked
   */
	public boolean click(int mouseX, int mouseY, ItemPlacementView placementView, ManageBackpack management, MessageView message) {
		var item = placementView.item();
		if (item != null && !item.isGold() && button.click(mouseX, mouseY)) {
			if (management.sell()) {
				message.showMessage("You sold " + item.name(), 1000);
			}
			return true; 
		}
		return false;
	}
}
