package my.game;

import java.util.List;


public class Level {

    private int maxScore;
    private BackGround backGround;
    private List<Wall> walls;


    public Level(int maxScore, BackGround backGround, List<Wall> walls) {
        this.maxScore = maxScore;
        this.backGround = backGround;
        this.walls = walls;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public BackGround getBackGround() {
        return backGround;
    }

    public List<Wall> getWalls() {
        return walls;
    }
}