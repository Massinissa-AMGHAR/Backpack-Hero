package fr.uge.graphics.view;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

import com.github.forax.zen.ApplicationContext;

import fr.uge.character.Hero;
import fr.uge.graphics.Controller;
import fr.uge.graphics.button.Button;
import fr.uge.graphics.image.ImageLoader;

/**
 * Creates a victory view.
 * It shows the hero, messages, and buttons after clearing the dungeon.
 */
public class VictoryView implements View {
	private final Hero hero;
	private final MessageView message;
	private final ImageLoader images;
	private final int imageSize = 300;
	private Button quitButton;
	private Button playAgainButton;
	private boolean isQuit = false;
	private boolean isPlayAgain = false;

	/**
	 * Creates the victory view with hero and images.
	 * Shows congratulation messages.
	 *
	 * @param hero the hero
	 * @param images the images of the hero
	 */
	public VictoryView(Hero hero, ImageLoader images) {
		Objects.requireNonNull(hero); 
		Objects.requireNonNull(images);
		this.hero = hero;
		this.images = images;
		this.message = new MessageView();
		message.showMessage("You have cleared the dungeon !", 2000);
		message.showMessage("Congratulations !", 4000);
	}
	
	/**
	 * Checks if the quit button was pressed.
	 *
	 * @return true if quitting, false otherwise
	 */
	public boolean isQuit() { return isQuit; }
	
	/**
	 * Checks if the play again button was pressed.
	 *
	 * @return true if play again, false otherwise
	 */
	public boolean isPlayAgain() { return isPlayAgain; }
	
	/**
	 * Sets the play again status.
	 *
	 * @param bool true to play again, false otherwise
	 */
	public void setPlayAgain(boolean bool) { isPlayAgain = bool; }
	
	private void drawQuitButton(Graphics2D graphics, ApplicationContext context) {
    int buttonWidth = 200;
    int buttonHeight = 100;
    int x = (context.getScreenInfo().width() - buttonWidth) / 2 + 200;
    int y = context.getScreenInfo().height() - 200;
    this.quitButton = new Button(x, y, buttonWidth, buttonHeight, "QUIT");
    quitButton.draw(graphics);
	}

	private void drawPlayAgainButton(Graphics2D graphics, ApplicationContext context) {
    int buttonWidth = 200;
    int buttonHeight = 100;
    int x = (context.getScreenInfo().width() - buttonWidth) / 2 - 200;
    int y = context.getScreenInfo().height() - 200;
		this.playAgainButton = new Button(x, y, buttonWidth, buttonHeight, "PLAY AGAIN");
		playAgainButton.draw(graphics);
	}
	
	private void drawButton(Graphics2D graphics, ApplicationContext context) {
		drawQuitButton(graphics, context);
		drawPlayAgainButton(graphics, context);
	}
	
	private void background(Graphics2D graphics, ApplicationContext context) {
		images.drawImage(graphics, images.get(12), 0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());
	}
	
	private void drawHeroScore(Graphics2D graphics, ApplicationContext context) {
		var score = "Your score : " + hero.score();
		var sX = context.getScreenInfo().width() - 350;
    var sY = context.getScreenInfo().height() / 6;
    graphics.drawString(score, sX, sY);
	}
	
	private void drawText(Graphics2D graphics, ApplicationContext context) {
		graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 40));
    drawHeroScore(graphics, context);
	}
	
	private void importHeroImage(Graphics2D graphics, ApplicationContext context) {
		images.drawImage(graphics, images.get(13), context.getScreenInfo().width() / 2 - 150, context.getScreenInfo().height() / 4, imageSize, imageSize);
	}
	
	/**
	 * Draws the victory view on the screen.
	 * It shows background, hero, text, buttons, and messages.
	 *
	 * @param graphics the graphics used to draw
	 * @param context the application context with screen info
	 */
	public void draw(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(graphics); 
		Objects.requireNonNull(context);
		message.update(); 
		background(graphics, context);
		drawText(graphics, context);
		importHeroImage(graphics, context);
		drawButton(graphics, context);
		message.drawMessage(graphics, context); 

	}
	
	/**
	 * Processes a click in the victory view.
	 * It checks clicks on the quit and play again buttons.
	 *
	 * @param x the mouse X position
	 * @param y the mouse Y position
	 * @param controller the game controller
	 * @param context the application context with screen info
	 */
	public void Click(int x, int y, Controller controller, ApplicationContext context) {
		Objects.requireNonNull(controller); 
		Objects.requireNonNull(context);
		if (quitButton.click(x, y)) {
			isQuit = true;
		}
		if (playAgainButton.click(x, y)) {
			isPlayAgain = true;
		}
	}
}
