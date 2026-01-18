package fr.uge.graphics.view;

import fr.uge.graphics.*;
import fr.uge.graphics.button.Button;
import fr.uge.graphics.button.DeleteButton;
import fr.uge.graphics.button.EndTurnButton;
import fr.uge.graphics.button.RotateButton;
import fr.uge.graphics.image.*;

import com.github.forax.zen.ApplicationContext;

import fr.uge.character.*;
import fr.uge.game.FightGraphics;
import fr.uge.game.ManageBackpack;
import fr.uge.inventory.*;
import fr.uge.map.Dungeon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;


public class BattleView implements View {
	
	private final MessageView messageView;
	private FightGraphics fightGraphics;
	private final ImageLoader imagesHero; 
	private final ImageLoader imagesEnemies; 
	private final ImageLoader imagesStatus;
	private final int imageSize = 200;
	private final Hero hero;
	private final Dungeon dungeon;
	private final Enemies enemies;
	private boolean readyToReturnToMap = false;
	private final BackpackView backpackView;
	private final HeroView heroView;
	private final ItemsView lootView; 
	private final ItemPlacementView placementView; 
	private final ItemDeleteView deleteView;
	private CurseView curseView; 
	private final ManageBackpack management; 
	private final Button mapButton; 
	private final RotateButton rotateButton;
	private final DeleteButton deleteButton;
	private final EndTurnButton endButton;
	private Enemy selectedEnemy;
	private final ImageLoader imagesItems; 
	private int refuseCount = 1; 
	
  /**
   * Creates the battle view with all components and data.
   *
   * @param hero the hero in the battle
   * @param dungeon the dungeon containing floors
   * @param lootItems items available as loot
   * @param enemies enemies in this battle
   * @param imagesHero images for the hero
   * @param imagesEnemies images for enemies
   * @param imagesItems images for items
   * @param imagesStatus images for status effects
   */
	public BattleView(Hero hero, Dungeon dungeon, Items lootItems, Enemies enemies, ImageLoader imagesHero, ImageLoader imagesEnemies, ImageLoader imagesItems, ImageLoader imagesStatus) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(dungeon);
		Objects.requireNonNull(enemies);
		Objects.requireNonNull(imagesHero);
		Objects.requireNonNull(imagesEnemies);
		Objects.requireNonNull(imagesStatus);
		Objects.requireNonNull(lootItems);
		Objects.requireNonNull(imagesItems);
		
		this.hero = hero;
		this.dungeon = dungeon;
		this.enemies = enemies;
		this.imagesHero = imagesHero;
		this.imagesEnemies = imagesEnemies;

		this.imagesStatus = imagesStatus;
		
		this.heroView = new HeroView(hero, imagesHero, imagesItems, imagesStatus);
		this.backpackView = new BackpackView(hero.backpack(), imagesItems); 
		this.lootView = new ItemsView(lootItems, imagesItems); 
		this.deleteView = new ItemDeleteView(imagesItems);
		this.placementView = new ItemPlacementView(imagesItems, deleteView);
		this.management = new ManageBackpack(hero.backpack(), placementView, null); 
		this.messageView = new MessageView(); 
		this.mapButton = new Button(300, 300, 200, 100, "MAP"); 
		this.rotateButton = new RotateButton(); 
		this.deleteButton = new DeleteButton();
		this.endButton = new EndTurnButton();
		this.imagesItems = imagesItems; 
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
	
	/**
	 * Sets the fight graphics.
	 *
	 * @param fight the fight graphics to use, cannot be null
	 */
	public void setFight(FightGraphics fight) {
		Objects.requireNonNull(fight);
    this.fightGraphics = fight;
    updateSelectedEnemy();
	}
	
	/**
	 * Returns the fight graphics.
	 *
	 * @return the current fight graphics
	 */
	public FightGraphics fight() {
    return fightGraphics;
	}

