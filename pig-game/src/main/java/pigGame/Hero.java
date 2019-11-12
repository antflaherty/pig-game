package pigGame;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hero implements Cloneable{
	
	private Position position;
	private Orientation orientation;
	private BufferedImage sprite;
	private int moveSpeed;
	
	public Hero(Position position, int moveSpeed)
	{
		this.position = position;
		this.moveSpeed = moveSpeed;
		this.orientation = new Orientation();
		importSprite();
	}
	
	public Hero(Position position, int moveSpeed, Orientation orientation, BufferedImage sprite)
	{
		this.position = position;
		this.moveSpeed = moveSpeed;
		this.orientation = orientation;
		this.sprite = sprite;
	}
	
	private void importSprite()
	{
		try {
			sprite = ImageIO.read(getClass().getResource("/pig.png"));
		} catch (Exception e) {
			System.out.println("Error reading image file - " + e.toString());
		}
	}
	
	public void move(Direction direction)
	{
		move(direction, moveSpeed);
	}
	
	public void move(Direction direction, int distance)
	{
		switch(direction)
		{
		case RIGHT :
			changePositionGivenOrientation(distance,0);
			break;
		case LEFT :
			changePositionGivenOrientation(-distance,0);
			break;
		case DOWN :
			changePositionGivenOrientation(0,distance);
			break;
		case UP :
			changePositionGivenOrientation(0,-distance);
			break;
		}
	}

	private void changePositionGivenOrientation(int x, int y)
	{

		if (orientation.isMirrored())
		{
			x = -x;
		}
		
		int newX = 0;
		int newY = 0;
		
		switch(orientation.getUpDirection())
		{
		case UP :
			newX = x;
			newY = y;
			break;
		case DOWN:
			newX = -x;
			newY = -y;
			break;
		case LEFT :
			newX = -y;
			newY = x;
			break;
		case RIGHT :
			newX = y;
			newY = -x;
		}
		
		changePosition(newX, newY);
	}

	public void changePosition(int horizontal, int vertical)
	{
		if(position.getXPosition() + horizontal > position.getScreen().getWidth()) {
			int nextScreenSide = position.calculateNewPosition(Screen.SCREEN_RIGHT, position.getScreen().getAdjacentScreen(Screen.SCREEN_RIGHT), horizontal);
			changeOrientation(Orientation.computeOrientationChange(Screen.SCREEN_RIGHT, nextScreenSide));
		}
		else if(position.getXPosition() + horizontal < 0) {
			int nextScreenSide = position.calculateNewPosition(Screen.SCREEN_LEFT, position.getScreen().getAdjacentScreen(Screen.SCREEN_LEFT), -horizontal);
			changeOrientation(Orientation.computeOrientationChange(Screen.SCREEN_LEFT, nextScreenSide));
		}
		else if(position.getYPosition() + vertical > position.getScreen().getHeight()) {
			int nextScreenSide = position.calculateNewPosition(Screen.SCREEN_DOWN, position.getScreen().getAdjacentScreen(Screen.SCREEN_DOWN), vertical);
			changeOrientation(Orientation.computeOrientationChange(Screen.SCREEN_DOWN, nextScreenSide));
		}
		else if(position.getYPosition() + vertical < 0) {
			int nextScreenSide = position.calculateNewPosition(Screen.SCREEN_UP, position.getScreen().getAdjacentScreen(Screen.SCREEN_UP), -vertical);
			changeOrientation(Orientation.computeOrientationChange(Screen.SCREEN_UP, nextScreenSide));
		}
		else {
			position.changePosition(horizontal, vertical);
		}
	}
	
	public void changeOrientation(int orientationTransformCoefficient)
	{
		rotate(Math.abs(orientationTransformCoefficient));
		if(orientationTransformCoefficient < 0) flip();
	}
	
	private void rotate(int numberOfQuarterTurns)
	{
		orientation.rotate(numberOfQuarterTurns);
		rotateSprite(numberOfQuarterTurns);
	}
	
	private void flip()
	{
		orientation.flip();
		flipSprite();
	}

	private void flipSprite()
	{
		AffineTransform transform = new AffineTransform();
		transform.scale(-1,1);
		transform.translate(-sprite.getHeight(),0);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    sprite = op.filter(sprite, null);
	}
	
	private void rotateSprite(int numberOfQuarterTurns)
	{
		AffineTransform transform = new AffineTransform();
		transform.quadrantRotate(numberOfQuarterTurns, sprite.getWidth()/2, sprite.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    sprite = op.filter(sprite, null);
	}
	
	public int leftOverlap()
	{
		return sprite.getWidth()/2 - position.getXPosition();
	}
	
	public int rightOverlap()
	{
		return sprite.getWidth()/2 + position.getXPosition() - position.getScreen().getWidth();
	}
	
	public int upOverlap()
	{
		return sprite.getHeight()/2 - position.getYPosition();
	}
	
	public int downOverlap()
	{
		return sprite.getHeight()/2 + position.getYPosition() - position.getScreen().getHeight();
	}
	
	public BufferedImage getSprite()
	{
		return sprite;
	}
	
	public Position getPosition()
	{
		return position;
	}
	
	public Orientation getOrientation()
	{
		return orientation;
	}
	
	public Hero clone()
	{
		try
		{
			BufferedImage sprite = copySprite();
			Orientation orientation = new Orientation(this.orientation.isMirrored(), this.orientation.getUpDirection());
			Position position =  new Position(this.position.getXPosition(), this.position.getYPosition(), this.position.getScreen());
			
			return new Hero(position, moveSpeed, orientation, sprite);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public BufferedImage copySprite(){
	    BufferedImage b = new BufferedImage(sprite.getWidth(), sprite.getHeight(), sprite.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(sprite, 0, 0, null);
	    g.dispose();
	    return b;
	}
}
