package pigGame;

import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args)
	{
		GameHandler handler = new GameHandler();
		SwingUtilities.invokeLater(handler);
	}
}
