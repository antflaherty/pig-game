package pigGame;

import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class GameHandler implements KeyListener, Runnable {
    private Level level;
    private int difficulty;

    private GameWindow gameWindow;
    private JFrame window;

    public static void main(String[] args) {
        GameHandler handler = new GameHandler();
        
        SwingUtilities.invokeLater(handler);
    }
    
    public GameHandler() {
        difficulty = 1;
        gameWindow = new GameWindow();
        level = Level.generateRandomLevel(difficulty);
    }

    // Runnable Override
    @Override
    public void run()
    {
        prepareWindow();
    }

    private void prepareWindow()
    {
        gameWindow.paint(level);
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
                level.hero.move(Direction.DOWN);
                break;
            case KeyEvent.VK_UP:
                level.hero.move(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                level.hero.move(Direction.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                level.hero.move(Direction.LEFT);
                break;
        }

        gameWindow.paint(level);
        
        if(level.target != null && level.target.isHeroInWinningPosition(level.hero))
        {
            JOptionPane.showMessageDialog(window, "you win.");
            difficulty++;
            level = Level.generateRandomLevel(difficulty);
            gameWindow.paint(level);
        }
    }

    @Override
	public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}