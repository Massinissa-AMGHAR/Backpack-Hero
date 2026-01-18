package fr.uge.graphics.view;

import com.github.forax.zen.ApplicationContext;

import fr.uge.inventory.Item;

public interface ClickableItems {
	
	/**
	 * Interface for clickable items.
	 * 
	 * @param mouseX the X coordinate of the click
	 * @param mouseY the Y coordinate of the click
	 * @param context the application context with screen info
	 * @return the clicked Item, or null if none
	 */
	
	Item click(int mouseX, int mouseY, ApplicationContext context); 
}
