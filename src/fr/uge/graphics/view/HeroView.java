package fr.uge.graphics.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

import com.github.forax.zen.ApplicationContext;

import fr.uge.character.Hero;
import fr.uge.graphics.image.ImageLoader;

/**
 * Creates a view for the hero with images and backpack.
 *
 * @param hero the hero to show
 * @param imagesHero the images of the hero
 * @param imagesItems the images of items for the backpack
 * @param imagesStatus the images for hero status effects
 */
public class HeroView {
	private final Hero hero;
	private final ImageLoader imagesHero;
	private final BackpackView backpackView; 
	private final ImageLoader imagesStatus;
	private final int imageSize = 200;

	
	public HeroView(Hero hero, ImageLoader imagesHero, ImageLoader imagesItems, ImageLoader imagesStatus) {
		Objects.requireNonNull(imagesHero);
		Objects.requireNonNull(imagesItems);
		this.hero = hero;
		this.imagesHero = imagesHero;
		this.imagesStatus = imagesStatus;
		this.backpackView = new BackpackView(hero.backpack(), imagesItems); 

	}
		
	private void heroDefense(Graphics2D graphics, ApplicationContext context, int statX, int statY) {
		var oldFont = graphics.getFont();
		var oldColor = graphics.getColor();
    var statusSize = 40;
    String pvText = hero.HP() + " / " + hero.HPMAX() + " PV";
    int textWidth = graphics.getFontMetrics().stringWidth(pvText);
    int defenseX = statX + textWidth + 10;
    int defenseY = statY - statusSize + 10;
    imagesStatus.drawImage(graphics, imagesStatus.get(2), defenseX, defenseY, statusSize, statusSize);
    graphics.setFont(new Font("Arial", Font.BOLD, 20));
    String protectionText = String.valueOf(hero.protection());
    int textX = defenseX + (statusSize - graphics.getFontMetrics().stringWidth(protectionText)) / 2;
    int textY = defenseY + statusSize - 13;
    graphics.drawString(protectionText, textX, textY);
    graphics.setFont(oldFont);
    graphics.setColor(oldColor);
	}
	
	private void heroArmor(Graphics2D graphics, ApplicationContext context, int statX, int statY) {
    var oldFont = graphics.getFont();
    var oldColor = graphics.getColor();
    var statusSize = 30;
    String pvText = hero.HP() + " / " + hero.HPMAX() + " PV";
    int textWidth = graphics.getFontMetrics().stringWidth(pvText);
    int armorX = statX - textWidth + 100; 
    int armorY = statY - statusSize + 5;
    imagesStatus.drawImage(graphics, imagesStatus.get(4), armorX, armorY, statusSize, statusSize); 
    graphics.setFont(new Font("Arial", Font.BOLD, 20));
    String armorText = String.valueOf(hero.armor());
    int textX = armorX + (statusSize - graphics.getFontMetrics().stringWidth(armorText) + 4) / 2;
    int textY = armorY + statusSize - 8; 
    graphics.drawString(armorText, textX, textY);
    graphics.setFont(oldFont);
    graphics.setColor(oldColor);
	}

	
	public void heroPV(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(graphics);
		int statX = context.getScreenInfo().width() / 5 + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
		if (hero.protection() > 0) {
	    heroDefense(graphics, context, statX, statY);
		}
		if (hero.armor() > 0) {
			heroArmor(graphics, context, statX, statY);
		}
		var stat = hero.HP() + " / " + hero.HPMAX() + " PV";
		graphics.drawString(stat, statX, statY);
	}
	
	private void heroEnergy(Graphics2D graphics, ApplicationContext context) {
		var energy = "Energy : " + hero.energy();
		var eX = context.getScreenInfo().width() / 8 - 175;
    var eY = context.getScreenInfo().height() / 6;
    graphics.drawString(energy, eX, eY);
	}
	
	public void heroManaEnergy(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(graphics);
		var mana = "ManaEnergy : " + hero.mana();
		var mX = context.getScreenInfo().width() / 8 - 175;
    var mY = context.getScreenInfo().height() / 6 + 50;
    graphics.drawString(mana, mX, mY);
	}
	
	private void heroPenalty(Graphics2D graphics, ApplicationContext context) {
		var penalty = "Penalties : " + hero.cursePenaltyStacks();
		var mX = context.getScreenInfo().width() / 8 - 175;
    var mY = context.getScreenInfo().height() / 6 + 150;
    graphics.drawString(penalty, mX, mY);
	}
	
	public void heroGold(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(graphics);
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
	
	public void heroLevel(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(graphics);
    var level = "LVL." + hero.level();
    var xp = hero.xp() + " / " + hero.nextLevelXp() + " XP"; 
    var levelX = context.getScreenInfo().width() / 8 - 175;
    var levelY = context.getScreenInfo().height() / 6 + 300;
    graphics.drawString(level, levelX, levelY);
    graphics.drawString(xp, levelX, levelY + 40);
	}
	
	/**
	 * Draws all hero statistics on the screen.
	 * It sets the font and color, shows life, energy, mana, level,
	 * penalties, and gold, then restores old settings.
	 *
	 * @param graphics the graphics used to draw
	 * @param context the application context with screen info
	 */
	public void heroStat(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(graphics);
		var oldFont = graphics.getFont();
		var oldColor = graphics.getColor();
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    heroPV(graphics, context);
    heroEnergy(graphics, context);
    heroManaEnergy(graphics, context);
    heroLevel(graphics, context);
    heroPenalty(graphics, context); 
    heroGold(graphics, context);
    graphics.setFont(oldFont);
    graphics.setColor(oldColor);
	}
	
	/**
	 * Draws the hero image on the screen.
	 * The position is based on the screen size.
	 *
	 * @param graphics the graphics used to draw
	 * @param context the application context with screen info
	 */
	public void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(graphics);
		int heroX = context.getScreenInfo().width() / 5;
		int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesHero.get(0), heroX, heroY, imageSize, imageSize);
	}
	
}
