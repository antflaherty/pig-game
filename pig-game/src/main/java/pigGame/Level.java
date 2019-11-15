package pigGame;

import java.util.ArrayList;
import java.util.HashMap;

public class Level
{
    public ArrayList<Screen> screens;
    public ArrayList<Barrier> barriers;
    public Hero hero;
    public Target target;
    

    public static void main(String[] args)
    {
        Level level = generateRandomLevel(3);
    }

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

        Screen.pairScreens(screen1, Screen.SCREEN_DOWN, screen4, Screen.SCREEN_LEFT);
        Screen.pairScreens(screen4, Screen.SCREEN_RIGHT, screen7, Screen.SCREEN_RIGHT);
        Screen.pairScreens(screen7, Screen.SCREEN_LEFT, screen2, Screen.SCREEN_RIGHT);
        Screen.pairScreens(screen2, Screen.SCREEN_LEFT, screen5, Screen.SCREEN_UP);
        Screen.pairScreens(screen5, Screen.SCREEN_DOWN, screen1, Screen.SCREEN_UP);

        
        Screen.pairScreens(screen2, Screen.SCREEN_UP, screen5, Screen.SCREEN_RIGHT);
        Screen.pairScreens(screen5, Screen.SCREEN_LEFT, screen6, Screen.SCREEN_DOWN);
        Screen.pairScreens(screen6, Screen.SCREEN_UP, screen7, Screen.SCREEN_UP);
        Screen.pairScreens(screen7, Screen.SCREEN_DOWN, screen4, Screen.SCREEN_DOWN);
        Screen.pairScreens(screen4, Screen.SCREEN_UP, screen3, Screen.SCREEN_RIGHT);
        Screen.pairScreens(screen3, Screen.SCREEN_LEFT, screen2, Screen.SCREEN_DOWN);
        
        
        testLevel.barriers = new ArrayList<Barrier>();
        
        testLevel.hero = new Hero(new Position(40, 40, screen1), 10);

        testLevel.target = new Target(new Position(30, 80, screen5), new Orientation(false, Direction.DOWN));

        return testLevel;
    }

    public static Level generateRandomLevel(int numberOfScreens)
    {
        
        ArrayList<Screen> screens = generateScreens(numberOfScreens);
        
        pairScreens(screens);
        
        ArrayList<Barrier> barriers = new ArrayList<>();
        
        Hero hero = new Hero(new Position(30, 30, screens.get(0)), 10);
        
        Level level = new Level();
        
        level.screens = screens;
        level.barriers = barriers;
        level.hero = hero;
        
        return level;
    }
    
    private static ArrayList<Screen> generateScreens(int numberOfScreens)
    {
        int screenSize = (int) (600 / (2 * (int) Math.round(Math.sqrt(numberOfScreens))));
        
        int maxXPosition = GameWindow.WIDTH - screenSize;
        int maxYPosition = GameWindow.HEIGHT - screenSize;
        
        ArrayList<Screen> screens = new ArrayList<>();
        
        for(int i = 0; i < numberOfScreens; i++)
        {
            Boolean screenOverlap = true;
            
            int xPosition;
            int yPosition;
            
            do
            {
                screenOverlap = false;
                
                xPosition = (int) (maxXPosition * Math.random());
                yPosition = (int) (maxYPosition * Math.random());
                
                for(Screen screen : screens)
                {
                    if(Math.abs(xPosition - screen.getXPosition()) <= screenSize
                    && Math.abs(yPosition - screen.getYPosition()) <= screenSize)
                    {
                        screenOverlap = true;
                    }
                }
            }
            while (screenOverlap);
            
            screens.add(new Screen(screenSize, screenSize, xPosition, yPosition));
            
        }
        
        return screens;
    }
    
    private static void pairScreens(ArrayList<Screen> screens)
    {
        HashMap<Integer, Integer> intToScreenSide = new HashMap<>();
        intToScreenSide.put(0, Screen.SCREEN_DOWN);
        intToScreenSide.put(1, Screen.SCREEN_UP);
        intToScreenSide.put(2, Screen.SCREEN_LEFT);
        intToScreenSide.put(3, Screen.SCREEN_RIGHT);
        
        ArrayList<int[]> unpairedSides = new ArrayList<>();

        for(int i = 0; i < screens.size(); i++)
        {
            unpairedSides.add(new int[]{i, Screen.SCREEN_DOWN});
            unpairedSides.add(new int[]{i, Screen.SCREEN_UP});
            unpairedSides.add(new int[]{i, Screen.SCREEN_LEFT});
            unpairedSides.add(new int[]{i, Screen.SCREEN_RIGHT});
        }

        ArrayList<int[]> pairedSides = new ArrayList<>();

        for (int i = 0; i < screens.size() * 4; i++)
        {
            int indexOfFirstSideToPair = (int) (Math.random() * unpairedSides.size());

            pairedSides.add(unpairedSides.get(indexOfFirstSideToPair));
            unpairedSides.remove(indexOfFirstSideToPair);
        }

        for (int i = 0; i < pairedSides.size() / 2; i++)
        {
            Screen firstScreen = screens.get(pairedSides.get(2 * i)[0]);
            int firstSide = pairedSides.get(2 * i)[1];
            Screen secondScreen = screens.get(pairedSides.get(2 * i+1)[0]);
            int secondSide = pairedSides.get(2 * i+1)[1];

            Screen.pairScreens(firstScreen, firstSide, secondScreen, secondSide);
        }
    }
}