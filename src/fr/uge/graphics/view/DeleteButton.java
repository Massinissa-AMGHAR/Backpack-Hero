package fr.uge.graphics.view;

import java.awt.Graphics2D;


import com.github.forax.zen.ApplicationContext;

import fr.uge.inventory.Item;

public class DeleteButton {
	private final Button button = new Button(0, 0, 200, 100, "DELETE");
	public void updateLayout(ApplicationContext context) {
		int x = context.getScreenInfo().width() - 225;
		int y = context.getScreenInfo().height() / 10 - 20;
		button.setPosition(x, y);
	}
	
	public void draw(Graphics2D graphics, ApplicationContext context) {
		updateLayout(context); 
		button.draw(graphics);
	}
	
	public boolean click(int mouseX, int mouseY, ItemPlacementView placementView, MessageView message) {
		if(placementView.hasItem() && !placementView.item().isGold() && button.click(mouseX, mouseY)) {
			if (placementView.deleteView().isFull()) {
				message.showMessage("Delete zone is full !", 1500);
				return true;
			}
			placementView.moveToDeleted();
			placementView.clear();
			return true; 
		}
		return false; 
	}
}
