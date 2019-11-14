package pigGame;

import java.util.ArrayList;

import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameHandler implements KeyListener, Runnable {
    private ArrayList<Screen> screens;
    private ArrayList<Barrier> barriers;
    private Hero hero;
    private Target target;

    private GameWindow gameWindow;
    private JFrame window;

    public static void main(String[] args) {
        GameHandler handler = new GameHandler();
        handler.prepareSampleGame();

        SwingUtilities.invokeLater(handler);
    }

    public GameHandler() {
        gameWindow = new GameWindow(800, 600);
        screens = new ArrayList<Screen>();
        barriers = new ArrayList<Barrier>();
    }

    public void prepareSampleGame() {
        Screen screen1 = new Screen(120, 180, 10, 10);
        screens.add(screen1);
        Screen screen2 = new Screen(180, 120, 610, 470);
        screens.add(screen2);
        Screen.pairScreens(screen1, Screen.SCREEN_LEFT, screen2, Screen.SCREEN_DOWN);
        Screen.pairScreens(screen2, Screen.SCREEN_UP, screen1, Screen.SCREEN_RIGHT);
        hero = new Hero(new Position(50, 50, screen1), 5);
        target = new Target(new Position(50, 50, screen2), new Orientation(false, Direction.RIGHT));
    }


    // Runnable Override
    @Override
    public void run()
    {
        prepareWindow();
    }

    private void prepareWindow()
    {
        gameWindow.paint(screens, barriers, hero, target);
        window = new JFrame("Pig Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(this);
        window.getContentPane().add(gameWindow);
        window.pack();
        window.setVisible(true);
    }
    
    // KeyListener Overrides
    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                hero.move(Direction.DOWN);
                break;
            case KeyEvent.VK_UP:
                hero.move(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                hero.move(Direction.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                hero.move(Direction.LEFT);
                break;
        }

        gameWindow.paint(screens, barriers, hero, target);
        System.out.println(target.isHeroInWinningPosition(hero));
    }

    @Override
	public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}