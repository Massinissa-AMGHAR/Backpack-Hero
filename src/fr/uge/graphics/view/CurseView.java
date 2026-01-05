package fr.uge.graphics.view;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

import fr.uge.character.Hero;
import fr.uge.graphics.app.Controller;
import fr.uge.graphics.app.FightGraphics;
import fr.uge.graphics.app.ManageBackpack;
import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;

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
	
	private boolean curseAccepted = false;
	private boolean madeChoice = false; 
	private final FightGraphics fight;
	
	public CurseView(Hero hero, Item curse, FightGraphics fight, ImageLoader imageHero, ImageLoader imagesItems, int refuseCount) {
		this.hero = hero; 
		this.backpackView = new BackpackView(hero.backpack(), imagesItems);
		this.curse = curse; 
		this.fight = fight;
		this.deleteView = new ItemDeleteView(imagesItems); 
		this.placementView = new ItemPlacementView(imagesItems, deleteView); 
		placementView.take(curse);
		
		this.refuseButton = new RefuseButton(); 
		this.management = new ManageBackpack(hero.backpack(), placementView, null); 
		this.messageView = new MessageView(); 
		this.refuseCount = refuseCount; 
		this.imageHero = imageHero; 
	}
	public boolean curseAccepted() { return curseAccepted; }
	public int refuseCount() { return refuseCount;}
	public void resetRefuseCount() { refuseCount = 0; }
	public Item getCurse() { return curse;}
	public boolean madeChoice() { return madeChoice; }
	
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
	int heroX = context.getScreenInfo().width() / 5;
	int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
	imageHero.drawImage(graphics, imageHero.get(0), heroX, heroY, imageSize, imageSize); 
	}
	
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		messageView.update(); 
		messageView.drawMessage(graphics, context);
		backpackView.drawBackpack(graphics, context);
		placementView.draw(graphics, context);
		deleteView.draw(graphics, context);
		refuseButton.draw(graphics, context);
		importHeroImage(graphics, context);   
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if(refuseButton.click(x, y)) {
			refuseCount++; 
			hero.receiveDamage(refuseCount);
			messageView.showMessage(refuseCount + " damage ! " + "It will be reproposed next turn.", 1000);
			//fight.setCurseLaunced(true);
			madeChoice = true; 
			return; 
		}
		int[] cell = backpackView.choiceCase(x, y, context); 
		if(cell != null) {
			if (management.placeCurseInBackpack(cell[0], cell[1])) {
				//fight.setCurseLaunced(false);
				curseAccepted = true; 
				madeChoice = true; 
				placementView.clear();
			}
		}
	}
}
