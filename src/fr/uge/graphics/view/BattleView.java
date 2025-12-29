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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class BattleView implements View {
	
	private final MessageView messageView;
	private FightGraphics fight;
	private final ImageLoader imagesHero;
	private final ImageLoader imagesEnemies; 
	private final int imageSize = 200;
	private final Hero hero;
	private final Enemies enemies;
	private boolean readyToReturnToMap = false;
	private final BackpackView backpackView; 
	private final ItemPlacementView placementView; 
	private final ItemDeleteView deleteView;
	private final ItemsView lootView; 
	private final ManageBackpack management; 
	private final Button mapButton; 
	private final RotateButton rotateButton;
	private final DeleteButton deleteButton;
	private final EndTurnButton endButton;
	private Enemy selectedEnemy;
	private boolean goldPlaced = false;
	
	public BattleView(Hero hero, Items lootItems, Enemies enemies, ImageLoader imagesHero, ImageLoader imagesEnemies, ImageLoader imagesItems) {

		this.hero = hero;
		this.enemies = enemies;
		this.imagesHero = imagesHero;
		this.imagesEnemies = imagesEnemies;
		
		this.backpackView = new BackpackView(hero.backpack(), imagesItems); 
		this.lootView = new ItemsView(lootItems, imagesItems); 
		this.deleteView = new ItemDeleteView(imagesItems);
		this.placementView = new ItemPlacementView(imagesItems, deleteView);
		this.management = new ManageBackpack(hero.backpack(), placementView, hero, null); 
		this.messageView = new MessageView(); 
		this.mapButton = new Button(300, 300, 200, 100, "MAP"); 
		this.rotateButton = new RotateButton(); 
		this.deleteButton = new DeleteButton();
		this.endButton = new EndTurnButton();
		
	}
	public MessageView messageView() {return messageView; }
	
	public void setReadyToReturnToMap(boolean ready) {
    readyToReturnToMap = ready;
	}
	
	public boolean isReadyToReturnToMap() { return readyToReturnToMap; }
	
	public void setFight(FightGraphics fight) {
    this.fight = fight;
    updateSelectedEnemy();
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
    heroEnergy(graphics, context);
    heroManaEnergy(graphics, context);
    heroLevel(graphics, context);
    heroGold(graphics, context);
    
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
	
	private void drawTarget(Graphics2D graphics, int x, int y, int size) {
		graphics.setColor(Color.RED);
		graphics.setStroke(new BasicStroke(3));
		int corner = 20;
		int topLeftX = x - size / 2;
		int topLeftY = y - size / 2;
		graphics.drawLine(topLeftX,  topLeftY, topLeftX + corner, topLeftY);
		graphics.drawLine(topLeftX, topLeftY, topLeftX, topLeftY + corner);
		
		graphics.drawLine(topLeftX + size, topLeftY, topLeftX + size - corner, topLeftY);
		graphics.drawLine(topLeftX + size, topLeftY, topLeftX + size, topLeftY + corner);
		
		graphics.drawLine(topLeftX, topLeftY + size, topLeftX + corner, topLeftY + size);
		graphics.drawLine(topLeftX, topLeftY + size, topLeftX, topLeftY + size - corner);
		
		graphics.drawLine(topLeftX + size, topLeftY + size, topLeftX + size - corner, topLeftY + size);
		graphics.drawLine(topLeftX + size, topLeftY + size, topLeftX + size, topLeftY + size - corner);
		graphics.setStroke(new BasicStroke(1));
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
      enemyStat(graphics, context, enemy, i, spacing);
      if (enemy == selectedEnemy) {
      	int centerX = enemyX + imageSize / 2;
      	int centerY = enemyY + imageSize / 2;
      	drawTarget(graphics, centerX, centerY, imageSize);
      }
    }
	}
	
	private int[] getEnemiesLayout(ApplicationContext context) {
		int enemyX = context.getScreenInfo().width() - imageSize * 3 - 150;
		int enemyY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		int spacing = imageSize + 30;
		return new int[] {enemyX, enemyY, spacing};
	}
	
	private void importEnemiesImages(Graphics2D graphics, ApplicationContext context) {
		var layout = getEnemiesLayout(context);
		importEnemy(graphics, context, enemies.getEnemies(), fight, layout[0], layout[1], layout[2]);
	}
	 
	private void goldReward() {
		if (goldPlaced || !fight.isLootPhase()) return;
		var reward = fight.getGoldReward();
		var backpack = backpackView.backpack();
		var gold = backpack.findGoldItem();
		if (gold != null) gold.addGold(reward);
		else {
			var newGold = ItemRegistry.create(ItemRegistry.GOLD, reward); 
			placementView.take(newGold);
		}
		goldPlaced = true;
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
			deleteView.draw(graphics, context);
			goldReward();
			mapButton.draw(graphics);
			rotateButton.draw(graphics, context);
			deleteButton.draw(graphics, context);
		}
		else {
			if (!fight.isFightOver()) endButton.draw(graphics, context);
			importEnemiesImages(graphics, context); 
			
		}
	}

	private void ClickUnlockCase(Backpack backpack, int line, int column, Controller controller, ApplicationContext context) {
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
      	messageView.showMessage("All backpack slots unlocked!", 2000);
      	messageView.showMessage("Choose your loot !!", 3500);
      	fight.setLootPhase(true);
      }
    }
	}
	
	private void updateSelectedEnemy() {
		selectedEnemy = null;
		for (var enemy : enemies.getEnemies()) {
			if (enemy.HP() > 0) {
				selectedEnemy = enemy;
				break;
			}
		}
	}
	
	private Enemy getClickedEnemy(int x, int y, List<Enemy> allEnemies, int startX, int startY, int spacing) {
		for (var i = 0; i < allEnemies.size(); i++) {
			var enemy = allEnemies.get(i);
			if (enemy.HP() <= 0) continue;
			var enemyX = startX + i * spacing;
			var enemyY = startY;
			if (x >= enemyX && x <= enemyX + imageSize && y >= enemyY && y <= enemyY + imageSize) return enemy;
		}
		return null;
	}
	
	private void ClickFight(int x, int y, Controller controller, ApplicationContext context) {
    var fight = controller.fightGraphics();
    if (fight == null) return;       
    var backpack = backpackView.backpack(); 
    var start = backpackView.calculateGridStart(context.getScreenInfo().width(), context.getScreenInfo().height());
    int line = (y - start[1]) / backpackView.cellSize();
    int column = (x - start[0]) / backpackView.cellSize();
    ClickUnlockCase(backpack, line, column, controller, context); 
    if (fight.isFightOver() || !fight.isHeroTurn()) return;
    var item = fight.Hero().backpack().choice(line, column);
    var layout = getEnemiesLayout(context);
    var ClickedEnemy = getClickedEnemy(x, y, fight.Enemies().getEnemies(), layout[0], layout[1], layout[2]);
    if (ClickedEnemy != null) selectedEnemy = ClickedEnemy;
    if (item != null && selectedEnemy != null) {
    	fight.heroUseItem(item, selectedEnemy); 
    	if (selectedEnemy.HP() <= 0) updateSelectedEnemy();
    }
	}
	
	private void clickLootPhase(int x, int y, Controller controller, ApplicationContext context) {
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
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		if (!fight.isLootPhase() && fight.isHeroTurn()) {
			if (endButton.click(x, y)) {
				fight.endHeroTurn();
				return;
			}
		}
		if (backpackView.backpack().isUnlockMode()) {
			ClickFight(x, y, controller, context);
			return;
		}
		if(fight.isLootPhase() && !backpackView.backpack().isUnlockMode()) clickLootPhase(x, y, controller, context); 
		else ClickFight(x, y, controller, context); 
	}
}
