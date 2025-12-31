package fr.uge.graphics.view;

import fr.uge.graphics.app.*;



import fr.uge.graphics.image.*;

import com.github.forax.zen.ApplicationContext;

import com.github.forax.zen.ScreenInfo;

import fr.uge.character.*;
import fr.uge.inventory.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TreasureView implements View {
	private boolean treasureMessage = false;
	private final MessageView messageView;
	private boolean readyToReturnToMap = false;
	private final Hero hero;
	private final BackpackView backpackView; 
	private final ImageLoader imagesHero;
	private final int imageSize = 200;
	private final ItemPlacementView placementView; 
	private final ItemDeleteView deleteView;
	private final ItemsView lootView; 
	private final Button mapButton; 
	private final RotateButton rotateButton;
	private final DeleteButton deleteButton;
	private final ManageBackpack management; 

	public TreasureView(Hero hero, ImageLoader imagesHero, ImageLoader imagesItems) {
		this.hero = hero;
		this.imagesHero = imagesHero;
		this.messageView = new MessageView(); 
		this.backpackView = new BackpackView(hero.backpack(), imagesItems); 
		var lootItems = new Items(); 
		lootItems.randomItems(3);
		this.lootView = new ItemsView(lootItems, imagesItems); 
		this.deleteView = new ItemDeleteView(imagesItems);
		this.placementView = new ItemPlacementView(imagesItems, deleteView);
		this.management = new ManageBackpack(hero.backpack(), placementView, hero, null); 
		this.mapButton = new Button(300, 300, 200, 100, "MAP"); 
		this.rotateButton = new RotateButton(); 
		this.deleteButton = new DeleteButton();
		
	}
	
	public MessageView messageView() {return messageView; }
	
	public void setReadyToReturnToMap(boolean ready) {
    readyToReturnToMap = ready;
	}
	
	public boolean isReadyToReturnToMap() { return readyToReturnToMap; }
	
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
	}
	
	private void heroPV(Graphics2D graphics, ApplicationContext context) {
		var stat = hero.HP() + " / " + hero.HPMAX() + " PV";
		int statX = context.getScreenInfo().width() / 5 + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
		graphics.drawString(stat, statX, statY);
	}
	
	private void heroManaEnergy(Graphics2D graphics, ApplicationContext context) {
		var mana = "ManaEnergy : " + hero.mana();
		var mX = context.getScreenInfo().width() / 8 - 175;
    var mY = context.getScreenInfo().height() / 6 + 50;
    graphics.drawString(mana, mX, mY);
	}
	
	private void heroGold(Graphics2D graphics, ApplicationContext context) {
		var gold = backpackView.backpack().findGoldItem(); 
		String goldText; 
    if(gold == null) {
    	goldText = "Gold : 0 gold"; 
    }else {
    	goldText = "Gold : " + gold.goldAmount() + " gold"; 
    }
		var gX = context.getScreenInfo().width() / 8 - 175;
    var gY = context.getScreenInfo().height() / 6 + 100;
    graphics.drawString(goldText, gX, gY);
	}
	
	private void heroLevel(Graphics2D graphics, ApplicationContext context) {
    var level = "LVL." + hero.level();
    var xp = hero.xp() + " / " + hero.nextLevelXp() + " XP"; 
    var levelX = context.getScreenInfo().width() / 8 - 175;
    var levelY = context.getScreenInfo().height() / 6 + 300;
    graphics.drawString(level, levelX, levelY);
    graphics.drawString(xp, levelX, levelY + 40);
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    heroPV(graphics, context);
    heroManaEnergy(graphics, context);
    heroLevel(graphics, context);
    heroGold(graphics, context);
    
	}
	
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		int heroX = context.getScreenInfo().width() / 5;
		int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesHero.get(0), heroX, heroY, imageSize, imageSize); // Hero
	}
	@Override
	public void draw(Graphics2D graphics, ApplicationContext context) {
		messageView.update(); 
		background(graphics, context.getScreenInfo());
		if (!treasureMessage) {
			messageView().showMessage("You found a treasure !", 1500);
			treasureMessage = true;
		}
		messageView.drawMessage(graphics, context);
		heroStat(graphics, context);
		backpackView.drawBackpack(graphics, context);
		lootView.drawItems(graphics, context, false);
		placementView.draw(graphics, context);
		deleteView.draw(graphics, context);
		if(!placementView.hasItem()) mapButton.draw(graphics);
		rotateButton.draw(graphics, context);
		deleteButton.draw(graphics, context);
		importHeroImage(graphics, context);   
	}
	@Override
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if(mapButton.click(x, y)) {
			messageView.showMessage("Returning to te map...", 2000);
			setReadyToReturnToMap(true);
			return; 
		}
		
		Item fromDelete = deleteView.click(x, y, context);
		if (fromDelete != null) {
			if (placementView.takeFromDelete(fromDelete)) return;
		}
		
		Item fromBackpack = backpackView.click(x, y, context); 
		if(management.clickFromBackpack(fromBackpack)) {
			if (fromBackpack.isMana()) hero.reduceMana(fromBackpack.manaGained());
			return;
		}
		
		Item placedItem = placementView.item(); 
		if(rotateButton.click(x, y, placedItem)) {
			placedItem.VisualRotation();
			return; 
		}
		
		if (deleteButton.click(x, y, placementView, messageView)) return;
		Item fromLoot = lootView.click(x, y, context); 
		if(fromLoot != null) {
			management.clickFromLoot(fromLoot, lootView.items()); 
			return; 
		}
		int[] cell = backpackView.choiceCase(x, y, context); 
		if(cell != null) {
			var item = placementView.item();
			if (management.placeInBackpack(cell[0], cell[1])) {
				if (item.isMana()) hero.addMana(item.manaGained());
			}
		}
	}
	
}
