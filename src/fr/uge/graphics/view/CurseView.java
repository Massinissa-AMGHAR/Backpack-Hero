package fr.uge.graphics.view;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.character.Hero;
import fr.uge.graphics.app.Controller;
import fr.uge.graphics.app.ManageBackpack;
import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;
import fr.uge.inventory.Items;

public class CurseView implements View{
	private final Hero hero;
	private final BackpackView backpackView; 
	private final Item curse; 
	
	private final ItemPlacementView placementView; 
	private final ItemDeleteView deleteView;
	
	private final RefuseButton refuseButton;
	private final ManageBackpack management;  
	private final MessageView messageView; 
	
	private final ImageLoader imageHero;
	private int refuseCount; 
	private final int imageSize = 200;
	
	private boolean choiceMade = false;  
	
	public CurseView(Hero hero, Item curse, ImageLoader imageHero, ImageLoader imagesItems, int refuseCount) {
		this.hero = hero; 
		this.backpackView = new BackpackView(hero.backpack(), imagesItems);
		this.curse = curse; 
		
		this.deleteView = new ItemDeleteView(imagesItems); 
		this.placementView = new ItemPlacementView(imagesItems, deleteView); 
		placementView.take(curse);
		
		this.refuseButton = new RefuseButton(); 
		this.management = new ManageBackpack(hero.backpack(), placementView, hero, null); 
		this.messageView = new MessageView(); 
		this.refuseCount = refuseCount; 
		this.imageHero = imageHero; 
	}
	public int refuseCount() {return refuseCount;}
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		int heroX = context.getScreenInfo().width() / 5;
		int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imageHero.drawImage(graphics, imageHero.get(0), heroX, heroY, imageSize, imageSize); // Hero
	}
	
	public boolean endPlacement() {
		return choiceMade; 
	}
	@Override
	public void draw(Graphics2D graphics, ApplicationContext context) {
		messageView.update(); 
		messageView.drawMessage(graphics, context);
		backpackView.drawBackpack(graphics, context);
		placementView.draw(graphics, context);
		deleteView.draw(graphics, context);
		refuseButton.draw(graphics, context);
		importHeroImage(graphics, context);   
	}
	
	@Override
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		
		if(refuseButton.click(x, y)) {
			refuseCount++; 
			hero.receiveDamage(refuseCount);
			choiceMade = true; 
			return; 
		}
		
		int[] cell = backpackView.choiceCase(x, y, context); 
		if(cell != null) {
			if (management.placeCurseInBackpack(cell[0], cell[1])){
				choiceMade = true; 
				placementView.clear();
			}
		}
	}
	
	
}
