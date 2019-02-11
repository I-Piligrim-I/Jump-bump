package my.game;
//
//import javafx.application.*;
//import javafx.scene.media.AudioClip;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class JumpBump extends Hitable {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 600;
    public static final int MAIN_TIMER_DELAY = 15;
    public static final int MAIN_TIMER_INITIAL_DELAY = 0;
    static int Width;
    static int Height;

    public static void music() {
    }


    static class MainGamePanel extends JPanel {


        @Override
        protected void paintComponent(Graphics arg0) {
            super.paintComponent(arg0);
            Graphics2D g2d = (Graphics2D) arg0;
            Width = getWidth();
            Height = getHeight();
            for (int i = 0; i < Drawable.drawables.size(); i++) {
                Drawable.drawables.get(i).draw(g2d);
            }

        }
    }

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        props.load(new FileReader("game.properties"));

        List<Level> levels = SettingsLoader.loadLevels(props);
        List<NamedRabbit> namedRabbits = SettingsLoader.loadRabbits(props);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Jump~n~Bump");
            frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            frame.setResizable(false);

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JPanel mainGamePanel = new MainGamePanel();
            frame.add(mainGamePanel);
            mainGamePanel.setFocusable(true);
            frame.setVisible(true);

            Timer timer = new Timer(MAIN_TIMER_DELAY, arg0 -> {
                for (NamedRabbit namedRabbit : namedRabbits) {
                    namedRabbit.update();
                }
                frame.repaint();
            });

            Game game = new Game(levels, namedRabbits, s -> {
                timer.stop();
                Drawable.drawables.clear();
                for (Hitable hitable : Hitable.hitables) {
                    hitable.disable(true);
                }
                Drawable.drawables.add(new GameOverScreen(s));
            });

            mainGamePanel.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent arg0) {

                }

                @Override
                public void keyReleased(KeyEvent arg0) {
                    for (NamedRabbit namedRabbit : game.getRabbits()) {
                        namedRabbit.keyreleased(arg0.getKeyCode());
                    }
                }

                @Override
                public void keyPressed(KeyEvent arg0) {
                    for (NamedRabbit namedRabbit : game.getRabbits()) {
                        namedRabbit.keypressed(arg0.getKeyCode());
                    }

                }
            });


            timer.setInitialDelay(MAIN_TIMER_INITIAL_DELAY);
            timer.start();
        });

    }

}