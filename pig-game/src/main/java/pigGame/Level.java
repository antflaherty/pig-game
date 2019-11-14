package pigGame;

import java.util.ArrayList;

public class Level
{
    public ArrayList<Screen> screens;
    public ArrayList<Barrier> barriers;
    public Hero hero;
    public Target target;

    public static Level getTestLevel()
    {
        Level testLevel = new Level();
        
        testLevel.screens = new ArrayList<Screen>();
        Screen screen1 = new Screen(120, 120, 10, 10);
        testLevel.screens.add(screen1);
        Screen screen2 = new Screen(120, 120, 120, 400);
        testLevel.screens.add(screen2);
        Screen screen3 = new Screen(120, 120, 270, 220);
        testLevel.screens.add(screen3);
        Screen screen4 = new Screen(120, 120, 450, 30);
        testLevel.screens.add(screen4);
        Screen screen5 = new Screen(120, 120, 460, 200);
        testLevel.screens.add(screen5);
        Screen screen6 = new Screen(120, 120, 600, 470);
        testLevel.screens.add(screen6);
        Screen screen7 = new Screen(120, 120, 650, 100);
        testLevel.screens.add(screen7);
        
        testLevel.barriers = new ArrayList<Barrier>();
        
        testLevel.hero = new Hero(new Position(40, 40, screen1), 10);

        testLevel.target = new Target(new Position(30, 80, screen5), new Orientation(false, Direction.DOWN));

        return testLevel;
    }
}