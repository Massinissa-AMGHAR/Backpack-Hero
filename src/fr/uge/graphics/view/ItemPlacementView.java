package fr.uge.graphics.view;

import java.awt.Color;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;

public class ItemPlacementView {
	private Item item; 
	private boolean hasItem; 
	
	private final ImageLoader imagesItems; 
	
	private final int zoneWidth = 300; 
	private final int zoneHeight = 300; 
	
	public ItemPlacementView(ImageLoader imagesItems) {
		this.imagesItems = imagesItems; 
		this.hasItem = false; 
	}
	
	public boolean hasItem() {return hasItem;}
	public Item item() {return item; }
	
	public void take(Item item) {
		this.item = item; 
		hasItem = true; 
	}
	
	public void clear() {
		this.item = null; 
		hasItem = false; 
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		int x = context.getScreenInfo().width() - zoneWidth - 200; 
		int y = context.getScreenInfo().height() / 10 + 100; 
		drawZone(graphics, x, y); 
		if(hasItem) {
			drawItem(graphics, x, y); 
		}
	}
	
	private void drawZone(Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.GRAY);
		graphics.fillRect(x, y, zoneWidth, zoneHeight);
		
		graphics.setColor(Color.WHITE);
		graphics.drawRect(x, y, zoneWidth, zoneHeight);
	}
	
	private void drawItem(Graphics2D graphics, int x, int y) {
		if(item == null) return; 
		int cellSize = 75; 
		int w = item.width() * cellSize; 
		int h = item.height() * cellSize; 
		
		int itemX = x + (zoneWidth - w)/2; 
		int itemY = y + (zoneHeight - h)/2; 
		
		imagesItems.drawImage(graphics, imagesItems.get(item.imageIndex()), itemX, itemY, w, h);
	}
	
	public boolean clickSource(int mouseX, int mouseY, ClickableItems source, ApplicationContext context) {
		Item clicked = source.click(mouseX, mouseY, context); 
		if(clicked != null) {
			take(clicked); 
			return true; 
		}
		return false; 
	}
	
}
