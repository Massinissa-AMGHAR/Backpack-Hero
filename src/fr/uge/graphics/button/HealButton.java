package fr.uge.graphics.button;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

import fr.uge.character.*;
import fr.uge.graphics.view.ItemPlacementView;
import fr.uge.graphics.view.MessageView;

/**
 * Represents a "HEAL" button that allows the hero to spend gold to restore health.
 */
public class HealButton {
	private final Button button = new Button(0, 0, 200, 100, "HEAL");
	
	private void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 225;
		int y = context.getScreenInfo().height() / 10 - 20;
		button.setPosition(x, y);
	}
	
  /**
   * Draws the heal button on the screen.
   *
   * @param graphics the graphics context to draw on
   * @param context the application context for screen information
   */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
  /**
   * Checks if the heal button is clicked and restores hero HP if conditions are met.
   *
   * @param mouseX the x-coordinate of the click
   * @param mouseY the y-coordinate of the click
   * @param placementView the current item placement view
   * @param hero the hero to heal
   * @param healer the healer service
   * @param message the message view to show feedback
   * @return true if healing was applied or blocked by conditions
   */
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
