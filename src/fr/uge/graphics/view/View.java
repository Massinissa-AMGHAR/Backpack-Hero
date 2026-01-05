package fr.uge.graphics.view;

import java.awt.Graphics2D;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.*;

public interface View {
	void draw(Graphics2D graphics, ApplicationContext context);
	void Click(int x, int y, Controller controller, ApplicationContext context);
}
