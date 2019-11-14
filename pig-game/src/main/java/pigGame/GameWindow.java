package pigGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWindow extends JPanel{

	public static final Color COLOR = new Color(0xDDDDDD);

	private int width;
	private int height;

	private ArrayList<Screen> screens;

	private ArrayList<Barrier> barriers = new ArrayList<Barrier>();

	private Hero hero;
	private ArrayList<Hero> ghosts = new ArrayList<Hero>();

	private Target target;

	public GameWindow(int width, int height)
	{
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));

		screens = new ArrayList<Screen>();
	}

	public void paint(ArrayList<Screen> screens, ArrayList<Barrier> barriers, Hero hero, Target target)
	{
		this.screens = screens;
		this.barriers = barriers;
		this.hero = hero;
		this.target = target;

		createGhosts();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(COLOR);

		g.fillRect(0, 0, width, height);

		drawScreens(g);

		if(target != null) drawTarget(g);
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
			g.fillRect(screen.getXPosition(), screen.getYPosition(), screen.getWidth(), screen.getHeight());

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
					int xPosition = barrier.getPosition().getXPosition();
					int yPosition = barrier.getPosition().getYPosition();

					int width = xPosition + barrier.getWidth() > screen.getWidth() ? screen.getWidth() - xPosition : barrier.getWidth();
					int height = yPosition + barrier.getHeight() > screen.getHeight() ? screen.getHeight() - yPosition : barrier.getHeight();

					g.setColor(Barrier.COLOR);
					g.fillRect(
						screen.getXPosition() + xPosition,
						screen.getYPosition() + yPosition,
						width,
						height
					);
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

			int xPosition = heroPosition.getXPosition() + screenContainingHero.getXPosition();
			int yPosition = heroPosition.getYPosition() + screenContainingHero.getYPosition();

			g.drawImage(sprite, xPosition - (heroToDraw.getSprite().getWidth()/2) + cropX, yPosition - (heroToDraw.getSprite().getHeight()/2) + cropY, null);
		}
	}

	private void drawTarget(Graphics g)
	{
		BufferedImage sprite = target.getSprite();

		int xPosition = target.getPosition().getScreen().getXPosition() + target.getPosition().getXPosition();
		int yPosition = target.getPosition().getScreen().getYPosition() + target.getPosition().getYPosition();

		g.drawImage(sprite, xPosition - sprite.getWidth()/2, yPosition - sprite.getHeight()/2, null);
	}
}
