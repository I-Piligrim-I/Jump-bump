package my.game;

import java.util.List;

public class Game {

    private List<Level> levels;
    private List<NamedRabbit> rabbits;

    private int curLevel = 0;

    private Score score;

    public Game(List<Level> levels, List<NamedRabbit> rabbits) {
        this.levels = levels;
        this.rabbits = rabbits;
        this.score = new Score(rabbits);


        for (NamedRabbit rabbit : rabbits) {

        }
        Level level = levels.get(curLevel);

        Drawable.drawable.add(level.getBackGround());

        for (Wall wall : level.getWalls()) {
            Drawable.drawable.add(wall);
            Hitable.hitableObjects.add(wall);
        }

        Drawable.drawable.add(score);


    }


    public List<NamedRabbit> getRabbits() {
        return rabbits;
    }
}
