package my.game;//Lundina Darya
//18.05.18 (рисование)
//Jump&Bump

import java.awt.Graphics2D;
import java.util.ArrayList;

public interface Drawable {
	void draw(Graphics2D g);


	static ArrayList<Drawable> drawable = new ArrayList<Drawable>();
}