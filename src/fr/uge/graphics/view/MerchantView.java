package fr.uge.graphics.view;

import fr.uge.character.Hero;
import fr.uge.character.Merchant;
import fr.uge.graphics.app.*;
import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.BackpackPlacement;
import fr.uge.inventory.Item;
import fr.uge.inventory.ItemType;
import fr.uge.inventory.Items;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.ScreenInfo;

public class MerchantView implements View {
	private final SellButton sellButton;
	private final Hero hero;
	private final Merchant merchant; 
	private final ItemsView shopView; 
	private final ImageLoader imagesHero;
	private final ImageLoader imagesServices; 
	private final HeroView heroView;
	private final BackpackView backpackView; 
	private final ItemDeleteView deleteView;
	private final ItemPlacementView placementView; 
	private final ManageBackpack management; 
	private final MessageView message; 
	private final Button mapButton; 
	private final RotateButton rotateButton; 
	private boolean readyToReturnToMap = false;

	private static final int imageSize = 200; 
	
	public MerchantView(Hero hero, Merchant merchant, Items shopItems, ImageLoader imagesHero, ImageLoader imagesServices, ImageLoader imagesItems) {
		this.hero = hero;
		this.merchant = merchant; 
		this.shopView= new ItemsView(shopItems, imagesItems);  
		this.imagesHero = imagesHero;
		this.imagesServices = imagesServices; 
		this.heroView = new HeroView(hero, imagesHero, imagesItems, null);
		this.backpackView = new BackpackView(hero.backpack(), imagesItems);
		this.deleteView = new ItemDeleteView(imagesItems);
		this.placementView = new ItemPlacementView(imagesItems, deleteView); 
		this.management = new ManageBackpack(hero.backpack(), placementView, merchant); 
		this.message = new MessageView(); 
		this.mapButton = new Button(300, 300, 200, 100, "MAP"); 
		this.rotateButton = new RotateButton(); 
		this.sellButton = new SellButton();
		message.showMessage("Welcome to the Merchant !", 2000);
	}
	
	public void setReadyToReturnToMap(boolean ready) {
    readyToReturnToMap = ready;
	}
	
	public boolean isReadyToReturnToMap() { return readyToReturnToMap; }
	
	public MessageView messageView() {return message; }

	
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		int statX = context.getScreenInfo().width() / 5 + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
		var gold = backpackView.backpack().findGoldItem(); 
		String goldText; 
    if(gold == null) {
    	goldText = "Gold : 0 gold"; 
    } else {
    	goldText = "Gold : " + gold.goldAmount() + " gold"; 
    }
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    graphics.drawString(goldText, statX, statY);
	}
	
	private void importMerchantImage(Graphics2D graphics, ApplicationContext context) {
		int MerchantX = context.getScreenInfo().width() - context.getScreenInfo().width() / 5 - imageSize;
		int MerchantY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesServices.get(0), MerchantX, MerchantY, imageSize, imageSize); 
	}
	
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		message.update();
		background(graphics, context.getScreenInfo());
		message.drawMessage(graphics, context);
		heroStat(graphics, context);
		heroView.importHeroImage(graphics, context);
		importMerchantImage(graphics, context);
		shopView.drawItems(graphics, context, true);
		backpackView.drawBackpack(graphics, context); 
		placementView.draw(graphics, context);
		if(!placementView.hasItem()) {
			mapButton.draw(graphics);
		}
		rotateButton.draw(graphics, context);
		sellButton.draw(graphics, context);
	}
	
	private boolean clickMapButton(int x, int y) {
		if(mapButton.click(x, y)) {
			message.showMessage("Returning to the map...", 2000);
			setReadyToReturnToMap(true);
			return true;
		}
		return false;
	}
	
	private boolean clickBackpack(int x, int y, ApplicationContext context) {
		Item fromBackpack = backpackView.click(x, y, context); 
		if(management.clickFromBackpack(fromBackpack)) {
			return true;
		}
		return false;
	}
	
	private boolean clickRotateButton(int x, int y) {
		Item placedItem = placementView.item(); 
		if(rotateButton.click(x, y, placedItem)) {
			placedItem.VisualRotation();
			return true;
		}
		return false;
	}
	
	private boolean clickShop(int x, int y, ApplicationContext context) {
		Item fromShop = shopView.click(x, y, context); 
		if(fromShop != null) {
			if (fromShop.isMana() && hero.backpack().findManaCount() >= hero.manaMax()) {
				message.showMessage("Maximum mana reached !", 1000);
				return true;
			}
			if(!management.clickFromShop(fromShop, shopView.items())) {
				message.showMessage("You don't have enough money !", 1000);
			}
			return true; 
		}
		return false;
	}
	
	private void clickBackpackCell(int x, int y, ApplicationContext context) {
		int[] cell = backpackView.choiceCase(x, y, context); 
		if(cell != null) {
			management.placeInBackpack(cell[0], cell[1]); 
		}
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if (clickMapButton(x, y)) {
			return;
		}
		if (clickBackpack(x, y, context)) {
			return;
		}
		if (clickRotateButton(x, y)) {
			return;
		}
		if (sellButton.click(x, y, placementView, management, message)) {
			return;
		}
		if (clickShop(x, y, context)) {
			return;
		}
		clickBackpackCell(x, y, context);
	}
}
