package my.game;
//
//import javafx.application.*;
//import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.im.InputMethodHighlight;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioPermission;
import javax.swing.*;



public class JumpBump extends Hitable {
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 600;
	public static final int MAIN_TIMER_DELAY = 15;
	public static final int MAIN_TIMER_INITIAL_DELAY = 0;
	static Level l;
	static BufferedImage Rabbit;
	static int Width;
	static int Height;

	public static void music(){}
	


	static class Main_Game_Pannel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics arg0) {
			super.paintComponent(arg0);
			Graphics2D g2d = (Graphics2D) arg0;
			Width = getWidth();
			Height = getHeight();
			int XX;
			int YY;
			for (int i = 0; i < Drawable.drawable.size(); i++) {
				Drawable.drawable.get(i).draw(g2d);
			}

		}
	}

	public static void main(String[] args) throws IOException {
		l = new Level("levelText.txt");

		SwingUtilities.invokeLater(() -> {
			JFrame window = new JFrame("Jump~n~Bump");
			window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
			window.setResizable(false);

			window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			JPanel main_Game = new Main_Game_Pannel();
			window.add(main_Game);
			main_Game.setFocusable(true);
//			window.setExtendedState(JFrame.MAXIMIZED_BOTH);
			window.setVisible(true);

			main_Game.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent arg0) {

				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					for (int i = 0; i < Level.Object_Array_Rabbit.size(); i++) {
						Level.Object_Array_Rabbit.get(i).keyreleased(arg0.getKeyCode());
					}

				}

				@Override
				public void keyPressed(KeyEvent arg0) {
					for (int i = 0; i < Level.Object_Array_Rabbit.size(); i++) {
						Level.Object_Array_Rabbit.get(i).keypressed(arg0.getKeyCode());
					}

				}
			});
			Timer Main_Timer = new Timer(MAIN_TIMER_DELAY, arg0 -> {
				for (int i = 0; i < Level.Object_Array_Rabbit.size(); i++) {
					Level.Object_Array_Rabbit.get(i).update();
				}
				window.repaint();
			});
			Main_Timer.setInitialDelay(MAIN_TIMER_INITIAL_DELAY);
			Main_Timer.start();
		});





	}

}