package my.game;//Lundina Darya
//18.05.18 (блоки)
//Jump&Bump

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class Wall extends Hitable implements Drawable{

	public Wall(int X, int Y, int w, int h, String Wall_Sample) throws IOException {
		x = X;
		y = Y;
		width = w;
		height = h;
		image = ImageIO.read(new File(Wall_Sample));
		Drawable.drawable.add(this);
		Hitable.hitableObjects.add(this);
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, (int) x, (int) y, (int)x + width, (int) y  + height, 0, 0, width, 3 *  height, null);
	}
	
}
