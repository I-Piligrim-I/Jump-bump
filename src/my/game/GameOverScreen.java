package my.game;

import java.awt.*;

public class GameOverScreen implements Drawable {

    public Color BCKGRND_COLOR = new Color(20, 200, 159);
    public Color FRGRND_COLOR = new Color(0, 50, 50);

    private String text;

    public GameOverScreen(String s) {
        text = s;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(BCKGRND_COLOR);
        g2d.fillRect(0,0,1300,1000);
        g2d.setColor(FRGRND_COLOR);

        g2d.setFont(new Font("PaladinPCRus Medium", 1, 40));
        g2d.drawString("Game over!", 200, 100);
        g2d.drawString(text, 200, 200);
    }
}
