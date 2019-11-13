package pigGame;

import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Color;

public class Screen {
	public static final Color COLOR = new Color(0x777777);

	public static final int SCREEN_UP = 1;
	public static final int SCREEN_RIGHT = 2;
	public static final int SCREEN_DOWN= -1;
	public static final int SCREEN_LEFT = -2;
	public static final int[] SCREEN_SIDES = {SCREEN_UP, SCREEN_RIGHT, SCREEN_DOWN, SCREEN_LEFT};
	
	private HashMap<Integer, ScreenSideWrapper> adjacentScreens = new HashMap<Integer, ScreenSideWrapper>();
	
	private int width;
	private int height;
	private int xPosition;
	private int yPosition;
	
	public Screen(int width, int height, int xPosition, int yPosition)
	{
		this.width = width;
		this.height = height;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		
		pairScreens(this, Screen.SCREEN_UP, this, Screen.SCREEN_DOWN);
		pairScreens(this, Screen.SCREEN_LEFT, this, Screen.SCREEN_RIGHT);
	}
	
	public static void pairScreens(Screen firstScreen, int firstDirection, Screen secondScreen, int secondDirection)
	{
		firstScreen.setAdjacentScreen(firstDirection, secondScreen, secondDirection);
		secondScreen.setAdjacentScreen(secondDirection, firstScreen, firstDirection);
	}
	
	public void setAdjacentScreen(int direction, Screen adjacentScreen, int adjacentScreenDirection)
	{
		adjacentScreens.put(direction, new ScreenSideWrapper(adjacentScreen, adjacentScreenDirection));
	}
	
	public ScreenSideWrapper getAdjacentScreen(int direction)
	{
		return adjacentScreens.get(direction);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	public int getXPosition()
	{
		return xPosition;
	}

	public int getYPosition()
	{
		return yPosition;
	}
	
	class ScreenSideWrapper
	{
		public Screen screen;
		public int side;
		
		public ScreenSideWrapper(Screen screen, int side)
		{
			this.screen = screen;
			this.side = side;
		}
	}
}
