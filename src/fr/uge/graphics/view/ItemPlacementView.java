package fr.uge.graphics.view;

import java.awt.Color;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;
import java.util.Objects;

/**
 * Creates a view to place one item.
 * It shows an item in a specific zone.
 *
 * This class uses item images and a delete view.
 */
public class ItemPlacementView implements ClickableItems{
	private Item item; 
	private boolean hasItem; 
	
	private final ImageLoader imagesItems; 
	private final ItemDeleteView deleteView;
	
	private final int zoneWidth = 300; 
	private final int zoneHeight = 300; 
	
	/**
	 * Creates the item placement view.
	 * It needs item images and the delete view.
	 *
	 * @param imagesItems the images of the items
	 * @param deleteView the delete view
	 */
	public ItemPlacementView(ImageLoader imagesItems, ItemDeleteView deleteView) {
		Objects.requireNonNull(imagesItems, "imagesItems can't be null");
		Objects.requireNonNull(deleteView, "deleteView can't be null");
		this.imagesItems = imagesItems; 
		this.deleteView = deleteView;
		this.hasItem = false; 
	}
	
	/**
	 * Returns the delete view linked to this placement view.
	 *
	 * @return the delete view
	 */
	public ItemDeleteView deleteView() { return deleteView; }
	
	/**
	 * Checks if there is an item in the placement zone.
	 *
	 * @return true if there is an item
	 */
	public boolean hasItem() {return hasItem;}
	
	/**
	 * Returns the item in the placement zone.
	 *
	 * @return the item or null if none
	 */
	public Item item() {return item; }
	
	/**
	 * Places an item in the zone if empty.
	 *
	 * @param item the item to place
	 */
	public void take(Item item) {
		if (hasItem) return;
		this.item = item; 
		hasItem = true; 
	}
	
	/**
	 * Removes the item from the zone.
	 * The zone becomes empty.
	 */
	public void clear() {
		this.item = null; 
		hasItem = false; 
	}
	
	/**
	 * Moves the current item to the delete view.
	 * Does nothing if there is no item.
	 */
	public void moveToDeleted() {
		if (item != null && deleteView != null) deleteView.add(item);
	}
	
	/**
	 * Takes an item from the delete view into this zone.
	 * Fails if the zone already has an item or the item is null.
	 *
	 * @param item the item to take
	 * @return true if the item was taken, false otherwise
	 */
	public boolean takeFromDelete(Item item) {
		if (hasItem || item == null) return false;
		this.item = item;
		hasItem = true;
		if (deleteView != null) deleteView.remove(item);
		return true;
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
		if(item == null) {
			return; 
		}
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
	
	/**
	 * Checks if the mouse clicks on an item in a source.
	 * If an item is clicked, it is taken into this zone.
	 *
	 * @param mouseX the mouse X position
	 * @param mouseY the mouse Y position
	 * @param source the source of clickable items
	 * @param context the application context with screen info
	 * @return true if an item was taken, false otherwise
	 */
	public boolean clickSource(int mouseX, int mouseY, ClickableItems source, ApplicationContext context) {
		Item clicked = source.click(mouseX, mouseY, context); 
		if(clicked != null) {
			take(clicked); 
			return true; 
		}
		return false; 
	}
	
	/**
	 * Checks if the mouse clicks on the item in this zone.
	 * Returns the item if clicked.
	 *
	 * @param mouseX the mouse X position
	 * @param mouseY the mouse Y position
	 * @param context the application context with screen info
	 * @return the item if clicked, null otherwise
	 */
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
