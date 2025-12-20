package fr.uge.graphics.view;

import java.awt.Color;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;

public class ItemPlacementView implements ClickableItems{
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
	private int zoneX(ApplicationContext context) {
		return context.getScreenInfo().width() - zoneWidth - 200; 
	}
	
	private int zoneY(ApplicationContext context) {
		return context.getScreenInfo().height() / 10 + 100;  
	}
	public void draw(Graphics2D graphics, ApplicationContext context) {
		int x = zoneX(context); 
		int y = zoneY(context); 
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
	 
		int angle = item.graphicRotation() % 360; 
		if(angle == 90 || angle == 270) {
			int tmp = w; 
			w = h; 
			h = tmp;
		}
		int cx = x + zoneWidth/2; 
		int cy = y + zoneHeight /2; 
		int itemX = cx - w/2; 
		int itemY = cy - h /2; 
		Graphics2D g2 = (Graphics2D) graphics.create(); 
		
		g2.rotate(Math.toRadians(angle),cx, cy);
		imagesItems.drawImage(g2, imagesItems.get(item.imageIndex()), itemX, itemY, w, h);
		
		g2.dispose();
	}
	
	public boolean clickSource(int mouseX, int mouseY, ClickableItems source, ApplicationContext context) {
		Item clicked = source.click(mouseX, mouseY, context); 
		if(clicked != null) {
			take(clicked); 
			return true; 
		}
		return false; 
	}
	
	@Override
	public Item click(int mouseX, int mouseY, ApplicationContext context) {
		if(!hasItem || item == null) return null; 
		
		int x = zoneX(context); 
		int y = zoneY(context); 
		
		if(mouseX >= x && mouseX <= x + zoneWidth && mouseY >= y && mouseY <= y + zoneHeight) {
			return item; 
		}
		
		return null; 
		
	}
	
}
