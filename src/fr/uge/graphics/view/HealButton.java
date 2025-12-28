package fr.uge.graphics.view;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

import fr.uge.character.*;

public class HealButton {
	private final Button button = new Button(0, 0, 200, 100, "HEAL");
	public void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 225;
		int y = context.getScreenInfo().height() / 10 - 20;
		button.setPosition(x, y);
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
	public boolean click(int mouseX, int mouseY, ItemPlacementView placementView, Hero hero, Healer healer, MessageView message) {
		if (placementView.hasItem() && placementView.item().isGold() && button.click(mouseX, mouseY)) {
			var gold = placementView.item();
			if (hero.HP() >= hero.HPMAX()) {
				message.showMessage("Your life is already full !", 1000);
				return true;
			}
			if (gold.goldAmount() < healer.price()) {
				message.showMessage("Not enough gold !", 1000);
				return true;
			}
			hero.heal(healer.hp());
			gold.spendGold(healer.price());
			message.showMessage("Your life was restored !", 1500);
			if (gold.goldAmount() <= 0) placementView.clear();
			return true;
		}
		return false;
	}
	
}
