package my.game;

import java.awt.*;
import java.io.IOException;

public class NamedRabbit extends Rabbit {

    private String name;
    private Font font = new Font("PaladinPCRus Medium", Font.BOLD, 15);

    public NamedRabbit(int _left, int _up, int _right, int X, String RunImage, String JumpImage, String rabbitName) throws IOException {
        super(_left, _up, _right, X, RunImage, JumpImage);
        this.name = rabbitName;
    }


    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setFont(font);
        g2d.setColor(new Color(212, 25, 69));
        g2d.drawString(name, (int) x, (int) (y + RABBIT_SIZE));
    }


    public String getName() {
        return name;
    }
}
