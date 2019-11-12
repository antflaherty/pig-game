package piggyPuzzle;

import java.util.HashMap;

public class Orientation {
	
	private Boolean mirrored;
	private Direction upDirection;
	
	public Orientation() {
		this.mirrored = false;
		this.upDirection = Direction.UP;
	}
	
	public Orientation(Boolean mirrored, Direction upDirection)
	{
		this.mirrored = mirrored;
		this.upDirection = upDirection;
	}
	
	public void rotate(int numberOfQuaterTurns)
	{
		HashMap<Integer, Direction> numberToUpDirection = new HashMap<Integer, Direction>();
		numberToUpDirection.put(0, Direction.UP);
		numberToUpDirection.put(1, Direction.LEFT);
		numberToUpDirection.put(2, Direction.DOWN);
		numberToUpDirection.put(3, Direction.RIGHT);
		
		int currentDirectionNumber = 0;
		
		for(int i = 0; i < 4; i++)
		{
			if(numberToUpDirection.get(i) == upDirection)
			{
				currentDirectionNumber = i;
			}
		}
		
		int newDirectionNumber = (currentDirectionNumber + numberOfQuaterTurns) % 4;
		
		upDirection = numberToUpDirection.get(newDirectionNumber);
	}
	
	public static int computeOrientationChange(int screenSideFrom, int screenSideTo)
	{
		int orientationChangeCoefficient = 0;
		if(screenSideFrom == screenSideTo)
		{
			orientationChangeCoefficient = 2;
		}
		else if ((screenSideFrom == Screen.SCREEN_UP && screenSideTo == Screen.SCREEN_RIGHT)
				|| (screenSideFrom == Screen.SCREEN_DOWN && screenSideTo == Screen.SCREEN_LEFT)
				||(screenSideFrom == Screen.SCREEN_RIGHT && screenSideTo == Screen.SCREEN_DOWN)
				|| (screenSideFrom == Screen.SCREEN_LEFT && screenSideTo == Screen.SCREEN_UP))
		{
			orientationChangeCoefficient = 3;
		}

		else if ((screenSideFrom == Screen.SCREEN_RIGHT && screenSideTo == Screen.SCREEN_UP)
				|| (screenSideFrom == Screen.SCREEN_UP && screenSideTo == Screen.SCREEN_LEFT)
				||(screenSideFrom == Screen.SCREEN_LEFT && screenSideTo == Screen.SCREEN_DOWN)
				|| (screenSideFrom == Screen.SCREEN_DOWN && screenSideTo == Screen.SCREEN_RIGHT))
		{
			orientationChangeCoefficient = 1;
		}
		
		return orientationChangeCoefficient;
	}
	
	public void flip()
	{
		mirrored = !mirrored;
	}

	public Boolean isMirrored() {
		return mirrored;
	}

	public Direction getUpDirection() {
		return upDirection;
	}
	
}
