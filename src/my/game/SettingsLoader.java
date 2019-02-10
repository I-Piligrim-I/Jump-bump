package my.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.lang.String.format;

public class SettingsLoader {

    public static List<Level> loadLevels(Properties props) throws IOException {
        List<Level> levels = new ArrayList<>();
        int levelsCount = Integer.parseInt(props.getProperty("levels.count"));
        for (int i = 0; i < levelsCount; i++) {
            String prefix = format("level.%d.", i + 1);
            int maxScore = Integer.parseInt(props.getProperty(prefix + "maxScore"));
            String background = props.getProperty(prefix + "background");

            List<Wall> walls = loadWalls(props, prefix);
            Level level = new Level(maxScore, new BackGround(background), walls);
            levels.add(level);
            System.out.println("Level #" + (i + 1) + " loaded.");
        }

        return levels;
    }

    private static List<Wall> loadWalls(Properties props, String prefixLevel) throws IOException {
        List<Wall> walls = new ArrayList<>();
        int wallsCount = Integer.parseInt(props.getProperty(prefixLevel + "wallsCount"));
        for (int i = 0; i < wallsCount; i++) {
            String prefix = format("%swall.%d.", prefixLevel, i + 1);
            int x = Integer.parseInt(props.getProperty(prefix + "x"));
            int y = Integer.parseInt(props.getProperty(prefix + "y"));
            int width = Integer.parseInt(props.getProperty(prefix + "width"));
            int height = Integer.parseInt(props.getProperty(prefix + "height"));
            String texture = props.getProperty(prefix + "texture");
            walls.add(new Wall(x, y, width, height, texture));
        }
        return walls;
    }

    public static List<NamedRabbit> loadRabbits(Properties props) throws IOException {
        List<NamedRabbit> rabbits = new ArrayList<>();
        int rabbitsCount = Integer.parseInt(props.getProperty("rabbits.count"));

        for (int i = 0; i < rabbitsCount; i++) {
            String prefix = format("rabbit.%d.", i + 1);
            String name = props.getProperty(prefix + "name");
            int leftKey = Integer.parseInt(props.getProperty(prefix + "leftKey"));
            int upKey = Integer.parseInt(props.getProperty(prefix + "upKey"));
            int rightKey = Integer.parseInt(props.getProperty(prefix + "rightKey"));
            int startX = Integer.parseInt(props.getProperty(prefix + "startX"));
            String runImg = props.getProperty(prefix + "runImg");
            String jumpImg = props.getProperty(prefix + "jumpImg");

            rabbits.add(new NamedRabbit(leftKey, upKey, rightKey, startX, runImg, jumpImg, name));
        }
        return rabbits;
    }

}
