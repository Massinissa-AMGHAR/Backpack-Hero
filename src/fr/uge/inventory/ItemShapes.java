package fr.uge.inventory;

/**
 * Defines common item shapes for the game.
 */
public class ItemShapes {
	
	/** A vertical 3x1 shape. */
	public static final boolean[][] SIZE_3x1 = {
			{true}, 
			{true}, 
			{true}
	}; 
	
	/** A vertical 2x1 shape (2 rows, 1 column). */
	public static final boolean[][] SIZE_2x1 = {
			{true}, 
			{true}
	}; 
	
	/** A horizontal 1x3 shape (1 row, 3 columns). */
	public static final boolean[][] SIZE_1x3 = {
			{true, true, true}
	}; 
	
	/** A horizontal 1x2 shape (1 row, 2 columns). */
	public static final boolean[][] SIZE_1x2 = {
			{true, true}
	}; 
	
	/** A square 2x2 shape. */
	public static final boolean[][] SIZE_2x2 = {
			{true, true}, 
			{true, true}, 
	}; 
	
	/** A vertical 3x2 shape (3 rows, 2 columns). */
	public static final boolean[][] SIZE_3x2 = {
			{true, true}, 
			{true, true}, 
			{true, true}
	}; 
	
	/** A single 1x1 block. */
	public static final boolean[][] SIZE_1x1 = {
			{true}
	}; 
	
	/** Custom shape for curse items. */
	public static final boolean[][] CURSE = {
			{true, true, false}, 
			{false, true, true}
	}; 

}
