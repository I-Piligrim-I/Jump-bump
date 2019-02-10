package my.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackGround implements Drawable {
	static final Color backgroundColor = Color.BLUE;

	private BufferedImage backGround;

	public BackGround (String input) throws IOException {
		backGround = ImageIO.read(new File(input));
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(backGround, 0, 0, null);
	}

}
