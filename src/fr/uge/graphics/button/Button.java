package fr.uge.graphics.button;

import java.awt.Color;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Objects;

/**
 * Represents a clickable button with a position, size, and label.
 *
 * @param x the x-coordinate of the button
 * @param y the y-coordinate of the button
 * @param width the width of the button
 * @param height the height of the button
 * @param text the label shown on the button
 */
public class Button {
	private int x; 
	private int y; 
	private final int width; 
	private final int height; 
	private final String text; 
	
	public Button(int x, int y, int width, int height, String text) {
		Objects.requireNonNull(text);
		this.x = x; 
		this.y = y; 
		this.width = width; 
		this.height = height; 
		this.text = text; 
	}
	
	/**
	 * Sets a new position for the button.
	 *
	 * @param x the new x-coordinate
	 * @param y the new y-coordinate
	 */
	public void setPosition(int x, int y) {
		this.x = x; 
		this.y = y; 
	}
	
	/**
	 * Draws the button with a red background, white border, and centered text.
	 *
	 * @param graphics the graphics context to draw on
	 */
	public void draw(Graphics2D graphics) {
		Objects.requireNonNull(graphics);
		graphics.setColor(Color.RED);
		graphics.fillRect(x, y, width, height);
		
		graphics.setColor(Color.WHITE);
		graphics.drawRect(x, y, width, height);
		
		graphics.setFont(new Font("Arial", Font.BOLD, 28));
		FontMetrics fm = graphics.getFontMetrics(); 
		
		int textX = x + (width - fm.stringWidth(text)) / 2; 
		int textY = y + (height + fm.getAscent()) / 2 - 4; 
		graphics.drawString(text, textX, textY); 
			
	}
	
	/**
	 * Checks if a point is inside the button (clicked).
	 *
	 * @param mouseX the x-coordinate of the click
	 * @param mouseY the y-coordinate of the click
	 * @return true if the click is inside the button
	 */
	public boolean click(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height; 
	}
}
