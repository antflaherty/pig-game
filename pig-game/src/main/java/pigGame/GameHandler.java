package pigGame;

import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameHandler implements KeyListener, Runnable
{
    private ArrayList<Screen> screens;
    private ArrayList<Barrier> barriers;
    private Hero hero;
    private Target target;
    
    private GameWindow gameWindow;
    private JFrame window;
    
    public static void main(String[] args)
    {
        GameHandler handler = new GameHandler();
        handler.run();
    }



    public GameHandler()
    {
        gameWindow = new GameWindow(500,500);
        screens = new ArrayList<Screen>();
        barriers = new ArrayList<Barrier>();
    }


    // Runnable Override
    @Override
    public void run()
    {
        prepareWindow();
    }

    private void prepareWindow()
    {
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
        
        gameWindow.repaint();
    }

    @Override
	public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}