package fr.uge.graphics.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.Message;

/**
 * Creates a view to show messages on the screen.
 * It stores all messages and the current message.
 */
public class MessageView {
	private final List<Message> messages = new ArrayList<>();
	private Message currentMessage;
	
	/**
	 * Adds a new message to show for a duration.
	 *
	 * @param msg the text of the message
	 * @param durationMs how long the message shows in milliseconds
	 */
	public void showMessage(String msg, int durationMs) {
		Objects.requireNonNull(msg); 
    var end = Instant.now().plusMillis(durationMs);
    messages.add(new Message(msg, end));
	}
	
	/**
	 * Updates the current message.
	 * It removes expired messages and shows the next message in the list.
	 */
	public void update() {
    if (currentMessage != null && Instant.now().isAfter(currentMessage.endTime())) {
      currentMessage = null;
    }
    if (currentMessage == null && !messages.isEmpty()) {
      currentMessage = messages.remove(0);
    }
	}
	
	/**
	 * Checks if there is a message currently active.
	 *
	 * @return true if a message is active, false otherwise
	 */
	public boolean isMessageActive() {
    return currentMessage != null && Instant.now().isBefore(currentMessage.endTime());
	}	
	
	/**
	 * Draws the current active message on the screen.
	 * The message is centered at the top of the screen.
	 *
	 * @param graphics the graphics used to draw
	 * @param context the application context with screen info
	 */
	public void drawMessage(Graphics2D graphics, ApplicationContext context) {
		Objects.requireNonNull(graphics); 
		Objects.requireNonNull(context);
    if (isMessageActive()) {
      graphics.setColor(Color.WHITE);
      graphics.setFont(new Font("Arial", Font.BOLD, 40));
      var fm = graphics.getFontMetrics();
      int x = (context.getScreenInfo().width() - fm.stringWidth(currentMessage.text())) / 2;
      int y = context.getScreenInfo().height() / 10;
      graphics.drawString(currentMessage.text(), x, y);
    } 
	}
}
