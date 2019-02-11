package my.game;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Game {

    private List<Level> levels;
    private List<NamedRabbit> rabbits;

    private int curLevel = 0;

    private Score score;

    private Consumer<String> gameOverListener;

    public Game(List<Level> levels, List<NamedRabbit> rabbits, Consumer<String> gameOverListener) {
        this.levels = levels;
        this.rabbits = rabbits;

        for (NamedRabbit rabbit : rabbits) {
            rabbit.addScoreListener(score -> {
                if (score >= levels.get(curLevel).getMaxScore()) {
                    if (levels.size() > curLevel + 1) {
                        loadLevel(++curLevel);
                    } else {
                        gameOverListener.accept("Score: " + rabbits.stream()
                                .map(r -> r.getName() + ": " + r.getScore()).collect(Collectors.joining("; ")));
                    }
                }
            });
        }

        this.score = new Score(rabbits);
        this.gameOverListener = gameOverListener;
        loadLevel(curLevel);


    }

    public void loadLevel(int levelNo) {
        Drawable.drawables.clear();
        for (Hitable hitable : Hitable.hitables) {
            if (hitable instanceof Rabbit) {
                ((Rabbit) hitable).respawn(false);
            }
        }
        Hitable.hitables.clear();

        Level level = levels.get(levelNo);
        Drawable.drawables.add(level.getBackGround());

        for (Wall wall : level.getWalls()) {
            Drawable.drawables.add(wall);
            Hitable.hitables.add(wall);
        }

        Drawable.drawables.add(score);
        for (NamedRabbit rabbit : rabbits) {
            Drawable.drawables.add(rabbit);
            Hitable.hitables.add(rabbit);
        }
    }

    public List<NamedRabbit> getRabbits() {
        return rabbits;
    }
}
