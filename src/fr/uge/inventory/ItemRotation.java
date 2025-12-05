package fr.uge.inventory;

public record ItemRotation(Item item) {
	
	private boolean[][] transpose() {
		boolean[][] shapeTranspose = new boolean[item.width()][item.height()]; 
		for(int i = 0; i < item.height(); i++) {
			for(int j = 0; j < item.width(); j++) {
				shapeTranspose[j][i] = item.shape()[i][j]; 
			}
		}
		return shapeTranspose; 
	}
	
	private boolean[] reverse(boolean[] line) {
		boolean[] reverse = new boolean[line.length]; 
		for(int i = 0; i < line.length; i++) {
			reverse[i] = line[line.length - 1 - i]; 
		}
		return reverse; 
	}
	
	public boolean[][] rotate() {
		boolean[][] newShape = transpose(); 
		for(int i = 0; i < newShape.length; i++) {
			newShape[i] = reverse(newShape[i]); 
		}
		return newShape;  
	}

}
