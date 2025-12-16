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
	private final List<Message> messages = new ArrayList<>();
	private Message currentMessage = null;
	private FightGraphics fight;
	private final ImageLoader imagesHero;
	private final ImageLoader imagesEnemies; 
	private final ImageLoader imagesItemsLoot;
	private final int imageSize = 200;
	private final Hero hero;
	private final Enemies enemies;
	private boolean readyToReturnToMap = false;
	private final BackpackView backpackView; 
	private ItemsView lootView; 

	
	public BattleView(Hero hero, Items lootItems, Enemies enemies, ImageLoader imagesHero, ImageLoader imagesEnemies, ImageLoader imagesItems, ImageLoader imagesItemsLoot) {

		this.hero = hero;
		this.enemies = enemies;
		this.imagesHero = imagesHero;
		this.imagesEnemies = imagesEnemies;
		this.imagesItemsLoot = imagesItemsLoot;
		
		this.backpackView = new BackpackView(hero.backpack(), imagesItemsLoot); 
		this.lootView = new ItemsView(lootItems, imagesItemsLoot); 
	}
	
	public void setReadyToReturnToMap(boolean ready) {
    readyToReturnToMap = ready;
	}

	public boolean isReadyToReturnToMap() {
    return readyToReturnToMap;
	}
	
	public List<Message> Messages() { return messages; }
	public void setFight(FightGraphics fight) {
    this.fight = fight;
	}

	public FightGraphics fight() {
    return fight;
	}

	
	public void showMessage(String msg, int durationMs) {
    var end = Instant.now().plusMillis(durationMs);
    messages.add(new Message(msg, end));
	}
	
	public boolean isMessageActive() {
    return currentMessage != null && Instant.now().isBefore(currentMessage.endTime());
	}	

	public void updateMessageState() {
    if (currentMessage != null && Instant.now().isAfter(currentMessage.endTime())) {
        currentMessage = null;
    }
    if (currentMessage == null && !messages.isEmpty()) {
        currentMessage = messages.remove(0);
    }
	}
	
	private void drawFightMessage(Graphics2D graphics, ApplicationContext context) {
    if (isMessageActive()) {
      graphics.setColor(Color.WHITE);
      graphics.setFont(new Font("Arial", Font.BOLD, 40));
      var fm = graphics.getFontMetrics();
      int x = (context.getScreenInfo().width() - fm.stringWidth(currentMessage.text())) / 2;
      int y = context.getScreenInfo().height() / 10;
      graphics.drawString(currentMessage.text(), x, y);
    } 
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
		updateMessageState(); 
		background(graphics, context.getScreenInfo());
		drawFightMessage(graphics, context);
		backpackView.draw(graphics, context);
		heroStat(graphics, context);
		importHeroImage(graphics, context);   
		if(fight.isLootPhase()) {
			lootView.drawItems(graphics, context, false);
		}
		else {
			importEnemiesImages(graphics, context); 
		}
	}

	public void Click(int x, int y, Controller controller, ApplicationContext context) {
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
      	showMessage("Case unlocked ! (" + backpack.unlockCount() + " left)", 1000);
      }
      else showMessage(msg + " (" + backpack.unlockCount() + " left)", 1000);
      if (backpack.unlockCount() == 0) {
      	backpack.setUnlockMode(false);
      	showMessage("You have unlocked all your backpack slots!", 2000);
        showMessage("Returning to the map...", 4000);
        setReadyToReturnToMap(true); 
      }
    }
    if (fight.isFightOver() || !fight.isHeroTurn()) return;
    var item = fight.Hero().backpack().choice(line, column);
    if (item != null) fight.heroUseItem(item); 
	}
}
