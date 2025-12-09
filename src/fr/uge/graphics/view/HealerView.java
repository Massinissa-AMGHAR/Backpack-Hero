package fr.uge.graphics.view;

import fr.uge.character.Hero;
import fr.uge.graphics.app.*;

import fr.uge.graphics.image.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.ScreenInfo;

public class HealerView implements View {
	private final Hero hero;
	private final ImageLoader imagesHero;
	private final ImageLoader imagesServices; 
	private final int imageSize = 200;
	
	
	public HealerView(Hero hero, ImageLoader imagesHero, ImageLoader imagesServices) {
		this.hero = hero;
		this.imagesHero = imagesHero;
		this.imagesServices = imagesServices;
	}
	
	private void background(Graphics2D graphics, ScreenInfo screenInfo) {
		graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, screenInfo.width(), screenInfo.height());  
	}
	
	private void title(Graphics2D graphics, ApplicationContext context, ScreenInfo screenInfo) {
    int titleX = screenInfo.width() / 2 - 250;
    int titleY = screenInfo.height() / 5 - 60;
    int titleWidth = 500;
    int titleHeight = 80;
    graphics.setColor(Color.LIGHT_GRAY);
    graphics.fillRect(titleX, titleY, titleWidth, titleHeight); 
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 50));
    var fontmetric1 = graphics.getFontMetrics();
    graphics.drawString("HEALER VIEW", (screenInfo.width() - fontmetric1.stringWidth("HEALER VIEW")) / 2, screenInfo.height()/5);
	}
	
	private void heroStat(Graphics2D graphics, ApplicationContext context) {
		int statX = context.getScreenInfo().width() / 5 + 40;
		int statY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 3 + 100;
    var stat = hero.HP() + " " + "/" + " " + hero.HPMAX() + " PV";
   // var eX = context.getScreenInfo().width() / 8 - 175;
   // var eY = context.getScreenInfo().height() / 6;
  //  var energy = "Energy : " + hero.energy();
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    graphics.drawString(stat, statX, statY);
  //  graphics.drawString(energy, eX, eY);
	}
	
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		int heroX = context.getScreenInfo().width() / 5;
		int heroY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesHero.get(0), heroX, heroY, imageSize, imageSize); 
	}
	
	private void importHealerImage(Graphics2D graphics, ApplicationContext context) {
		int MerchantX = context.getScreenInfo().width() - context.getScreenInfo().width() / 5 - imageSize;
		int MerchantY = context.getScreenInfo().height() / 2 + context.getScreenInfo().height() / 6;
		imagesHero.drawImage(graphics, imagesServices.get(1), MerchantX, MerchantY, imageSize, imageSize); 
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		background(graphics, context.getScreenInfo());
		title(graphics, context, context.getScreenInfo());
		heroStat(graphics, context);
		importHeroImage(graphics, context);
		importHealerImage(graphics, context);
	}
	
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		
	}
}
