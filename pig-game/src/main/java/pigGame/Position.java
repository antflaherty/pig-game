package pigGame;

public class Position {

	private Screen screen;
	private int xPosition;
	private int yPosition;

	public Position(int xPosition, int yPosition, Screen screen)
	{	
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.screen = screen;
	}

	public int calculateNewPosition(int direction, Screen.ScreenSideWrapper nextScreenAndSide, int distance)
	{	
		Screen nextScreen = nextScreenAndSide.screen;
		int nextScreenSide = nextScreenAndSide.side;

		int nextXPosition = 0;
		int nextYPosition = 0;


		double lengthRatio = 0;

		switch(direction)
		{
		case Screen.SCREEN_LEFT :
			switch(nextScreenSide)
			{
			case Screen.SCREEN_UP :
				lengthRatio = (double) nextScreen.getWidth() / (double) screen.getHeight();
				nextXPosition = (int) (yPosition * lengthRatio);
				nextYPosition = distance - xPosition;
				break;
			case Screen.SCREEN_DOWN :
				lengthRatio = (double) nextScreen.getWidth() / (double) screen.getHeight();
				nextXPosition = nextScreen.getWidth() - (int) (yPosition * lengthRatio);
				nextYPosition = nextScreen.getHeight() - (distance - xPosition);
				break;
			case Screen.SCREEN_RIGHT :
				lengthRatio = (double) nextScreen.getHeight() / (double) screen.getHeight();
				nextXPosition = nextScreen.getWidth() - distance + xPosition;
				nextYPosition = (int) (yPosition * lengthRatio);
				break;
			case Screen.SCREEN_LEFT :
				lengthRatio = (double) nextScreen.getHeight() / (double) screen.getHeight();
				nextXPosition = distance - xPosition;
				nextYPosition = (int) ((screen.getHeight() - yPosition) * lengthRatio);
				break;
			}
			break;
		case Screen.SCREEN_RIGHT :
			switch(nextScreenSide)
			{
			case Screen.SCREEN_UP :
				lengthRatio = (double) nextScreen.getWidth() / (double) screen.getHeight();
				nextXPosition = nextScreen.getWidth() - (int) (yPosition * lengthRatio);
				nextYPosition = xPosition + distance - screen.getWidth();
				break;
			case Screen.SCREEN_DOWN :
				lengthRatio = (double) nextScreen.getWidth() / (double) screen.getHeight();
				nextXPosition = (int) (yPosition * lengthRatio);
				nextYPosition = nextScreen.getHeight() - (xPosition + distance - screen.getWidth());
				break;
			case Screen.SCREEN_RIGHT :
				lengthRatio = (double) nextScreen.getHeight() / (double) screen.getHeight();
				nextXPosition = nextScreen.getWidth() - (xPosition + distance - screen.getWidth());
				nextYPosition = (int) ((screen.getHeight() - yPosition) * lengthRatio);
				break;
			case Screen.SCREEN_LEFT :
				lengthRatio = (double) nextScreen.getHeight() / (double) screen.getHeight();
				nextXPosition = xPosition + distance - screen.getWidth();
				nextYPosition = (int) (yPosition * lengthRatio);
				break;
			}
			break;
		case Screen.SCREEN_UP :
			switch(nextScreenSide)
			{
			case Screen.SCREEN_DOWN :
				lengthRatio = (double) nextScreen.getWidth() / (double) screen.getWidth();
				nextXPosition = (int) (xPosition * lengthRatio);
				nextYPosition = nextScreen.getHeight() + (yPosition - distance);
				break;
			case Screen.SCREEN_UP :
				lengthRatio = (double) nextScreen.getWidth() / (double) screen.getWidth();
				nextXPosition = (int) ((screen.getWidth() - xPosition) * lengthRatio);
				nextYPosition = - yPosition + distance;
				break;
			case Screen.SCREEN_RIGHT :
				lengthRatio = (double) nextScreen.getHeight() / (double) screen.getWidth();
				nextXPosition = nextScreen.getWidth() + (yPosition - distance);
				nextYPosition = nextScreen.getHeight() - (int) (xPosition * lengthRatio);
				break;
			case Screen.SCREEN_LEFT :
				lengthRatio = (double) nextScreen.getHeight() / (double) screen.getWidth();
				nextXPosition = - yPosition + distance;
				nextYPosition = (int) (xPosition * lengthRatio);
				break;
			}
			break;
		case Screen.SCREEN_DOWN :
			switch(nextScreenSide)
			{
			case Screen.SCREEN_DOWN :
				lengthRatio = (double) nextScreen.getWidth() / (double) screen.getWidth();
				nextXPosition = (int) ((screen.getWidth() - xPosition) * lengthRatio);
				nextYPosition = nextScreen.getHeight() - (yPosition + distance - screen.getHeight());
				break;
			case Screen.SCREEN_UP :
				lengthRatio = (double) nextScreen.getWidth() / (double) screen.getWidth();
				nextXPosition = (int) (xPosition * lengthRatio);
				nextYPosition = yPosition + distance - screen.getHeight();
				break;
			case Screen.SCREEN_RIGHT :
				lengthRatio = (double) nextScreen.getHeight() / (double) screen.getWidth();
				nextXPosition = nextScreen.getWidth() - (yPosition + distance - screen.getHeight());
				nextYPosition = (int) (xPosition * lengthRatio);
				break;
			case Screen.SCREEN_LEFT :
				lengthRatio = (double) nextScreen.getHeight() / (double) screen.getWidth();
				nextXPosition = yPosition + distance - screen.getHeight();
				nextYPosition = nextScreen.getHeight() - (int) (xPosition * lengthRatio);
				break;
			}
			break;
		}

		screen = nextScreen;
		xPosition = nextXPosition;
		yPosition = nextYPosition;

		return nextScreenSide;
	}

	public void changePosition(int horizontal, int vertical)
	{
		xPosition += horizontal;
		yPosition += vertical;
	}

	public int getXPosition()
	{
		return xPosition;
	}

	public int getYPosition()
	{
		return yPosition;
	}

	public Screen getScreen()
	{
		return screen;
	}
}
