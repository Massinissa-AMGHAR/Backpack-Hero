package fr.uge.graphics.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.imageio.ImageIO;

public class ImageLoader {
	private final BufferedImage[] images;
	
	public ImageLoader(String dir, String... pics) {
		Objects.requireNonNull(pics, "pics can't be null.");
		images = new BufferedImage[pics.length];
		for (var i = 0; i < pics.length; i++) {
			setImage(i, dir, pics[i]);
		}
	}
	
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
	
	public void drawImage(Graphics2D graphics, BufferedImage image, float x, float y, float dimX, float dimY) {
		var width = image.getWidth();
		var height = image.getHeight();
		var scale = Math.min(dimX / width, dimY / height);
		var transform = new AffineTransform(scale, 0, 0, scale, x + (dimX - scale * width) / 2,
				y + (dimY - scale * height) / 2);
		graphics.drawImage(image, transform, null);
	}

	public BufferedImage get(int i) {
		return images[i];
	}
}
