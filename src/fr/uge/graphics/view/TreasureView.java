package fr.uge.graphics.view;

import fr.uge.graphics.*;
import fr.uge.graphics.button.Button;
import fr.uge.graphics.button.DeleteButton;
import fr.uge.graphics.button.RotateButton;
import fr.uge.graphics.image.*;

import com.github.forax.zen.ApplicationContext;

import com.github.forax.zen.ScreenInfo;

import fr.uge.character.*;
import fr.uge.game.ManageBackpack;
import fr.uge.inventory.*;
import fr.uge.map.Dungeon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TreasureView implements View {
	private final Dungeon dungeon;
	private boolean treasureMessage = false;
	private final MessageView messageView;
	private boolean readyToReturnToMap = false;
	private final Hero hero;
	private final Items lootItems;
	private final HeroView heroView;
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

	public TreasureView(Hero hero, Dungeon dungeon, Items lootItems, ImageLoader imagesHero, ImageLoader imagesItems) {
		this.hero = hero;
		this.dungeon = dungeon;
		this.imagesHero = imagesHero;
		this.messageView = new MessageView(); 
		this.heroView = new HeroView(hero, imagesHero, imagesItems, null);
		this.backpackView = new BackpackView(hero.backpack(), imagesItems); 
		this.lootItems = lootItems;
		this.lootView = new ItemsView(lootItems, imagesItems); 
		this.deleteView = new ItemDeleteView(imagesItems);
		this.placementView = new ItemPlacementView(imagesItems, deleteView);
		this.management = new ManageBackpack(hero.backpack(), placementView, null); 
		this.mapButton = new Button(300, 300, 200, 100, "MAP"); 
		this.rotateButton = new RotateButton(); 
		this.deleteButton = new DeleteButton();
	}
	
	public MessageView messageView() {return messageView; }
	
	public void setReadyToReturnToMap(boolean ready) {
    readyToReturnToMap = ready;
	}
	
	public boolean isReadyToReturnToMap() { return readyToReturnToMap; }
	
	private void background(Graphics2D graphics, ApplicationContext context) {
		imagesHero.drawImage(graphics, imagesHero.get(8 + dungeon.currentFloor()), 0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    heroView.heroPV(graphics, context);
    heroView.heroManaEnergy(graphics, context);
    heroView.heroLevel(graphics, context);
    heroView.heroGold(graphics, context); 
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		messageView.update(); 
		background(graphics, context);
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
		mapButton.draw(graphics);
		rotateButton.draw(graphics, context);
		deleteButton.draw(graphics, context);
		heroView.importHeroImage(graphics, context);   
	}
	
	private boolean clickMapButton(int x, int y) {
		if(mapButton.click(x, y)) {
			messageView.showMessage("Returning to te map...", 2000);
			setReadyToReturnToMap(true);
			return true;
		}
		return false;
	}
	
	private boolean clickDeleteView(int x, int y, ApplicationContext context) {
		Item fromDelete = deleteView.click(x, y, context);
		if (fromDelete != null) {
			if (placementView.takeFromDelete(fromDelete)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean clickBackpackView(int x, int y, ApplicationContext context) {
		Item fromBackpack = backpackView.click(x, y, context); 
		if(management.clickFromBackpack(fromBackpack)) {
			if (fromBackpack.isMana()) {
				hero.reduceMana(fromBackpack.manaGained());
			}
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
	
	private boolean clickDeleteButton(int x, int y) {
		return deleteButton.click(x, y, placementView, messageView);
	}
	
	private boolean clickLootView(int x, int y, ApplicationContext context) {
		Item fromLoot = lootView.click(x, y, context); 
		if(fromLoot != null) {
			management.clickFromLoot(fromLoot, lootView.items()); 
			return true;
		}
		return false;
	}
	
	private void clickBackpackCell(int x, int y, ApplicationContext context) {
		int[] cell = backpackView.choiceCase(x, y, context); 
		if(cell != null) {
			var item = placementView.item();
			if(item == null)return; 
			if (item.isMana()) {
				var manaStones = hero.backpack().findManaCount();
				if (manaStones >= hero.manaMax()) {
					messageView.showMessage("Maximum mana reached !", 1000);
					return;
				}
			}
			if (management.placeInBackpack(cell[0], cell[1])) {
				if (item.isMana()) {
					hero.addMana(item.manaGained());
				}
			}
		}
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if (clickMapButton(x, y)) {
			return;
		}
		if (clickDeleteView(x, y, context)) {
			return;
		}
		if (clickBackpackView(x, y, context)) {
			return;
		}
		if (clickRotateButton(x, y)) {
			return;
		}
		if (clickDeleteButton(x, y)) {
			return;
		}
		if (clickLootView(x, y, context)) {
			return;
		}
		clickBackpackCell(x, y, context);
	}
}
