package fr.uge.graphics.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.github.forax.zen.ApplicationContext;

import fr.uge.graphics.Message;

public class MessageView {
	private final List<Message> messages = new ArrayList<>();
	private Message currentMessage;
	
	public void showMessage(String msg, int durationMs) {
    var end = Instant.now().plusMillis(durationMs);
    messages.add(new Message(msg, end));
	}
	
	public void update() {
    if (currentMessage != null && Instant.now().isAfter(currentMessage.endTime())) {
      currentMessage = null;
    }
    if (currentMessage == null && !messages.isEmpty()) {
      currentMessage = messages.remove(0);
    }
	}
	
	public boolean isMessageActive() {
    return currentMessage != null && Instant.now().isBefore(currentMessage.endTime());
	}	
	
	public void drawMessage(Graphics2D graphics, ApplicationContext context) {
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
