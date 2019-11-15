package pigGame;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

public class Target {

	private Position position;
	private Orientation orientation;
	private BufferedImage sprite;
	
	public Target(Position position, Orientation orientation)
	{
		this.position = position;
		this.orientation = orientation;
		importSprite();

		int rotationSide = Screen.SCREEN_UP;

		switch(orientation.getUpDirection())
		{
			case UP :
				rotationSide = Screen.SCREEN_DOWN;
				break;
			case RIGHT :
				rotationSide = Screen.SCREEN_LEFT;
				break;
			case LEFT:
				rotationSide = Screen.SCREEN_RIGHT;
				break;
			case DOWN :
				rotationSide = Screen.SCREEN_UP;
				break;
		}
		System.out.println(Orientation.computeOrientationChange(Screen.SCREEN_UP, rotationSide));
		transformSprite(Orientation.computeOrientationChange(Screen.SCREEN_UP, rotationSide));
	}

	private void importSprite()
	{
		try {
			sprite = ImageIO.read(getClass().getResource("/pig.png"));
		} catch (Exception e) {
			System.out.println("Error reading image file - " + e.toString());
		}

		AffineTransform transform = new AffineTransform();
		transform.scale(1.3,1.3);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    sprite = op.filter(sprite, null);
	}

	private void transformSprite(int transformCoefficient)
	{
		AffineTransform transform = new AffineTransform();
		transform.quadrantRotate(transformCoefficient, sprite.getWidth()/2, sprite.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    sprite = op.filter(sprite, null);
	}

	public Boolean isHeroInWinningPosition(Hero hero)
	{
		Boolean winningPosition = false;

		Position heroPosition = hero.getPosition();

		if(hero.getOrientation().isMirrored() == orientation.isMirrored()
			&& hero.getOrientation().getUpDirection() == orientation.getUpDirection())
		{
			if(heroPosition.getScreen() == position.getScreen())
			{
				int tolerance = hero.getMoveSpeed();

				if(Math.abs(heroPosition.getXPosition() - position.getXPosition()) <= tolerance
					&& Math.abs(heroPosition.getYPosition() - position.getYPosition()) <= tolerance)
				{
					winningPosition = true;
				}
			}
		}

		return winningPosition;
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