	private void background(Graphics2D graphics, ApplicationContext context) {
		graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());  
		imagesHero.drawImage(graphics, imagesHero.get(8 + dungeon.currentFloor()), 0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());
	}
	
	private void importEnemyDefense(Graphics2D graphics, ApplicationContext context, Enemy enemy, int statX, int statY) {
		var statusSize = 40;
		var defenseX = statX + graphics.getFontMetrics().stringWidth(enemy.HP() + " / " + enemy.HPMax() + " PV ") + 10;
		var defenseY = statY - statusSize + 10;
		imagesStatus.drawImage(graphics, imagesStatus.get(2), defenseX, defenseY, statusSize, statusSize);
    graphics.setFont(new Font("Arial", Font.BOLD, 20));
    String protectionText = String.valueOf(enemy.protection());
    int textWidth = graphics.getFontMetrics().stringWidth(protectionText);
    int textX = defenseX + (statusSize - textWidth) / 2;
    int textY = defenseY + statusSize - 15; 
    graphics.drawString(protectionText, textX, textY);
	}
	
	private void enemyStat(Graphics2D graphics, ApplicationContext context, Enemy enemy, int index, int spacing) {
		int statX = context.getScreenInfo().width() - (imageSize * 3 + 150) + index * spacing + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    String stat = enemy.HP() + " / " + enemy.HPMax() + " PV ";
    graphics.drawString(stat, statX, statY);
    if (enemy.protection() > 0) {
      importEnemyDefense(graphics, context, enemy, statX, statY);
    }
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
	
	private void importEnemyPoison(Graphics2D graphics, ApplicationContext context, Enemy enemy, int enemyX, int enemyY) {
		if (enemy.isPoisoned()) {
			var statusSize = 30;
			var statusX = enemyX + imageSize - statusSize - 5;
			var statusY = enemyY + 5;
			imagesStatus.drawImage(graphics, imagesStatus.get(0), statusX, statusY, statusSize, statusSize);
		}
	}
	
	private void importEnemyAction(Graphics2D graphics, Enemy enemy, int enemyX, int enemyY) {
		var action = enemy.getAction(fightGraphics.curseLaunched());
		var statusSize = 30;
		var statusX = enemyX + imageSize - statusSize - 5;
		var statusY = enemyY - 50;
		int imageIndex = 0;
		switch(action) {
			case 0 -> imageIndex = 1;
			case 1 -> imageIndex = 2;
			case 2 -> {
				imageIndex = 3;
				fightGraphics.setCurseLaunced(true);
			}
		}
		imagesStatus.drawImage(graphics, imagesStatus.get(imageIndex), statusX, statusY, statusSize, statusSize);
	}
	
	private int getEnemyImageIndex(Enemy enemy) {
		return switch(enemy.name()) {
			case "Rat-Wolf" -> 0;
			case "Small Rat-Wolf" -> 1;
			case "Frog Wizard" -> 2;
			case "Living Shadow" -> 3;
			case "Queen Bee" -> 4;
			default -> -1;
		};
	}
	
	private void importEnemy(Graphics2D graphics, ApplicationContext context, List<Enemy> allEnemies, FightGraphics fight, int startX, int startY, int spacing) {
    for (int i = 0; i < allEnemies.size(); i++) {
      var enemy = allEnemies.get(i);
      if (enemy.HP() <= 0) continue;
      int enemyX = startX + i * spacing;
      int enemyY = startY;
      var imageIndex = getEnemyImageIndex(enemy);
      imagesEnemies.drawImage(graphics, imagesEnemies.get(imageIndex), enemyX, enemyY, imageSize, imageSize);
      enemyStat(graphics, context, enemy, i, spacing);
      if (enemy == selectedEnemy) {
      	int centerX = enemyX + imageSize / 2;
      	int centerY = enemyY + imageSize / 2;
      	drawTarget(graphics, centerX, centerY, imageSize);
      }
      importEnemyPoison(graphics, context, enemy, enemyX, enemyY);
      importEnemyAction(graphics, enemy, enemyX, enemyY);
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
		importEnemy(graphics, context, enemies.getEnemies(), fightGraphics, layout[0], layout[1], layout[2]);
	}
	 
	private void goldReward() {
		if (!fightGraphics.isLootPhase()) return;
		if(fightGraphics.goldRewardConsumed()) return; 
		var reward = fightGraphics.getGoldReward();
		var backpack = backpackView.backpack();
		var gold = backpack.findGoldItem();
		if (gold != null) {
			gold.addGold(reward);
		} else {
			var newGold = ItemRegistry.create(ItemRegistry.GOLD, reward); 
			placementView.take(newGold);
		}
		fightGraphics.consumeGoldReward();
	}
	
	private void manaStoneReward() {
		if(!fightGraphics.isLootPhase())return; 
		if(fightGraphics.manaRewardConsumed()) return; 
		if(placementView.hasItem())return; 
		var manaStone = ItemRegistry.create(ItemRegistry.MANA_STONE, 1); 
		placementView.take(manaStone);
		fightGraphics.consumeManaReward();
	}
	
	private void drawCursePhase(Graphics2D graphics, ApplicationContext context) {
		if(fightGraphics != null && fightGraphics.expectedCurse() != null && curseView == null) {
			if(!fightGraphics.curseShown()) {
				startCursePhase(fightGraphics.expectedCurse()); 
				fightGraphics.setCurseShown(true);
			}
			
		}
	}
	
	private void drawLootPhase(Graphics2D graphics, ApplicationContext context) {
		lootView.drawItems(graphics, context, false);
		placementView.draw(graphics, context);
		deleteView.draw(graphics, context);
		goldReward();
		manaStoneReward();
		mapButton.draw(graphics);
		rotateButton.draw(graphics, context);
		deleteButton.draw(graphics, context);
	}
	
	/**
	 * Draws the battle screen with all elements.
	 *
	 * @param graphics the graphics context used to draw
	 * @param context the application context with screen info
	 */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(graphics);
		Objects.requireNonNull(context);
		messageView.update(); 
		drawCursePhase(graphics, context);
		background(graphics, context);
		messageView.drawMessage(graphics, context); 
		if(curseView != null) {
			curseView.draw(graphics, context);
			return; 
		}
		backpackView.drawBackpack(graphics, context);
		heroView.heroStat(graphics, context);
		heroView.importHeroImage(graphics, context);  
		if(fightGraphics.isLootPhase()) {
			drawLootPhase(graphics, context);
		} else {
			if (!fightGraphics.isFightOver()) {
				endButton.draw(graphics, context);
			}
			importEnemiesImages(graphics, context); 
		}
	}

	private boolean ClickUnlockCase(Backpack backpack, int line, int column, Controller controller, ApplicationContext context) {
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
      	messageView.showMessage("Choose your loot !!", 3000);
      	fightGraphics.setLootPhase(true);
      }
      return true;
    }
    return false;
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
	
	private Item getClickedCurse(int line, int column) {
		Item item = hero.backpack().choice(line, column); 
		if(item != null && item.type().equals(ItemType.CURSE)) {
			return item; 
		}
		return null; 
	}
	
	private boolean applyCurseIfClicked(int line, int column) {
    Item curse = getClickedCurse(line, column);
    if(curse != null) {
    	hero.backpack().delete(curse);
    	if (refuseCount == 1) {
      	hero.receiveDamage(2); 
      	messageView.showMessage(2 + " damage from the curse !", 1500);    		
    	}
    	hero.addCursePenalty(); 
    	return true;
    }
    return false;
	}
	
	private void useItemOnEnemy(int x, int y, int line, int column, ApplicationContext context) {
    var item = fightGraphics.Hero().backpack().choice(line, column);
    var layout = getEnemiesLayout(context);
    var ClickedEnemy = getClickedEnemy(x, y, fightGraphics.Enemies().getEnemies(), layout[0], layout[1], layout[2]);
    if (ClickedEnemy != null) {
    	selectedEnemy = ClickedEnemy;
    }
    if (item != null && selectedEnemy != null) {
    	fightGraphics.heroUseItem(item, selectedEnemy); 
    	if (item.type() == ItemType.CONSUMABLE) {
    		hero.backpack().delete(item);
    	}
    	if (selectedEnemy.HP() <= 0) updateSelectedEnemy();
    }
	}
	
	private void ClickFight(int x, int y, Controller controller, ApplicationContext context) {
    var fight = fightGraphics;
    if (fight == null) return;   
    var backpack = backpackView.backpack(); 
    var start = backpackView.calculateGridStart(context.getScreenInfo().width(), context.getScreenInfo().height());
    int line = (y - start[1]) / backpackView.cellSize();
    int column = (x - start[0]) / backpackView.cellSize();
    if (ClickUnlockCase(backpack, line, column, controller, context)) {
    	return;
    }
    if (fight.isFightOver() || !fight.isHeroTurn()) {
    	return;
    }
    if (applyCurseIfClicked(line, column)) {
    	return;
    }
    useItemOnEnemy(x, y, line, column, context);
	}
	
	private boolean clickMapButton(int x, int y) {
		if(mapButton.click(x, y)) {
			messageView.showMessage("Returning to te map...", 2000);
			fightGraphics.consumeManaReward();
			fightGraphics.consumeGoldReward();
			if (placementView.hasItem() && (placementView.item().isGold() || placementView.item().isMana())) {
				placementView.clear();  
			}
			deleteView.removeGoldItems();
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
			if (item != null) {
				if (item.isMana()) {
					var manaStones = hero.backpack().findManaCount();
					if (manaStones >= hero.manaMax()) {
						messageView.showMessage("Maximum mana reached !", 1000);
						return;
					}
				}			
			}
			if (management.placeInBackpack(cell[0], cell[1])) {
				if (item.isMana()) {
					hero.addMana(item.manaGained());
				}
			}
		}
	}
	
	private void clickLootPhase(int x, int y, Controller controller, ApplicationContext context) {
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
	
	private void startCursePhase(Item curseItem) {
		if(curseItem == null || curseView != null)return; 
		curseView = new CurseView(hero, curseItem, fightGraphics, imagesHero, imagesItems, refuseCount); 
	}
	
	private boolean curseViewClick(int x, int y, Controller controller, ApplicationContext context) {
		if(curseView != null) {
			curseView.Click(x, y, controller, context);
			refuseCount = curseView.refuseCount(); 
			if(curseView.madeChoice()) {
				if(curseView.curseAccepted()) {
					fightGraphics.clearExpectedCurse(); 
					refuseCount = 1;
					fightGraphics.setCurseLaunced(false);
				}
				curseView = null; 
			}	 
			return true;
		}
		return false;
	}
	
	/**
	 * Process a mouse click in the battle view.
	 *
	 * @param x the x-coordinate of the click
	 * @param y the y-coordinate of the click
	 * @param controller the game controller handling the logic
	 * @param context the application context with screen and input info
	 */
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		Objects.requireNonNull(controller);
		Objects.requireNonNull(context);
		if (curseViewClick(x, y, controller, context)) {
			return;
		}
		if (!fightGraphics.isLootPhase() && fightGraphics.isHeroTurn()) {
			if (endButton.click(x, y)) {
				fightGraphics.endHeroTurn();
				return;
			}
		}
		if (backpackView.backpack().isUnlockMode()) {
			ClickFight(x, y, controller, context);
			return;
		}
		if(fightGraphics.isLootPhase() && !backpackView.backpack().isUnlockMode()) {
			clickLootPhase(x, y, controller, context); 
		} else {
			ClickFight(x, y, controller, context); 
		}
	}
}
