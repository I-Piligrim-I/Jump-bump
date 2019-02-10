package my.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

public class Score implements Drawable {

	public static final int SCORE_FONT_SIZE = 30;
	public Color SCORE_COLOR = new Color(212, 25, 69);


	private List<NamedRabbit> rabbits;
    private int xCoord = 450;


    public Score(List<NamedRabbit> rabbits) {
        this.rabbits = rabbits;
    }

    @Override
	public void draw(Graphics2D g2d) {
		int j = 40;
		int i = 1;
		for (NamedRabbit r : rabbits) {
			g2d.setColor(SCORE_COLOR);
			g2d.setFont(new Font("PaladinPCRus Medium", 1, SCORE_FONT_SIZE));
			g2d.drawString("rabbit" +" " + r.getName() +" - "+ Integer.toString(r.score), xCoord, j);
			i = i + 1;
			j = j + 30;
		}

	}


}
