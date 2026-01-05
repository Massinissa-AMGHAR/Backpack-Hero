package fr.uge.graphics.view;




import fr.uge.graphics.image.*;

import com.github.forax.zen.ApplicationContext;


import fr.uge.inventory.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class BackpackView implements ClickableItems{
	private final Backpack backpack; 
	private final ImageLoader imagesItems; 
	
	private final int cellSize = 75; 
	
	public BackpackView(Backpack backpack, ImageLoader imagesItems) {
		this.backpack = backpack; 
		this.imagesItems = imagesItems; 
	}
	public Backpack backpack() {
		return backpack; 
	}
	public int cellSize() {return cellSize;}
	
	public int[] calculateGridStart(int screenWidth, int screenHeight) {
		int mapWidth = backpack.maxColumns() * cellSize;
		int mapHeight = backpack.maxLines() * cellSize;
		int x = (screenWidth - mapWidth) / 2;
		int y = (screenHeight - mapHeight) / 4;
		return new int[] {x, y};
	}
	
	public int[] choiceCase(int mouseX, int mouseY, ApplicationContext context) {
		var start = calculateGridStart(context.getScreenInfo().width(), context.getScreenInfo().height());
		for(int i = 0; i < backpack.maxLines(); i++) {
			for(int j = 0; j < backpack.maxColumns(); j++) {
				int x = start[0] + j * cellSize; 
				int y = start[1] + i * cellSize; 
				if(mouseX >= x && mouseX <= x + cellSize && mouseY >= y && mouseY < y + cellSize) {
					return new int[] {i, j}; 
				}
			}
		}
		return null; 
	}
	
	private void drawRec(Graphics2D graphics, int x, int y, int cellSize, Color fillColor) {
    graphics.setColor(fillColor);
    graphics.fillRect(x, y, cellSize, cellSize);
    graphics.setColor(Color.WHITE);
    graphics.drawRect(x, y, cellSize, cellSize);
	}
	
	private boolean isItemStart(Item item, int line, int column, ArrayList<ArrayList<Item>> grid) {
		if (item == null) return false;
    boolean topFree = (line == 0 || grid.get(line - 1).get(column) != item);
    boolean leftFree = (column == 0 || grid.get(line).get(column - 1) != item);
    return topFree && leftFree;
	}
	
	private void drawItemAtPosition(Graphics2D graphics, Item item, int startX, int startY, int i, int j) {
		int x = startX + j * cellSize;
		int y = startY + i * cellSize;
		int width = item.width() * cellSize;
		int height = item.height() * cellSize;
		int angle = item.graphicRotation() % 360; 
		if(angle == 90 || angle == 270) {
			int tmp = width; 
			width = height; 
			height = tmp; 
			x = x - (width - height)/2; 
			y = y - (height - width)/2; 
		}
		Graphics2D g2 = (Graphics2D) graphics.create(); 
		int cx = x + width/2; 
		int cy = y + height/2; 
		g2.rotate(Math.toRadians(angle), cx, cy);
		imagesItems.drawImage(g2, imagesItems.get(item.imageIndex()), x, y, width, height);
		g2.dispose();
	}
	
	private void importImageItemOnBackPack(Graphics2D graphics, int startX, int startY) {
		var grid = backpack.grid();
		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(i).size(); j++) {
				var item = grid.get(i).get(j);
				if (isItemStart(item, i, j, grid)) { 
					drawItemAtPosition(graphics, item, startX, startY, i, j);
				}
			}
		}
	}
	
	public void drawBackpack(Graphics2D graphics, ApplicationContext context) {
		int cols = backpack.maxColumns(); 
		int rows = backpack.maxLines(); 
	  var start = calculateGridStart(context.getScreenInfo().width(), context.getScreenInfo().height());
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int x = start[0] + j * cellSize;
				int y = start[1] + i * cellSize;
				Color color;
				if (backpack.unlocked()[i][j]) color = Color.GRAY;
				else color = Color.LIGHT_GRAY;
				drawRec(graphics, x, y, cellSize, color);
			}
		}
		importImageItemOnBackPack(graphics, start[0], start[1]);
	}
	
	public Item click(int mouseX, int mouseY, ApplicationContext context) {
		var start = calculateGridStart(context.getScreenInfo().width(), context.getScreenInfo().height());
		for(int i = 0; i < backpack.maxLines(); i++) {
			for(int j = 0; j < backpack.maxColumns(); j++) {
				int x = start[0] + j * cellSize; 
				int y = start[1] + i * cellSize; 
				if(mouseX >= x && mouseX <= x + cellSize && mouseY >= y && mouseY <= y + cellSize) {
					return backpack.grid().get(i).get(j);
				}
			}
		}
		return null; 
	}
	
}
