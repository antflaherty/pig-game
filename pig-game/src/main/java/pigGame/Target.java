package pigGame;

import java.awt.image.*;
import javax.imageio.ImageIO;

public class Target {

	private Position position;
	private Orientation orientation;
	private BufferedImage sprite;
	
	public Target(Position position, Orientation orientation)
	{
		this.position = position;
		this.orientation = orientation;
		importSprite();
	}

	private void importSprite()
	{
		try {
			sprite = ImageIO.read(getClass().getResource("/pig.png"));
		} catch (Exception e) {
			System.out.println("Error reading image file - " + e.toString());
		}
	}

	public BufferedImage getSprite()
	{
		return sprite;
	}
	
	public Position getPosition()
	{
		return position;
	}

	public Orientation getOrientation() {
		return orientation;
	}
}
