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
import java.util.List;
import java.util.Properties;


public class JumpBump extends Hitable {
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 600;
	public static final int MAIN_TIMER_DELAY = 15;
	public static final int MAIN_TIMER_INITIAL_DELAY = 0;
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
			for (int i = 0; i < Drawable.drawable.size(); i++) {
				Drawable.drawable.get(i).draw(g2d);
			}

		}
	}

	public static void main(String[] args) throws IOException {


		Properties props = new Properties();
		props.load(new FileReader("game.properties"));

		List<Level> levels = SettingsLoader.loadLevels(props);
		List<NamedRabbit> namedRabbits = SettingsLoader.loadRabbits(props);

		final Game game = new Game(levels, namedRabbits);


		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Jump~n~Bump");
			frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
			frame.setResizable(false);

			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			JPanel main_Game = new Main_Game_Pannel();
			frame.add(main_Game);
			main_Game.setFocusable(true);
//			window.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);

			main_Game.addKeyListener(new KeyListener() {

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
			Timer Main_Timer = new Timer(MAIN_TIMER_DELAY, arg0 -> {
				for (NamedRabbit namedRabbit : game.getRabbits()) {
					namedRabbit.update();
				}
				frame.repaint();
			});
			Main_Timer.setInitialDelay(MAIN_TIMER_INITIAL_DELAY);
			Main_Timer.start();
		});





	}

}