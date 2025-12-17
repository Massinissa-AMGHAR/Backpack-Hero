package fr.uge.graphics.view;

import com.github.forax.zen.ApplicationContext;

import fr.uge.inventory.Item;

public interface ClickableItems {
	Item click(int mouseX, int mouseY, ApplicationContext context); 
}
