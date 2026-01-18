package fr.uge.inventory;

/**
 * Manages rotation logic for an item.
 * @param item the item that can be rotated
 */
public record ItemRotation(Item item) {
  /**
   * Returns the transposed shape of the item (rows become columns).
   *
   * @return the transposed boolean shape array
   */
	private boolean[][] transpose() {
		boolean[][] shapeTranspose = new boolean[item.width()][item.height()]; 
		for(int i = 0; i < item.height(); i++) {
			for(int j = 0; j < item.width(); j++) {
				shapeTranspose[j][i] = item.shape()[i][j]; 
			}
		}
		return shapeTranspose; 
	}
	
	/**
	 * Reverses a boolean array.
	 *
	 * @param line the array to reverse
	 * @return a new array with elements in reverse order
	 */
	private boolean[] reverse(boolean[] line) {
		boolean[] reverse = new boolean[line.length]; 
		for(int i = 0; i < line.length; i++) {
			reverse[i] = line[line.length - 1 - i]; 
		}
		return reverse; 
	}
	
	/**
	 * Rotates the item's shape by 90 degrees.
	 *
	 * @return the rotated shape as a new boolean array
	 */
	public boolean[][] rotate() {
		boolean[][] newShape = transpose(); 
		for(int i = 0; i < newShape.length; i++) {
			newShape[i] = reverse(newShape[i]); 
		}
		return newShape;  
	}

}
