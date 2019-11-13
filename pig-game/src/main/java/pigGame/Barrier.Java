package pigGame;

import java.awt.Color;

public class Barrier
{
	public static final Color COLOR = new Color(0x000000);

    private int height;
    private int width;

    public Barrier(int width, int height)
    {
        this.height = height;
        this.width = width;
    }
} 