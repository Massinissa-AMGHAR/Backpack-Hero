package fr.uge.graphics.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

import fr.uge.character.Hero;
import fr.uge.game.FightGraphics;
import fr.uge.game.ManageBackpack;
import fr.uge.graphics.Controller;
import fr.uge.graphics.button.RefuseButton;
import fr.uge.graphics.image.ImageLoader;
import fr.uge.inventory.Item;

public class CurseView implements View{
	private final Hero hero;
	private final BackpackView backpackView; 
	private final HeroView heroView;
	private final Item curse; 
	
	private final ItemPlacementView placementView; 
	private final ItemDeleteView deleteView;
	
	private final RefuseButton refuseButton;
	private final ManageBackpack management;  
	private final MessageView messageView; 
	
	private final ImageLoader imageHero;
	private int refuseCount; 
	
	private boolean curseAccepted = false;
	private boolean madeChoice = false; 
	private final FightGraphics fight;
	
	public CurseView(Hero hero, Item curse, FightGraphics fight, ImageLoader imageHero, ImageLoader imagesItems, int refuseCount) {
		this.hero = hero; 
		this.backpackView = new BackpackView(hero.backpack(), imagesItems);
		this.heroView = new HeroView(hero, imageHero, imagesItems, null);

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
	public Item getCurse() { return curse;}
	public boolean madeChoice() { return madeChoice; }
	
	private void drawRefuseCount(Graphics2D graphics, ApplicationContext context) {
		int[] start = backpackView.calculateGridStart(context.getScreenInfo().width(), context.getScreenInfo().height());
    int backpackWidth = hero.backpack().maxColumns() * backpackView.cellSize();
    int backpackHeight = hero.backpack().maxLines() * backpackView.cellSize();
    int rectX = start[0];
    int rectY = start[1] + backpackHeight + 10; 
    int rectHeight = 40;
    graphics.setColor(Color.RED);
    graphics.fillRect(rectX, rectY, backpackWidth, rectHeight);
    graphics.setColor(Color.WHITE);
    graphics.drawRect(rectX, rectY, backpackWidth, rectHeight);
    graphics.setFont(new Font("Arial", Font.BOLD, 18));
    String text = "Refuse count: " + refuseCount + " – Take " + refuseCount + " damage";
    graphics.drawString(text, rectX + 10, rectY + 26);
	}

	public void draw(Graphics2D graphics, ApplicationContext context) {
		messageView.update(); 
		messageView.drawMessage(graphics, context);
		backpackView.drawBackpack(graphics, context);
		drawRefuseCount(graphics, context);
		placementView.draw(graphics, context);
		deleteView.draw(graphics, context);
		refuseButton.draw(graphics, context);
		heroView.importHeroImage(graphics, context);   
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if(refuseButton.click(x, y)) {
			hero.receiveDamage(refuseCount);
			refuseCount++; 
			madeChoice = true; 
			return; 
		}
		int[] cell = backpackView.choiceCase(x, y, context); 
		if(cell != null) {
			if (management.placeCurseInBackpack(cell[0], cell[1])) {
				curseAccepted = true; 
				madeChoice = true; 
				placementView.clear();
			}
		}
	}
}
