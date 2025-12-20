package fr.uge.graphics.view;

import fr.uge.graphics.app.*;


import fr.uge.graphics.image.*;

import com.github.forax.zen.ApplicationContext;

import com.github.forax.zen.ScreenInfo;

import fr.uge.character.*;
import fr.uge.inventory.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class BattleView implements View {
	
	private final MessageView messageView = new MessageView(); 
	private FightGraphics fight;
	private final ImageLoader imagesHero;
	private final ImageLoader imagesEnemies; 
	private final ImageLoader imagesItemsLoot;
	private final int imageSize = 200;
	private final Hero hero;
	private final Enemies enemies;
	private boolean readyToReturnToMap = false;
	private final BackpackView backpackView; 
	private final ItemPlacementView placementView; 
	private ItemsView lootView; 
	private final ManageBackpack management; 
	private final MessageView message; 
	private final Button mapButton; 
	private final RotateButton rotateButton;

	
	public BattleView(Hero hero, Items lootItems, Enemies enemies, ImageLoader imagesHero, ImageLoader imagesEnemies, ImageLoader imagesItems, ImageLoader imagesItemsLoot) {

		this.hero = hero;
		this.enemies = enemies;
		this.imagesHero = imagesHero;
		this.imagesEnemies = imagesEnemies;
		this.imagesItemsLoot = imagesItemsLoot;
		
		this.backpackView = new BackpackView(hero.backpack(), imagesItemsLoot); 
		this.lootView = new ItemsView(lootItems, imagesItemsLoot); 
		this.placementView = new ItemPlacementView(imagesItemsLoot); 
		this.management = new ManageBackpack(hero.backpack(), placementView, hero, null ); 
		this.message = new MessageView(); 
		this.mapButton = new Button(300, 300, 200, 100, "MAP"); 
		this.rotateButton = new RotateButton(); 
		
	}
	public MessageView messageView() {return messageView; }
	public void setReadyToReturnToMap(boolean ready) {
    readyToReturnToMap = ready;
	}

	public boolean isReadyToReturnToMap() {
    return readyToReturnToMap;
	}
	
	public void setFight(FightGraphics fight) {
    this.fight = fight;
	}

	public FightGraphics fight() {
    return fight;
	}

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
	
	private void heroEnergy(Graphics2D graphics, ApplicationContext context) {
		var energy = "Energy : " + hero.energy();
		var eX = context.getScreenInfo().width() / 8 - 175;
    var eY = context.getScreenInfo().height() / 6;
    graphics.drawString(energy, eX, eY);
	}
	
	private void heroLevel(Graphics2D graphics, ApplicationContext context) {
    var level = "LVL." + hero.level();
    var xp = hero.xp() + " / " + hero.nextLevelXp() + " XP"; 
    var levelX = context.getScreenInfo().width() / 8 - 175;
    var levelY = context.getScreenInfo().height() / 6 + 200;
    graphics.drawString(level, levelX, levelY);
    graphics.drawString(xp, levelX, levelY + 40);
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    heroPV(graphics, context);
    heroEnergy(graphics, context);
    heroLevel(graphics, context);
    
	}
	
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		int heroX = context.getScreenInfo().width() / 5;
		int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesHero.get(0), heroX, heroY, imageSize, imageSize); // Hero
	}
	
	private void enemyStat(Graphics2D graphics, ApplicationContext context, Enemy enemy, int index, int spacing) {
		int statX = context.getScreenInfo().width() - (imageSize * 3 + 150) + index * spacing + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    String stat = enemy.HP() + " / " + enemy.HPMax() + " PV ";
    graphics.drawString(stat, statX, statY);
	}
	
	private void importEnemy(Graphics2D graphics, ApplicationContext context, List<Enemy> allEnemies, FightGraphics fight, int startX, int startY, int spacing) {
    for (int i = 0; i < allEnemies.size(); i++) {
      var enemy = allEnemies.get(i);
      if (enemy.HP() <= 0) continue;
      int enemyX = startX + i * spacing;
      int enemyY = startY;
      var imageIndex = 0;
      if (enemy.name().equals("Little Rat-Wolf")) imageIndex = 1;
      if (enemy.name().equals("Rat-Wolf")) imageIndex = 0;
      imagesEnemies.drawImage(graphics, imagesEnemies.get(imageIndex), enemyX, enemyY, imageSize, imageSize);
      if (enemy == fight.currentEnemy()) {
        enemyStat(graphics, context, fight.currentEnemy(), i, spacing);
      } else enemyStat(graphics, context, enemy, i, spacing);
    }
	}
	
	private void importEnemiesImages(Graphics2D graphics, ApplicationContext context) {
		int enemyX = context.getScreenInfo().width() - imageSize * 3 - 150;
		int startY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		int spacing = imageSize + 30;
		importEnemy(graphics, context, enemies.getEnemies(), fight, enemyX, startY, spacing);
	}
	public void draw(Graphics2D graphics, ApplicationContext context) {
		messageView.update(); 
		background(graphics, context.getScreenInfo());
		messageView.drawMessage(graphics, context);
		backpackView.drawBackpack(graphics, context);
		heroStat(graphics, context);
		importHeroImage(graphics, context);   
		if(fight.isLootPhase()) {
			lootView.drawItems(graphics, context, false);
			placementView.draw(graphics, context);
			mapButton.draw(graphics);
			rotateButton.draw(graphics, context);
		}
		else {
			importEnemiesImages(graphics, context); 
		}
	}

	private void ClickFight(int x, int y, Controller controller, ApplicationContext context) {
    var fight = controller.fightGraphics();
    if (fight == null) return;       
    var backpack = backpackView.backpack(); 
    var start = backpackView.calculateGridStart(context.getScreenInfo().width(), context.getScreenInfo().height());
    int line = (y - start[1]) / backpackView.cellSize();
    int column = (x - start[0]) / backpackView.cellSize();
   
    if (backpack.isUnlockMode()) {
      IO.println("UnlockMode : " + backpack.isUnlockMode());
      var msg = backpack.tryUnlockCase(line, column);
      if (msg == null) {
      	backpack.useUnlockCase();
      	messageView.showMessage("Case unlocked ! (" + backpack.unlockCount() + " left)", 1000);
      }
      else messageView.showMessage(msg + " (" + backpack.unlockCount() + " left)", 1000);
      if (backpack.unlockCount() == 0) {
      	backpack.setUnlockMode(false);
      	messageView.showMessage("You have unlocked all your backpack slots!", 2000);
        messageView.showMessage("Returning to the map...", 4000);
        setReadyToReturnToMap(true); 
      }
    }
    if (fight.isFightOver() || !fight.isHeroTurn()) return;
    var item = fight.Hero().backpack().choice(line, column);
    if (item != null) fight.heroUseItem(item); 
	}
	private void clickLootPhase(int x, int y, Controller controller, ApplicationContext context) {
		if(mapButton.click(x, y)) {
			controller.changeView(controller.mapView());
			return; 
		}
		Item fromBackpack = backpackView.click(x, y, context); 
		if(management.clickFromBackpack(fromBackpack)) return;
		
		Item placedItem = placementView.item(); 
		if(rotateButton.click(x, y, placedItem)) {
			placedItem.VisualRotation();
			return; 
		}
		
		Item fromPlacement = placementView.click(x, y, context); 
		if(fromPlacement != null && !fromPlacement.isGold()) {
			placementView.clear();
			return; 
		}
		Item fromLoot = lootView.click(x, y, context); 
		if(fromLoot != null) {
			management.clickFromLoot(fromLoot, lootView.items()); 
			return; 
		}
		int[] cell = backpackView.choiceCase(x, y, context); 
		if(cell != null) management.placeInBackpack(cell[0], cell[1]); 
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if(fight.isLootPhase())clickLootPhase(x, y, controller, context); 
		else ClickFight(x, y, controller, context); 
	}
	
	
}
