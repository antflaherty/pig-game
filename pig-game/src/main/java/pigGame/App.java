package pigGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {


	public static void main(String[] args)
	{
		final JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		final GameWindow gameWindow = new GameWindow(1500,700);

		final Screen screen1 = new Screen(120, 180);
		final Screen screen2 = new Screen(150, 150);
		final Screen screen3 = new Screen(135, 165);
		final Screen screen4 = new Screen(100, 150);
		
		gameWindow.addScreen(screen1, new GameWindow.ScreenLocation(10, 40));
//		gameWindow.addScreen(screen2, new GameWindow.ScreenLocation(150, 200));
//		gameWindow.addScreen(screen3, new GameWindow.ScreenLocation(600, 20));
//		gameWindow.addScreen(screen4, new GameWindow.ScreenLocation(400, 400));
//		
//		Screen.pairScreens(screen1, Screen.SCREEN_UP, screen2, Screen.SCREEN_DOWN);
//		Screen.pairScreens(screen2, Screen.SCREEN_UP, screen3, Screen.SCREEN_RIGHT);
//		Screen.pairScreens(screen3, Screen.SCREEN_LEFT, screen3, Screen.SCREEN_UP);
//		Screen.pairScreens(screen3, Screen.SCREEN_DOWN, screen1, Screen.SCREEN_RIGHT);
//		Screen.pairScreens(screen1, Screen.SCREEN_LEFT, screen1, Screen.SCREEN_DOWN);
//		
//		Screen.pairScreens(screen2, Screen.SCREEN_RIGHT, screen4, Screen.SCREEN_UP);
//		Screen.pairScreens(screen4, Screen.SCREEN_DOWN, screen2, Screen.SCREEN_LEFT);
//		
//		Screen.pairScreens(screen1, Screen.SCREEN_RIGHT, screen4, Screen.SCREEN_LEFT);
//		Screen.pairScreens(screen4, Screen.SCREEN_RIGHT, screen1, Screen.SCREEN_LEFT);
		

		
		final Hero piggy = new Hero(new Position(50, 50, screen1), 5);
		gameWindow.addHero(piggy);

		window.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					piggy.move(Direction.DOWN);
					gameWindow.repaint();
					break;
				case KeyEvent.VK_UP:
					piggy.move(Direction.UP);
					gameWindow.repaint();
					break;
				case KeyEvent.VK_RIGHT:
					piggy.move(Direction.RIGHT);
					gameWindow.repaint();
					break;
				case KeyEvent.VK_LEFT:
					piggy.move(Direction.LEFT);
					gameWindow.repaint();
					break;
//				case KeyEvent.VK_R:
//					piggy.changeOrientation(1);
//					gameWindow.repaint();
//					break;
				default :
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				window.getContentPane().add(gameWindow);
				window.pack();
				window.setVisible(true);
			}
		});

	}
}
