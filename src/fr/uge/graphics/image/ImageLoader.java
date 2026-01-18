package fr.uge.graphics.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.imageio.ImageIO;

/**
 * Loads and saves images from a directory for drawing.
 */
public class ImageLoader {
	/**
	 * Array of loaded images.
	 */
	private final BufferedImage[] images;
	
  /**
   * Loads images from the given directory.
   *
   * @param dir the folder containing images
   * @param pics names of image files to load
   */
	public ImageLoader(String dir, String... pics) {
		Objects.requireNonNull(dir);
		Objects.requireNonNull(pics, "pics can't be null.");
		images = new BufferedImage[pics.length];
		for (var i = 0; i < pics.length; i++) {
			setImage(i, dir, pics[i]);
		}
	}
	
  /**
   * Loads a single image into the array.
   *
   * @param position index in the array
   * @param dirPath folder path
   * @param imagePath image file name
   */
	private void setImage(int position, String dirPath, String imagePath) {
		Objects.requireNonNull(dirPath, "dirPath can't be null.");
		Objects.requireNonNull(imagePath, "dirPath can't be null.");
		var path = Path.of(dirPath + "/" + imagePath);
    System.out.println("Trying to load image from: " + path.toAbsolutePath());
		try (var input = Files.newInputStream(path)) {
			images[position] = ImageIO.read(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
  /**
   * Draws an image scaled to fit inside a given rectangle.
   *
   * @param graphics graphics context
   * @param image the image to draw
   * @param x x-position
   * @param y y-position
   * @param dimX width of drawing area
   * @param dimY height of drawing area
   */
	public void drawImage(Graphics2D graphics, BufferedImage image, float x, float y, float dimX, float dimY) {
		var width = image.getWidth();
		var height = image.getHeight();
		var scale = Math.min(dimX / width, dimY / height);
		var transform = new AffineTransform(scale, 0, 0, scale, x + (dimX - scale * width) / 2,
				y + (dimY - scale * height) / 2);
		graphics.drawImage(image, transform, null);
	}

  /**
   * Gets an image at a specific index.
   *
   * @param i index of the image
   * @return the BufferedImage
   */
	public BufferedImage get(int i) {
		return images[i];
	}
}
