package fr.uge.graphics.button;

import java.awt.Graphics2D;



import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.view.ItemPlacementView;
import fr.uge.graphics.view.MessageView;

/**
 * Represents a "DELETE" button that updates its position based on the screen size.
 */
public class DeleteButton {
	private final Button button = new Button(0, 0, 200, 100, "DELETE");
	
	private void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 225;
		int y = context.getScreenInfo().height() / 10 - 20;
		button.setPosition(x, y);
	}
	
  /**
   * Draws the delete button on the screen.
   *
   * @param graphics the graphics context to draw on
   * @param context the application context for screen information
   */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
	/**
	 * Checks if the delete button is clicked and moves the selected item to the delete zone.
	 *
	 * @param mouseX the x-coordinate of the click
	 * @param mouseY the y-coordinate of the click
	 * @param placementView the current item placement view
	 * @param message the message view to show
	 * @return true if an item was deleted, false otherwise
	 */
	public boolean click(int mouseX, int mouseY, ItemPlacementView placementView, MessageView message) {
		if(placementView.hasItem() && button.click(mouseX, mouseY)) {
			if (placementView.deleteView().isFull()) {
				message.showMessage("Delete zone is full !", 1500);
				return true;
			}
			placementView.moveToDeleted();
			placementView.clear();
			return true; 
		}
		return false; 
	}
}
