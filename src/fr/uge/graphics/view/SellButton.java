package fr.uge.graphics.view;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.app.ManageBackpack;
import fr.uge.inventory.Item;

public class SellButton {
	private final Button button = new Button(0, 0, 200, 100, "SELL"); 
	
	public void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 225;
		int y = context.getScreenInfo().height() / 10 - 20;
		button.setPosition(x, y);
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
	public boolean click(int mouseX, int mouseY, ItemPlacementView placementView, ManageBackpack management, MessageView message) {
		var item = placementView.item();
		if (item != null && !item.isGold() && button.click(mouseX, mouseY)) {
			if (management.sell()) message.showMessage("You sold " + item.name(), 1000);
			return true; 
		}
		return false;
	}
}
