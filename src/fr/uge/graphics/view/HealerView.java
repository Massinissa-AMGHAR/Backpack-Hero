package fr.uge.graphics.view;

import fr.uge.character.*;
import fr.uge.game.ManageBackpack;
import fr.uge.graphics.*;
import fr.uge.graphics.button.Button;
import fr.uge.graphics.button.HealButton;
import fr.uge.graphics.image.*;
import fr.uge.inventory.Item;
import fr.uge.map.Dungeon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

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
	
	/**
	 * Creates the healer screen with hero info, dungeon info, and game components.
	 * Sets up buttons, backpack, hero view, and message system.
	 *
	 * @param hero the hero character
	 * @param dungeon the current dungeon
	 * @param healer the healer NPC
	 * @param imagesHero images for the hero
	 * @param imagesServices images for healer services
	 * @param imagesItems images for items
	 */
	
	public HealerView(Hero hero, Dungeon dungeon, Healer healer, ImageLoader imagesHero, ImageLoader imagesServices, ImageLoader imagesItems) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(dungeon);
		Objects.requireNonNull(healer);
		Objects.requireNonNull(imagesHero);
		Objects.requireNonNull(imagesServices);
		Objects.requireNonNull(imagesItems);
		
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
	
	/**
	 * Returns the message view.
	 *
	 * @return the message view
	 */
	public MessageView messageView() {return messageView; }
	
	/**
	 * Sets if the player is ready to return to the map.
	 *
	 * @param ready true if the player can return to the map
	 */
	public void setReadyToReturnToMap(boolean ready) {
    readyToReturnToMap = ready;
	}
	
	/**
	 * Checks if the player is ready to return to the map.
	 *
	 * @return true if the player can return to the map
	 */
	public boolean isReadyToReturnToMap() { 
		return readyToReturnToMap; 
	}
	
	private void background(Graphics2D graphics, ApplicationContext context) {
		graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());  
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
	
	/**
	 * Draws the healer screen with hero, backpack, buttons, and healer image.
	 *
	 * @param graphics the graphics context to draw on
	 * @param context the application context with screen info
	 */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(graphics);
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
	
	/**
	 * Process click on the healer screen: map button, backpack items, and heal button.
	 *
	 * @param x mouse x position
	 * @param y mouse y position
	 * @param controller the game controller
	 * @param context application context
	 */
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(controller);
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
