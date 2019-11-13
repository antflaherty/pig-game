package pigGame;

import java.awt.Color;

public class Barrier
{
	public static final Color COLOR = new Color(0x000000);

    private int height;
	private int width;
	
	private Position topLeftCorner;

    public Barrier(int width, int height, Position topLeftCorner)
    {
        this.height = height;
		this.width = width;
		this.topLeftCorner = topLeftCorner;
	}
	
	public Position getPosition()
	{
		return topLeftCorner;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
} 