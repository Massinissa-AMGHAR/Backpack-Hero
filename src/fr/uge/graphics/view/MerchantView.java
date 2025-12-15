package fr.uge.graphics.view;

import fr.uge.character.Hero;
import fr.uge.graphics.app.*;
import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;
import fr.uge.inventory.Items;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.ScreenInfo;

public class MerchantView implements View {
	private final Hero hero;
	private final Items shopItems; 
	private final ImageLoader imagesHero;
	private final ImageLoader imagesItems; 
	private final ImageLoader imagesServices; 
	private final BackpackView backpackView; 
	
	private static final int imageSize = 200;
	private static final int shopWidth = 350; 
	private static final int shopHeight = 70; 
	private static final int shopGap = 15; 
	private static final int itemSize = 48; 
	
	private static final Font itemName = new Font("Arial", Font.BOLD, 16); 
	private static final Font itemPrice = new Font("Arial", Font.PLAIN, 14); 
	
	public MerchantView(Hero hero, Items shopItems, ImageLoader imagesHero, ImageLoader imagesServices, ImageLoader imagesItems) {
		this.hero = hero;
		this.shopItems = shopItems; 
		this.imagesHero = imagesHero;
		this.imagesServices = imagesServices;
		this.imagesItems = imagesItems; 
		this.backpackView = new BackpackView(hero.backpack(), imagesItems); 
	}
	
	private int imageIndex(Item item) {
		return switch(item.name()) {
		 case "Wooden Sword" -> 0; 
		 case "Arrow" -> 1; 
		 case "Rough Buckler" ->2; 
		 case "Tunic" -> 3; 
		 case "Magic Wand" -> 4; 
		 case "Mana Stone" -> 5; 
		 default -> 0; 
		};
	}
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
	}
	
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		int statX = context.getScreenInfo().width() / 5 + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
    var stat = hero.HP() + " " + "/" + " " + hero.HPMAX() + " PV";
   // var eX = context.getScreenInfo().width() / 8 - 175;
   // var eY = context.getScreenInfo().height() / 6;
  //  var energy = "Energy : " + hero.energy();
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    graphics.drawString(stat, statX, statY);
  //  graphics.drawString(energy, eX, eY);
	}
	
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		int heroX = context.getScreenInfo().width() / 5;
		int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesHero.get(0), heroX, heroY, imageSize, imageSize); 
	}
	
	private void importMerchantImage(Graphics2D graphics, ApplicationContext context) {
		int MerchantX = context.getScreenInfo().width() - context.getScreenInfo().width() / 5 - imageSize;
		int MerchantY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesServices.get(0), MerchantX, MerchantY, imageSize, imageSize); 
	}
	
	private int shopX(ApplicationContext context) {
		return context.getScreenInfo().width()/2 - shopWidth /2; 
	}
	
	private int shopY(ApplicationContext context, int index) {
		return context.getScreenInfo().height()/2 + context.getScreenInfo().height() / 6 + index *(shopHeight + shopGap); 
	}
	private void drawItemsSlot(Graphics2D graphics, ApplicationContext context, int index) {
		int x = shopX(context); 
		int y = shopY(context, index); 
		
		graphics.setColor(Color.GRAY);
		graphics.fillRect(x,  y, shopWidth, shopHeight);
		graphics.setColor(Color.WHITE);
		graphics.drawRect(x,  y, shopWidth, shopHeight);
	}
	
	private void drawItemImage(Graphics2D graphics, ApplicationContext context, int index ) {
		Item item = shopItems.getItems().get(index); 
		var img = imagesItems.get(imageIndex(item)); 
		
		if(img != null) {
			imagesItems.drawImage(graphics, img, shopX(context) + 10, shopY(context, index) + 10, itemSize, itemSize);
		}
	}
	
	private void drawItemText(Graphics2D graphics, ApplicationContext context, int index) {
		Item item = shopItems.getItems().get(index); 
		
		int textX = shopX(context) + imageSize + 25; 
		int textY = shopY(context, index) + 28; 
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(itemName);
		graphics.drawString(item.name(), textX, textY);
		
		graphics.setFont(itemPrice);
		graphics.drawString(item.goldCost() + " gold", textX, textY + 20);
		
	}
	private void drawShop(Graphics2D graphics, ApplicationContext context) {
		var items = shopItems.getItems(); 
		for(int i = 0; i < items.size(); i++) {
			drawItemsSlot(graphics, context, i); 
			drawItemImage(graphics, context, i); 
			drawItemText(graphics, context, i); 
		}
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		background(graphics, context.getScreenInfo());
		heroStat(graphics, context);
		importHeroImage(graphics, context);
		importMerchantImage(graphics, context);
		drawShop(graphics, context); 
		backpackView.draw(graphics, context); 
	}
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		
	}
}
