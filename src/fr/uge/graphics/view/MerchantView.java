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
	private final ItemsView shopView; 
	private final ImageLoader imagesHero;
	private final ImageLoader imagesServices; 
	private final BackpackView backpackView; 
	private final ItemPlacementView placementView; 
	
	private static final int imageSize = 200; 
	
	public MerchantView(Hero hero, Items shopItems, ImageLoader imagesHero, ImageLoader imagesServices, ImageLoader imagesItems) {
		this.hero = hero;
		this.shopView= new ItemsView(shopItems, imagesItems);  
		this.imagesHero = imagesHero;
		this.imagesServices = imagesServices; 
		this.backpackView = new BackpackView(hero.backpack(), imagesItems);
		this.placementView = new ItemPlacementView(imagesItems); 
	
	}
	
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
	}
	
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		int statX = context.getScreenInfo().width() / 5 + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
    var stat = hero.HP() + " " + "/" + " " + hero.HPMAX() + " PV";
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    graphics.drawString(stat, statX, statY);
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
		heroStat(graphics, context);
		importHeroImage(graphics, context);
		importMerchantImage(graphics, context);
		shopView.drawItems(graphics, context, true);
		backpackView.drawBackpack(graphics, context); 
		placementView.draw(graphics, context);
		
	}
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		placementView.clickSource(x, y, backpackView, context); 
	}
}
