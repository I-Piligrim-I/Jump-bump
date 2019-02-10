package my.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Score implements Drawable {

	public Score () {
		Drawable.drawable.add(this);
	}

	public static final int SCORE_FONT_SIZE = 30;
	public Color SCORE_COLOR = new Color(212, 25, 69);

	@Override
	public void draw(Graphics2D g2d) {
		int j = 40;
		int i = 1;
		for (NamedRabbit r : JumpBump.l.rabbits) {
			g2d.setColor(SCORE_COLOR);
			g2d.setFont(new Font("PaladinPCRus Medium", 1, SCORE_FONT_SIZE));
			g2d.drawString("rabbit" +" " + r.getName() +" - "+ Integer.toString(r.score), JumpBump.Width /2, j);
			i = i + 1;
			j = j + 30;
		}

	}


}
