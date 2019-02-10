package my.game;//Lundina Darya
//18.05.18 (���������)
//Jump&Bump

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public interface Drawable {
	void draw(Graphics2D g);

	List<Drawable> drawable = new ArrayList<>();
}