package fr.uge.graphics.view;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.*;

/**
 * Interface for all views in the game.
 * Each view can draw itself and respond to clicks.
 */
public interface View {
	/**
	 * Draws the view on the screen.
	 *
	 * @param graphics the graphics used to draw
	 * @param context the application context with screen info
	 */
	void draw(Graphics2D graphics, ApplicationContext context);
	
	/**
	 * Processes a click in the view.
	 *
	 * @param x the mouse X position
	 * @param y the mouse Y position
	 * @param controller the game controller
	 * @param context the application context with screen info
	 */
	void Click(int x, int y, Controller controller, ApplicationContext context);
}
