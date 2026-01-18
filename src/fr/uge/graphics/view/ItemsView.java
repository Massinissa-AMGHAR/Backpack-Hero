package fr.uge.graphics.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;
import fr.uge.inventory.Items;

/**
 * Creates a view to show all items.
 * It displays items with images, names, and info.
 */
public class ItemsView implements ClickableItems{
	private final Items items; 
	private final ImageLoader images;  
	
	private static final int imageSize = 200;
	private static final int itemWidth = 350; 
	private static final int itemHeight = 70; 
	private static final int spacing = 15; 
	private static final int itemSize = 48; 
	
	private static final Font itemName = new Font("Arial", Font.BOLD, 16); 
	private static final Font itemInfos = new Font("Arial", Font.PLAIN, 14); 
	
	/**
	 * Creates the items view with items and their images.
	 *
	 * @param items the items to show
	 * @param images the images of the items
	 */
	public ItemsView(Items items, ImageLoader images) {
		Objects.requireNonNull(items);
		Objects.requireNonNull(images);
		this.items = items; 
		this.images = images; 
	}
	
	/**
	 * Returns the items shown in this view.
	 *
	 * @return the items
	 */
	public Items items(){ return items; }
	
	private int itemX(ApplicationContext context) {
		return context.getScreenInfo().width()/2 - itemWidth /2; 
	}
	
	private int itemY(ApplicationContext context, int index) {
		return context.getScreenInfo().height()/2 + context.getScreenInfo().height() / 6 + index *(itemHeight + spacing); 
	}
	
	private void drawItemsSlot(Graphics2D graphics, ApplicationContext context, int index) {
		int x = itemX(context); 
		int y = itemY(context, index); 
		
		graphics.setColor(Color.GRAY);
		graphics.fillRect(x,  y, itemWidth, itemHeight);
		graphics.setColor(Color.WHITE);
		graphics.drawRect(x,  y, itemWidth, itemHeight);
	}
	
	private void drawItemImage(Graphics2D graphics, ApplicationContext context, int index ) {
		Item item = items.getItems().get(index); 
		var img = images.get(item.imageIndex()); 
		
		if(img != null) {
			images.drawImage(graphics, img, itemX(context) + 10, itemY(context, index) + 10, itemSize, itemSize);
		}
	}
	
	private void drawItemText(Graphics2D graphics, ApplicationContext context, int index, boolean isShop) {
		Item item = items.getItems().get(index); 
		
		int textX = itemX(context) + imageSize + 25; 
		int textY = itemY(context, index) + 28; 
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(itemName);
		graphics.drawString(item.name(), textX, textY);
		
		graphics.setFont(itemInfos);
		if(isShop)graphics.drawString(item.goldCost() + " gold", textX, textY + 20);
		else graphics.drawString(item.rarity().name(), textX, textY + 20);
		
	}
	
	/**
	 * Draws all items in the view.
	 * It draws each slot, the item image, and the item text.
	 *
	 * @param graphics the graphics used to draw
	 * @param context the application context with screen info
	 * @param isShop true if showing in shop mode, false otherwise
	 */
	public void drawItems(Graphics2D graphics, ApplicationContext context, boolean isShop) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(graphics);
		var list = items.getItems(); 
		for(int i = 0; i < list.size(); i++) {
			drawItemsSlot(graphics, context, i); 
			drawItemImage(graphics, context, i); 
			drawItemText(graphics, context, i, isShop); 
		}
	}
	
	/**
	 * Checks if the mouse clicks on an item in this view.
	 * Returns the clicked item or null if none.
	 *
	 * @param mouseX the mouse X position
	 * @param mouseY the mouse Y position
	 * @param context the application context with screen info
	 * @return the clicked item or null
	 */
	public Item click(int mouseX, int mouseY, ApplicationContext context) {
		Objects.requireNonNull(context); 
		for(int i = 0; i < items.getItems().size(); i++) {
			int x = itemX(context); 
			int y = itemY(context, i); 
			if(mouseX >= x && mouseX <= x + itemWidth && mouseY >= y && mouseY <= y + itemHeight) {
				return items.getItems().get(i); 
			}
		}
		return null; 
	}
}
