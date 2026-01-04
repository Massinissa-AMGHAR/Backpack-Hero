package fr.uge.graphics.view;

import java.awt.Color;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;
import java.util.List;
import java.util.ArrayList;

public class ItemDeleteView implements ClickableItems {
	private final List<Item> deletedItems = new ArrayList<>();
	private final ImageLoader imagesItems;
	
	private final int zoneWidth = 400;
	private final int zoneHeight = 250;
	
	private final int columns = 3;
	private final int rows = 2;
	private final int maxItems = columns * rows;
	
	public ItemDeleteView(ImageLoader imagesItems) {
		this.imagesItems = imagesItems;
	}
	
	public void add(Item item) {
		if (item != null) deletedItems.add(item);
	}
	
	public boolean isFull() { return deletedItems.size() >= maxItems; }
	
	public void remove(Item item) {
		deletedItems.remove(item);
	}
	
	private int zoneX(ApplicationContext context) {
		return context.getScreenInfo().width() - zoneWidth - 50; 
	}
	
	private int zoneY(ApplicationContext context) {
		return context.getScreenInfo().height() - zoneHeight - 50;  
	}
	
	private void drawZone(Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.GRAY);
		graphics.fillRect(x, y, zoneWidth, zoneHeight);
		
		graphics.setColor(Color.WHITE);
		graphics.drawRect(x, y, zoneWidth, zoneHeight);
	}
	
	private void drawRotatedItem(Graphics2D graphics, Item item, int itemX, int itemY, int itemWidth, int itemHeight) {
		int w = itemWidth;
		int h = itemHeight; 
	 
		int angle = item.graphicRotation() % 360; 
		if(angle == 90 || angle == 270) {
			int tmp = w; 
			w = h; 
			h = tmp;
		}
		int cx = itemX + itemWidth/2; 
		int cy = itemY + itemHeight /2; 
		Graphics2D g2 = (Graphics2D) graphics.create(); 
		
		g2.rotate(Math.toRadians(angle),cx, cy);
		imagesItems.drawImage(g2, imagesItems.get(item.imageIndex()), itemX, itemY, w, h);
		
		g2.dispose();
	}
	
	private void drawItem(Graphics2D graphics, int x, int y, Item item, int index) {
		if(item == null) {
			return; 
		}
		int itemWidth = zoneWidth / columns - 10;
		int itemHeight = zoneHeight / rows - 10;
		int col = index % columns;
		int row = index / columns;
		int itemX = x + col * (itemWidth + 5) + 5;
		int itemY = y + row * (itemHeight + 5) + 5;
		drawRotatedItem(graphics, item, itemX, itemY, itemWidth, itemHeight);

	}

	public void draw(Graphics2D graphics, ApplicationContext context) {
		int x = zoneX(context);
		int y = zoneY(context);
		drawZone(graphics, x, y);
		for (int i = 0; i < deletedItems.size(); i++) drawItem(graphics, x, y, deletedItems.get(i), i);
	}
	
	public Item click(int mouseX, int mouseY, ApplicationContext context) {
		int x = zoneX(context);
		int y = zoneY(context);
		int itemWidth = zoneWidth / columns - 10;
		int itemHeight = zoneHeight / rows - 10;
		for (int i = 0; i < deletedItems.size(); i++) {
			int col = i % columns;
			int row = i / columns;
			int itemX = x + col * (itemWidth + 5) + 5;
			int itemY = y + row * (itemHeight + 5) + 5;
			if (mouseX >= itemX && mouseX <= itemX + itemWidth && mouseY >= itemY && mouseY <= itemY + itemHeight) {
				return deletedItems.get(i);
			}
		}
		return null;
	}
}
