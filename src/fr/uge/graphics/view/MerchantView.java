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
	private final int imageSize = 200;
	
	
	public MerchantView(Hero hero, Items shopItems, ImageLoader imagesHero, ImageLoader imagesServices, ImageLoader imagesItems) {
		this.hero = hero;
		this.shopItems = shopItems; 
		this.imagesHero = imagesHero;
		this.imagesServices = imagesServices;
		this.imagesItems = imagesItems; 
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
	
	private void title(Graphics2D graphics, ApplicationContext context, ScreenInfo screenInfo) {
    int titleX = screenInfo.width() / 2 - 250;
    int titleY = screenInfo.height() / 5 - 60;
    int titleWidth = 500;
    int titleHeight = 80;
    graphics.setColor(Color.LIGHT_GRAY);
    graphics.fillRect(titleX, titleY, titleWidth, titleHeight); 
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 50));
    var fontmetric1 = graphics.getFontMetrics();
    graphics.drawString("MERCHANT VIEW", (screenInfo.width() - fontmetric1.stringWidth("MERCHANT VIEW")) / 2, screenInfo.height()/5);
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
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		background(graphics, context.getScreenInfo());
		title(graphics, context, context.getScreenInfo());
		heroStat(graphics, context);
		importHeroImage(graphics, context);
		importMerchantImage(graphics, context);
		drawShop(graphics, context); 
	}
	
	private void drawShop(Graphics2D graphics, ApplicationContext context) {
		int x = context.getScreenInfo().width() / 2 - 150; 
		int y = context.getScreenInfo().height()/3; 
		
		int width = 300; 
		int height = 60; 
		int gap = 20; 
		int size = 48; 
		
		var items = shopItems.getItems(); 
		
		graphics.setFont(new Font("Arial", Font.BOLD, 18));
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i); 
			int itemY = y + i * (height + gap); 
			
			graphics.setColor(Color.GRAY);
			graphics.fillRect(x, itemY, width, height);
			
			graphics.setColor(Color.WHITE);
			graphics.drawRect(x, itemY, width, height);
			
			int imgIndex = imageIndex(item); 
			imagesItems.drawImage(graphics, imagesItems.get(imgIndex), x + 10, itemY + 6, size, size);
		}
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		
	}
}
