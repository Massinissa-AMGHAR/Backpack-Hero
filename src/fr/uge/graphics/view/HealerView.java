package fr.uge.graphics.view;

import fr.uge.character.*;
import fr.uge.game.ManageBackpack;
import fr.uge.graphics.*;
import fr.uge.graphics.image.*;
import fr.uge.inventory.Item;
import fr.uge.map.Dungeon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.ScreenInfo;

public class HealerView implements View {
	private final MessageView messageView;
	private final Hero hero;
	private final Dungeon dungeon;
	private final Healer healer;
	private final ImageLoader imagesHero;
	private final ImageLoader imagesServices; 
	private final ImageLoader imagesItems;
	private final HeroView heroView;
	private final BackpackView backpackView;
	private final ItemPlacementView placementView; 
	private final ItemDeleteView deleteView;
	private final ManageBackpack management; 


	private final Button mapButton; 
	private final HealButton healButton;
	private boolean readyToReturnToMap = false;

	private final int imageSize = 200;
	
	
	public HealerView(Hero hero, Dungeon dungeon, Healer healer, ImageLoader imagesHero, ImageLoader imagesServices, ImageLoader imagesItems) {
		this.hero = hero;
		this.dungeon = dungeon;
		this.healer = healer;
		this.imagesHero = imagesHero;
		this.imagesServices = imagesServices;
		this.imagesItems = imagesItems;
		this.mapButton = new Button(300, 300, 200, 100, "MAP");
		this.heroView = new HeroView(hero, imagesHero, imagesItems, null);
		this.backpackView = new BackpackView(hero.backpack(), imagesItems); 
		this.deleteView = new ItemDeleteView(imagesItems);
		this.placementView = new ItemPlacementView(imagesItems, deleteView);
		this.management = new ManageBackpack(hero.backpack(), placementView, null); 

		this.messageView = new MessageView(); 
		this.healButton = new HealButton();
		messageView.showMessage("Welcome to the Healer !", 2000);

	}
	
	public void setReadyToReturnToMap(boolean ready) {
    readyToReturnToMap = ready;
	}
	
	public boolean isReadyToReturnToMap() { return readyToReturnToMap; }
		
	public MessageView messageView() { return messageView; }
	
	private void background(Graphics2D graphics, ApplicationContext context) {
		imagesHero.drawImage(graphics, imagesHero.get(8 + dungeon.currentFloor()), 0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    heroView.heroLevel(graphics, context);
    heroView.heroPV(graphics, context);
    heroView.heroGold(graphics, context);
	}
	
	private void importHealerImage(Graphics2D graphics, ApplicationContext context) {
		int MerchantX = context.getScreenInfo().width() - context.getScreenInfo().width() / 5 - imageSize;
		int MerchantY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesServices.get(1), MerchantX, MerchantY, imageSize, imageSize); 
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		messageView.update(); 
		background(graphics, context);
		messageView.drawMessage(graphics, context);
		backpackView.drawBackpack(graphics, context);
		placementView.draw(graphics, context);
		if(!placementView.hasItem()) {
			mapButton.draw(graphics);
		}
		healButton.draw(graphics, context);
		heroStat(graphics, context);
		heroView.importHeroImage(graphics, context);
		importHealerImage(graphics, context);
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if(mapButton.click(x, y)) {
			messageView.showMessage("Returning to te map...", 2000);
			setReadyToReturnToMap(true);
			return; 
		}
		Item fromBackpack = backpackView.click(x, y, context);
		if (fromBackpack != null) {
			if (!fromBackpack.isGold()) {
				messageView.showMessage("Only gold can be used to heal !", 1000);
				return;
			}
			if (management.clickFromBackpack(fromBackpack) && fromBackpack.isGold()) {
				return;
			}
		}
		if (healButton.click(x, y, placementView, hero, healer, messageView)) {
			return;
		}
		int[] cell = backpackView.choiceCase(x, y, context); 
		if(cell != null) {
			management.placeInBackpack(cell[0], cell[1]); 
		}
	}
}
