package fr.uge.graphics.view;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.Controller;
import fr.uge.inventory.Item;

public class Button {
	private int x; 
	private int y; 
	private final int width; 
	private final int height; 
	private final String text; 
	
	public Button(int x, int y, int width, int height, String text) {
		this.x = x; 
		this.y = y; 
		this.width = width; 
		this.height = height; 
		this.text = text; 
	}
	public void setPosition(int x, int y) {
		this.x = x; 
		this.y = y; 
	}
	public void draw(Graphics2D graphics) {
		
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
	
	public boolean click(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height; 
			 
	}
}
