package pigGame;

import java.util.HashMap;
import java.util.ArrayList;

public class Screen {

	public static final int SCREEN_UP = 1;
	public static final int SCREEN_RIGHT = 2;
	public static final int SCREEN_DOWN= -1;
	public static final int SCREEN_LEFT = -2;
	public static final int[] SCREEN_SIDES = {SCREEN_UP, SCREEN_RIGHT, SCREEN_DOWN, SCREEN_LEFT};
	
	private HashMap<Integer, ScreenSideWrapper> adjacentScreens = new HashMap<Integer, ScreenSideWrapper>();

	private ArrayList<Barrier> barriers;
	
	private int width;
	private int height;
	
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		
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

	public void addBarrier(Barrier barrier)
	{
		barriers.add(barrier);
	}

	public void addBarriers(ArrayList<Barrier> barriers)
	{
		for(Barrier b : barriers)
		{
			addBarrier(b);
		}
	}

	public void setBarriers(ArrayList<Barrier> barriers)
	{
		this.barriers = barriers;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
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
