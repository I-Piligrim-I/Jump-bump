package my.game;//Lundina Darya

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public interface Drawable {
	void draw(Graphics2D g);

	List<Drawable> drawables = new ArrayList<>();
}