package pigGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWindow extends JPanel{

	public static final Color COLOR = new Color(0xDDDDDD);

	private int width;
	private int height;

	private ArrayList<Screen> screens;
	private HashMap<Screen, ScreenLocation> screenLocations;

	private ArrayList<Barrier> barriers;

	private Hero hero;
	private ArrayList<Hero> ghosts = new ArrayList<Hero>();

	public GameWindow(int width, int height)
	{
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));

		screens = new ArrayList<Screen>();
		screenLocations = new HashMap<Screen, ScreenLocation>();
	}

	public void addScreen(Screen screen, ScreenLocation location)
	{
		screens.add(screen);
		screenLocations.put(screen, location);
	}

	public void setHero(Hero hero)
	{
		this.hero = hero;
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

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(COLOR);

		g.fillRect(0, 0, width, height);

		createGhosts();

		drawScreens(g);
	}

	private void createGhosts()
	{
		
		
		
		ghosts.clear();


		if(hero.leftOverlap() > 0)
		{
			Hero ghost = hero.clone();

			Screen.ScreenSideWrapper screenAndSide = ghost.getPosition().getScreen().getAdjacentScreen(Screen.SCREEN_LEFT);

			ghost.changePosition(-ghost.getSprite().getWidth()/2, 0);

			adjustPosition(ghost, screenAndSide.side, ghost.getSprite().getWidth()/2);

			ghosts.add(ghost);
		}

		if(hero.rightOverlap() > 0)
		{
			Hero ghost = hero.clone();

			Screen.ScreenSideWrapper screenAndSide = ghost.getPosition().getScreen().getAdjacentScreen(Screen.SCREEN_RIGHT);

			ghost.changePosition(ghost.getSprite().getWidth()/2, 0);

			adjustPosition(ghost, screenAndSide.side, ghost.getSprite().getWidth()/2);

			ghosts.add(ghost);
		}
		
		if(hero.upOverlap() > 0)
		{
			Hero ghost = hero.clone();

			Screen.ScreenSideWrapper screenAndSide = ghost.getPosition().getScreen().getAdjacentScreen(Screen.SCREEN_UP);

			ghost.changePosition(0, -ghost.getSprite().getHeight()/2);

			adjustPosition(ghost, screenAndSide.side, ghost.getSprite().getHeight()/2);

			ghosts.add(ghost);
		}

		if(hero.downOverlap() > 0)
		{
			Hero ghost = hero.clone();

			Screen.ScreenSideWrapper screenAndSide = ghost.getPosition().getScreen().getAdjacentScreen(Screen.SCREEN_DOWN);

			ghost.changePosition(0, ghost.getSprite().getHeight()/2);

			adjustPosition(ghost, screenAndSide.side, ghost.getSprite().getHeight()/2);

			ghosts.add(ghost);
		}
	}

	private void adjustPosition(Hero ghost, int side, int distance)
	{
		switch(side)
		{
		case Screen.SCREEN_RIGHT : 
			ghost.getPosition().changePosition(distance, 0);
			break;
		case Screen.SCREEN_UP :
			ghost.getPosition().changePosition(0, -distance);
			break;
		case Screen.SCREEN_LEFT : 
			ghost.getPosition().changePosition(-distance, 0);
			break;
		case Screen.SCREEN_DOWN :
			ghost.getPosition().changePosition(0, distance);
			break;
		}
	}

	private void drawScreens(Graphics g)
	{
		g.setColor(Screen.COLOR);

		for(Screen screen : screens)
		{
			ScreenLocation location = screenLocations.get(screen);
			g.fillRect(location.xPosition, location.yPosition, screen.getWidth(), screen.getHeight());

			if(hero.getPosition().getScreen().equals(screen))
			{
				drawHero(g, screen, hero);
			}

			for(Hero ghost : ghosts)
			{
				if(ghost.getPosition().getScreen().equals(screen))
				{
					drawHero(g, screen, ghost);
				}
			}

			for(Barrier barrier : barriers)
			{
				if (barrier.getPosition().getScreen() == screen)
				{
					g.setColor(Barrier.COLOR);
					g.fillRect(location.xPosition + barrier.getPosition().getXPosition(), location.yPosition + barrier.getPosition().getYPosition(), barrier.getWidth(), barrier.getHeight());
				}
			}
		}
	}

	private void drawHero(Graphics g, Screen screenContainingHero, Hero heroToDraw)
	{
		Position heroPosition = heroToDraw.getPosition();

		int cropX = heroToDraw.leftOverlap() > 0 ? heroToDraw.leftOverlap() : 0;
		int cropY = heroToDraw.upOverlap() > 0 ? heroToDraw.upOverlap() : 0;
		int cropWidth = heroToDraw.getSprite().getWidth() - cropX - (heroToDraw.rightOverlap() > 0 ? heroToDraw.rightOverlap() : 0);
		int cropHeight = heroToDraw.getSprite().getHeight() - cropY - (heroToDraw.downOverlap() > 0 ? heroToDraw.downOverlap() : 0);

		if(cropWidth > 0 && cropHeight > 0)
		{
			BufferedImage sprite = heroToDraw.getSprite().getSubimage(cropX, cropY, cropWidth, cropHeight);

			int xPosition = heroPosition.getXPosition() + screenLocations.get(screenContainingHero).xPosition;
			int yPosition = heroPosition.getYPosition() + screenLocations.get(screenContainingHero).yPosition;

			g.drawImage(sprite, xPosition - (heroToDraw.getSprite().getWidth()/2) + cropX, yPosition - (heroToDraw.getSprite().getHeight()/2) + cropY, null);
		}
	}


	static class ScreenLocation
	{
		int xPosition;
		int yPosition;

		public ScreenLocation(int x, int y)
		{
			this.xPosition = x;
			this.yPosition = y;
		}
	}
}
